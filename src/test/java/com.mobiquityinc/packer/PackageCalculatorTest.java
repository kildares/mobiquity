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

        final Map<Integer, List<BackpackItem>> lines = new HashMap<>();
        lines.put(1000, asList(new BackpackItem(1, 220, 330)));
        String result = PackageCalculator.calculate(lines);
        Assertions.assertEquals("1", result);

        Map<Integer, List<BackpackItem>> lines2 = new HashMap<>();
        lines2.put(10000, asList(new BackpackItem(1, 220, 330), new BackpackItem(2, 520, 510)));
        String result2 = PackageCalculator.calculate(lines2);
        Assertions.assertEquals("1, 2", result2);

        Map<Integer, List<BackpackItem>> lines3 = new HashMap<>();
        lines3.put(1000, asList(
                new BackpackItem(1, 220, 330),
                new BackpackItem(2, 400, 510),
                new BackpackItem(3, 542, 720),
                new BackpackItem(4, 52, 160),
                new BackpackItem(5, 230, 920)
        ));
        String result3 = PackageCalculator.calculate(lines3);
        Assertions.assertEquals("1, 2", result3);

    }

}
