package test.erp_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.user.User;
import test.erp_project.dto.user_dto.UserInfo;
import test.erp_project.dto.user_dto.UserSearchDto;

public interface UserRepositoryImpl extends JpaRepository<User, Long> {

    @Query("select new test.erp_project.dto.user_dto.UserInfo(u.userNum, u.name, u.tel, u.email, d.deptName, p.positionName, u.role, u.idPhotoStoredName) " +
            "from User u inner join  u.dept d inner join u.position p")
    Page<UserInfo> findAllUserSearchDto(Pageable pageable);


    @Query("select new test.erp_project.dto.user_dto.UserInfo(u.userNum, u.name, u.tel, u.email, d.deptName, p.positionName, u.role, u.idPhotoStoredName) " +
            "from User u inner join  u.dept d inner join u.position p where u.name like %:name%")
    Page<UserInfo> findUsersByName(@Param("name")String name, Pageable pageable);


    User findByUserNum(Long userNum);
}
