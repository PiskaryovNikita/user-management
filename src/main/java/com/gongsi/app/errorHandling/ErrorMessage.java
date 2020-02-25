package com.gongsi.app.errorHandling;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//send back this entity to client
public class ErrorMessage {

    private String errorMessage;
    private int errorCode;

    public ErrorMessage() {
    }

    public ErrorMessage(int erorCode, String errorMessage) {
        this.errorCode = erorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
