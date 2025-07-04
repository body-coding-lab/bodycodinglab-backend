package com.bcl.fitmate.backend.common.constants;

public interface Regex {
    String USER_NAME = "^[a-zA-Z][a-zA-Z0-9]{5,12}$";
    String PASSWORD =  "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$";
    String NAME_KOREAN = "^[가-힣]{2,10}$";
    String PHONE = "^(01[0-9]{1})-([0-9]{3,4})-([0-9]{4})$";
    String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
}
