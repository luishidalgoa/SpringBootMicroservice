package dev.luishidalgoa.student.controller;

import dev.luishidalgoa.student.entity.Student;
import dev.luishidalgoa.student.service.IStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@Tag(name = "Student", description = "Microservice Student")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Operation(
            summary = "Save a student",
            description = "Save a student",
            tags = {"Creation"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Necesitara algunos parametros como id, name, lastName,email courseId",
                    required = false,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class)
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
    public void saveStudent(@RequestBody Student student) {
        studentService.save(student);
    }

    @Operation(
            summary = "get all student",
            description = "devuelve todos los estudiantes existentes en la base de datos",
            tags = {"Get all"},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class)
                    )
            )
    )
    @GetMapping("/all")
    public ResponseEntity<?> findAllStudent() {
        List<Student> students = studentService.findAll();
        return ResponseEntity.ok(students);
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
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class)
                    )
            )
    )
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(student);
    }

    @Operation(
            summary = "get students by id course",
            description = "devuelve el estudiante con el id del curso indicado",
            tags = {"GetByCourse"},
            responses = @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = Student.class)
                    )
            )
    )
    @GetMapping("/search-by-course/{idCourse}")
    public ResponseEntity<?> findByIdCourse(@PathVariable("idCourse") Long idCourse) {
        return ResponseEntity.ok(studentService.findByIdCourse(idCourse));
    }
}
