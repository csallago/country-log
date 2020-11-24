package integration.com.mercadolibre.countrylog.controller;

import com.mercadolibre.countrylog.CountryLogApplication;
import com.mercadolibre.countrylog.controller.CountryLogController;
import com.mercadolibre.countrylog.service.CountryInfoService;
import com.mercadolibre.countrylog.service.CountryStatsService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

@SpringBootTest(classes = CountryLogApplication.class)
public class CountryLogControllerTest {

    @InjectMocks
    CountryLogController controller;

    @Mock
    CountryInfoService infoService;

    @Mock
    CountryStatsService statsService;

    @Test
    public void test_GetAverageDistanceCountry_Success() throws Exception 
    {
       
    }
}
