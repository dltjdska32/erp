package test.erp_project.dto.user_dto;

import lombok.*;
import test.erp_project.domain.user.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    private Long userNum;

    private String name;

    private String email;

    private String deptName;

    private String tel;

    private String positionName;

    private Role role;

    private String idPhotoName;

}


