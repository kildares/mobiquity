package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class APIFileReaderTest {

    private static final String FILE_PREFIX = "src/test/resources/";

    private static final String TEST_FILE_LINE_1 = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";

    @Test
    public void testGetValidateFileName() {

        try {
            File file = new File(FILE_PREFIX + "good/testFile.txt");
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

        File file2 = new File(FILE_PREFIX + "good/testFile.txt");
        Assertions.assertThrows(APIException.class, () -> APIFileReader.getValidatedFile(file2.getPath()));
    }

    @Test
    public void testCloseBufferedReader() {

        try {
            File file = new File(FILE_PREFIX + "good/testFile.txt");
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
            File file = new File(FILE_PREFIX + "good/testFile.txt");
            BufferedReader bufferedReader = APIFileReader.getBufferedReader(APIFileReader.getValidatedFile(file.getAbsolutePath()));
            str = bufferedReader.readLine();
            bufferedReader.close();
        } catch (APIException | IOException e) {
            fail();
        }

        Assertions.assertEquals(TEST_FILE_LINE_1, str);
    }
}
