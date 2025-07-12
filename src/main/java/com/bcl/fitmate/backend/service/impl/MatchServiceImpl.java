package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetMemberMatchResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchListResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.response.GetMemberFormResponseDto;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.repository.*;
import com.bcl.fitmate.backend.service.MatchService;
import com.bcl.fitmate.backend.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UploadFileService uploadFileService;
    private final PaymentRepository paymentRepository;
    private final MemberFormRepository memberFormRepository;

    @Override
    public ResponseDto<GetMemberMatchResponseDto> getMemberMatch(Long userId) {
        GetMemberMatchResponseDto response = null;

        User member = userRepository.findById(userId).orElse(null);

        if(member == null){
            return ResponseDto.fail(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
        }

        if(member.getMemberMatch() == null){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        String profileImageUrl = null;


        response = new GetMemberMatchResponseDto(
                member.getMemberMatch().getId(),
                member.getMemberMatch().getTrainer().getId(),
                profileImageUrl,
                member.getMemberMatch().getTrainer().getName(),
                member.getMemberMatch().getMatchedAt(),
                member.getMemberMatch().getTrainer().getTrainer().getJobAddress()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional
    public ResponseDto<Void> cancelMatch(Long userId, Long matchId) {
       Match match = matchRepository.findById(matchId).orElse(null);

       if(match == null){
           return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
       }

       User member = userRepository.findById(userId).orElse(null);

       if(member == null){
           return ResponseDto.fail(ResponseCode.MEMBER_NOT_FOUND, ResponseMessage.MEMBER_NOT_FOUND);
       }

       User trainer = userRepository.findById(match.getTrainer().getId()).orElse(null);

       if(trainer == null){
           return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
       }

       member.setMemberMatch(null);
       trainer.removeTrainerMatches(match);
       matchRepository.delete(match);

       Subscription subscription = match.getMember().getMember().getSubscription();

       member.getMember().setSubscription(null);
       subscriptionRepository.delete(subscription);

       Payment payment = match.getMember().getMember().getPayment();
       member.getMember().setPayment(null);
       paymentRepository.delete(payment);

       MemberForm memberForm = match.getMember().getMember().getMemberForm();
       if(memberForm != null){
           member.getMember().setMemberForm(null);
           memberFormRepository.delete(memberForm);
       }

       return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<List<GetTrainerMatchListResponseDto>> getTrainerMatchList(Long userId) {
        List<GetTrainerMatchListResponseDto> matchList = null;

        User trainer = userRepository.findById(userId).orElse(null);

        if(trainer == null){
            return ResponseDto.fail(ResponseCode.TRAINER_NOT_FOUND, ResponseMessage.TRAINER_NOT_FOUND);
        }

        if(trainer.getMemberMatch() == null){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        matchList = trainer.getTrainerMatches().stream()
                .map(match -> new GetTrainerMatchListResponseDto(
                        match.getId(),
                        match.getMember().getId(),
                        match.getMember().getName(),
                        match.getMember().getGender()
                )).toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseDto<GetTrainerMatchResponseDto> getTrainerMatch(Long userId, Long matchId) {
        GetTrainerMatchResponseDto response = null;
        Match match = matchRepository.findById(matchId).orElse(null);

        if(match == null){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        if(!match.getTrainer().getId().equals(userId)){
            return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH_PERMISSION, ResponseMessage.NOT_EXISTS_MATCH_PERMISSION);
        }

        LocalDate birthdate = match.getMember().getBirthdate();
        int age = Period.between(birthdate, LocalDate.now()).getYears();

        String profileImageUrl = null;

        if(match.getMember().getMember().getMemberForm() != null){
            GetMemberFormResponseDto memberFormResponseDto = new GetMemberFormResponseDto(
                    match.getMember().getMember().getMemberId(),
                    match.getMember().getName(),
                    match.getMember().getMember().getMemberForm().getBodyForm(),
                    match.getMember().getMember().getMemberForm().getGoal(),
                    match.getMember().getMember().getMemberForm().getBmi(),
                    match.getMember().getMember().getMemberForm().getImprovedPart(),
                    match.getMember().getMember().getMemberForm().getPreferredDiet(),
                    match.getMember().getMember().getMemberForm().getSugarIntake(),
                    match.getMember().getMember().getMemberForm().getWaterIntake(),
                    match.getMember().getMember().getMemberForm().getHeight(),
                    match.getMember().getMember().getMemberForm().getWeight(),
                    match.getMember().getMember().getMemberForm().getWeightGoal(),
                    match.getMember().getMember().getMemberForm().getPhysicalLevel(),
                    match.getMember().getMember().getMemberForm().getExercisingProblem(),
                    match.getMember().getMember().getMemberForm().getPushupLevel(),
                    match.getMember().getMember().getMemberForm().getPullupLevel(),
                    match.getMember().getMember().getMemberForm().getExerciseFrequency(),
                    match.getMember().getMember().getMemberForm().getInvestableTime()
            );

            response = new GetTrainerMatchResponseDto(
                    profileImageUrl,
                    match.getMember().getName(),
                    age,
                    match.getMember().getGender(),
                    match.getMember().getPhone(),
                    match.getMember().getMember().getMemberAddress(),
                    memberFormResponseDto
            );

            return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        }else{
            response = new GetTrainerMatchResponseDto(
                    profileImageUrl,
                    match.getMember().getName(),
                    age,
                    match.getMember().getGender(),
                    match.getMember().getPhone(),
                    match.getMember().getMember().getMemberAddress()
            );
        }
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
