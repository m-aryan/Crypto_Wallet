package com.ary.wallet.modal;

import com.ary.wallet.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private String otp;
    private String email;
    private String mobile;
    private VerificationType verificationType;
}
