package com.mobiquityinc.comparator;

import com.mobiquityinc.entities.BackpackItem;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class LineConverter {


    /**
     * Converts a line read from the file into a structure that can be easily handled
     *
     * @param line
     * @return a Map whose key is the total weight that can be carried and a list of all the items that will be processed
     */
    public static Map<Integer, List<BackpackItem>> convertLineToEntityPair(String line) {

        String l = StringUtils.deleteWhitespace(line);
        //Splits the line in a two elements array. The first is the total weight and the second is the list of elements
        String[] data = l.split(":");

        HashMap<Integer, List<BackpackItem>> weightItems = new HashMap<>();

        //Applies a Regular Expression to check if the total weight is a valid integer value
        String d = data[0];
        boolean match = d.matches("\\d+");
        if (data.length != 2 || !match) {
            return weightItems;
        }

        Integer weight = Integer.valueOf(data[0]);

        if (weight <= 0 || weight > 100) {
            return weightItems;
        }

        //Obtains the list of backpack items
        List<BackpackItem> items = convertBackPackItem(data[1]);

        if (items.isEmpty()) {
            return weightItems;
        }

        weight = weight * 100;

        weightItems.put(weight, items);

        return weightItems;
    }

    /**
     * Converts a String cotaining the data elements read from a file line to the List of objects
     *
     * @param elements
     * @return
     */
    private static List<BackpackItem> convertBackPackItem(String elements) {

        List<BackpackItem> backpackItems = new ArrayList<>();

        //Obtains an array of all items. i.e. {"1,50.5,€62", "2,45.1,€89"}
        String[] rawItems = StringUtils.substringsBetween(elements, "(", ")");

        if (Objects.isNull(rawItems) || rawItems.length == 0) {
            return Collections.emptyList();
        }

        //For each index of the string array, will convert to the entity object and add to the list of objects
        for (int i = 0; i < rawItems.length; i++) {

            Optional<BackpackItem> item = convertRawDataToObject(rawItems[i]);

            if (!item.isPresent()) {
                return Collections.emptyList();
            }

            backpackItems.add(item.get());
        }

        return backpackItems;

    }

    /**
     * Converts a String containing one item to a BackpackItem
     *
     * @param rawItem
     * @return
     */
    private static Optional<BackpackItem> convertRawDataToObject(String rawItem) {

        String[] raw = rawItem.split(",");

        if (Objects.isNull(raw) || raw.length != 3 || !Objects.equals(raw[2].charAt(0), '€')) {
            return Optional.empty();
        }

        try {
            //In the process of conversion, multiply the item weight and costs by 100 to convert to integer values
            //This should ease the comparison and avoid conversion of data later on while filtering the chosen elements
            Double weight = Double.valueOf(raw[1]);
            Integer w = Double.valueOf(weight * 100).intValue();

            Double cost = Double.valueOf(raw[2].substring(1));
            Integer c = Double.valueOf(cost * 100).intValue();

            if (weight > 100.0 || cost > 100.0) {
                return Optional.empty();
            }

            BackpackItem backpackItem = new BackpackItem(Integer.valueOf(raw[0]), w, c);

            return Optional.of(backpackItem);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
