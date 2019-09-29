package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.fail;

public class PackerTest {


    @Test
    public void testInvalidFileContent() {

        final File file = new File("src/test/resources/bad/cost_bigger_100.txt");
        Assertions.assertThrows(APIException.class, () -> Packer.pack(file.getAbsolutePath()));

        final File file2 = new File("src/test/resources/bad/empty.txt");
        Assertions.assertThrows(APIException.class, () -> Packer.pack(file.getAbsolutePath()));

        final File file3 = new File("src/test/resources/bad/max_backpack_weight_bigger_100.txt");
        Assertions.assertThrows(APIException.class, () -> Packer.pack(file.getAbsolutePath()));

        final File file4 = new File("src/test/resources/bad/negative_backpack_weight.txt");
        Assertions.assertThrows(APIException.class, () -> Packer.pack(file.getAbsolutePath()));

        final File file5 = new File("src/test/resources/bad/misformat.txt");
        Assertions.assertThrows(APIException.class, () -> Packer.pack(file.getAbsolutePath()));

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

        Assertions.assertEquals("1\n-\n1, 2, 6, 9", output);


    }

}
