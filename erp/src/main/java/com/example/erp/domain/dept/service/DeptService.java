package com.example.erp.domain.dept.service;

import com.example.erp.domain.dept.entity.Dept;
import com.example.erp.domain.dept.exception.DeptException;
import com.example.erp.domain.dept.repository.DeptRepository;
import com.example.erp.global.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeptService {
    private final DeptRepository deptRepository;

    // 이름으로 부서찾는 메서드.
    public Dept findByDeptName(String deptName) {
        return deptRepository.findByDeptName(deptName)
                .orElseThrow(() -> new CustomException(DeptException.DEPT_NOT_FOUND));
    }


}
