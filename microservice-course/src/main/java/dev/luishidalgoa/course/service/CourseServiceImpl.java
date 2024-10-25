package dev.luishidalgoa.course.service;

import dev.luishidalgoa.course.client.StudentClient;
import dev.luishidalgoa.course.http.response.StudentByCourseResponse;
import dev.luishidalgoa.course.model.entity.Course;
import dev.luishidalgoa.course.model.entity.DTO.StudentDTO;
import dev.luishidalgoa.course.persistence.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private StudentClient studentClient;

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    /**
     * Este método se encarga de obtener los estudiantes de un curso mediante el microservicio de estudiantes
     * @param idCourse identificador del curso a consultar
     * @return devuelve un objeto Response de tipo StudentByCourseResponse
     */
    @Override
    public StudentByCourseResponse findStudentsByIdCourse(Long idCourse) {
        //consultar el curso
        Course course = courseRepository.findById(idCourse).orElse(new Course());

        //obtenemos los estudiantes consumiendo el microservicio mediante el cliente feign
        List<StudentDTO> studentDTOList = studentClient.findAllStudentByCourse(idCourse);
        return StudentByCourseResponse.builder() //construimos el objeto
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList(studentDTOList)
                .build();
    }
}
