package com.ary.wallet.controller;

import com.ary.wallet.domain.VerificationType;
import com.ary.wallet.modal.ForgotPasswordToken;
import com.ary.wallet.modal.User;
import com.ary.wallet.modal.VerificationCode;
import com.ary.wallet.requests.ForgotPasswordTokenRequest;
import com.ary.wallet.requests.ResetPasswordRequest;
import com.ary.wallet.response.ApiResponse;
import com.ary.wallet.response.AuthResponse;
import com.ary.wallet.service.EmailService;
import com.ary.wallet.service.ForgotPasswordService;
import com.ary.wallet.service.UserService;
import com.ary.wallet.service.VerificationCodeService;
import com.ary.wallet.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    // End-Point to get user profile
    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // End Point to send OTP
    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<>("Verification Otp Send Successfully", HttpStatus.OK);
    }

    // End Point to verify OTP
    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(
            @PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserProfileByJwt(jwt);
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if (isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(),
                    sendTo,
                    user
            );

            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Incorrect OTP");
    }

    // End Point to Send Forgot Password OTP to User
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(
            @RequestBody ForgotPasswordTokenRequest req) throws Exception {

        User user = userService.findUserByEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findByUser(user.getId());

        if (forgotPasswordToken == null) {
            forgotPasswordToken = forgotPasswordService.createToken(user, id, otp, req.getVerificationType(), req.getSendTo());
        }

        if (req.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), forgotPasswordToken.getOtp());
        }

        AuthResponse response = new AuthResponse();
        response.setSession(forgotPasswordToken.getId());
        response.setMessage("Password Reset OTP Sent Successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // End Point to Verify OTP and Reset the Password
    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(
            @RequestParam String id,
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);

        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());

        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse response = new ApiResponse();
            response.setMessage("Password Update Successfully");

            return  new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        throw new Exception("Incorrect OTP");
    }
}
