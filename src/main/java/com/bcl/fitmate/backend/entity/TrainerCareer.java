package com.bcl.fitmate.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "trainer_careers")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainerCareer extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, name = "company_join")
    private LocalDate companyJoin;

    @Column(nullable = false, name = "company_quit")
    private LocalDate companyQuit;

    public static TrainerCareer create(Trainer trainer, String companyName,
                                       LocalDate companyJoin, LocalDate companyQuit) {
        TrainerCareer career = new TrainerCareer();
        career.trainer = trainer;
        career.companyName = companyName;
        career.companyJoin = companyJoin;
        career.companyQuit = companyQuit;
        return career;
    }

}
