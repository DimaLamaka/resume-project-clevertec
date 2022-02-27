package by.lamaka.resume.controller;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.service.ResumeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResumeControllerTest {
    @Autowired
    ResumeController controller;
    @Autowired
    RestTemplate template;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    ResumeService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    void getResumeByIdWithRestTemplateSuccess() {
        String url = "http://localhost:8080/api/v1/resumes/3";
        ResponseEntity<ResumeDTO> forEntity = template.getForEntity(url, ResumeDTO.class);

        assertThat(forEntity.toString(), containsString("id"));
        assertThat(forEntity.toString(), containsString("first_name"));
        assertThat(forEntity.toString(), containsString("second_name"));
        assertThat(forEntity.toString(), containsString("email"));
        assertThat(forEntity.toString(), containsString("phone_number"));
        assertThat(forEntity.toString(), containsString("3"));
        assertThat(forEntity.toString(), containsString("Alisa"));
        assertThat(forEntity.toString(), containsString("Alisova"));
        assertThat(forEntity.toString(), containsString("Alisa@mail.ru"));
        assertThat(forEntity.toString(), containsString("+375331236791"));
    }

    @Test
    void getResumeByIdShouldReturnResumeDTO() throws Exception {
        ResumeDTO resume = ResumeDTO.newBuilder()
                .setId(1L)
                .setFirstName("zhenya")
                .setSecondName("sidorov")
                .setEmail("sidorov@mail.ru")
                .setPhoneNumber("+375-29-3234131")
                .build();

        when(service.getById(1L)).thenReturn(resume);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/resumes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/x-protobuf"));

        verify(service, times(1)).getById(1L);
    }

    @Test
    void getResumeByIdShouldThrowException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/resumes/aa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteResumeByIdSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/resumes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

        verify(service, times(1)).delete(1L);
    }

    @Test
    void saveResumeShouldSuccessAndReturnResumeDTO() throws Exception {
        ResumeDTO resumeDTO = ResumeDTO.newBuilder()
                .setId(1L)
                .setFirstName("zhenya")
                .setSecondName("sidorov")
                .setEmail("sidorov@mail.ru")
                .setPhoneNumber("+375-29-3234131")
                .build();
        Resume resume = Resume.builder()
                .firstName("zhenya")
                .secondName("sidorov")
                .phoneNumber("+375-29-3234131")
                .email("sidorov@mail.ru")
                .dateOfBirth(new Date())
                .build();

        String json = mapper.writeValueAsString(resume);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/resumes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        Mockito.when(service.save(any(Resume.class))).thenReturn(resumeDTO);

        verify(service, times(1)).save(any(Resume.class));
    }
    @Test
    void updateResumeShouldSuccessAndReturnResumeDTO() throws Exception {
        ResumeDTO resumeDTO = ResumeDTO.newBuilder()
                .setId(1L)
                .setFirstName("zhenya")
                .setSecondName("sidorov")
                .setEmail("sidorov@mail.ru")
                .setPhoneNumber("+375-29-3234131")
                .build();
        Resume resume = Resume.builder()
                .firstName("zhenya")
                .secondName("sidorov")
                .phoneNumber("+375-29-3234131")
                .email("sidorov@mail.ru")
                .dateOfBirth(new Date())
                .build();

        String json = mapper.writeValueAsString(resume);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/resumes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
        Mockito.when(service.save(any(Resume.class))).thenReturn(resumeDTO);

        verify(service, times(1)).save(any(Resume.class));
    }
    @Test
    void getListResumeByIdShouldReturnResumeDTO() throws Exception {
        ResumeDTO resume = ResumeDTO.newBuilder()
                .setId(1L)
                .setFirstName("zhenya")
                .setSecondName("sidorov")
                .setEmail("sidorov@mail.ru")
                .setPhoneNumber("+375-29-3234131")
                .build();
        ListResumeDTO listResumeDTO = ListResumeDTO.newBuilder()
                .addAllResumeDTO(Arrays.asList(resume))
                .build();

        when(service.getAllByFilter(null)).thenReturn(listResumeDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/resumes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/x-protobuf"));

        verify(service, times(1)).getAllByFilter(null);
    }

    @Test
    void getListResumeWithFilterByIdShouldReturnResumeDTO() throws Exception {
        ResumeDTO resume = ResumeDTO.newBuilder()
                .setId(1L)
                .setFirstName("zhenya")
                .setSecondName("sidorov")
                .setEmail("sidorov@mail.ru")
                .setPhoneNumber("+375-29-3234131")
                .build();
        ListResumeDTO listResumeDTO = ListResumeDTO.newBuilder()
                .addAllResumeDTO(Arrays.asList(resume))
                .build();

        when(service.getAllByFilter("firstName:dima")).thenReturn(listResumeDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/resumes?filter=firstName:dima")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/x-protobuf"));

        verify(service, times(1)).getAllByFilter("firstName:dima");
    }
    @Test
    void getListResumeWithPageableByIdShouldReturnResumeDTO() throws Exception {
        ResumeDTO resume = ResumeDTO.newBuilder()
                .setId(1L)
                .setFirstName("zhenya")
                .setSecondName("sidorov")
                .setEmail("sidorov@mail.ru")
                .setPhoneNumber("+375-29-3234131")
                .build();
        ListResumeDTO listResumeDTO = ListResumeDTO.newBuilder()
                .addAllResumeDTO(Arrays.asList(resume))
                .build();

        when(service.getAllByPageable("page:1,size:2")).thenReturn(listResumeDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8080/api/v1/resumes?pageable=page:1,size:2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/x-protobuf"));

        verify(service, times(1)).getAllByPageable("page:1,size:2");
    }

}

