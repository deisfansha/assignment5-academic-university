package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.*;
import com.example.AcademicInformationSystem.repositories.CourseRepository;
import com.example.AcademicInformationSystem.repositories.DepartmentRepository;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import com.example.AcademicInformationSystem.repositories.StudentRepository;
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

    public Student getStudentById(Long id){
        return studentRepository.findById(id).get();
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
