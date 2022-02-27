package by.lamaka.resume.service.mapper;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RequestMapper<T> {
    Specification<T> filterToSpecification(String filter);

    Pageable requestPageableToPageable(String pageable);
}
