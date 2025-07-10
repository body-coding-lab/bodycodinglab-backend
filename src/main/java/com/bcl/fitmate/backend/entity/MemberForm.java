package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.memberForm.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_forms")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MemberForm {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Long formId;

   @OneToOne
   @JoinColumn(name = "member_id", nullable = false)
   private Member member;

   @Column(name = "is_submit", nullable = false)
   private boolean isSubmit = false;

   @Enumerated(EnumType.STRING)
   @Column(name = "bodyform", nullable = false, length = 30)
   private BodyForm bodyForm;

   @Enumerated(EnumType.STRING)
   @Column(name = "goal", nullable = false, length = 30)
   private Goal goal;

   @Enumerated(EnumType.STRING)
   @Column(name = "bmi", nullable = false, length = 30)
   private Bmi bmi;

   @Enumerated(EnumType.STRING)
   @Column(name = "improved_part", nullable = false, length = 30)
   private Improved_part improvedPart;

   @Enumerated(EnumType.STRING)
   @Column(name = "preferred_diet", nullable = false, length = 30)
   private PreferredDiet preferredDiet;

   @Enumerated(EnumType.STRING)
   @Column(name = "sugar_intake", nullable = false, length = 30)
   private SugarIntake sugarIntake;

   @Enumerated(EnumType.STRING)
   @Column(name = "water_intake", nullable = false, length = 30)
   private WaterIntake waterIntake;

   @Column(name = "height", nullable = false)
   private short height;

   @Column(name = "weight", nullable = false)
   private short weight;

   @Column(name = "weight_goal", nullable = false)
   private short weightGoal;

   @Column(name = "physical_level", nullable = false)
   private short physicalLevel;

   @Enumerated(EnumType.STRING)
   @Column(name = "exericising_problem", nullable = false, length = 30)
   private ExercisingProblem exercisingProblem;

   @Enumerated(EnumType.STRING)
   @Column(name = "pushup_level", nullable = false, length = 30)
   private PushupLevel pushupLevel;

   @Enumerated(EnumType.STRING)
   @Column(name = "pullup_level", nullable = false, length = 30)
   private PullupLevel pullupLevel;

   @Enumerated(EnumType.STRING)
   @Column(name = "exercise_frequency", nullable = false, length = 30)
   private ExerciseFrequency exerciseFrequency;

   @Enumerated(EnumType.STRING)
   @Column(name = "investable_time", nullable = false, length = 30)
   private InvestableTime investableTime;
}
