package be.albatroz.javacase.integration;

import be.albatroz.javacase.controller.CompanyController;
import be.albatroz.javacase.entity.Company;
import be.albatroz.javacase.repository.CompanyRepository;
import be.albatroz.javacase.resource.CompanyResource;
import be.albatroz.javacase.resource.mapper.CompanyMapper;
import be.albatroz.javacase.service.CompanyService;
import be.albatroz.javacase.util.Json;
import be.albatroz.javacase.validator.CompanyResourceValidator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@Import({CompanyService.class, CompanyController.class, RandomObjects.class, CompanyResourceValidator.class})
class CompanyIntegrationTest {

    @Setter(onMethod_ = {@Autowired})
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @MockBean
    private CompanyRepository companyRepository;

    @Setter(onMethod_ = {@Autowired})
    private CompanyService companyService;

    @Setter(onMethod_ = {@Autowired})
    private RandomObjects randomObjects;


    @Test
    void getCompany() throws Exception {
        log.error("http://localhost:" + port);
        mockMvc.perform(get("/company")
                .header("Origin", "http://localhost:" + port)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void postCompany() throws Exception {

        log.info(mockMvc.getDispatcherServlet().getServletContext().getServerInfo());

        Company company = randomObjects.createCompany();
        given(companyRepository.getOne(company.getId())).willReturn(company);
        given(companyService.save(company)).willReturn(company);

        String response = mockMvc.perform(post("/company")
                .header("Origin", "http://localhost:" + port)
                .contentType("application/json")
                .content(Json.objectToJsonString(CompanyMapper.INSTANCE.toResource(company))))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CompanyResource returnedCompany = Json.jsonStringToObject(response, CompanyResource.class);
        Company savedCompany = companyRepository.getOne(returnedCompany.getId());
        Assert.assertEquals(savedCompany.getName(), company.getName());

    }


}