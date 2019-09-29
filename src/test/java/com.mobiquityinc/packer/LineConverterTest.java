package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.mobiquityinc.packer.LineConverter.convertLineToEntityPair;

public class LineConverterTest {

    @Test
    public void testLineConverter() {

        Map<Integer, List<BackpackItem>> convertLineToEntityPair = convertLineToEntityPair("25 : (1, 2.5,€50) (2, 10, €50) (3, 2.5, €50)");

        Assertions.assertEquals(convertLineToEntityPair.keySet().size(), 1);
        convertLineToEntityPair.forEach((weight, backPackList) -> {
            Assertions.assertEquals(weight, 25);

            Assertions.assertEquals(1, backPackList.get(0).getIndex());
            Assertions.assertEquals(2.5, backPackList.get(0).getWeight());
            Assertions.assertEquals(50, backPackList.get(0).getCost());

            Assertions.assertEquals(2, backPackList.get(1).getIndex());
            Assertions.assertEquals(10, backPackList.get(1).getWeight());
            Assertions.assertEquals(50, backPackList.get(1).getCost());

            Assertions.assertEquals(3, backPackList.get(2).getIndex());
            Assertions.assertEquals(2.5, backPackList.get(2).getWeight());
            Assertions.assertEquals(50, backPackList.get(2).getCost());
        });

    }


    @Test
    public void testLineConverterFormatError() {

        final Map<Integer, List<BackpackItem>> converted = convertLineToEntityPair("100 (1, 2.5,€50) (2, 10, €50) (3, 2.5, €50)");
        Assertions.assertTrue(converted.isEmpty());

        final Map<Integer, List<BackpackItem>> converted2 = convertLineToEntityPair(": (1, 2.5,€50) (2, 10, €50) (3, 2.5, €50)");
        Assertions.assertTrue(converted2.isEmpty());

        final Map<Integer, List<BackpackItem>> converted3 = convertLineToEntityPair("100 : (1, 2.5,€50) (2, 10, €50) :(3, 2.5, €50)");
        Assertions.assertTrue(converted3.isEmpty());


    }

    @Test
    public void testLineConverterInvalidWeight() {


        final Map<Integer, List<BackpackItem>> converted = convertLineToEntityPair("101 : (1, 2.5,€50) (2, 10, €50) (3, 2.5, €50)");
        Assertions.assertTrue(converted.isEmpty());

        final Map<Integer, List<BackpackItem>> converted2 = convertLineToEntityPair("0 : (1, 2.5,€50) (2, 10, €50) (3, 2.5, €50)");
        Assertions.assertTrue(converted2.isEmpty());

    }

    @Test
    public void testLineConverterInvalidItem() {


        final Map<Integer, List<BackpackItem>> converted = convertLineToEntityPair("10 :");
        Assertions.assertTrue(converted.isEmpty());

        final Map<Integer, List<BackpackItem>> converted2 = convertLineToEntityPair("10 : ()");
        Assertions.assertTrue(converted2.isEmpty());

        final Map<Integer, List<BackpackItem>> converted3 = convertLineToEntityPair("10 : a");
        Assertions.assertTrue(converted3.isEmpty());

        final Map<Integer, List<BackpackItem>> converted4 = convertLineToEntityPair("10 : (213,523,124,46)");
        Assertions.assertTrue(converted4.isEmpty());

        final Map<Integer, List<BackpackItem>> converted5 = convertLineToEntityPair("10 : (ad, 14, €43)");
        Assertions.assertTrue(converted5.isEmpty());

        final Map<Integer, List<BackpackItem>> converted6 = convertLineToEntityPair("10 : (1, 12dsf, €43)");
        Assertions.assertTrue(converted6.isEmpty());

        final Map<Integer, List<BackpackItem>> converted7 = convertLineToEntityPair("10 : (1, 12.22, rt2)");
        Assertions.assertTrue(converted7.isEmpty());


    }

}
