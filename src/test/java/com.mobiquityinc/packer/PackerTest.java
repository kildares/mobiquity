package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class PackerTest {

    @Test
    public void testInvalidFileContent() throws APIException {

        final File file = new File("src/test/resources/bad/cost_bigger_100.txt");

        Assertions.assertEquals("1\n-\n", Packer.pack(file.getAbsolutePath()));

        final File file2 = new File("src/test/resources/bad/empty.txt");
        Assertions.assertEquals("0\n-\n", Packer.pack(file2.getAbsolutePath()));

        final File file3 = new File("src/test/resources/bad/max_backpack_weight_bigger_100.txt");
        Assertions.assertEquals("1\n-\n", Packer.pack(file3.getAbsolutePath()));

        final File file4 = new File("src/test/resources/bad/negative_backpack_weight.txt");
        Assertions.assertEquals("1\n-\n", Packer.pack(file4.getAbsolutePath()));

        final File file5 = new File("src/test/resources/bad/misformat.txt");
        Assertions.assertEquals("4\n-\n", Packer.pack(file5.getAbsolutePath()));

    }

    @Test
    public void testSingleLine() {
        final File file5 = new File("src/test/resources/good/singleLine.txt");
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
        final File file5 = new File("src/test/resources/good/testFile.txt");
        String output = null;
        try {
            output = Packer.pack(file5.getAbsolutePath());
        } catch (APIException e) {
            fail();
        }

        Assertions.assertEquals("1\n-\n1, 2, 6, 9", output);


    }

}
