package utils;

import exceptions.InvalidJPGException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.Base64;

public class Utils {
    public static String encodedFileToBase64String(String pathToFile) throws InvalidJPGException,IOException {

        byte[] fileContent;
        try {
            if(isNotJpg(new File(pathToFile))){
                throw new InvalidJPGException("JPG file is invalid");
            }
            fileContent = FileUtils.readFileToByteArray(new File(pathToFile));
        }catch (IOException e) {
            throw new IOException("File opening fail, check a path to the file");
        }
        return Base64.getEncoder().encodeToString(fileContent);
    }
    public static void writeEncodedImageToFile(String stringForDecoding, String outputFilePath) throws IOException {
            File outputFile = new File( outputFilePath);
            byte[] decodedBytes = Base64
                    .getDecoder()
                    .decode(stringForDecoding);
            try {
                FileUtils.writeByteArrayToFile(outputFile, decodedBytes);
            } catch (IOException e) {
               throw new IOException("File writing fail, check a path and file name extension");
            }
        }


    public static Boolean isNotJpg(File filename) throws IOException {
        try (DataInputStream ins = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
            return ins.readInt() != 0xffd8ffe0;
        }catch (IOException e){
            throw new IOException();
        }
    }
    }

