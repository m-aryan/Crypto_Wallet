package com.ary.wallet.repository;

import com.ary.wallet.modal.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<Coin, String> {}
