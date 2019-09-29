package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class APIFileReaderTest {

    private static final String TEST_FILE_LINE_1 = "25 : (1, 2.5,€50) (2, 10, €50) (3, 2.5, €50) (4, 2.5, €50) (5, 2.5, €50) (6, 2.5, €50) (7, 2.5, €50) (8, 2.5, €50) (9, 2.5, €50)";

    @Test
    public void testGetValidateFileName() {

        try {
            File file = new File("src/test/resources/good/testFile.txt");
            APIFileReader.getValidatedFile(file.getAbsolutePath());
        } catch (APIException e) {
            fail();
        }
    }


    @Test
    public void testInvalidFileName() {
        Assertions.assertThrows(APIException.class, () -> APIFileReader.getValidatedFile(null));

        Assertions.assertThrows(APIException.class, () -> APIFileReader.getValidatedFile(""));

        File file = new File("");
        Assertions.assertThrows(APIException.class, () -> APIFileReader.getValidatedFile(file.getPath()));

        File file2 = new File("src/test/resources/good/testFile.txt");
        Assertions.assertThrows(APIException.class, () -> APIFileReader.getValidatedFile(file2.getPath()));
    }

    @Test
    public void testCloseBufferedReader() {

        try {
            File file = new File("src/test/resources/good/testFile.txt");
            BufferedReader bufferedReader = APIFileReader.getBufferedReader(APIFileReader.getValidatedFile(file.getAbsolutePath()));
            bufferedReader.close();
        } catch (IOException | APIException e) {
            fail();
        }
    }

    @Test
    public void testReadLine() {

        String str = null;
        try {
            File file = new File("src/test/resources/good/testFile.txt");
            BufferedReader bufferedReader = APIFileReader.getBufferedReader(APIFileReader.getValidatedFile(file.getAbsolutePath()));
            str = bufferedReader.readLine();
            bufferedReader.close();
        } catch (APIException | IOException e) {
            fail();
        }

        Assertions.assertEquals(TEST_FILE_LINE_1, str);
    }
}
