package by.lamaka.resume.service.filter;

import by.lamaka.resume.model.Resume;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for creating a Specification from a list of filter criteria objects
 *
 * @see Specification
 * @see FilterCriteria
 */
@Component
@Scope(value = "prototype")
public class ResumeSpecificationsBuilder {
    private final List<FilterCriteria> params;

    /**
     * Constructor for ResumeSpecificationsBuilder
     */
    public ResumeSpecificationsBuilder() {
        params = new ArrayList<FilterCriteria>();
    }

    /**
     * Method to add FilterCriteria
     *
     * @param key       key
     * @param operation operation
     * @param value     value
     * @return ResumeSpecificationsBuilder
     * @see FilterCriteria
     */
    public ResumeSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new FilterCriteria(key, operation, value));
        return this;
    }

    /**
     * Method to convert a list of filter criteria into a specification
     *
     * @return Specification
     * @see FilterCriteria
     */
    public Specification<Resume> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Resume>> specs = params.stream()
                .map(ResumeSpecification::new)
                .collect(Collectors.toList());

        Specification<Resume> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }
        return result;
    }
}
