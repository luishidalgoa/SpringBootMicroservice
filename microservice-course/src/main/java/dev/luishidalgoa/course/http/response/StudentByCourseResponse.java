package dev.luishidalgoa.course.http.response;

import dev.luishidalgoa.course.model.entity.DTO.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Es un objeto Wrapper que nos servira para enviarlo mediante HTTP (filosofia parecia al de los DTO)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentByCourseResponse {
    private String courseName;
    private String teacher;
    private List<StudentDTO> studentDTOList;

}
