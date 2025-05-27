package com.example.cathay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.client.RestTemplate;

import com.example.cathay.model.Currency;
import com.example.cathay.repository.CurrencyRepository;
import com.example.cathay.util.DateUtils;

@DataJpaTest
class CathayApplicationTests {

	@Test
	void testGetCoindeskApi() {
		//取得資料測試
		RestTemplate restTemplate = new RestTemplate();
        String url = "https://kengp3.github.io/blog/coindesk.json";
        String result = restTemplate.getForObject(url, String.class);
      	// Assert
        assertNotNull(result);
		assertTrue(result.contains("time"));
        assertTrue(result.contains("bpi"));
	}

	@Test
	void testFormatIsoDate() {
		//日期轉換測試
		String formateDate= DateUtils.formatDate("2024-05-27T00:03:00+00:00");
		
        // Assert
		assertEquals("2024/05/27 08:03:00", formateDate);
	}

	@Autowired
    private CurrencyRepository currencyRepository;
	@Test
	void testGetAllCurrencies() {
		//DB資料取得測試
        List<Currency> all = currencyRepository.findAll();

        // Assert
        assertEquals(3, all.size());
        assertEquals("USD", all.get(0).getCode());
        assertEquals("美元", all.get(0).getChineseName());
	}

}
