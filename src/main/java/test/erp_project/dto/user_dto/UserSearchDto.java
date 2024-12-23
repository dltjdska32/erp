package test.erp_project.dto.user_dto;


import lombok.*;
import test.erp_project.domain.dept.Dept;
import test.erp_project.domain.position.Position;
import test.erp_project.domain.user.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDto {
/*
    private Long userNum;

    private String userId;

    private String name;

    private String tel;

    private String email;

    private Role role;

    private int remainedLeave;

    private Position position;

    private Dept dept;
*/

    private String userId;
    private String name;
    private String deptName;
    private String positionName;
}
