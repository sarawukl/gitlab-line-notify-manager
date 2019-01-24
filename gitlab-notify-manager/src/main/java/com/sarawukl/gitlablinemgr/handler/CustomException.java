package com.sarawukl.gitlablinemgr.handler;

public class CustomException extends Exception {

    String error;

    public CustomException(String error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error;
    }
}