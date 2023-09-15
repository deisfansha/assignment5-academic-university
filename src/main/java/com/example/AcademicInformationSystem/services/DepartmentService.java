package com.example.AcademicInformationSystem.services;

import com.example.AcademicInformationSystem.models.Department;
import com.example.AcademicInformationSystem.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.AcademicInformationSystem.repositories.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Menambahkan Data Jurusan.
     *
     * @param department     Jurusan yang akan ditambahkan.
     * @param response       pesan untuk ditampilkan pada api,
     * @return               Data jurusan jika berhasil ditambahkan dan bernilai true,
     *                       false jika gagal.
     */
    public Department createDepartment(Department department, Response response){
        Optional<Department> existingDepartment = departmentRepository.findByNameAndIsDeleteIsFalse(department.getName());
        System.out.println(existingDepartment);
        if (department.getName().isEmpty()){
            response.setMessage("Name Department Must Be Filled In");
            return null;
        } else if (existingDepartment.isPresent()){
            response.setMessage("Data Is Already Exists");
            return null;
        }
        response.setData(department);
        return departmentRepository.save(department);
    }

    /**
     * Mendapatkan Data Jurusan.
     *
     * @return              Data Jurusan berdasarkan isDelete = false.
     */
    public List<Department> getAll(){
        return departmentRepository.findByIsDeleteIsFalse();
    }

    /**
     *
     * @param page      custom nomor halaman
     * @param limit     custom data yang ditampilkan pada setiap page
     * @return          data page Jurusan berdasarkan isDelete = false.
     */
    public Page<Department> pageView(int page, int limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<Department> result =  departmentRepository.findAllByIsDeleteIsFalseOrderByIdAsc(pageable);
        return new PageImpl(result.getContent(), PageRequest.of(page, limit), result.getTotalPages());
    }

    /**
     * Mengubah Data Jurusan.
     *
     * @param id                ID Jurusan yang akan diubah,
     * @param department        Mengubah nama jurusan,
     * @param response          pesan untuk ditampilkan pada api,
     * @return                  Data Jurusan jika berhasil diubah dan null jika gagal.
     *
     */
    public Department updateDepartment(Long id, Department department, Response response) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);

        if (department.getName().isEmpty()){
            response.setMessage("Name Department Is Must Be Filled In");
            return null;
        } else if (!existingDepartment.isPresent()){
            response.setMessage("Department Not Found");
            return null;
        } else if (department.getName().equalsIgnoreCase(existingDepartment.get().getName())) {
            response.setMessage("Data is already exists");
            return null;
        }

        existingDepartment.get().setName(department.getName());

        // Save department updated
        response.setMessage("Success");
        response.setData(existingDepartment);
        return departmentRepository.save(existingDepartment.get());
    }

    /**
     * Menghapus secara halus Data Jurusan.
     *
     * @param id        ID Jurusan yang akan dihapus
     * @param response  pesan untuk ditampilkan pada api,
     * @return          Data Jurusan jika berhasil dihapus, null jika gagal.
     */
    public Department softDelete(Long id, Response response){
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (!existingDepartment.isPresent()){
            response.setMessage("Department Not Found");
            return null;
        }

        existingDepartment.get().setDelete(true);
        // Save department deleted
        response.setMessage("Success");
        response.setData(existingDepartment);
        return departmentRepository.save(existingDepartment.get());
    }

}
