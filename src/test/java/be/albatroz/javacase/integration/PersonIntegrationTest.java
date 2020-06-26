package be.albatroz.javacase.integration;

import be.albatroz.javacase.common.PersonType;
import be.albatroz.javacase.controller.PersonController;
import be.albatroz.javacase.entity.Person;
import be.albatroz.javacase.repository.CompanyRepository;
import be.albatroz.javacase.repository.PersonRepository;
import be.albatroz.javacase.resource.PersonResource;
import be.albatroz.javacase.resource.mapper.PersonMapper;
import be.albatroz.javacase.service.CompanyService;
import be.albatroz.javacase.service.PersonService;
import be.albatroz.javacase.util.Json;
import be.albatroz.javacase.validator.PersonResourceValidator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
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
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({
        PersonController.class, PersonService.class, PersonResourceValidator.class,
        RandomObjects.class,
        CompanyService.class})
class PersonIntegrationTest {

    @Setter(onMethod_ = {@Autowired})
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private CompanyRepository companyRepository;

    @Setter(onMethod_ = {@Autowired})
    private PersonService personService;

    @Setter(onMethod_ = {@Autowired})
    private CompanyService companyService;


    @Setter(onMethod_ = {@Autowired})
    private RandomObjects randomObjects;

//    @Before
//    void before() {
//        randomObjects.setCompanyService(companyService);
//    }

    @Test
    void getPerson() throws Exception {
        log.error("http://localhost:" + port);
        mockMvc.perform(get("/person")
                .header("Origin", "http://localhost:" + port)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void postPerson() throws Exception {

        log.info(mockMvc.getDispatcherServlet().getServletContext().getServerInfo());

        final Person person = randomObjects.createPerson();
        final PersonResource personResource = PersonMapper.INSTANCE.toResource(person);

        given(personRepository.getOne(person.getId())).willReturn(person);
        given(personService.save(person)).willReturn(person);

        String response = mockMvc.perform(post("/person")
                .header("Origin", "http://localhost:" + port)
                .contentType("application/json")
                .content(Json.objectToJsonString(personResource)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PersonResource returnedPerson = Json.jsonStringToObject(response, PersonResource.class);
        Person savedPerson = personRepository.getOne(returnedPerson.getId());
        Assert.assertEquals(savedPerson.getName(), person.getName());

    }

    @Test
    void postInvalidPerson() throws Exception {

        log.info(mockMvc.getDispatcherServlet().getServletContext().getServerInfo());

        final Person person = randomObjects.createPerson();
        person.setType(PersonType.FREELANCER);
        person.setVat(null);
        final PersonResource personResource = PersonMapper.INSTANCE.toResource(person);

        mockMvc.perform(post("/person")
                .header("Origin", "http://localhost:" + port)
                .contentType("application/json")
                .content(Json.objectToJsonString(personResource)))
                .andExpect(status().isBadGateway());
    }

}