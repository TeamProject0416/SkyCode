package teamproject.skycode.news.notion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageToBase64 {

//    public static String convertToBase64(String imagePath) {
//        try {
//            Path path = Paths.get(imagePath);
//            byte[] imageBytes = Files.readAllBytes(path);
//            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//
//            return base64Image;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static String convertToBase64(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            String dataUri = "data:image/png;base64," + base64Image;
            return dataUri;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 예외 발생 시 null 반환
        }
    }
}
