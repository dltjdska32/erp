package com.example.erp.domain.user.dto;

import com.example.erp.domain.user.entity.Role;
import lombok.*;

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
