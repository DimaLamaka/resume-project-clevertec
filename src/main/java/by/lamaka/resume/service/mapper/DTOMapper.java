package by.lamaka.resume.service.mapper;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.model.Resume;

import java.util.List;

public interface DTOMapper {
    ResumeDTO toDTO(Resume resume);

    ListResumeDTO toListDTO(List<Resume> resumes);
}
