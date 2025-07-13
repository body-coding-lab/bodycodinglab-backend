package com.bcl.fitmate.backend.service.impl;

import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.dto.ResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.request.CreateMemberFormRequestDto;
import com.bcl.fitmate.backend.dto.memberForm.response.CreateMemberFormResponseDto;
import com.bcl.fitmate.backend.dto.memberForm.response.GetMemberFormResponseDto;
import com.bcl.fitmate.backend.entity.Member;
import com.bcl.fitmate.backend.entity.MemberForm;
import com.bcl.fitmate.backend.repository.MemberFormRepository;
import com.bcl.fitmate.backend.repository.MemberRepository;
import com.bcl.fitmate.backend.service.MemberFormService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberFormServiceImpl implements MemberFormService {

    private final MemberRepository memberRepository;
    private final MemberFormRepository memberFormRepository;


    @Override
    public Member getMemberByUserId(Long userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.MEMBER_NOT_FOUND));
    }


    @Override
    @Transactional
    public ResponseDto<CreateMemberFormResponseDto> createMemberForm(Long userId, CreateMemberFormRequestDto dto) {
        CreateMemberFormResponseDto response = null;

        Member member = getMemberByUserId(userId);

        MemberForm memberForm = new MemberForm(
                null,
                member,
                true,
                dto.getBodyForm(),
                dto.getGoal(),
                dto.getBmi(),
                dto.getImprovedPart(),
                dto.getPreferredDiet(),
                dto.getSugarIntake(),
                dto.getWaterIntake(),
                dto.getHeight(),
                dto.getWeight(),
                dto.getWeightGoal(),
                dto.getPhysicalLevel(),
                dto.getExercisingProblem(),
                dto.getPushupLevel(),
                dto.getPullupLevel(),
                dto.getExerciseFrequency(),
                dto.getInvestableTime()
        );

        member.setMemberForm(memberForm);
        memberFormRepository.save(memberForm);

        response = new CreateMemberFormResponseDto(memberForm.getFormId());
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetMemberFormResponseDto> getMemberForm(Long userId) {
        GetMemberFormResponseDto response = null;

        Member member = getMemberByUserId(userId);

        MemberForm memberForm = member.getMemberForm();


        response = new GetMemberFormResponseDto(
                memberForm.getMember().getMemberId(),
                memberForm.getMember().getUser().getName(),
                memberForm.getBodyForm(),
                memberForm.getGoal(),
                memberForm.getBmi(),
                memberForm.getImprovedPart(),
                memberForm.getPreferredDiet(),
                memberForm.getSugarIntake(),
                memberForm.getWaterIntake(),
                memberForm.getHeight(),
                memberForm.getWeight(),
                memberForm.getWeightGoal(),
                memberForm.getPhysicalLevel(),
                memberForm.getExercisingProblem(),
                memberForm.getPushupLevel(),
                memberForm.getPullupLevel(),
                memberForm.getExerciseFrequency(),
                memberForm.getInvestableTime()
        );

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, response);
    }
}
