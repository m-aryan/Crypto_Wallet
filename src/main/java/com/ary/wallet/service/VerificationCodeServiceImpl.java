package com.ary.wallet.service;

import com.ary.wallet.domain.VerificationType;
import com.ary.wallet.modal.User;
import com.ary.wallet.modal.VerificationCode;
import com.ary.wallet.repository.VerificationCodeRepository;
import com.ary.wallet.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;


    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
        VerificationCode verificationCode1 = new VerificationCode();
        verificationCode1.setUser(user);
        verificationCode1.setOtp(OtpUtils.generateOTP());
        verificationCode1.setVerificationType(verificationType);

        return verificationCodeRepository.save(verificationCode1);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {

        Optional<VerificationCode> verificationCode = verificationCodeRepository.findById(id);

        if(verificationCode.isPresent()){
            return  verificationCode.get();
        }

        throw new Exception("verification code not found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(Long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }
}
