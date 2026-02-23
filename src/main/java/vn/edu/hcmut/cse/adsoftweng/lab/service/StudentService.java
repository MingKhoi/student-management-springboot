package vn.edu.hcmut.cse.adsoftweng.lab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import vn.edu.hcmut.cse.adsoftweng.lab.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public List<Student> getAll() {
        return repository.findAll();
    }

    public List<Student> searchByName(String keyword) {
        List<Student> students = repository.findAll();
        System.out.println("cc");
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public Student getById(String id) {
        return repository.findById(id).orElse(null);
    }
}
