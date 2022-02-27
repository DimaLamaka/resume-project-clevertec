package by.lamaka.resume.service;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.model.Resume;

public interface ResumeService {
    ResumeDTO getById(Long id);

    ResumeDTO save(Resume resume);

    void delete(Long id);

    ListResumeDTO getAllByFilter(String filter);

    ListResumeDTO getAllByPageable(String pageable);
}
