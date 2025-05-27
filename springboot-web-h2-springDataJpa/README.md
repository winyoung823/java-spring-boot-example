@@@@@架構<br>
java 1.8<br>
spring-boot 2.7.18<br>
spring-data-jpa<br>
H2 DB<br>
db schema放在/src/resources/schema.sql<br>
db data放在/src/resources/data.sql<br>
@@@@@api<br>
#1.幣別資料 CRUD API（Controller）<br>
路徑：/api/currencies<br>
GET /api/currencies：查詢全部幣別<br>
GET /api/currencies/{code}：查詢單一幣別<br>
POST /api/currencies：新增<br>
PUT /api/currencies/{code}：修改<br>
DELETE /api/currencies/{code}：刪除<br>
<br>
#2.coindesk資料 <br>
路徑：/api/coindesk<br>
GET /api/coindesk：取得coindesk api資料<br>
GET /api/coindesk/converted：取得coindesk api資料並轉換<br>
<br>
@@@@@單元測試<br>
#1.CathayApplicationTests <br>
api呼叫測試<br>
日期轉換測試<br>
db資料取得測試<br>
<br>
#2.CurrencyControllerTest<br>
testGetAllCurrencies(TestInfo) 回傳內容：[{"code":"USD","chineseName":"美元"}]<br>
testCreateCurrency(TestInfo) 回傳內容：{"code":"EUR","chineseName":"歐元"}<br>
testGetCurrency(TestInfo) 回傳內容：{"code":"USD","chineseName":"美元"}<br>
testUpdateCurrency(TestInfo) 回傳內容：{"code":"USD","chineseName":"美金"}<br>
testDeleteCurrency(TestInfo) 回傳內容：<br>
<br>

#3.CoindeskControllerTest<br>
testGetCoindeskApi(TestInfo) 回傳內容：{"time":{"updated":"Sep 2, 2024 07:07:20 UTC","updatedISO":"2024-09-02T07:07:20+00:00","updateduk":"Sep 2, 2024 at 08:07 BST"},"disclaimer":"just for test","chartName":"Bitcoin","bpi":{"USD":{"code":"USD","symbol":"&#36;","rate":"57,756.298","description":"United States Dollar","rate_float":57756.2984},"GBP":{"code":"GBP","symbol":"&pound;","rate":"43,984.02","description":"British Pound Sterling","rate_float":43984.0203},"EUR":{"code":"EUR","symbol":"&euro;","rate":"52,243.287","description":"Euro","rate_float":52243.2865}}}<br>
testGetConverted(TestInfo) 回傳內容：{"updatedTime":"2024/09/02 15:07:20","currencyList":[{"code":"USD","rate":57756.2984,"chineseName":"美元"},{"code":"GBP","rate":43984.0203,"chineseName":"英鎊"},{"code":"EUR","rate":52243.2865,"chineseName":"歐元"}]}<br>
