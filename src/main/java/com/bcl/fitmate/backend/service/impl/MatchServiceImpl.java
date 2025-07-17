package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ApiMappingPattern;
import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.member.MemberStatus;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetMemberMatchResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchListResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetTrainerMatchResponseDto;
import com.bcl.fitmate.backend.dto.match.response.GetUserMatchListResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.response.GetMemberFormResponseDto;
import com.bcl.fitmate.backend.entity.*;
import com.bcl.fitmate.backend.repository.*;
import com.bcl.fitmate.backend.service.MatchService;
import com.bcl.fitmate.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final MemberFormRepository memberFormRepository;
    private final UserService userService;


    @Override
    public Match getMatchById(Long matchId) {
        return matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.NOT_EXISTS_MATCH));
    }

    @Override
    public ResponseDto<List<GetUserMatchListResponseDto>> getUserMatchList(Long userId) {
        List<GetUserMatchListResponseDto> response = null;

        User user = userService.getUserById(userId);

        if(user.getRole().getName() == UserRole.MEMBER){
           response = user.getMemberMatches().stream().
                        map(match -> {
                            LocalDate birthdate = match.getTrainer().getBirthdate();
                            int age = Period.between(birthdate, LocalDate.now()).getYears();

                            return new GetUserMatchListResponseDto(
                                    match.getId(),
                                    user.getRole().getName(),
                                    match.getTrainer().getName(),
                                    match.getTrainer().getGender(),
                                    age
                            );
                        }).toList();
        }else if(user.getRole().getName() == UserRole.TRAINER){
          response = user.getTrainerMatches().stream().
                        map(match -> {

                            LocalDate birthdate = match.getMember().getBirthdate();
                            int age = Period.between(birthdate, LocalDate.now()).getYears();

                            return new GetUserMatchListResponseDto(
                                match.getId(),
                                user.getRole().getName(),
                                match.getMember().getName(),
                                match.getMember().getGender(),
                                    age
                        );}).toList();
        }


        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetMemberMatchResponseDto> getMemberMatch(Long userId) {
        GetMemberMatchResponseDto response = null;

        User member = userService.getUserById(userId);

        Match match = null;

        for(Match existMatch : member.getMemberMatches()){
            if(existMatch.getIsMaintained()){
                match = existMatch;
            }
        }


        if(match == null){
           return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH, ResponseMessage.NOT_EXISTS_MATCH);
        }

        User trainer = userService.getUserById(match.getTrainer().getId());

        String profileImageUrl = null;
        UploadFile trainerProfileImage = trainer.getProfileImage();
        if(trainerProfileImage != null){
            profileImageUrl = ApiMappingPattern.FILE_API + "/single/" + trainerProfileImage.getId();
        }


        response = new GetMemberMatchResponseDto(
                match.getId(),
                match.getTrainer().getId(),
                profileImageUrl,
                match.getTrainer().getName(),
                match.getCreatedAt(),
                match.getTrainer().getTrainer().getJobAddress()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional
    public ResponseDto<Void> cancelMatch(Long userId, Long matchId) {
       Match match = getMatchById(matchId);


       User member = userService.getUserById(userId);


       for (Match memberMatch: member.getMemberMatches()){
           if(memberMatch.getIsMaintained()){
               memberMatch.setIsMaintained(false);
           }
       }

       Subscription subscription = match.getMember().getMember().getSubscription();
       member.getMember().setSubscription(null);
       subscriptionRepository.delete(subscription);
       member.getMember().setStatus(MemberStatus.NOT_SUBSCRIPTION);

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
    @Transactional(readOnly = true)
    public ResponseDto<List<GetTrainerMatchListResponseDto>> getTrainerMatchList(Long userId) {
        List<GetTrainerMatchListResponseDto> matchList = null;

        User trainer = userService.getUserById(userId);


        matchList = trainer.getTrainerMatches().stream()
                .filter(Match::getIsMaintained)
                .map(match -> new GetTrainerMatchListResponseDto(
                        match.getId(),
                        match.getMember().getId(),
                        match.getMember().getName(),
                        match.getMember().getGender()
                )).toList();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, matchList);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetTrainerMatchResponseDto> getTrainerMatch(Long userId, Long matchId) {
        GetTrainerMatchResponseDto response = null;
        Match match = getMatchById(matchId);


        if(!match.getTrainer().getId().equals(userId)){
          return ResponseDto.fail(ResponseCode.NOT_EXISTS_MATCH_PERMISSION, ResponseMessage.NOT_EXISTS_MATCH_PERMISSION);
        }

        User member = userService.getUserById(match.getMember().getId());

        String profileImageUrl = null;
        UploadFile memberProfileImage = member.getProfileImage();
        if(memberProfileImage != null){
            profileImageUrl = ApiMappingPattern.FILE_API + "/single/" + memberProfileImage.getId();
        }


        LocalDate birthdate = match.getMember().getBirthdate();
        int age = Period.between(birthdate, LocalDate.now()).getYears();



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

            return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
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
