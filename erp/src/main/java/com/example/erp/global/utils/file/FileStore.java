package com.example.erp.global.utils.file;


import com.example.erp.domain.user.dto.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileStore {

    // 해당 위치에 파일저장
    @Value("${file.dir}")
    private String fileDir;


    // 파일이름을 통해 최종적으로 파일경로 가져옴
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }


    // 여러개의 파일을 저장하는 메서드
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {

        List<UploadFile> storeFileResult= new ArrayList<UploadFile>();

        // 머리파트파일들을 순회하면서 파일저장
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFIle(multipartFile);
                storeFileResult.add(uploadFile);

            }
        }

        return storeFileResult;
    }



    // 멀티파트 파일을을 받아서 저장한 후, 업로드 파일로 반환
    public UploadFile storeFIle(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        //원래 파일이름
        String originalFilename = multipartFile.getOriginalFilename();
        // 서버에 저장하는 파일명생성
        String storeFileName = createStoreFileName(originalFilename);


        log.info("full path = {}", getFullPath(storeFileName));
        // transferTo -> 실제 파일을 디스크에 저장
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);
    }

    // 서버에 저장할 파일명 생성 메서드
    private String createStoreFileName(String originalFilename) {

        // 확장자
        String ext = extractExt(originalFilename);

        // 서버에 저장하는 파일명
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }


    // 확장자 추출하는 메서드
    private String extractExt(String originalFilename) {
        //오리지널 파일명에서 확장자 추출 (ex. .png, .jpg 등)
        int pos = originalFilename.lastIndexOf(".");
        // 파일명.jpg일경우 . 다음의 위치에 String을 추출
        String ext = originalFilename.substring(pos + 1);
        return ext;
    }

}

