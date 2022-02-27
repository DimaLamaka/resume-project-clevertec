package by.lamaka.resume.service.filter;

import by.lamaka.resume.model.Resume;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Custom class ResumeSpecification for get an object Specification by object FilterCriteria
 *
 * @see FilterCriteria
 */
@Data
public class ResumeSpecification implements Specification<Resume> {
    private FilterCriteria filterCriteria;

    /**
     * Constructor for ResumeSpecification
     *
     * @param filterCriteria filter criteria
     * @see FilterCriteria
     */
    public ResumeSpecification(FilterCriteria filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    /**
     * Method to convert filter criteria to predicate
     *
     * @param root            Root
     * @param query           Query
     * @param criteriaBuilder criteriaBuilder
     * @return Predicate
     * @see FilterCriteria
     * @see Root
     * @see CriteriaQuery
     * @see CriteriaBuilder
     * @see Predicate
     */
    @Override
    public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (filterCriteria.getOperation().equalsIgnoreCase(">")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(filterCriteria.getKey()), filterCriteria.getValue().toString());
        } else if (filterCriteria.getOperation().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(filterCriteria.getKey()), filterCriteria.getValue().toString());
        } else if (filterCriteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(filterCriteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.get(filterCriteria.getKey()), "%" + filterCriteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(filterCriteria.getKey()), filterCriteria.getValue());
            }
        }
        return null;

    }
}
