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


}
