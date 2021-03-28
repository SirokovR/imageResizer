package utils;

import exceptions.ImageCompressionException;
import exceptions.ImageResizerException;
import exceptions.InvalidJPGException;
import imageResizer.ImageResizer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void encodedFileToBase64String() {
    }

    @Test
    void writeEncodedImageToFile() throws IOException, ImageResizerException, ImageCompressionException {
        String pathToResultFile = "src/test/resources/images/resultFromWritingTest.jpg";
        String incomingString = Files.readString(Paths.get("src/test/resources/string/base64.txt"), StandardCharsets.UTF_8);
        String outgoingString = ImageResizer.resizeAndCompressBase64Image(incomingString,100,4000);
        Utils.writeEncodedImageToFile(outgoingString,pathToResultFile);
    }

    @Test
    void test_EncodedFileToBase64StringThrowsExceptionOnNotJPEGFile(){
        assertThrows(InvalidJPGException.class, () ->{
            String pathToFile = "resources/images/invalid.jpg";
            Utils.encodedFileToBase64String(pathToFile);
        });
    }

    @Test
    void test_EncodedFileToBase64StringThrowsExceptionOnMissingFile(){
        assertThrows(IOException.class, () ->{
            String pathToFile = "resources/images/missing.jpg";
            Utils.encodedFileToBase64String(pathToFile);
        });
    }

    @Test
    void test_EncodedFileToBase64StringThrowsIOException(){
        assertThrows(IOException.class, () ->{
            String pathToFile = "resources/images/ghost.jpg";
            Utils.encodedFileToBase64String(pathToFile);
        });
    }
}
