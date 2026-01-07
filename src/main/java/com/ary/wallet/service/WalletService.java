package com.ary.wallet.service;

import com.ary.wallet.modal.User;
import com.ary.wallet.modal.Wallet;

public interface WalletService {
    Wallet getUserWallet(User user);

    Wallet addBalanceToWallet(Wallet wallet, Long money);

    Wallet findById(Long id);

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount);

    Wallet payOrderPayment(Wallet wallet, Long amount);
}