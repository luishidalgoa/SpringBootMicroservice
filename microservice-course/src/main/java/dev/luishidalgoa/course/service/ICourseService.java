package dev.luishidalgoa.course.service;

import dev.luishidalgoa.course.http.response.StudentByCourseResponse;
import dev.luishidalgoa.course.model.entity.Course;
import dev.luishidalgoa.course.model.entity.DTO.StudentDTO;

import java.util.List;

public interface ICourseService {
    List<Course> findAll();

    Course findById(Long id);

    void save(Course course);

    StudentByCourseResponse findStudentsByIdCourse(Long idCourse);
}
