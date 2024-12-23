package test.erp_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import test.erp_project.file.FileStore;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class ImageController {


    private final FileStore fileStore;

    // 이미지 다운로호드
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downlaodImage(@PathVariable("filename") String filename) throws MalformedURLException {

        //    file:/C:~~~~~~~/이미지파일명(uuid)  을통해서 내부 파일에 직접 접근 리소스를 가져와 스트림이되어 반환
        return new UrlResource("file:" + fileStore.getFullPath(filename));

    }

}
