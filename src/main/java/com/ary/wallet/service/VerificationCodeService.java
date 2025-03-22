package com.ary.wallet.service;

import com.ary.wallet.domain.VerificationType;
import com.ary.wallet.modal.User;
import com.ary.wallet.modal.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
