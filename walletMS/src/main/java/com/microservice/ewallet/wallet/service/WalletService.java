package com.microservice.ewallet.wallet.service;

import com.microservice.ewallet.wallet.domain.Wallet;
import com.microservice.ewallet.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public void createWallet(Wallet wallet){
        walletRepository.save(wallet);
    }

}

