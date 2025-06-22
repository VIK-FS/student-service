package ait.cohort5860_HW.student.service;

import ait.cohort5860_HW.student.dao.StudentRepository;
import ait.cohort5860_HW.student.dto.ScoreDto;
import ait.cohort5860_HW.student.dto.StudentCredentialsDto;
import ait.cohort5860_HW.student.dto.StudentDto;
import ait.cohort5860_HW.student.dto.StudentUpdateDto;
import ait.cohort5860_HW.student.dto.exceptions.NotFoundException;
import ait.cohort5860_HW.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Boolean addStudent(StudentCredentialsDto studentCredentialsDto) {
        if (studentRepository.findById(studentCredentialsDto.getId()).isPresent()) {
            return false;
        }
        Student student = new Student(studentCredentialsDto.getId(), studentCredentialsDto.getName(), studentCredentialsDto.getPassword());
        studentRepository.save(student);
        return true;
    }

    @Override
    public StudentDto findStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        return new StudentDto(student.getId(), student.getName(), student.getScores());
    }

    @Override
    public StudentDto removeStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        StudentDto studentDto = new StudentDto(student.getId(), student.getName(), student.getScores());
        studentRepository.deleteById(id);
        return studentDto;
    }

    @Override
    public StudentCredentialsDto updateStudent(Long id, StudentUpdateDto studentUpdateDto) {
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        if (studentUpdateDto.getName() != null) {
            student.setName(studentUpdateDto.getName());
        }
        if (studentUpdateDto.getPassword() != null) {
            student.setPassword(studentUpdateDto.getPassword());
        }

        studentRepository.save(student);
        return new StudentCredentialsDto(student.getId(), student.getName(), student.getPassword());
    }

    @Override
    public Boolean addScore(Long id, ScoreDto scoreDto) {
        Student student = studentRepository.findById(id).orElseThrow(NotFoundException::new);
        boolean isNewScore = student.addScore(scoreDto.getExamName(), scoreDto.getScore());
        studentRepository.save(student);
        return isNewScore;
    }

    @Override
    public List<StudentDto> findStudentsByName(String name) {

        return studentRepository.findAll().stream()
                .filter(student -> student.getName().equals(name))
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getScores()))
                .collect(Collectors.toList());
    }

    @Override
    public Long countStudentsByNames(Set<String> names) {
        return studentRepository.findAll().stream()
                .filter(student -> names.contains(student.getName()))
                .count();
    }

    @Override
    public List<StudentDto> findStudentsByExamNameMinScore(String examName, Integer minScore) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getScores().containsKey(examName))
                .filter(student -> student.getScores().get(examName) >= minScore)
                .map(student -> new StudentDto(student.getId(), student.getName(), student.getScores()))
                .collect(Collectors.toList());
    }
}
