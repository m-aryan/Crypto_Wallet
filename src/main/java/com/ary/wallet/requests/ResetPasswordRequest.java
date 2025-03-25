package com.ary.wallet.requests;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String otp;
    private String password;
}
