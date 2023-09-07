package teamproject.skycode.news.notion;

import java.io.*;
import java.nio.file.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Base64;
import javax.imageio.*;

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
