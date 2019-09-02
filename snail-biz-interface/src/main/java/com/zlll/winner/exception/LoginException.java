package com.zlll.winner.exception;

public class LoginException extends Exception {

    private String errorMsg;

    public LoginException(String errorMsg){
        super();
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
