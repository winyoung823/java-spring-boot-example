package com.example.cathay;

import com.example.cathay.model.Currency;
import com.example.cathay.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

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
    public void testGetAllCurrencies(TestInfo testInfo) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code", is("USD")))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(testInfo.getDisplayName() + " 回傳內容：" + responseBody);
    }

    @Test
    public void testGetCurrency(TestInfo testInfo) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/currencies/USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("USD")))
                .andExpect(jsonPath("$.chineseName", is("美元")))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(testInfo.getDisplayName() + " 回傳內容：" + responseBody);
    }

    @Test
    public void testCreateCurrency(TestInfo testInfo) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"EUR\", \"chineseName\":\"歐元\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("EUR")))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(testInfo.getDisplayName() + " 回傳內容：" + responseBody);
    }

    @Test
    public void testUpdateCurrency(TestInfo testInfo) throws Exception {
        MvcResult result = mockMvc.perform(put("/api/currencies/USD")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\":\"USD\", \"chineseName\":\"美金\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chineseName", is("美金")))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(testInfo.getDisplayName() + " 回傳內容：" + responseBody);
            
    }

    @Test
    public void testDeleteCurrency(TestInfo testInfo) throws Exception {
        MvcResult result = mockMvc.perform(delete("/api/currencies/USD"))
                .andExpect(status().isNoContent())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(testInfo.getDisplayName() + " 回傳內容：" + responseBody);
    }
}
