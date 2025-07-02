package com.bcl.fitmate.backend.common.constants;

public interface ResponseCode {
    String SUCCESS = "SU";
    String FAIL = "FA";
    String DATABASE_ERROR = "DBE";

    String LOGIN_FAIL = "SF";
    String AUTHENTICATION_FAIL = "AF";
    String AUTHORIZATION_FAIL = "AUF";
    String NO_PERMISSION = "NP";
    String TOKEN_CREATE_FAIL = "TCF";
    String TOKEN_EXPIRED = "TE";
    String INVALID_TOKEN = "IT";
    String MISSING_TOKEN = "MT";

    String VALIDATION_FAIL = "VF";
    String INVALID_INPUT = "IV";
    String REQUIRED_FIELD_MISSING = "RM";
    String FORMAT_ERROR = "FE";
    String NOT_MATCH_PASSWORD = "NMP";

    String DUPLICATED_USER_ID = "DUI";
    String DUPLICATED_EMAIL = "DE";
    String DUPLICATED_TEL_NUMBER = "DN";
    String NO_EXIST_USER_ID = "NI";
    String NO_EXIST_EMAIL = "NE";
    String USER_NOT_FOUND = "UNF";
    String USER_ALREADY_EXIST = "UAE";
    String NOT_CORRECT_PASSWORD = "NCP";
    String NOT_MATCH_INFORMATION = "NMI";
    String RESET_PASSWORD_FAIL = "RPF";
    String MEMBER_NOT_FOUND = "MNF";

    String NO_EXIST_TOOL = "NT";
    String NO_EXIST_CUSTOMER = "NC";
    String TOOL_INSUFFICIENT = "TI";
    String RESOURCE_NOT_FOUND = "RNF";

    String MAIL_AUTH_FAIL = "MAF";
    String MAIL_SEND_FAIL = "MF";
    String VERIFICATION_CODE_INVALID = "VCI";
    String VERIFICATION_CODE_EXPIRED = "VCE";

    String FILE_UPLOAD_FAIL = "FUF";
    String FILE_NOT_FOUND = "FNF";
    String FILE_NOT_ATTACHED = "FNA";

    String DATA_INTEGRITY_VIOLATION = "DIV";
    String CONSTRAINT_VIOLATION = "CV";
    String DUPLICATE_ENTRY = "DUP";

    String INTERNAL_SERVER_ERROR = "ISE";
    String SERVICE_UNAVAILABLE = "SUA";
    String REQUEST_TIMEOUT = "RT";

    String ALREADY_EQUAL_STATUS = "AES";
    String NOT_EXISTS_LICENSE = "NEL";
    String NOT_EXISTS_CAREER = "NEC";
    String TRAINER_NOT_FOUND = "TNF";

    String NOT_EXISTS_POST = "NEP";
    String COMMENT_NOT_BELONG_POST = "CNBP";
    String NOT_EXISTS_COMMENT = "NECM";

    String NOT_EXISTS_ONE_DAY_TICKET = "NET";
    String NOT_TRIAL_CHANCE_LEFT = "TCL";
    String INVALID_TICKET_STATUS = "ITS";
}
