package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Course;
import com.example.AcademicInformationSystem.models.Response;
import com.example.AcademicInformationSystem.repositories.CourseRepository;
import com.example.AcademicInformationSystem.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private QuizRepository quizRepository;

    /**
     * Menambahkan Mata Kuliah Baru.
     *
     * @param course   Mata kuliah yang akan ditambahkan.
     * @param response pesan untuk ditampilkan pada api,
     * @return              Data Mata kuliah jika berhasil ditambahkan, null jika gagal.
     */
    public Course createCourse(Course course, Response response){
        Optional<Course> existingCourse = courseRepository.findByName(course.getName());

        if (course.getName().isEmpty()){
            response.setMessage("Data must be filled in");
            return null;
        } else if (existingCourse.isPresent()){
            response.setMessage("Data Is Already Exists");
            return null;
        }

        response.setData(course);
        return courseRepository.save(course);
    }

    /**
     * Mengembalikan semua data course.
     *
     * @return          data course berdasarkan isDelete = false.
     */
    public List<Course> viewCourse(){
        return courseRepository.findByIsDeleteIsFalse();
    }


    /**
     *
     * @param page      custom nomor halaman
     * @param limit     custom data yang ditampilkan pada setiap page
     * @return          data page course berdasarkan isDelete = false.
     */
    public Page<Course> pageView(int page, int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Course> result =  courseRepository.findAllByIsDeleteIsFalseOrderByNameAsc(pageable);
        return new PageImpl(result.getContent(), PageRequest.of(page, limit), result.getTotalPages());
    }

    /**
     * Mengubah Nama Mata Kuliah.
     *
     * @param id        ID Course yang akan diubah
     * @param course    Mata kuliah yang akan diubah.
     * @param response  pesan untuk ditampilkan pada api,
     * @return              Data Mata kuliah jika berhasil diupdate, null jika gagal.
     */
    public Course updateCourse(Long id, Course course, Response response) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (!existingCourse.isPresent()) {
            response.setMessage("Course Not Found");
            return null;
        } else if (course.getName().equalsIgnoreCase(existingCourse.get().getName())) {
            response.setMessage("Data is already exists");
            return null;
        }

        existingCourse.get().setName(course.getName());

        // Save department updated
        response.setData(existingCourse);
        return courseRepository.save(existingCourse.get());
    }


    /**
     * Menghapus secara halus data Mata Kuliah.
     *
     * @param id        ID Course yang akan dihapus
     * @param response  pesan untuk ditampilkan pada api,
     * @return          Data Mata kuliah jika berhasil dihapus, null jika gagal.
     */
    public Course softDelete(Long id, Response response){
        Optional<Course> existingCourse = courseRepository.findByIdAndIsDeleteIsFalse(id);
        if (!existingCourse.isPresent()) {
            response.setMessage("Course Not Found");
            return null;
        }

        Course existingData = existingCourse.get();
        existingData.setDelete(true);
        // Save course deleted
        response.setData(existingCourse);
        return courseRepository.save(existingData);
    }
}
