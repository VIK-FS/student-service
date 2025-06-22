package ait.cohort5860_HW.student.service;

import ait.cohort5860_HW.student.dto.ScoreDto;
import ait.cohort5860_HW.student.dto.StudentCredentialsDto;
import ait.cohort5860_HW.student.dto.StudentDto;
import ait.cohort5860_HW.student.dto.StudentUpdateDto;

import java.util.List;
import java.util.Set;

public interface StudentService {
    Boolean addStudent(StudentCredentialsDto studentCredentialsDto);

    StudentDto findStudent(Long id);

    StudentDto removeStudent(Long id);

    StudentCredentialsDto updateStudent(Long id, StudentUpdateDto student);

    Boolean addScore(Long id, ScoreDto scoreDto);

    List<StudentDto> findStudentsByName(String name);
    Long countStudentsByNames(Set<String> names);

    List<StudentDto> findStudentsByExamNameMinScore(String examName, Integer minScore);

}

