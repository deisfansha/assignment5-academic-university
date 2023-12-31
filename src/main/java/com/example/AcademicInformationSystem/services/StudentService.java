package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.*;
import com.example.AcademicInformationSystem.repositories.CourseRepository;
import com.example.AcademicInformationSystem.repositories.DepartmentRepository;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import com.example.AcademicInformationSystem.repositories.ScoreRepository;
import com.example.AcademicInformationSystem.repositories.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private ScoreRepository scoreRepository;
    private int idNpm = 0;
    private Response response;

    public void setResponse(Response response){
        this.response = response;
    }

    public Student createStudent(Student student, Response response) {
        String trimmedName = student.getName().trim();
        String trimmedGender = student.getGender().trim();
        String trimmedPhoneNumber = student.getPhoneNumber().trim();

        Long departmentId = student.getDepartment().getId();
        Optional<Department> existingDepartment = departmentRepository.findById(departmentId);

        if (trimmedName.isEmpty() || trimmedGender.isEmpty() || trimmedPhoneNumber.isEmpty()){
            response.setMessage("All data must be filled in");
            return null;
        }

        if (!trimmedName.matches("^[a-zA-Z -]*$") || !trimmedGender.matches("^[a-zA-Z -]*$")){
            response.setMessage("Can only input the alphabet");
            return null;
        }

        if (!trimmedPhoneNumber.matches("^[0-9]{8,13}$")){
            response.setMessage("Format Number appropriate");
            return null;
        }

        if (!(trimmedGender.equalsIgnoreCase("Laki-Laki")|| trimmedGender.equalsIgnoreCase("Perempuan"))){
            response.setMessage("There only 2 Gender");
            return null;
        }

        if (!existingDepartment.isPresent()) {
            response.setMessage("Department Not Found");
            return null;
        }

        List<Student> existingStudentsWithPhoneNumber = studentRepository.findByPhoneNumber(trimmedPhoneNumber);

        if (existingStudentsWithPhoneNumber == null) {
            response.setMessage("Data already exists");
            return null;
        }
        // Ambil ID mahasiswa
        List<Student> students = studentRepository.findLast();
        Student lastStudent = students.get(0);
        String last = String.valueOf(lastStudent.getId());

        // Get Year Now
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int codeNpm = Integer.parseInt(last) +1;

        // Set NPM
        String npm = departmentId + String.valueOf(currentYear) + "00"+codeNpm;

        student.setName(trimmedName);
        student.setGender(trimmedGender);
        student.setPhoneNumber(trimmedPhoneNumber);
        student.setNpm(npm);
        student.setDelete(false);

        response.setMessage("Success");
        response.setData(student);
        return studentRepository.save(student);
    }

    public List<Student> viewStudent(){
        return studentRepository.findAllNotDeleted();
    }

    public List<Scores> viewScoresForStudent(Long studentId) {
        List<Scores> scores = scoreRepository.findByStudentId(studentId);
        if (scores.isEmpty()) {
            return null;
        }

        return scores;
    }

    public Student softDelete(Long id, Response response){
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (!existingStudent.isPresent()){
            response.setMessage("Student Not Found");
            return null;
        }

        Student existingData = existingStudent.get();

        existingData.setDelete(true);
        // Save department deleted
        response.setMessage("Success");
        response.setData(existingStudent);
        return studentRepository.save(existingData);
    }

    public boolean enrollStudentInCourse(EnrollRequest enrollRequest, Response response) {
        Long studentId = enrollRequest.getStudentId();
        Long courseId = enrollRequest.getCourseId();
        Student student = studentRepository.findById(studentId).orElse(null);
        if(student == null){
            response.setMessage("Student Not Found");
            return false;
        }

        Course course = courseRepository.findById(courseId).orElse(null);
        if(course == null){
            response.setMessage("Course Not Found");
            return false;
        }

        // Cek apakah mahasiswa sudah terdaftar pada mata kuliah ini
        if (student.getCourses().contains(course)) {
            response.setMessage("Student Is Already Exists In Course");
            return false;
        }

        // Lakukan pendaftaran mahasiswa ke mata kuliah
        student.getCourses().add(course);
        studentRepository.save(student);
        return true;
    }

    public boolean addScore(Long studentId, Long courseId, Long quizId, ScoreRequest scoreRequest, Response response){
        List<Scores> student = scoreRepository.findByStudentId(studentId);
        List<Scores> course = scoreRepository.findByCorurseId(courseId);
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
//        List<Scores> scores = scoreRepository.findByStudentsIdCourseIdAndQuizId(studentId, courseId, quizId);
        if (student.isEmpty()){
            response.setMessage("Student Not Found");
            return false;
        }

        if (course.isEmpty()){
            response.setMessage("Course In Student Not Found");
            return false;
        }

//        ObjectMapper object = new ObjectMapper();
//        try {
//            System.out.println(object.writeValueAsString(scores));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        if (course.get().getQuizes().isEmpty()){
//            response.setMessage("Quiz In Course Not Found");
//            return false;
//        }

        if (scoreRequest.getScore()<0 || scoreRequest.getScore()>=100){
            response.setMessage("Format is not appropriate");
            return false;
        }

        Scores score = new Scores();

        score.setStudent((Student) student);
        score.setCourse((Course) course);
        score.setQuiz(quiz);
        score.setValue(scoreRequest.getScore());

        response.setMessage("Success");
        response.setData(score);
        scoreRepository.save(score);
        return true;
    }

    private boolean validateInputStudent(String nameStudent, String genderStudent, String numberPhone){
        // Validasi nama harus berupa alfabet
        if (nameStudent.isEmpty() || genderStudent.isEmpty() || numberPhone.isEmpty()){
            response.setMessage("All data must be filled in");
            return false;
        }

        if (!nameStudent.matches("^[a-zA-Z -]*$") || !genderStudent.matches("^[a-zA-Z -]*$")){
            response.setMessage("Can only input the alphabet");
            return false;
        }

        if (!(genderStudent.equalsIgnoreCase("Laki-Laki")|| genderStudent.equalsIgnoreCase("Perempuan"))){
            response.setMessage("There only 2 Gender");
            return false;
        }
        return true;
    }
    public int generateId(){
        idNpm+=11;
        return idNpm;
    }

}
