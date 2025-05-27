package com.example.cathay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cathay.model.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
    
    Currency findByChineseName(String chineseName);
}
