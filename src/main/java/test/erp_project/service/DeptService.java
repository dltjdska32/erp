package test.erp_project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.dept.Dept;
import test.erp_project.repository.DeptRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DeptService {

    private final DeptRepository deptRepository;


    public Dept getDept(String deptName) {
        Dept dept = deptRepository.findDeptByDeptName(deptName);
        return dept;
    }
}
