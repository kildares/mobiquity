package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.io.*;
import java.util.Objects;

/**
 * This class is responsible for handling operations within the files to open, read and close the handler.
 */
public class APIFileReader {


    /**
     * Closes the BufferedReader. Will log a Warning, as this should be done in the end of the operation
     *
     * @param bufferedReader
     */
    public static void closeBufferedReader(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Warning: Could not close file");
            e.printStackTrace();
        }
    }

    /**
     * Obtains the BufferedReader to read files
     *
     * @param file
     * @return
     * @throws APIException when it is unable to read the chosen file under any circumstances
     */
    public static BufferedReader getBufferedReader(File file) throws APIException {
        try {
            System.out.println(file.getAbsolutePath());
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new APIException("Unable to read File.", e);
        }
    }

    /**
     * Validates the file received as parameter. Checks if it is null and if it is an absolute path
     *
     * @param filePath
     * @return
     * @throws APIException
     */
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

    /**
     * Reads the next line in the file.
     *
     * @param bufferedReader
     * @return
     * @throws APIException
     */
    public static String readLine(BufferedReader bufferedReader) throws APIException {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            throw new APIException("Error while reading file");
        }
    }

}
