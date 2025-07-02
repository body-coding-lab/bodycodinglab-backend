package com.bcl.fitmate.backend.common.constants;

public interface ApiMappingPattern {
    String AUTH_API = "/api/v2/auth";
    String ADMIN_API = "/api/v2/admins";
    String USER_API = "/api/v2/users";
    String TRAINER_API = "/api/v2/trainers";
    String COMMON_API = "/api/v2/common";
    String TRAINER_ONE_DAY_TICKET_API = TRAINER_API + "/me/one-day-tickets";
    String MEMBER_ONE_DAY_TICKET_API = MEMBER_API + "/me/one-day-tickets";
    String BOARD_API = "/api/v2/boards";
    String BOARD_COMMENT_API = BOARD_API + "/{boardId}/comments";
    String FILE_API = "/api/v2/files";
}