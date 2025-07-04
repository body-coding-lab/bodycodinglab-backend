package com.bcl.fitmate.backend.common.constants;

public interface ApiMappingPattern {
    String AUTH_API = "/api/v2/auth";
    String ADMIN_API = "/api/v2/admins";
    String USER_API = "/api/v2/users";
    String TRAINER_API = "/api/v2/trainers";
    String COMMON_API = "/api/v2/common";
    String MEMBER_API = "/api/v2/members";
    String TRAINER_ONE_DAY_TICKET_API = TRAINER_API + "/me/one-day-tickets";
    String MEMBER_ONE_DAY_TICKET_API = MEMBER_API + "/me/one-day-tickets";
    String BOARD_API = "/api/v2/boards";
    String BOARD_COMMENT_API = BOARD_API + "/{boardId}/comments";
    String FILE_API = "/api/v2/files";
    String MEMBER_COUPON_API = MEMBER_API + "/me/coupons";
    String TRAINER_COUPON_API = TRAINER_API + "/me/coupons";
    String NOTE_API = "/api/v2/notes";
    String MEMBER_FORM_API = MEMBER_API + "/me/form";
    String MEMBER_MATCH_WAITING_LIST_API = MEMBER_API + "/me/match-waiting-list";
    String TRAINER_MATCH_WAITING_LIST_API = TRAINER_API + "/me/match-waiting-list";
    String MEMBER_MATCH_API = MEMBER_API + "/me/match-list";
    String TRAINER_MATCH_API = TRAINER_API + "/me/match-list";
    String MEMBER_SUBSCRIPTION_API = MEMBER_API + "/me/subscription";
    String PAYMENT_API = MEMBER_API + "/me/payment";
}