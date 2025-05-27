package com.example.cathay.controller;

import com.example.cathay.model.Currency;
import com.example.cathay.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    
     @Autowired
    private CurrencyRepository currencyRepository;

    // 取得所有幣別
    @GetMapping
    public List<Currency> getAllCurrencies() {
        List<Currency> result=currencyRepository.findAll();
        System.out.println(result);
        return result;
    }

    // 取得單一幣別
    @GetMapping("/{code}")
    public ResponseEntity<Currency> getCurrencyByCode(@PathVariable String code) {
        Optional<Currency> currency = currencyRepository.findById(code);
        return currency.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // 新增幣別
    @PostMapping
    public ResponseEntity<Currency> createCurrency(@RequestBody Currency currency) {
        if (currencyRepository.existsById(currency.getCode())) {
            return ResponseEntity.badRequest().body(null); // 已存在則回傳錯誤
        }
        Currency saved = currencyRepository.save(currency);
        return ResponseEntity.ok(saved);
    }

    // 修改幣別
    @PutMapping("/{code}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable String code, @RequestBody Currency updatedCurrency) {
        return currencyRepository.findById(code)
            .map(existing -> {
                existing.setChineseName(updatedCurrency.getChineseName());
                return ResponseEntity.ok(currencyRepository.save(existing));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // 刪除幣別
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable String code) {
        if (!currencyRepository.existsById(code)) {
            return ResponseEntity.notFound().build();
        }
        currencyRepository.deleteById(code);
        return ResponseEntity.noContent().build();
    }
}
