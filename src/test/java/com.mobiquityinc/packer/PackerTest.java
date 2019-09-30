package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class PackerTest {

    private static final String FILE_PREFIX = "src/test/resources/";

    @Test
    public void testInvalidFileContent() throws APIException {

        final File file = new File(FILE_PREFIX + "bad/cost_bigger_100.txt");

        Assertions.assertEquals("1\n-\n", Packer.pack(file.getAbsolutePath()));

        final File file2 = new File(FILE_PREFIX + "bad/empty.txt");
        Assertions.assertEquals("0\n-\n", Packer.pack(file2.getAbsolutePath()));

        final File file3 = new File(FILE_PREFIX + "bad/max_backpack_weight_bigger_100.txt");
        Assertions.assertEquals("1\n-\n", Packer.pack(file3.getAbsolutePath()));

        final File file4 = new File(FILE_PREFIX + "bad/negative_backpack_weight.txt");
        Assertions.assertEquals("1\n-\n", Packer.pack(file4.getAbsolutePath()));

        final File file5 = new File(FILE_PREFIX + "bad/misformat.txt");
        Assertions.assertEquals("4\n-\n", Packer.pack(file5.getAbsolutePath()));

    }

    @Test
    public void testSingleLine() {
        final File file5 = new File(FILE_PREFIX + "good/singleLine.txt");
        String output = null;
        try {
            output = Packer.pack(file5.getAbsolutePath());
        } catch (APIException e) {
            fail();
        }

        Assertions.assertEquals("1\n-\n4\n", output);


    }

    @Test
    public void testSeveralLines() {
        final File file5 = new File(FILE_PREFIX + "good/testFile.txt");
        String output = null;
        try {
            output = Packer.pack(file5.getAbsolutePath());
        } catch (APIException e) {
            fail();
        }

        Assertions.assertEquals("4\n-\n4\n\n2, 7\n8, 9\n", output);

    }

}
