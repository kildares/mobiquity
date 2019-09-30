package com.mobiquityinc.packer;

import com.mobiquityinc.comparator.IndexComparator;
import com.mobiquityinc.entities.BackpackItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * This class contains the main logic to obtain the best subset of items to put in the backpack.
 * It makes use of a Dynamic Programming Solution, by generating a Matrix, whereas
 * the rows represent each item weight, ordered by increasing order and each column represent a weight unity,
 * from 0..n, where n == total backpack weight. The content of each element contains the optimal cost for the
 * [i-nth][j-nth] position.
 * <p>
 * The optimal cost will be localized in the last index of the last column and the last row of the matrix.
 * <p>
 * Later, it will obtain the chosen elements by rolling back from the last item and comparing with the
 * adjacent contents, above and on the left side. For each M[i-th][nth] element, if the element above has the same
 * cost, then the current element will not be chosen and the algorithm restarts from that position. If it the cost is minor,
 * then the element will be chosen and its index will be put on a List to return. The next element will be calculated
 * by subtracting current weight available - the current item weight.
 */
public class PackageCalculator {

    /**
     * Receives the a Map with only 1 key-value, representing a line read from the file and returns a String with
     * the chosen items indexes
     *
     * @param dataToCalc
     * @return
     */
    public static List<String> calculate(Map<Integer, List<BackpackItem>> dataToCalc) {

        Integer weight = dataToCalc.keySet().iterator().next();
        List<BackpackItem> items = dataToCalc.get(weight);

        //Sorts the list of items in increasing order
        Collections.sort(items, new BackPackItemComparator());

        Integer lines = items.size();

        Integer columns = weight + 1;


        if (lines == 1 && items.get(0).getWeight() <= columns) {
            return asList(String.valueOf(items.get(0).getIndex()));
        }

        //Starts a matrix to calculate results
        Integer[][] resultMatrix = getInitializedMatrix(columns, lines);

        resultMatrix = calculateResultMatrix(resultMatrix, columns, lines, items);

        //This element contains the best combination possible
        //System.out.println("last Index Sum: " + resultMatrix[lines - 1][columns - 1]);

        List<String> chosenItems = calculateChoices(resultMatrix, columns, lines, items);

        chosenItems.sort(new IndexComparator());

        return chosenItems;

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

        int upperLine = lines - 2;
        int line = lines - 1;
        int column = columns - 1;
        List<String> chosenItems = new ArrayList<>();

        while (upperLine >= 0 && column >= 0) {


            int cost = resultMatrix[line][column];
            int upperCost = resultMatrix[upperLine][column];

            if (upperCost == cost) {
                line--;
                upperLine--;
            } else {
                String itemIndex = String.valueOf(items.get(line).getIndex());
                Integer itemWeight = items.get(line).getWeight();
                chosenItems.add(itemIndex);

                column = column - itemWeight;
                line--;
                upperLine--;
            }
        }

        if (line == 0 && column > 0 && resultMatrix[line][column] > 0) {
            chosenItems.add(String.valueOf(items.get(line).getIndex()));
        }


        return chosenItems;
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
    private static Integer calculateMax(Integer currentItemCost, Integer bestRemainingWeightCost, Integer
            prevBestCost) {
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
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 0;
            }
        }
        return matrix;
    }


}
