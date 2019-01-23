package com.sarawukl.gitlablinemgr.handler;

public class TokenNotFoundException extends Exception {

    Long notifyId;
    String message;

    public TokenNotFoundException(Long notifyId) {
        this.notifyId = notifyId;
    }

    @Override
    public String getMessage() {
        return String.format("Error! Notify ID '%s' Data Not Found", notifyId);
    }

}