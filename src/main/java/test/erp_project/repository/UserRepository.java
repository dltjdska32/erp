package test.erp_project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import test.erp_project.domain.dept.Dept;
import test.erp_project.domain.position.Position;
import test.erp_project.domain.user.User;
import test.erp_project.dto.user_dto.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Slf4j
public class UserRepository {

    private final EntityManager em;

    // 아이디 저장.
    public void save(User user) {
        log.info("save user: {}", user);
        em.persist(user);

    }

        // 부서 직위 수정
        public void update(UserInfo userInfo, Dept dept, Position position) {

            User user = em.find(User.class, userInfo.getUserNum());

            if(user != null) {
                user.setDept(dept);
                user.setPosition(position);

            }
        }

        public Optional<User> findByUserNum(Long userNum) {
            User user = em.find(User.class, userNum);
            return Optional.ofNullable(user);
        }

    public Optional<User> findByEmail(String email) {
        try {
            User user = em.createQuery(
                            "select u from User u where u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    // 아이디 찾기
    public Optional<User> findById(String id) {
        try {
            User user = em.createQuery(
                            "select u from User u where u.userId = :id", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // 모든 직원 찾기
    public List<UserSearchDto> findAllUserSearchDto() {
        List<UserSearchDto> resultList = em.createQuery("select u.userId, u.name, d.deptName, p.positionName " +
                "from User u inner join u.dept d inner join u.position p", UserSearchDto.class).getResultList();
        log.info("resultList: {}", resultList.toString());
        return resultList;
    }

 // 이름과 일치하는 유저 찾기
    public List<User> findUserByName(String name) {
        return em.createQuery("select u from User u where u.name = : name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    //모든유저 찾기
    public List<User> findAllUser() {
        List<User> resultList = em.createQuery("select u from User u", User.class).getResultList();
        return resultList;
    }

    //유저와 유저의 휴가기록 조회하는 메서드
    public List<UserAndLeaveInfo> findUserAndLeaveInfo() {
        //coalesce -> 첫번째인자가 null이면 두번째인자가 기본값
        List<UserAndLeaveInfo> infos = em.createQuery("select new test.erp_project.dto.user_dto.UserAndLeaveInfo(u, l.endDate, l.startDate, coalesce(l.acceptanceStatus, false)) from " +
                "User u left join  LeaveLog l on u = l.user ", UserAndLeaveInfo.class).getResultList();
        return infos;
    }



}
