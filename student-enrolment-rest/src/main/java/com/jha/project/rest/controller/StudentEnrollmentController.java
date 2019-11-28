package com.jha.project.rest.controller;

import com.jha.project.dao.repository.StudentRepository;
import com.jha.project.model.Student;
import com.jha.project.rest.model.Response;
import com.jha.project.rest.exception.ValueNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.jha.project.model.Status.*;
import static com.jha.project.rest.util.Validator.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
@RestController
@RequestMapping(value = "/enroll")
public class StudentEnrollmentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("add")
    public String addStudent(@RequestBody Student toBeAdded) {
        log.info("Inside addStudent, student is {}", toBeAdded);
        validateOnAdd(toBeAdded);
        if (Objects.nonNull(studentRepository.findByIdAndStatusNot(toBeAdded.getId(), REMOVED.getStatus()).orElse(null))) {
            throw new IllegalArgumentException("Student with id " + toBeAdded.getId() + " Already Exists");
        }
        toBeAdded.setStatus(ADDED.getStatus());
        studentRepository.saveAndFlush(toBeAdded);
        return ADDED.getStatus();
    }

    @PutMapping("update")
    public Response updateStudent(@RequestBody Student toBeUpdated) {
        log.info("Inside updateStudent, request is {}", toBeUpdated);
        validateOnUpdate(toBeUpdated);

        final Student existingRecord = studentRepository.findById(toBeUpdated.getId()).orElse(null);
        if (Objects.isNull(existingRecord)) {
            throw new ValueNotFoundException("Student with id: " + toBeUpdated.getId() + " not found");
        }
        Student pre = new Student(existingRecord.getId(), existingRecord.getFirstName(), existingRecord.getLastName(), existingRecord.getGrade(), existingRecord.getNationality(), existingRecord.getStatus());
        existingRecord.setFirstName(isNotEmpty(toBeUpdated.getFirstName()) ? toBeUpdated.getFirstName() : existingRecord.getFirstName());
        existingRecord.setLastName(isNotEmpty(toBeUpdated.getLastName()) ? toBeUpdated.getLastName() : existingRecord.getLastName());
        existingRecord.setGrade(isNotEmpty(toBeUpdated.getGrade()) ? toBeUpdated.getGrade() : existingRecord.getGrade());
        existingRecord.setNationality(isNotEmpty(toBeUpdated.getNationality()) ? toBeUpdated.getNationality() : existingRecord.getNationality());
        existingRecord.setStatus(UPDATED.getStatus());
        studentRepository.saveAndFlush(existingRecord);
        return Response.builder().pre(pre).post(existingRecord).build();
    }

    @DeleteMapping("remove")
    public boolean deleteStudent(@RequestBody Student toBeDeleted) {
        validateOnDelete(toBeDeleted);
        final Student existingRecord = studentRepository.findById(toBeDeleted.getId()).orElse(null);
        if (Objects.isNull(existingRecord)) {
            throw new ValueNotFoundException("Student with id: " + toBeDeleted.getId() + " not found");
        }
        toBeDeleted.setStatus(REMOVED.getStatus());
        return Objects.nonNull(studentRepository.saveAndFlush(toBeDeleted));
    }

    @GetMapping(value = "/getall")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping(value = "/get/{id}")
    public Student getStudentById(@PathVariable final Long id) {
        validateOnSearch(id);
        return studentRepository.findById(id).orElseThrow(() -> new ValueNotFoundException("Student with id: " + id + " not found"));
    }
}
