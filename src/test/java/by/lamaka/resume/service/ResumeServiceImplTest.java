package by.lamaka.resume.service;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.exceptions.ResourceNotFoundException;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.repository.ResumeRepository;
import by.lamaka.resume.service.filter.FilterCriteria;
import by.lamaka.resume.service.filter.ResumeSpecification;
import by.lamaka.resume.service.mapper.DTOMapper;
import by.lamaka.resume.service.mapper.RequestMapper;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ResumeServiceImplTest {

    @MockBean
    ResumeRepository repository;
    @MockBean
    DTOMapper DTOMapper;
    @MockBean
    RequestMapper<Resume> requestMapper;
    @Autowired
    ResumeServiceImpl resumeService;

    @Test
    void getByIdShouldReturnResumeDTO() {
        ResumeDTO resumeDTO = ResumeDTO.newBuilder()
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

        when(repository.findById(1L)).thenReturn(Optional.of(resume));
        when(DTOMapper.toDTO(resume)).thenReturn(resumeDTO);

        ResumeDTO byId = resumeService.getById(1L);

        verify(repository, times(1)).findById(1L);
        verify(DTOMapper, times(1)).toDTO(resume);

        assertEquals(resumeDTO, byId);
    }

    @Test
    void getByIdShouldThrowException() {
        when(repository.findById(5L)).thenThrow(ResourceNotFoundException.class);
        Exception exc = null;

        try {
            resumeService.getById(5L);
        } catch (ResourceNotFoundException ex) {
            exc = ex;
        }

        assertNotNull(exc);
    }

    @Test
    void saveShouldSaveAndReturnResumeDTO() {
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
        when(repository.save(resume)).thenReturn(resume);
        when(DTOMapper.toDTO(resume)).thenReturn(resumeDTO);

        ResumeDTO serviceResumeDTO = resumeService.save(resume);

        verify(repository, times(1)).save(resume);
        verify(DTOMapper, times(1)).toDTO(resume);

        assertEquals(resumeDTO,serviceResumeDTO);
    }
    @Test
    void saveShouldUpdateAndReturnResumeDTO() {
        ResumeDTO resumeDTO = ResumeDTO.newBuilder()
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
        when(repository.save(resume)).thenReturn(resume);
        when(DTOMapper.toDTO(resume)).thenReturn(resumeDTO);
        when(repository.findById(1L)).thenReturn(Optional.of(resume));

        ResumeDTO serviceResumeDTO = resumeService.save(resume);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(resume);
        verify(DTOMapper, times(1)).toDTO(resume);

        assertEquals(resumeDTO,serviceResumeDTO);
    }

    @Test
    void saveShouldThrowException() {
        Resume resume = Resume.builder()
                .id(1L)
                .firstName("zhenya")
                .secondName("sidorov")
                .phoneNumber("+375-29-3234131")
                .email("sidorov@mail.ru")
                .dateOfBirth(new Date())
                .build();
        Exception exc = null;
        when(repository.findById(5L)).thenThrow(ResourceNotFoundException.class);

        try {
            resumeService.save(resume);
        } catch (ResourceNotFoundException ex) {
            exc = ex;
        }

        assertNotNull(exc);
    }

    @Test
    void deleteByIdShouldSuccess(){
        Resume resume = Resume.builder()
                .id(1L)
                .firstName("zhenya")
                .secondName("sidorov")
                .phoneNumber("+375-29-3234131")
                .email("sidorov@mail.ru")
                .dateOfBirth(new Date())
                .build();
        when(repository.findById(1L)).thenReturn(Optional.of(resume));

        resumeService.delete(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteShouldThrowException() {
        Resume resume = Resume.builder()
                .id(1L)
                .firstName("zhenya")
                .secondName("sidorov")
                .phoneNumber("+375-29-3234131")
                .email("sidorov@mail.ru")
                .dateOfBirth(new Date())
                .build();

        Exception exc = null;
        when(repository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        try {
            resumeService.delete(1L);
        } catch (ResourceNotFoundException ex) {
            exc = ex;
        }

        assertNotNull(exc);
    }

    @Test
    void getAllByNullFilterShouldSuccess(){
        List<Resume> resumes = Arrays.asList(new Resume(),new Resume());
        ListResumeDTO expected = ListResumeDTO.newBuilder().build();

        when(repository.findAll()).thenReturn(resumes);
        when(DTOMapper.toListDTO(resumes)).thenReturn(expected);

        ListResumeDTO actual = resumeService.getAllByFilter(null);

        verify(repository, times(1)).findAll();
        verify(DTOMapper, times(1)).toListDTO(resumes);

        assertEquals(expected,actual);
    }

    @Test
    void getAllByFilterShouldSuccess(){
        List<Resume> resumes = Arrays.asList(new Resume(),new Resume());
        ListResumeDTO expected = ListResumeDTO.newBuilder().build();
        ResumeSpecification resumeSpecification = new ResumeSpecification(new FilterCriteria());

        when(requestMapper.filterToSpecification("filter")).thenReturn(resumeSpecification);
        when(repository.findAll(resumeSpecification)).thenReturn(resumes);
        when(DTOMapper.toListDTO(resumes)).thenReturn(expected);

        ListResumeDTO actual = resumeService.getAllByFilter("filter");

        verify(repository, times(1)).findAll(resumeSpecification);
        verify(DTOMapper, times(1)).toListDTO(resumes);

        assertEquals(expected,actual);
    }

    @Test
    void getAllByPageableShouldSuccess(){
        List<Resume> resumes = Arrays.asList(new Resume(),new Resume());
        ListResumeDTO expected = ListResumeDTO.newBuilder().build();
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Resume> expectedPage = new PageImpl<Resume>(resumes);

        when(repository.findAll(pageable)).thenReturn(expectedPage);
        when(DTOMapper.toListDTO(resumes)).thenReturn(expected);
        when(requestMapper.requestPageableToPageable("pageable")).thenReturn(pageable);

        Page<Resume> actualPage = repository.findAll(pageable);
        ListResumeDTO actual = resumeService.getAllByPageable("pageable");

        verify(repository, times(2)).findAll(pageable);
        verify(DTOMapper, times(1)).toListDTO(resumes);
        verify(requestMapper, times(1)).requestPageableToPageable("pageable");

        assertEquals(expected,actual);
        assertEquals(expectedPage,actualPage);
    }

}