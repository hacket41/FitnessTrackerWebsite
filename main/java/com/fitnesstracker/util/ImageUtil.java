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
     * 
     * @param part the Part object containing the uploaded file
     * @return the extracted file name, or a default "download.png" if none found
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
     * Uploads an image to the specified directory path.
     * Creates the directory if it does not exist.
     * 
     * @param part the Part object containing the uploaded image
     * @param savePath the directory path where the image should be saved
     * @return true if upload is successful, false otherwise
     */
    public boolean uploadImage(Part part, String savePath) {
        File fileSaveDir = new File(savePath);

        // Create directories if they don't exist
        if (!fileSaveDir.exists()) {
            if (!fileSaveDir.mkdirs()) {
                return false; // Failed to create directory
            }
        }

        try {
            String imageName = getImageNameFromPart(part);
            String filePath = savePath + File.separator + imageName;
            part.write(filePath);  // Saves the uploaded file to disk
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;  // Upload failed due to IOException
        }
    }
}
