package test.erp_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import test.erp_project.domain.board.Board;
import test.erp_project.dto.board_dto.BoardInfoDto;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select new test.erp_project.dto.board_dto.BoardInfoDto(b.boardNum, d.deptName, p.positionName, u.name, b.title, b.createdDate)" +
            "from Board b " +
            "inner join b.user u " +
            "inner join u.dept d " +
            "inner join u.position p order by b.boardNum desc")
    Page<BoardInfoDto> findAllBoardInfoDto(Pageable pageable);

    @Query("select new test.erp_project.dto.board_dto.BoardInfoDto(b.boardNum, d.deptName, p.positionName, u.name, b.title, b.createdDate)" +
            "from Board b " +
            "inner join b.user u " +
            "inner join u.dept d " +
            "inner join u.position p where b.title like %:titleOrName% or u.name like %:titleOrName% order by b.boardNum desc")
    Page<BoardInfoDto> findBoardInfoDtoByTitle(Pageable pageable, @Param("titleOrName") String titleOrName);

    @Query("select new test.erp_project.dto.board_dto.BoardInfoDto(b.boardNum, d.deptName, p.positionName, u.name, b.title, b.createdDate)" +
            "from Board b " +
            "inner join b.user u " +
            "inner join u.dept d " +
            "inner join u.position p where u.userNum = :userNum order by b.boardNum desc")
    Page<BoardInfoDto> findAllBoardInfoDtoByUserNum(Pageable pageable,@Param("userNum") Long userNum);

    @Query("select new test.erp_project.dto.board_dto.BoardInfoDto(b.boardNum, d.deptName, p.positionName, u.name, b.title, b.createdDate)" +
            "from Board b " +
            "inner join b.user u " +
            "inner join u.dept d " +
            "inner join u.position p " +
            "where u.userNum = :userNum " +
            "and b.title like %:titleOrName% " +
            "or u.name like %:titleOrName% order by b.boardNum desc")
    Page<BoardInfoDto> findBoardInfoDtoByTitleAndUserNum(Pageable pageable, @Param("titleOrName") String titleOrName, @Param("userNum") Long userNum);

}
