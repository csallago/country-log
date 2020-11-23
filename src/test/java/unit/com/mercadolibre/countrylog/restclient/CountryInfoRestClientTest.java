package unit.com.mercadolibre.countrylog.restclient;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.mercadolibre.countrylog.exception.IPNotFoundException;
import com.mercadolibre.countrylog.exception.ServiceNotAvailableException;
import com.mercadolibre.countrylog.repository.CountryInfoRepository;
import com.mercadolibre.countrylog.restclient.CountryInfoRestClient;
import com.mercadolibre.countrylog.restclient.dto.CountryInfo;
import com.mercadolibre.countrylog.restclient.dto.ExchangeRates;
import com.mercadolibre.countrylog.restclient.dto.Ip2Country;
import com.mercadolibre.countrylog.restclient.impl.CountryInfoRestClientImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CountryInfoRestClientTest {
    
    @Test
    public void test_GetCountryCodeByIp_Success() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_IP2COUNTRY = "https://api.ip2country.info/ip?%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String ip = "5.6.7.8";
        Ip2Country response = new Ip2Country();
        response.setCountryCode("DE");
        response.setCountryCode3("DEU");
        response.setCountryName("Germany");
        when(rt.getForObject(String.format(URL_IP2COUNTRY, ip), Ip2Country.class)).thenReturn(response);
        assertEquals(response, client.getCountryCodeByIp(ip));
    }

    @Test
    public void test_GetCountryCodeByIp_Invalid_IP() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_IP2COUNTRY = "https://api.ip2country.info/ip?%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String ip = "555.6.7.8";
        Ip2Country response = new Ip2Country();
        response.setCountryCode("");
        when(rt.getForObject(String.format(URL_IP2COUNTRY, ip), Ip2Country.class)).thenReturn(response);
        assertThrows(IPNotFoundException.class, ()->client.getCountryCodeByIp(ip));
    }

    @Test
    public void test_GetCountryCodeByIp_Service_Not_Available() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_IP2COUNTRY = "https://api.ip2country.info/ip?%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String ip = "555.6.7.8";
        Ip2Country response = new Ip2Country();
        response.setCountryCode("");
        when(rt.getForObject(String.format(URL_IP2COUNTRY, ip), Ip2Country.class)).thenThrow(new RuntimeException());
        assertThrows(ServiceNotAvailableException.class, ()->client.getCountryCodeByIp(ip));
    }

    @Test
    public void test_GetCountryInfoByCode_InCache() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String code = "DE";
        CountryInfo response = getCountryInfoObj();
        when(repo.findById(code)).thenReturn(Optional.of(response));
        
        assertEquals(response, client.getCountryInfoByCode(code));
    }

    @Test
    public void test_GetCountryInfoByCode_NotInCache() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_RESTCOUNTRY = "https://restcountries.eu/rest/v2/alpha/%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String code = "DE";
        CountryInfo response = getCountryInfoObj();
        when(repo.findById(code)).thenReturn(Optional.empty());
        
        when(rt.getForObject(String.format(URL_RESTCOUNTRY, code), CountryInfo.class))
            .thenReturn(response);

        assertEquals(response, client.getCountryInfoByCode(code));
    }

    @Test
    public void test_GetCountryInfoByCode_NotAvailableService() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_RESTCOUNTRY = "https://restcountries.eu/rest/v2/alpha/%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String code = "DE";
        
        when(repo.findById(code)).thenReturn(Optional.empty());
        
        when(rt.getForObject(String.format(URL_RESTCOUNTRY, code), CountryInfo.class))
            .thenThrow(new RuntimeException());

        assertThrows(ServiceNotAvailableException.class, ()->client.getCountryInfoByCode(code));
    }

    @Test
    public void test_GetExchangeRateToUSD_Success() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_EXCHANGE_RATES_API = "https://api.exchangeratesapi.io/latest?base=%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String code = "DE";
        ExchangeRates ex = new ExchangeRates();
        ex.setBase("DE");
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR",Double.valueOf(1.2));
        rates.put("USD",Double.valueOf(1.2));
        ex.setRates(rates);
        when(rt.getForObject(String.format(URL_EXCHANGE_RATES_API, code), ExchangeRates.class))
            .thenReturn(ex);

        assertEquals(Double.valueOf(1.2), client.getExchangeRateToUSD(code));
    }

    @Test
    public void test_GetExchangeRateToUSD_USD_NOT_PRESENT() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_EXCHANGE_RATES_API = "https://api.exchangeratesapi.io/latest?base=%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String code = "DE";
        ExchangeRates ex = new ExchangeRates();
        ex.setBase("DE");
        Map<String, Double> rates = new HashMap<>();
        rates.put("EUR",Double.valueOf(1.2));
        ex.setRates(rates);
        when(rt.getForObject(String.format(URL_EXCHANGE_RATES_API, code), ExchangeRates.class))
            .thenReturn(ex);

        assertEquals(Double.NaN, client.getExchangeRateToUSD(code));
    }

    @Test
    public void test_GetExchangeRateToUSD_NotAvailableService() {
    
        RestTemplate rt = Mockito.mock(RestTemplate.class);
        CountryInfoRepository repo = Mockito.mock(CountryInfoRepository.class);
        String URL_EXCHANGE_RATES_API = "https://api.exchangeratesapi.io/latest?base=%s";

        CountryInfoRestClient client = new CountryInfoRestClientImpl(repo, rt);
        String code = "DE";
        
        when(rt.getForObject(String.format(URL_EXCHANGE_RATES_API, code), ExchangeRates.class))
            .thenThrow(new RuntimeException());

        assertEquals(Double.NaN, client.getExchangeRateToUSD(code));
    }

    private CountryInfo getCountryInfoObj() {
        CountryInfo ci = new CountryInfo();
        ci.setAlpha2Code("DE");
        ci.setAlpha3Code("DES");
        ci.setCurrencies(null);
        ci.setName("Germany");
        return ci;
    }
}
