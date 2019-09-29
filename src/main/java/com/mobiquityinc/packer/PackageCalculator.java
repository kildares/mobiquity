package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PackageCalculator {

    public static String calculate(Map<Integer, List<BackpackItem>> dataToCalc) {

        String result = "";
        Integer weight = dataToCalc.keySet().iterator().next();
        List<BackpackItem> items = dataToCalc.get(weight);

        Collections.sort(items, new BackPackItemComparator());

        Integer lines = items.size();

        Integer calculatedWeight = weight * 100;
        Double[][] resultMatrix = getInitializedMatrix(calculatedWeight, lines);

        for (int i = 0; i < lines; i++) {
            for (int j = 1; j < calculatedWeight; j++) {
                Double currentWeight = items.get(i).getWeight() * 100;
                Double currentCost = items.get(i).getCost();
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

        //printMatrix(items, resultMatrix, lines, weight);
        System.out.println("last: " + resultMatrix[lines - 1][calculatedWeight - 1]);
        return result;
    }

    private static void printMatrix(List<BackpackItem> items, Double[][] resultMatrix, Integer lines, Integer weight) {
        for (int i = 0; i < lines; i++) {
            System.out.print("|" + StringUtils.rightPad(String.valueOf(items.get(i).getWeight()) + "|", 8, ' '));
            for (int j = 0; j < weight; j++) {
                System.out.print(StringUtils.rightPad(String.valueOf(resultMatrix[i][j]), 8, ' '));
            }
            System.out.println("");
        }
    }

    private static Double calculateMax(Double currentWeight, Double bestRemainingWeight, Double prevBest) {
        Double sum = currentWeight + bestRemainingWeight;
        return sum > prevBest ? sum : prevBest;
    }

    private static Double[][] getInitializedMatrix(Integer weight, Integer numberItems) {
        Double[][] matrix = new Double[numberItems][weight + 1];
        for (int i = 0; i < numberItems; i++) {
            matrix[i][0] = 0.0;
        }
        return matrix;
    }


}
