package dev.luishidalgoa.course.controller;

import dev.luishidalgoa.course.entity.Course;
import dev.luishidalgoa.course.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class courseController {
    @Autowired
    private ICourseService courseService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Course course) {
        courseService.save(course);
    }

    @GetMapping("/all")
    public List<Course> findAll() {
        return (List<Course>) courseService.findAll();
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }
}
