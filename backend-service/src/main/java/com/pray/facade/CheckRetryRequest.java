package com.pray.facade;


/**
 * <p>
 * CheckRetryRequest
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/7/19 15:41
 */
public class CheckRetryRequest {

    private String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "CheckRetryRequest{" +
                "check='" + check + '\'' +
                '}';
    }
}
