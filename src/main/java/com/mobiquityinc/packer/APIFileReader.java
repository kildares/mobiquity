package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.io.*;
import java.util.Objects;

public class APIFileReader {


    public static void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Warning: Could not close file");
            e.printStackTrace();
        }
    }

    public static BufferedReader getBufferedReader(File file) throws APIException {
        try {
            System.out.println(file.getAbsolutePath());
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new APIException("Unable to read File.", e);
        }
    }

    public static File getValidatedFile(String filePath) throws APIException {

        if (Objects.isNull(filePath)) {
            throw new APIException("Invalid filePath. It cannot be null. You must provide an absolute path to a file");
        }

        File file = new File(filePath);

        if (file.isDirectory() || !file.isAbsolute()) {
            throw new APIException("Invalid file path. It cannot be a relative path or a directory. You must provide an absolute path to a file");
        }

        return file;
    }

    public static String readLine(BufferedReader bufferedReader) throws APIException {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new APIException("Error while reading file");
        }
    }


}
