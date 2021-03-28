package imageResizer;

import exceptions.ImageCompressionException;
import exceptions.ImageResizerException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageResizer {
    public static String resizeAndCompressBase64Image(String incomingStringBase64, int width, int maxBytes) throws
            ImageResizerException,
            IllegalArgumentException,
            ImageCompressionException {
        BufferedImage decodedImageFromString;
        BufferedImage compressedImage;
        BufferedImage resizedImage;
        String resultImage;

        /*
          Decoding string to BufferedImage
         */
        try {
            decodedImageFromString = decodeImage(incomingStringBase64);
        } catch (AssertionError e) {
            throw new AssertionError("Impossible things are happening in decoding");
        }

        /*
          Resizing to a specified width.
         */
        try {
            resizedImage = ImageResizer.resizeImage(decodedImageFromString, width);
        } catch (ImageResizerException e) {
            throw new ImageResizerException("Width cannot be zero or negative value");
        }

        /*
          Compression under a specified limit.
         */
        try {
             compressedImage = ImageCompression.compressImage(resizedImage, maxBytes);
         } catch (ImageCompressionException e) {
             throw new ImageCompressionException("Compression Parameter must be positive Integer");
         }

        /*
          Encoding BufferedImage to String
         */
        try {
            resultImage = encodeImage(compressedImage);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException();
        }
        return resultImage;
    }
    private static String encodeImage(BufferedImage compressedImage) throws IOException {
        byte[] output = fromBufferedImageToByteArray(compressedImage);
        return Base64.getEncoder().encodeToString(output);
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

    private static BufferedImage resizeImage(BufferedImage img, int width) throws ImageResizerException {
        if (width <= 0) {
            throw new ImageResizerException();
        }
        int h = (int)((float) img.getHeight() *((float) width /img.getWidth()));

        Image tmp = img.getScaledInstance(width, h, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, h, img.getType());
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private static BufferedImage fromByteArrayToBufferedImage(byte[] bytes)
            throws IOException {
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(byteArrayInputStream);
    }
    private static byte[] fromBufferedImageToByteArray(BufferedImage bufferedImage)
            throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


}
