package imageResizer;

import exceptions.ImageCompressionException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageCompression {
    private final int size;
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    private float quality = 1f;
    private int width = 0;
    private int height = 0;

    public ImageCompression(int size) {
        this.size = size;
    }

    public byte[] write(BufferedImage bufferedImage) {

        final int iw = bufferedImage.getWidth();
        final int ih = bufferedImage.getHeight();
        if (width != iw || height != ih) {
            width = iw;
            height = ih;
            quality = 1f;
        }

        float size;
        do {
            size = compress(bufferedImage, quality);
            if (size > this.size) {
                quality *= 0.85;
            }
        } while (size > this.size);
        return byteArrayOutputStream.toByteArray();
    }

    private int compress(BufferedImage bufferedImage, float quality) {

        byteArrayOutputStream.reset();

        final JPEGImageWriteParam params = new JPEGImageWriteParam(null);
        params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        params.setCompressionQuality(quality);

        try (MemoryCacheImageOutputStream memoryCacheImageOutputStream = new MemoryCacheImageOutputStream(byteArrayOutputStream)) {
            final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            writer.setOutput(memoryCacheImageOutputStream);
            writer.write(null, new IIOImage(bufferedImage, null, null), params);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return byteArrayOutputStream.size();
    }


    public static BufferedImage compressImage(BufferedImage decodedImage, Integer maxBytes) throws ImageCompressionException {
        if (maxBytes < 0) {
            throw new ImageCompressionException();
        }
        final ImageCompression writer = new ImageCompression(maxBytes/2);
        assert decodedImage != null;
        String resultString = Base64.getEncoder().encodeToString(writer.write(decodedImage));
        return decodeImage(resultString);
    }

    private static BufferedImage fromByteArrayToBufferedImage(byte[] bytes)
            throws IOException {
        InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return ImageIO.read(byteArrayInputStream);

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


}
