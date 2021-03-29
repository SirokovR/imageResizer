import exceptions.ImageCompressionException;
import exceptions.ImageResizerException;
import exceptions.InvalidJPGException;
import imageResizer.ImageResizer;
import utils.Utils;
import java.io.IOException;

public class Starter {
    public static void main(String[] args) {
        String pathToFile = "resources/images/valid.jpg";
        String pathToResultFile = "resources/images/result.jpg";
        try{
        String incomingString = Utils.encodedFileToBase64String(pathToFile);
        String outgoingString = ImageResizer.resizeAndCompressBase64Image(incomingString,1600,400000);
        Utils.writeEncodedImageToFile(outgoingString,pathToResultFile);
        }catch (ImageResizerException | ImageCompressionException | IOException | InvalidJPGException e){
            e.printStackTrace();
        }
    }
}
