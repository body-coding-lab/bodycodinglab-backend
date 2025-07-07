package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.trainer.TrainerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "trainers")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Trainer extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, name = "job_address")
    private String jobAddress;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "attachment_file_id")
    private UploadFile attachmentFile;

    @Column(name = "short_introduce")
    private String shortIntroduce;

    @Column(name = "long_introduce")
    private String longIntroduce;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private TrainerStatus trainerStatus;

    @Column(name = "education_name")
    private String educationName;

    @Column(name = "education_entrance")
    private String educationEntrance;

    @Column(name = "education_graduate")
    private String educationGraduate;

    @OneToMany(mappedBy = "trainers", cascade = CascadeType.ALL)
    private List<TrainerCareer> trainerCareers;

    @OneToMany(mappedBy = "trainers", cascade = CascadeType.ALL)
    private List<TrainerLicense> trainerLicenses;
}
