package by.lamaka.resume.service;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.exceptions.ResourceNotFoundException;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.repository.ResumeRepository;
import by.lamaka.resume.service.filter.ResumeSpecificationsBuilder;
import by.lamaka.resume.service.mapper.DTOMapper;
import by.lamaka.resume.service.mapper.RequestMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resume service for crud operation with Resume
 */
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResumeServiceImpl implements ResumeService {
    ResumeRepository repository;
    DTOMapper DTOMapper;
    RequestMapper<Resume> requestMapper;

    /**
     * Constructor for ResumeServiceImpl
     *
     * @param repository    ResumeRepository
     * @param DTOMapper     DTOMapper
     * @param requestMapper RequestMapper
     * @see ResumeRepository
     * @see by.lamaka.resume.service.mapper.impl.ResumeDTOMapper
     * @see by.lamaka.resume.service.mapper.impl.ResumeRequestMapper
     */
    @Autowired
    public ResumeServiceImpl(ResumeRepository repository, DTOMapper DTOMapper, RequestMapper<Resume> requestMapper) {
        this.repository = repository;
        this.DTOMapper = DTOMapper;
        this.requestMapper = requestMapper;
    }

    /**
     * Method for getting resume by id and map to ResumeDTO
     *
     * @param id resume id
     * @return ResumeDTO
     * @throws ResourceNotFoundException if resume with input id not found
     */
    @Override
    public ResumeDTO getById(Long id) {
        Resume resume = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resume with id = " + id + " not found!"));
        return DTOMapper.toDTO(resume);
    }

    /**
     * Method to save resume or update and map to ResumeDTO
     *
     * @param resume Resume
     * @return ResumeDTO
     * @throws ResourceNotFoundException if resume with input id not found
     */
    @Override
    public ResumeDTO save(Resume resume) {
        if (resume.getId() != null) {
            repository.findById(resume.getId()).orElseThrow(() -> new ResourceNotFoundException("Resume with id = " + resume.getId() + " not found!"));
        }
        repository.save(resume);
        return DTOMapper.toDTO(resume);
    }

    /**
     * Method to delete resume and map to ResumeDTO
     *
     * @param id resume id
     * @throws ResourceNotFoundException if resume with input id not found
     */
    @Override
    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resume with id = " + id + " not found!"));
        repository.deleteById(id);
    }

    /**
     * Method for getting list resume by filter and map to ListResumeDTO
     *
     * @param filter request filter
     * @return ListResumeDTO
     */
    @Override
    public ListResumeDTO getAllByFilter(String filter) {
        if (filter == null) {
            return DTOMapper.toListDTO(repository.findAll());
        }
        Specification<Resume> specification = requestMapper.filterToSpecification(filter);
        return DTOMapper.toListDTO(repository.findAll(specification));
    }

    /**
     * Method for getting list resume by pageable and map to ListResumeDTO
     *
     * @param reqPageable request pageable
     * @return ListResumeDTO
     * @throws ResourceNotFoundException if nubmer page does not exist
     */
    @Override
    public ListResumeDTO getAllByPageable(String reqPageable) {
        Pageable pageable = requestMapper.requestPageableToPageable(reqPageable);
        Page<Resume> page = repository.findAll(pageable);

        if (page.getTotalPages() < pageable.getPageNumber()) {
            throw new ResourceNotFoundException("This page does not exist");
        }
        return DTOMapper.toListDTO(page.getContent());
    }

}
