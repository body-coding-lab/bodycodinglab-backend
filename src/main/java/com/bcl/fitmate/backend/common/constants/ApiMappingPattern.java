package com.bcl.fitmate.backend.common.constants;

public interface ApiMappingPattern {
    String AUTH_API = "/api/v2/auth";
    String ADMIN_API = "/api/v2/admins";
    String USER_API = "/api/v2/users";
    String COMMON_API = "/api/v2/common";
    String MEMBER_ONE_DAY_TICKET_API = MEMBER_API + "/me/one-day-tickets";
    String BOARD_COMMENT_API = BOARD_API + "/{boardId}/comments";
    String FILE_API = "/api/v2/files";
}