package dev.luishidalgoa.course.controller;

import dev.luishidalgoa.course.model.entity.Course;
import dev.luishidalgoa.course.model.entity.DTO.StudentDTO;
import dev.luishidalgoa.course.service.ICourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@Tag(name = "Course", description = "Microservice Course")
public class courseController {
    @Autowired
    private ICourseService courseService;

    @Operation(
            summary = "Save a course",
            description = "Save a course",
            tags = {"Creation"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Necesitara algunos parametros como id, name, teacher",
                    required = false,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Course.class)
                    )
            ),
            responses = @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = int.class)
                    )
            )
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Course course) {
        courseService.save(course);
    }

    @Operation(
            summary = "get students by id",
            description = "devuelve todos los cursos de la base de datos",
            tags = {"Get by id"},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Course.class)
                    )
            )
    )
    @GetMapping("/all")
    public List<Course> findAll() {
        return (List<Course>) courseService.findAll();
    }

    @Operation(
            summary = "get course by id",
            description = "devuelve el curso con el id indicado",
            tags = {"Get by id"},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Course.class)
                    )
            )
    )
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @Operation(
            summary = "get students by id",
            description = "devuelve el estudiante con el id indicado",
            tags = {"Get by id"},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = StudentDTO.class)
                    )
            )
    )
    @GetMapping("/search-student/{idCourse}")
    public ResponseEntity<?> findStudentsByIdCourse(@PathVariable Long idCourse) {
        return ResponseEntity.ok(courseService.findStudentsByIdCourse(idCourse));
    }
}
