package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class LineConverter {


    public static Map<Integer, List<BackpackItem>> convertLineToEntityPair(String line) {

        String l = StringUtils.deleteWhitespace(line);
        String[] data = l.split(":");

        HashMap<Integer, List<BackpackItem>> weightItems = new HashMap<>();

        String d = data[0];
        boolean match = d.matches("\\d+");
        if (data.length != 2 || !match) {
            return weightItems;
        }

        Integer weight = Integer.valueOf(data[0]);

        if (weight <= 0 || weight > 100) {
            return weightItems;
        }

        List<BackpackItem> items = convertBackPackItem(data[1]);

        if (items.isEmpty()) {
            return weightItems;
        }

        weightItems.put(weight, items);

        return weightItems;
    }

    private static List<BackpackItem> convertBackPackItem(String elements) {

        List<BackpackItem> backpackItems = new ArrayList<>();

        String[] rawItems = StringUtils.substringsBetween(elements, "(", ")");

        if (Objects.isNull(rawItems) || rawItems.length == 0) {
            return Collections.emptyList();
        }

        for (int i = 0; i < rawItems.length; i++) {

            Optional<BackpackItem> item = convertRawDataToObject(rawItems[i]);

            if (!item.isPresent()) {
                return Collections.emptyList();
            }

            backpackItems.add(item.get());
        }

        return backpackItems;

    }

    private static Optional<BackpackItem> convertRawDataToObject(String rawItem) {

        String[] raw = rawItem.split(",");

        if (Objects.isNull(raw) || raw.length != 3) {
            return Optional.empty();
        }

        return Optional.empty();
    }

}
