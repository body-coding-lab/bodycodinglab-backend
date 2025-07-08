package com.bcl.fitmate.backend.dto.memberForm.response;

import com.bcl.fitmate.backend.common.enums.memberForm.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetMemberFormResponseDto {
    private Long memberId;
    private String memberName;
    private BodyForm bodyForm;
    private Goal goal;
    private Bmi bmi;
    private Improved_part improvedPart;
    private PreferredDiet preferredDiet;
    private SugarIntake sugarIntake;
    private WaterIntake waterIntake;
    private Short height;
    private Short weight;
    private Short weightGoal;
    private Short physicalLevel;
    private ExercisingProblem exercisingProblem;
    private PushupLevel pushupLevel;
    private PullupLevel pullupLevel;
    private ExerciseFrequency exerciseFrequency;
    private InvestableTime investableTime;
}
