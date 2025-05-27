package com.example.cathay.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.cathay.model.Currency;
import com.example.cathay.repository.CurrencyRepository;
import com.example.cathay.util.DateUtils;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {
    @Autowired
    private CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private String coindesk_url="https://kengp3.github.io/blog/coindesk.json";
    // 取得https://kengp3.github.io/blog/coindesk.json資料
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCoindesk() {
        ResponseEntity<Map> response = restTemplate.getForEntity(coindesk_url, Map.class);
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/converted")
    public ResponseEntity<Map<String, Object>> getConvertedCoindesk() {
        Map<String, Object> response = restTemplate.getForObject(coindesk_url, Map.class);
        if (response == null || !response.containsKey("bpi")) {//資料未包含幣別
            return ResponseEntity.status(502).body(Collections.singletonMap("error", "Invalid Coindesk response"));
        }

        // 取得更新時間
        Map<String, Object> timeMap = (Map<String, Object>) response.get("time");
        String updatedISO = (String) timeMap.get("updatedISO");
        String formattedTime =  DateUtils.formatDate(updatedISO);

        // 幣別資訊解析
        Map<String, Map<String, Object>> bpi = (Map<String, Map<String, Object>>) response.get("bpi");
        List<Map<String, Object>> convertedList = new ArrayList<>();

        for (Map.Entry<String, Map<String, Object>> entry : bpi.entrySet()) {
            String code = entry.getKey();
            Map<String, Object> info = entry.getValue();
            double rate = Double.parseDouble(info.get("rate_float").toString());

            // 從資料庫查中文名稱
            Optional<Currency> currency = currencyRepository.findById(code);
            String chineseName = currency.map(Currency::getChineseName).orElse("未知");

            Map<String, Object> currencyMap = new HashMap<>();
            currencyMap.put("code", code);
            currencyMap.put("chineseName", chineseName);
            currencyMap.put("rate", rate);

            convertedList.add(currencyMap);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("updatedTime", formattedTime);
        result.put("currencyList", convertedList);

        return ResponseEntity.ok(result);
    }

     private String formatDate(String isoString) {
        try {
            //原時間格式
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
            Date date = isoFormat.parse(isoString);
            //新時間格式
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return targetFormat.format(date);
        } catch (Exception e) {
            return isoString;
        }
    }
}
