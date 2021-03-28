package imageResizer;

import exceptions.ImageCompressionException;
import exceptions.ImageResizerException;
import exceptions.InvalidJPGException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImageResizerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test_ImageResizerOnCorrectInputWidth() {
        int expectedWidth = 900;
        String pathToFile = "resources/images/valid.jpg";
        BufferedImage expectedImage = null;
        try{
            String incomingString = Utils.encodedFileToBase64String(pathToFile);
            String outgoingString = ImageResizer.resizeAndCompressBase64Image(incomingString,900,400000);
            expectedImage = decodeImage(outgoingString);
        }catch (ImageResizerException | ImageCompressionException | IOException | InvalidJPGException e){
            e.printStackTrace();
        }
        assert expectedImage != null;
        assertEquals(expectedWidth,expectedImage.getWidth());
    }

    @Test
     void test_ImageResizerThrowsExceptionOnBadInputWidth(){
        assertThrows(ImageResizerException.class, () -> {
            int badInputWidth = -900;
            String incomingString = Files.readString(Paths.get("src/test/resources/string/base64.txt"), StandardCharsets.UTF_8);
            ImageResizer.resizeAndCompressBase64Image(incomingString, badInputWidth, 400000);
        });
    }

    @Test
    void test_ImageResizerOnCorrectInputCompressionValue() {
        long expectedMaxBytes = 4000000;
        String pathToFile = "resources/images/valid.jpg";
        String pathToResultFile = "src/test/resources/images/resultFromCompressionTest.jpg";
        try{
            String incomingString = Utils.encodedFileToBase64String(pathToFile);
            String outgoingString = ImageResizer.resizeAndCompressBase64Image(incomingString,1900,400000);
            Utils.writeEncodedImageToFile(outgoingString,pathToResultFile);
        }catch (ImageResizerException | ImageCompressionException | IOException | InvalidJPGException e){
            e.printStackTrace();
        }
        File file = new File("resources/images/resultFromCompressionTest.jpg");
        long actualBytes = file.length();
        assert(expectedMaxBytes >= actualBytes);
    }

    @Test
    void test_ImageResizerThrowsExceptionOnBadInputCompressionValue(){
        assertThrows(ImageCompressionException.class, () -> {
            int maxSizeInBytes = -40000;
            String pathToFile = "resources/images/valid.jpg";
            String incomingString = Utils.encodedFileToBase64String(pathToFile);
            ImageResizer.resizeAndCompressBase64Image(incomingString, 100, maxSizeInBytes);
        });
    }

    private static BufferedImage decodeImage(String incomingStringForDecoding) {
        byte[] bytesFromDecode = Base64.getDecoder().decode(incomingStringForDecoding);
        BufferedImage images = null;
        try {
            images = fromByteArrayToBufferedImage(bytesFromDecode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }

    private static BufferedImage fromByteArrayToBufferedImage(byte[] bytes)
            throws IOException {
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(byteArrayInputStream);
    }
}
