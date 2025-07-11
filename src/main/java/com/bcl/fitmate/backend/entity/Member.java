package com.bcl.fitmate.backend.entity;


import com.bcl.fitmate.backend.common.constants.ResponseCode;
import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.member.MemberStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long memberId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "member_address", nullable = false)
    private String memberAddress;

    @Min(0) @Max(3)
    @Column(name = "one_day_ticket_count", nullable = false)
    private int oneDayTicketCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_status", nullable = false)
    private MemberStatus status = MemberStatus.NOT_SUBSCRIPTION;

    @OneToOne(mappedBy = "member", cascade =  CascadeType.ALL)
    private MemberForm memberForm;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Subscription subscription;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Payment payment;


    @Builder
    public Member(User user, String memberAddress, MemberStatus status){
        this.user = user;
        this.memberAddress = memberAddress;
        this.status = status;
    }

    public void minusOneDayTicketCount() throws Exception{
        if(this.oneDayTicketCount <= 0){
            throw new Exception(ResponseMessage.NOT_TRIAL_CHANCE_LEFT);
        }
        this.oneDayTicketCount--;
    }

    public void plusOneDayTicketCount() {
        this.oneDayTicketCount++;
    }
}
