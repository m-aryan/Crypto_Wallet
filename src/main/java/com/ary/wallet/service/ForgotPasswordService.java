package com.ary.wallet.service;

import com.ary.wallet.domain.VerificationType;
import com.ary.wallet.modal.ForgotPasswordToken;
import com.ary.wallet.modal.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(
            User user,
            String id,
            String otp,
            VerificationType verificationType,
            String sendTo
    );

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);

}
