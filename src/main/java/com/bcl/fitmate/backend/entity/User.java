package com.bcl.fitmate.backend.entity;

import com.bcl.fitmate.backend.common.enums.user.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "profile_image_id")
    private UploadFile profileImage;

//    @OneToOne(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private Trainer trainer;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Member member;

    @OneToOne(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private MatchWaitingList matchWaitingListAsMember;

    @OneToMany(
            mappedBy = "trainer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MatchWaitingList> matchWaitingListAsTrainers = new ArrayList<>();

    @OneToOne(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Match memberMatch;

    @OneToMany(
            mappedBy = "trainer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Match> trainerMatches = new ArrayList<>();

    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Coupon> memberCoupons = new ArrayList<>();

    @OneToMany(
            mappedBy = "trainer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Coupon> trainerCoupons = new ArrayList<>();

    @OneToMany(
            mappedBy = "member",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OneDayTicket> memberOneDayTickets = new ArrayList<>();

    @OneToMany(
            mappedBy = "trainer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OneDayTicket> trainerOneDayTickets = new ArrayList<>();

    public void addMatchWaitingListAsTrainers(MatchWaitingList matchWaitingList) {
        matchWaitingListAsTrainers.add(matchWaitingList);
    }

    public void removeMatchWaitingListAsTrainers(MatchWaitingList matchWaitingList) {
        matchWaitingListAsTrainers.remove(matchWaitingList);
    }

    public void addTrainerMatches(Match match) {
        trainerMatches.add(match);
    }

    public void removeTrainerMatches(Match match) {
        trainerMatches.remove(match);
    }
}
