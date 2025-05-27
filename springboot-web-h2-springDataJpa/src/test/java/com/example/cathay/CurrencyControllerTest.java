package com.example.cathay;

import com.example.cathay.model.Currency;
import com.example.cathay.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    public void setup() {
        currencyRepository.deleteAll();
        currencyRepository.save(new Currency("USD", "美元"));
    }

    @Test
    public void testGetAllCurrencies() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code", is("USD")));
    }

    @Test
    public void testCreateCurrency() throws Exception {
        mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"EUR\", \"chineseName\":\"歐元\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("EUR")));
    }

    @Test
    public void testUpdateCurrency() throws Exception {
        mockMvc.perform(put("/api/currencies/USD")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"USD\", \"chineseName\":\"美金\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chineseName", is("美金")));
    }

    @Test
    public void testDeleteCurrency() throws Exception {
        mockMvc.perform(delete("/api/currencies/USD"))
                .andExpect(status().isNoContent());
    }
}
