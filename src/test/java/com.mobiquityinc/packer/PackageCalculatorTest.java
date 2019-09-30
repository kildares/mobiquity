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
        lines.put(1000, asList(new BackpackItem(1, 220, 770)));
        List<String> result = PackageCalculator.calculate(lines);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("1", result.get(0));

        Map<Integer, List<BackpackItem>> lines2 = new HashMap<>();
        lines2.put(1000, asList(new BackpackItem(1, 220, 330), new BackpackItem(2, 520, 510)));
        List<String> result2 = PackageCalculator.calculate(lines2);
        Assertions.assertEquals(2, result2.size());
        Assertions.assertEquals("1", result2.get(0));
        Assertions.assertEquals("2", result2.get(1));

        Map<Integer, List<BackpackItem>> lines3 = new HashMap<>();
        lines3.put(1000, asList(
                new BackpackItem(1, 220, 330),
                new BackpackItem(2, 400, 510),
                new BackpackItem(3, 542, 720),
                new BackpackItem(4, 52, 160),
                new BackpackItem(5, 230, 920)
        ));
        List<String> result3 = PackageCalculator.calculate(lines3);
        Assertions.assertEquals(3, result3.size());
        Assertions.assertEquals("1", result3.get(0));
        Assertions.assertEquals("3", result3.get(1));
        Assertions.assertEquals("5", result3.get(2));

    }

}
