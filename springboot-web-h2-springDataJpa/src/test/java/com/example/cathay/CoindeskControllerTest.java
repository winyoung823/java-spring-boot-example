package com.example.cathay;

import com.example.cathay.model.Currency;
import com.example.cathay.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CoindeskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    public void setup() {
        currencyRepository.deleteAll();
        currencyRepository.save(new Currency("USD", "美元"));
        currencyRepository.save(new Currency("EUR", "歐元"));
        currencyRepository.save(new Currency("GBP", "英鎊"));
    }

    @Test
    public void testGetCoindeskApi() throws Exception {
        mockMvc.perform(get("/api/coindesk"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.time.updatedISO", not(emptyString())))
            .andExpect(jsonPath("$.bpi.USD.code", is("USD")))
            .andExpect(jsonPath("$.bpi.USD.rate", not(emptyString())));
    }

    @Test
    public void testGetConverted() throws Exception {
        mockMvc.perform(get("/api/coindesk/converted"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updatedTime", not(empty())))
                .andExpect(jsonPath("$.currencyList", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.currencyList[0].code", not(empty())))
                .andExpect(jsonPath("$.currencyList[0].chineseName", not(empty())));
    }
}
