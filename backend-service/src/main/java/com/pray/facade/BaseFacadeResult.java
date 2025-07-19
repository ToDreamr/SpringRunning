package com.pray.facade;


/**
 * <p>
 * BaseFacadeResult
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 16:50
 */
public class BaseFacadeResult {

    private boolean success;

    private String message;

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

    public BaseFacadeResult(boolean success,String message) {
        this.message = message;
        this.success = success;
    }

    public BaseFacadeResult(boolean success) {
        this.success = success;
    }

    public BaseFacadeResult(String message) {
        this.message = message;
    }
}
