package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.trainer.LicenseType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trainer_licenses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrainerLicense extends BaseTime {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(nullable = false, name = "license_type")
    @Enumerated(EnumType.STRING)
    private LicenseType licenseType;

    @Column(nullable = false, name = "license_name")
    private String licenseName;
}
