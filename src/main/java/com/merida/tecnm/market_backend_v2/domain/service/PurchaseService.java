package com.merida.tecnm.market_backend_v2.domain.service;

import com.merida.tecnm.market_backend_v2.domain.Purchase;
import com.merida.tecnm.market_backend_v2.domain.repository.PurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> getAll(){
        return purchaseRepository.getAll();
    }

    public Optional<List<Purchase>> getByClient(String clientId){
        return purchaseRepository.getByClient(clientId);
    }

    @Transactional
    public Purchase save(Purchase purchase){
        return purchaseRepository.save(purchase);
    }

}
