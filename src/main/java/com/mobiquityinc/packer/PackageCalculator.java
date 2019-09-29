package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class PackageCalculator {

    /**
     * Receives the a Map with only 1 key-value, representing a line read from the file and returns a String with
     * the chosen items indexes
     *
     * @param dataToCalc
     * @return
     */
    public static String calculate(Map<Integer, List<BackpackItem>> dataToCalc) {

        Integer weight = dataToCalc.keySet().iterator().next();
        List<BackpackItem> items = dataToCalc.get(weight);

        //Sorts the list of items in increasing order
        Collections.sort(items, new BackPackItemComparator());

        Integer lines = items.size();

        Integer columns = weight + 1;

        //Starts a matrix to calculate results
        Integer[][] resultMatrix = getInitializedMatrix(columns, lines);

        resultMatrix = calculateResultMatrix(resultMatrix, columns, lines, items);

        System.out.println("last: " + resultMatrix[lines - 1][columns - 1]);
        //printMatrix(items, resultMatrix, lines, weight);

        List<String> options = calculateChoices(resultMatrix, columns, lines, items);

        StringBuilder builder = new StringBuilder();

        options.forEach(builder::append);

        return builder.toString();

    }

    /**
     * Rolls back the result matrix, goes from the last row and last column until the first column to
     * check which items have been chosen
     *
     * @param resultMatrix
     * @param columns
     * @param lines
     * @param items
     * @return
     */
    private static List<String> calculateChoices(Integer[][] resultMatrix, Integer columns, Integer lines, List<BackpackItem> items) {

        //In case there is only one item, checks the last line to see if it the weight cost is bigger than zero
        //If it is, then the only element is above weight and returns an empty list. Otherwise returns it
        if (lines == 1) {
            if (resultMatrix[0][columns - 1] > 0) {
                return asList(String.valueOf(items.get(0).getIndex()));
            } else {
                return Collections.emptyList();
            }
        }

        List<String> options = new ArrayList<>();
        int line = lines - 1;
        int column = columns - 1;

        int upperLine = line - 1;

        Integer upper = resultMatrix[upperLine][column];

        while (column > 0 && upperLine > 0) {

            Integer currentCost = resultMatrix[line][column];

            if (currentCost.doubleValue() != upper) {
                options.add(String.valueOf(items.get(line).getIndex()));
                //System.out.println("Option: " + items.get(line).getIndex());
                System.out.println("current1: " + currentCost);
                System.out.println("current2: " + upper);
                System.out.println("");
                line--;
                upperLine--;

                column = column - items.get(line - 1).getWeight();

            } else {
                line--;
                upperLine--;
            }
            upper = resultMatrix[upperLine][column];

        }
        return options;
    }

    /**
     * Takes the initialized matrix and fills each position with the best cost combination for the total weight available.
     *
     * @param resultMatrix
     * @param columns
     * @param lines
     * @param items
     * @return
     */
    private static Integer[][] calculateResultMatrix(Integer[][] resultMatrix, Integer columns, Integer
            lines, List<BackpackItem> items) {

        for (int i = 0; i < lines; i++) {
            for (int j = 1; j < columns; j++) {
                Integer currentWeight = items.get(i).getWeight();
                Integer currentCost = items.get(i).getCost();
                //If Current weight is bigger than I can carry, use the last value calculated
                if (j < currentWeight) {
                    if (i == 0) {
                        resultMatrix[i][j] = resultMatrix[i][j - 1];
                    } else {
                        resultMatrix[i][j] = resultMatrix[i - 1][j];
                    }
                } else {

                    if (i == 0) {
                        resultMatrix[i][j] = currentCost;
                    } else {
                        int diff = Double.valueOf(j - currentWeight).intValue();
                        resultMatrix[i][j] = calculateMax(currentCost, resultMatrix[i - 1][diff], resultMatrix[i - 1][j]);
                    }
                }
            }
        }
        return resultMatrix;
    }

    /**
     * Helper method to print the matrix
     *
     * @param items
     * @param resultMatrix
     * @param lines
     * @param weight
     */
    private static void printMatrix(List<BackpackItem> items, Double[][] resultMatrix, Integer lines, Integer
            weight) {
        for (int i = 0; i < lines; i++) {
            System.out.print("|" + StringUtils.rightPad(String.valueOf(items.get(i).getWeight()) + "|", 8, ' '));
            for (int j = 0; j < weight; j++) {
                System.out.print(StringUtils.rightPad(String.valueOf(resultMatrix[i][j]), 8, ' '));
            }
            System.out.println("");
        }
    }

    /**
     * Sets the best option to choose from for the current weight.
     * It is set by summing the current item cost with the total cost calculated previously for the remaining weight.
     * If it is bigger than the total cost calculated previously for the current weight, then it will be set as the
     * total
     *
     * @param currentItemCost         is the latest item cost row being processed in the algorithm
     * @param bestRemainingWeightCost is obtained by calculating the weight difference between the current item weight and the current weight column
     * @param prevBestCost            is the cost calculated in the upper row.
     * @return
     */
    private static Integer calculateMax(Integer currentItemCost, Integer bestRemainingWeightCost, Integer prevBestCost) {
        Integer sum = currentItemCost + bestRemainingWeightCost;
        return sum > prevBestCost ? sum : prevBestCost;
    }

    /**
     * Start the matrix. The first column will be set with zeroes in all rows
     *
     * @param columns
     * @param numberItems
     * @return
     */
    private static Integer[][] getInitializedMatrix(Integer columns, Integer numberItems) {
        Integer[][] matrix = new Integer[numberItems][columns];
        for (int i = 0; i < numberItems; i++) {
            matrix[i][0] = 0;
        }
        return matrix;
    }


}
