package dev.luishidalgoa.course.persistence;

import dev.luishidalgoa.course.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends CrudRepository<Course, Long> { }
