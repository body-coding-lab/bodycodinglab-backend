package com.bcl.fitmate.backend.dto.match.response;

import com.bcl.fitmate.backend.common.enums.user.Gender;
import com.bcl.fitmate.backend.dto.memberForm.response.GetMemberFormResponseDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class GetTrainerMatchResponseDto {
    private String profileImageUrl;
    private String memberName;
    private int memberAge;
    private Gender memberGender;
    private String memberPhone;
    private String memberAddress;
    private GetMemberFormResponseDto memberFormResponseDto;

    public GetTrainerMatchResponseDto(
            String profileImageUrl,
            String memberName,
            int MemberAge,
            Gender memberGender,
            String memberPhone,
            String memberAddress
    ){
        this.profileImageUrl = profileImageUrl;
        this.memberName = memberName;
        this.memberAge = MemberAge;
        this.memberGender = memberGender;
        this.memberPhone = memberPhone;
        this.memberAddress = memberAddress;
    }
}
