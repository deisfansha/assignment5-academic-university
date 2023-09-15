package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.dto.request.DtoStudentRequest;
import com.example.AcademicInformationSystem.dto.response.DtoStudentResponse;
import com.example.AcademicInformationSystem.models.*;
import com.example.AcademicInformationSystem.repositories.CourseRepository;
import com.example.AcademicInformationSystem.repositories.DepartmentRepository;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import com.example.AcademicInformationSystem.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Student createStudent(DtoStudentRequest studentRequest, Response response) {
        Optional<Department> existingDepartment = departmentRepository.findByIdAndIsDeleteIsFalse(studentRequest.getCodeDepartment());
        List<Student> existingStudentsWithPhoneNumber = studentRepository.findByPhoneNumber(studentRequest.getPhoneNumber());

        if (studentRequest.getName().isEmpty()|| studentRequest.getGender().isEmpty() || studentRequest.getPhoneNumber().isEmpty()){
            response.setMessage("All data must be filled in");
            return null;
        } else if (!studentRequest.getName().matches("^[a-zA-Z -]*$") || !studentRequest.getGender().matches("^[a-zA-Z -]*$")){
            response.setMessage("Can only input the alphabet");
            return null;
        }else if (!studentRequest.getPhoneNumber().matches("^[0-9]{8,13}$")){
            response.setMessage("Format Number appropriate");
            return null;
        } else if (!(studentRequest.getGender().equalsIgnoreCase("Laki-Laki")|| studentRequest.getGender().equalsIgnoreCase("Perempuan"))){
            response.setMessage("There only 2 Gender");
            return null;
        } else if (!existingDepartment.isPresent()) {
            response.setMessage("Department Not Found");
            return null;
        } else if (existingStudentsWithPhoneNumber == null) {
            response.setMessage("Data already exists");
            return null;
        }

        // Ambil ID mahasiswa
        List<Student> students = studentRepository.findLast();
        Student lastStudent = students.get(0);

        String last = String.valueOf(lastStudent.getId());
        int codeNpm = Integer.parseInt(last) +1;

        // Get Year Now
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        // Set NPM
        String npm = studentRequest.getCodeDepartment() + String.valueOf(currentYear) + "00"+codeNpm;

        Student newStudent = new Student();
        newStudent.setName(studentRequest.getName());
        newStudent.setGender(studentRequest.getGender());
        newStudent.setPhoneNumber(studentRequest.getPhoneNumber());
        newStudent.setNpm(npm);
        newStudent.setDepartment(existingDepartment.get());

        response.setData(new DtoStudentResponse(newStudent.getNpm(), newStudent.getName(), newStudent.getGender(), newStudent.getPhoneNumber(), newStudent.getDepartment().getName()));
        return studentRepository.save(newStudent);
    }

    public List<DtoStudentResponse> viewStudent(){
        List<Student> student = studentRepository.findAllNotDeleted();
        List<DtoStudentResponse> studentList = new ArrayList<>();
        for (Student students: student){
            DtoStudentResponse studentResponse = new DtoStudentResponse(students.getNpm(), students.getName(), students.getGender(), students.getPhoneNumber(), students.getDepartment().getName());
            studentList.add(studentResponse);
        }
        return studentList;
    }

    public Page<DtoStudentResponse> pageView(int page, int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> result =  studentRepository.findAllByIsDeleteIsFalseOrderByNameAsc(pageable);
        List<DtoStudentResponse> studentList = new ArrayList<>();
        for (Student students: result.getContent()){
            DtoStudentResponse studentResponse = new DtoStudentResponse(students.getNpm(), students.getName(), students.getGender(), students.getPhoneNumber(), students.getDepartment().getName());
            studentList.add(studentResponse);
        }
        return new PageImpl(studentList, PageRequest.of(page, limit), result.getTotalPages());
    }

    public Boolean updateStudent(Long id, DtoStudentRequest studentRequest,Response response){
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (!existingStudent.isPresent()){
            response.setMessage("Student Not Found");
            return false;
        } else if (!studentRequest.getName().matches("^[a-zA-Z -]*$")){
            response.setMessage("Can only input the alphabet");
            return false;
        } else if (!studentRequest.getPhoneNumber().matches("^[0-9]{8,13}$")){
            response.setMessage("Format Number appropriate");
            return false;
        }

        existingStudent.get().setName(studentRequest.getName());
        existingStudent.get().setPhoneNumber(studentRequest.getPhoneNumber());
        studentRepository.save(existingStudent.get());

        DtoStudentResponse studentResponse = new DtoStudentResponse(existingStudent.get().getNpm(),
                existingStudent.get().getName(), existingStudent.get().getGender(),
                existingStudent.get().getPhoneNumber(), existingStudent.get().getDepartment().getName());
        response.setData(studentResponse);
        return true;
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
        DtoStudentResponse studentResponse = new DtoStudentResponse(existingStudent.get().getNpm(),
                existingStudent.get().getName(), existingStudent.get().getGender(),
                existingStudent.get().getPhoneNumber(), existingStudent.get().getDepartment().getName());
        // Save department deleted
        response.setMessage("Success");
        response.setData(studentResponse);
        return studentRepository.save(existingData);
    }

    public int generateId(){
        idNpm+=11;
        return idNpm;
    }

}
