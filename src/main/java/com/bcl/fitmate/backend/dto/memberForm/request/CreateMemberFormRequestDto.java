package com.bcl.fitmate.backend.dto.memberForm.request;

import com.bcl.fitmate.backend.common.enums.memberForm.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;



@Getter
@Valid
public class CreateMemberFormRequestDto {
    @NotNull(message = "체형은 필수 입력란입니다.")
    private BodyForm bodyForm;
    @NotNull(message = "목표는 필수 입력란입니다.")
    private Goal goal;
    @NotNull(message = "BMI는 필수 입력란입니다.")
    private Bmi bmi;
    @NotNull(message = "향상시키고 싶은 부위는 필수 입력란입니다.")
    private Improved_part improvedPart;
    @NotNull(message = "따르는 식단은 필수 입력란입니다.")
    private PreferredDiet preferredDiet;
    @NotNull(message = "당 섭취 빈도는 필수 입력란입니다.")
    private SugarIntake sugarIntake;
    @NotNull(message = "수분 섭취 빈도는 필수 입력란입니다.")
    private WaterIntake waterIntake;
    @NotNull(message = "신장은 필수 입력란입니다.")
    private Short height;
    @NotNull(message = "현재 체중은 필수 입력란입니다.")
    private Short weight;
    @NotNull(message = "목표 체중은 필수 입력란입니다.")
    private Short weightGoal;
    @NotNull(message = "신체능력 점수는 필수 입력란입니다.")
    private Short physicalLevel;
    @NotNull(message = "운동 시 겪었던 문제는 필수 입력란입니다.")
    private ExercisingProblem exercisingProblem;
    @NotNull(message = "팔굽혀펴기 갯수는 필수 입력란입니다.")
    private PushupLevel pushupLevel;
    @NotNull(message = "턱걸이 갯수는 필수 입력란입니다.")
    private PullupLevel pullupLevel;
    @NotNull(message = "운동 빈도는 필수 입력란입니다.")
    private ExerciseFrequency exerciseFrequency;
    @NotNull(message = "운동 투자 가능 시간 여부는 필수 입력란입니다.")
    private InvestableTime investableTime;
}
