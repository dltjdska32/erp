package com.example.erp.domain.dept.repository;


import com.example.erp.domain.dept.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeptRepository extends JpaRepository<Dept, Long>, DeptRepositoryCustom {

    Optional<Dept> findByDeptName(String deptName);
}
