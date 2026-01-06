package com.example.erp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UploadFile {
    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버에서 관리하는
    // 파일명 uuid로 생성

}
