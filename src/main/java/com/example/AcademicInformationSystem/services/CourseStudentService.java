package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.dto.request.DtoStudentCourseRequest;
import com.example.AcademicInformationSystem.dto.response.DtoStudentCourseResponse;
import com.example.AcademicInformationSystem.dto.response.DtoStudentResponse;
import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.CourseStudents;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.models.Student;
import com.example.AcademicInformationSystem.repositories.CourseRepository;
import com.example.AcademicInformationSystem.repositories.CourseStudentRepository;
import com.example.AcademicInformationSystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseStudentService {
    @Autowired
    private CourseStudentRepository courseStudentRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    public Boolean addStudentCourse(DtoStudentCourseRequest courseStudent, Response response) {
        Optional<Student> existingStudent = studentRepository.findById(courseStudent.getStudentId());
        Optional<Course> existingCourse = courseRepository.findById(courseStudent.getCourseId());

        if (!existingStudent.isPresent()){
            response.setMessage("Student Not Found");
            return false;
        } else if (!existingCourse.isPresent()) {
            response.setMessage("Course Not Found");
            return false;
        }

        Optional<CourseStudents> existingCourseStudent = courseStudentRepository.findFirstByStudent_IdAndCourse_Id(existingStudent.get().getId(), existingCourse.get().getId());
        System.out.println(existingCourseStudent);
        if (existingCourseStudent.isPresent()) {
            response.setMessage("Data is Already Exists");
            return false;
        }

        CourseStudents newCourseStudent = new CourseStudents();
        newCourseStudent.setStudent(existingStudent.get());
        newCourseStudent.setCourse(existingCourse.get());
        courseStudentRepository.save(newCourseStudent);

        response.setData(new DtoStudentCourseResponse(existingStudent.get().getNpm(),
                existingStudent.get().getName(),
                existingStudent.get().getDepartment().getName(), existingCourse.get().getName(), true));
        response.setMessage("Success");
        return true;
    }

    public List<DtoStudentCourseResponse> getAll(){
        List<CourseStudents> courseStudents = courseStudentRepository.findAllByIsDeletedIsTrueAndIsActiveTrue();
        List<DtoStudentCourseResponse> courseStudentList = new ArrayList<>();

        for (CourseStudents courseStudent: courseStudents){
            DtoStudentCourseResponse studentCourseData = new DtoStudentCourseResponse(courseStudent.getStudent().getNpm(),
                    courseStudent.getStudent().getName(), courseStudent.getStudent().getDepartment().getName(),
                    courseStudent.getCourse().getName(),
                    courseStudent.getActive());
            courseStudentList.add(studentCourseData);
        }
        return courseStudentList;
    }

    public CourseStudents getById(Long id){
        return courseStudentRepository.findById(id).get();
    }

    public Boolean updateActive(Long id, CourseStudents courseStudents, Response response){
        Optional<CourseStudents> existingCourseStudent = courseStudentRepository.findById(id);

        if (!existingCourseStudent.isPresent()){
            response.setMessage("Course Student Not Found");
            return false;
        }

        response.setData(new DtoStudentCourseResponse(existingCourseStudent.get().getStudent().getNpm(),
                existingCourseStudent.get().getStudent().getName(),
                existingCourseStudent.get().getStudent().getDepartment().getName(),
                existingCourseStudent.get().getCourse().getName(), existingCourseStudent.get().getActive()));

        existingCourseStudent.get().setActive(courseStudents.getActive());
        courseStudentRepository.save(existingCourseStudent.get());
        return true;
    }
}
