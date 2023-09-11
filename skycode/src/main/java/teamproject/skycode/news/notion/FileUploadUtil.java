package teamproject.skycode.news.notion;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        File file = new File(uploadDir + fileName);
        multipartFile.transferTo(file);
    }
}
