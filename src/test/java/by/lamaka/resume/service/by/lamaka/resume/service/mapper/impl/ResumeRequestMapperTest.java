package by.lamaka.resume.service.by.lamaka.resume.service.mapper.impl;

import by.lamaka.resume.exceptions.BadRequestException;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.service.filter.FilterCriteria;
import by.lamaka.resume.service.filter.ResumeSpecification;
import by.lamaka.resume.service.filter.ResumeSpecificationsBuilder;
import by.lamaka.resume.service.mapper.impl.ResumeRequestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ResumeRequestMapperTest {
    @Autowired
    ResumeRequestMapper mapper;
    @MockBean
    ResumeSpecificationsBuilder builder;

    @Test
    void filterToSpecificationSuccess() {
        ResumeSpecification expected = new ResumeSpecification(new FilterCriteria("firstName",":","dima"));
        when(builder.with("firstName",":","dima")).thenReturn(builder);
        when(builder.build()).thenReturn(expected);

        Specification<Resume> actual = mapper.filterToSpecification("firstName:dima");

        assertEquals(expected, actual);

        verify(builder, times(1)).with("firstName",":","dima");
        verify(builder, times(1)).build();

    }

    @Test
    void requestPageableToPageableSuccess(){
        Pageable expected = PageRequest.of(1, 1, Sort.by("id"));

        Pageable actual = mapper.requestPageableToPageable("page:1,size:1");

        assertEquals(expected,actual);
    }

    @Test
    void requestPageableToPageableShouldThrowException(){
        Exception exception = null;
        try{
            mapper.requestPageableToPageable("page:ss,size:1");
        }catch (BadRequestException ex){
            exception = ex;
        }
        assertNotNull(exception);
    }
}
