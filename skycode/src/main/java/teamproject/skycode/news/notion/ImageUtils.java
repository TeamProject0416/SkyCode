package teamproject.skycode.news.notion;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static byte[] compressImage(byte[] originalImageBytes, float quality) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(originalImageBytes);
        BufferedImage originalImage = ImageIO.read(input);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", output);

        ByteArrayInputStream compressedInput = new ByteArrayInputStream(output.toByteArray());
        BufferedImage compressedImage = ImageIO.read(compressedInput);

        ByteArrayOutputStream compressedOutput = new ByteArrayOutputStream();
        ImageIO.write(compressedImage, "jpg", compressedOutput);

        return compressedOutput.toByteArray();
    }
}
