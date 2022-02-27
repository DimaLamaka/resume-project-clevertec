package by.lamaka.resume.service.mapper.impl;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.service.mapper.DTOMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for convert to dto objects
 */
@Component
public class ResumeDTOMapper implements DTOMapper {
    /**
     * Method for convert Resume to ResumeDTO
     *
     * @param resume entity resume
     * @return ResumeDTO
     * @see Resume
     * @see ResumeDTO
     */
    @Override
    public ResumeDTO toDTO(Resume resume) {
        return ResumeDTO.newBuilder()
                .setId(resume.getId())
                .setFirstName(resume.getFirstName())
                .setSecondName(resume.getSecondName())
                .setEmail(resume.getEmail())
                .setPhoneNumber(resume.getPhoneNumber())
                .build();
    }

    /**
     * Method for convert list resume to ListResumeDTO
     *
     * @param resumes list resume
     * @return ListResumeDTO
     * @see Resume
     * @see ListResumeDTO
     */
    @Override
    public ListResumeDTO toListDTO(List<Resume> resumes) {
        List<ResumeDTO> list = resumes.stream().map(this::toDTO).collect(Collectors.toList());
        ListResumeDTO listResumeDTO = ListResumeDTO.newBuilder().addAllResumeDTO(list).build();
        return listResumeDTO;
    }
}
