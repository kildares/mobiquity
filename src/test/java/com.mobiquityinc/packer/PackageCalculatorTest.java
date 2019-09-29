package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class PackageCalculatorTest {

    @Test
    public void testPackageCalculator() {

//        final Map<Integer, List<BackpackItem>> lines = new HashMap<>();
//        lines.put(10, asList(new BackpackItem(1, 2.2, 3.3)));
//        String result = PackageCalculator.calculate(lines);
//        //Assertions.assertEquals("1", result);
//
//        Map<Integer, List<BackpackItem>> lines2 = new HashMap<>();
//        lines2.put(100, asList(new BackpackItem(1, 2.2, 3.3), new BackpackItem(2, 5.2, 5.1)));
//        String result2 = PackageCalculator.calculate(lines2);
        //Assertions.assertEquals("1, 2", result);

        Map<Integer, List<BackpackItem>> lines3 = new HashMap<>();
        lines3.put(15, asList(
                new BackpackItem(1, 2.2, 3.3),
                new BackpackItem(2, 4.0, 5.1),
                new BackpackItem(3, 5.42, 7.2),
                new BackpackItem(4, 0.52, 1.6),
                new BackpackItem(5, 2.3, 9.2)
        ));
        String result3 = PackageCalculator.calculate(lines3);
        Assertions.assertEquals("1, 2", result3);

    }

}
