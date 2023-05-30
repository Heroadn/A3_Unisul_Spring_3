package com.example.demo.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class Bruxaria {
    public static final String IMAGE_PATH = "upload/";
    public static String getBase64Ext(String base64)
    {
        return base64.substring(
                base64.indexOf("/") + 1,
                base64.indexOf(";"));
    }

    public static String getBase64Image(String base64)
    {
        //'data:image/png;base64,data...'
        return base64.split(",")[1];
    }

    public static String getAbsoluteResoucesPath()
    {
        ClassPathResource tmpPath = new ClassPathResource("src/main/resources");
        File outPath = new File(tmpPath.getPath());
        return outPath.getAbsolutePath();
    }

    public static String getAbsoluteImagePath()
    {
        //ClassPathResource tmpPath = new ClassPathResource("src/main/resources");
        ClassPathResource tmpPath = new ClassPathResource(IMAGE_PATH);
        File outPath = new File(tmpPath.getPath());
        return outPath.getAbsolutePath() + "/";
    }

    public static String toImageFilename(String name, String ext)
    {
        return name + "." + ext;
    }

    public static void writeBase64ImageToPath(String base64Image, String path)
    {
        byte[] bytes = Base64.decodeBase64(base64Image);
        //File file = new File("newimage.png");
        //ImageIO.write(myJpegImage, "png", file);

        try (OutputStream stream = new FileOutputStream(path)) {
            stream.write(bytes);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
