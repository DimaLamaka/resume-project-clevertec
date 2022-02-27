package by.lamaka.resume.service.by.lamaka.resume.service.mapper.impl;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.service.mapper.DTOMapper;
import by.lamaka.resume.service.mapper.RequestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ResumeDTOMapperTest {
    @Autowired
    DTOMapper mapper;

    @Test
    void toDTOSuccess() {
        ResumeDTO expected = ResumeDTO.newBuilder()
                .setId(1L)
                .setFirstName("zhenya")
                .setSecondName("sidorov")
                .setEmail("sidorov@mail.ru")
                .setPhoneNumber("+375-29-3234131")
                .build();
        Resume resume = Resume.builder()
                .id(1L)
                .firstName("zhenya")
                .secondName("sidorov")
                .phoneNumber("+375-29-3234131")
                .email("sidorov@mail.ru")
                .dateOfBirth(new Date())
                .build();
        ResumeDTO actual = mapper.toDTO(resume);

        assertEquals(expected, actual);
    }

    @Test
    void toListDTO() {
        List<Resume> resumes = Arrays.asList(new Resume(1L, "Zhenya", "Sidorov", " ", " ", new Date())
                , new Resume(2L, "Petya", "Sidorov", " ", " ", new Date()));
        List<ResumeDTO> resumeDTOs = resumes.stream().map(mapper::toDTO).collect(Collectors.toList());
        ListResumeDTO expected = ListResumeDTO.newBuilder().addAllResumeDTO(resumeDTOs).build();

        ListResumeDTO actual = mapper.toListDTO(resumes);

        assertEquals(expected,actual);
    }
}
