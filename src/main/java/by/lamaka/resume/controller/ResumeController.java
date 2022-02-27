package by.lamaka.resume.controller;

import by.lamaka.resume.dto.ListResumeDTO;
import by.lamaka.resume.dto.ResumeDTO;
import by.lamaka.resume.exceptions.BadRequestException;
import by.lamaka.resume.exceptions.ResourceNotFoundException;
import by.lamaka.resume.model.Resume;
import by.lamaka.resume.service.ResumeService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

/**
 * Rest controller
 * crud operations
 */
@RestController
@RequestMapping("/api/v1/resumes")
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResumeController {
    ResumeService service;

    @Autowired
    public ResumeController(ResumeService service) {
        this.service = service;
    }

    /**
     * Get resume by id
     *
     * @param id resume id
     * @return ResumeDTO in binary form (x-protobuf)
     */
    @GetMapping(value = "/{id}", produces = "application/x-protobuf")
    public ResponseEntity<ResumeDTO> getResume(@PathVariable("id") Long id) throws ResourceNotFoundException {

        ResumeDTO resumeDTO = service.getById(id);
        return new ResponseEntity<>(resumeDTO, HttpStatus.OK);
    }

    /**
     * Save resume
     *
     * @param resume resume request body in json format
     * @return ResumeDTO in binary form (x-protobuf)
     */
    @PostMapping(produces = "application/x-protobuf")
    public ResponseEntity<ResumeDTO> saveResume(@Valid @RequestBody Resume resume) {

        ResumeDTO resumeDTO = service.save(resume);
        return new ResponseEntity<>(resumeDTO, HttpStatus.CREATED);
    }

    /**
     * Update resume by id
     *
     * @param resume resume request body in json format
     * @param id     resume id
     * @return ResumeDTO in binary form (x-protobuf)
     */
    @PutMapping(value = "/{id}", produces = "application/x-protobuf")
    public ResponseEntity<ResumeDTO> updateResume(@Valid @RequestBody Resume resume, @PathVariable("id") Long id) {

        resume.setId(id);
        ResumeDTO resumeDTO = service.save(resume);
        return new ResponseEntity<>(resumeDTO, HttpStatus.OK);
    }

    /**
     * Delete resume by id
     *
     * @param id resume id
     * @return HttpStatus
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResumeDTO> deleteResume(@PathVariable("id") Long id) {

        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get all resumes by filter, example filter=firstName:Dima
     * Filter options: firstName, secondName,phoneNumber,email,dateOfBirth
     * For request without filter you can use pagination, example pageable=page:2,size:2
     * Pageable options: Page - page number, Size - count of elements
     *
     * @param filter   filter resumes by filter options, not required
     * @param pageable pagination for resumes, not required
     * @return ListResumeDTO in binary form (x-protobuf)
     */
    @GetMapping(produces = "application/x-protobuf")
    public ResponseEntity<ListResumeDTO> getAllResumes(@RequestParam(value = "filter", required = false) String filter,
                                                       @RequestParam(value = "pageable", required = false) String pageable) {

        ListResumeDTO listResumeDTO = pageable == null ? service.getAllByFilter(filter) : service.getAllByPageable(pageable);
        return new ResponseEntity<>(listResumeDTO, HttpStatus.OK);
    }
}
