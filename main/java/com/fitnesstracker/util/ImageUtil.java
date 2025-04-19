package com.fitnesstracker.util;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling image file uploads.
 */
public class ImageUtil {

    /**
     * Extracts the file name from a Part object.
     */
    public String getImageNameFromPart(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        String imageName = null;

        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                imageName = s.substring(s.indexOf('=') + 2, s.length() - 1);
            }
        }

        return (imageName == null || imageName.isEmpty()) ? "download.png" : imageName;
    }

    /**
     * Uploads an image to the given path.
     */
    public boolean uploadImage(Part part, String savePath) {
        File fileSaveDir = new File(savePath);

        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdirs()) {
                return false; 
            }
        }

        try {
            String imageName = getImageNameFromPart(part);
            String filePath = savePath + File.separator + imageName;
            part.write(filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
