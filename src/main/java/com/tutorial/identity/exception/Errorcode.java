package com.tutorial.identity.exception;

public enum Errorcode {
    USER_NOTFOUND(101, "User not found !"),
    USER_EXISTED(102, "Username existed !"),
    USERNAME_INVALID(103, "Username invalid !"),
    PASSWORD_INVALID(104, "Password invalid !"),
    ;
    private int code;
    private String message;

    Errorcode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
