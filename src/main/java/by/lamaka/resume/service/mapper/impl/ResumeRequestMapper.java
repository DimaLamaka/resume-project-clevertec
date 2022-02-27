package by.lamaka.resume.service.mapper.impl;

import by.lamaka.resume.exceptions.BadRequestException;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.service.filter.ResumeSpecificationsBuilder;
import by.lamaka.resume.service.mapper.RequestMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Сlass for converting requests to objects
 */
@Component
@Scope(value = "prototype")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResumeRequestMapper implements RequestMapper<Resume> {
    ApplicationContext context;

    /**
     * Constructor for ResumeRequestMapper
     *
     * @param context ApplicationContext
     * @see ApplicationContext
     */
    @Autowired
    public ResumeRequestMapper(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Method for convert String request filter to Specification
     *
     * @param filter request filter
     * @return Specification
     * @see Specification
     */
    @Override
    public Specification<Resume> filterToSpecification(String filter) {
        ResumeSpecificationsBuilder builder = context.getBean(ResumeSpecificationsBuilder.class);
        Pattern pattern = Pattern.compile("(\\w+?)(:)(\\w+?),");
        Matcher matcher = pattern.matcher(filter + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Resume> spec = builder.build();
        return spec;
    }

    /**
     * Method for convert String request pageable to Pageable
     *
     * @param reqPageable request pageable
     * @return Pageable
     * @see Pageable
     */
    @Override
    public Pageable requestPageableToPageable(String reqPageable) {
        String[] splits = reqPageable.split(",");
        int countPage = 0;
        int countSize = 0;
        try {
            if (splits[0].split(":")[0].equals("page")) {
                countPage = Integer.parseInt(splits[0].split(":")[1]);
            }
            if (splits[1].split(":")[0].equals("size")) {
                countSize = Integer.parseInt(splits[1].split(":")[1]);
            }
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Bad request with pageable");
        }
        Pageable pageable = PageRequest.of(countPage, countSize, Sort.by("id"));
        return pageable;
    }
}
