package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.CourseStudents;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.models.Student;
import com.example.AcademicInformationSystem.repositories.CourseRepository;
import com.example.AcademicInformationSystem.repositories.CourseStudentRepository;
import com.example.AcademicInformationSystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Boolean addStudentCourse(CourseStudents courseStudent, Response response) {
        Optional<Student> existingStudent = studentRepository.findById(courseStudent.getStudent().getId());
        Optional<Course> existingCourse = courseRepository.findById(courseStudent.getCourse().getId());
        Optional<CourseStudents> existingCourseStudent = courseStudentRepository.findFirstByStudent_IdAndCourse_Id(existingStudent.get().getId(), existingStudent.get().getId());

        if (!existingCourse.isPresent()){
            response.setMessage("Course Not Found");
            return false;
        }
        else if (existingCourseStudent.isPresent()) {
            response.setMessage("Data is Already Exists");
            return false;
        }

        courseStudent.setStudent(existingStudent.get());
        courseStudent.setCourse(existingCourse.get());
        courseStudentRepository.save(courseStudent);
        response.setMessage("Success");
        return true;
    }

    public List<CourseStudents> getAll(){
        return courseStudentRepository.findAllByIsDeletedIsTrue();
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

        existingCourseStudent.get().setActive(courseStudents.getActive());
        courseStudentRepository.save(existingCourseStudent.get());
        return true;
    }
}
