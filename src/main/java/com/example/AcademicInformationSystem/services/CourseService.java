package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Quiz;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.repositories.CourseRepository;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private QuizRepository quizRepository;
    private Response response;

    public void setResponse(Response response){
        this.response = response;
    }

    public Course createCourse(Course course, Response response){
        List<Course> existingCourse = courseRepository.findByName(course.getName().trim());

//        if (!validateInput(department.getName())){
//            return null;
//        }

        if (existingCourse !=null){
            response.setMessage("Data Is Already Exists");
            return null;
        }
        course.setDelete(false);
        response.setMessage("Success");
        response.setData(course);
        return courseRepository.save(course);
    }

    public List<Course> viewCourse(){
        return courseRepository.findAllNotDeleted();
    }

    public Course updateCourse(Long id, Course course, Response response) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (!existingCourse.isPresent()) {
            response.setMessage("Course Not Found");
            return null;
        }

        Course existingData = existingCourse.get();

        String newName = course.getName().trim();

        if (!newName.equalsIgnoreCase(existingData.getName())) {
            existingData.setName(newName);
        }else {
            response.setMessage("Data is already exists");
            return null;
        }

        // Save department updated
        response.setMessage("Success");
        response.setData(existingCourse);
        return courseRepository.save(existingData);
    }

    public Course softDelete(Long id, Response response){
        Optional<Course> existingCourse = courseRepository.findById(id);

        if (!existingCourse.isPresent()) {
            response.setMessage("Course Not Found");
            return null;
        }

        Course existingData = existingCourse.get();

        existingData.setDelete(true);
        // Save course deleted
        response.setMessage("Success");
        response.setData(existingCourse);
        return courseRepository.save(existingData);
    }

    public boolean addQuizToCourse(Long courseId, Quiz quiz, Response response) {
        List<Quiz> quizList = quizRepository.findByName(quiz.getName());
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (!optionalCourse.isPresent()) {
            response.setMessage("Course Not Found");
            return false;
        }

        if (quizList.isEmpty()) {
            response.setMessage("Quiz Not Found");
            return false;
        }

        Quiz existingQuiz = quizList.get(0);

        Course course = optionalCourse.get();
        course.getQuizes().add(existingQuiz); // Add the quiz to the course's list of quizzes
        courseRepository.save(course);
        return true;
    }


    private boolean validateFounded(Optional<Course> existingCourse){
        if (!existingCourse.isPresent()) {
            response.setMessage("Department Not Found");
            return false;
        }
        return true;
    }
}
