package vn.edu.hcmut.cse.adsoftweng.lab.repository;

import vn.edu.hcmut.cse.adsoftweng.lab.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}