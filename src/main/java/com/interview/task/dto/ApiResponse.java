package com.interview.task.dto;

/**
 * Represents usual api response.
 */
public class ApiResponse {
    private boolean success;
    private String message;
    private boolean isMulticurrent;

    public ApiResponse() {
    }

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, boolean isMulticurrent) {
        this.success = success;
        this.message = message;
        this.isMulticurrent = isMulticurrent;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMulticurrent() {
        return isMulticurrent;
    }

    public void setMulticurrent(boolean multicurrent) {
        isMulticurrent = multicurrent;
    }
}
