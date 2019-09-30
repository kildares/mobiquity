package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import com.mobiquityinc.exception.APIException;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mobiquityinc.comparator.LineConverter.convertLineToEntityPair;
import static com.mobiquityinc.packer.APIFileReader.*;
import static com.mobiquityinc.packer.PackageCalculator.calculate;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {

        File file = getValidatedFile(filePath);

        BufferedReader bufferedReader = getBufferedReader(file);

        String line = readLine(bufferedReader);
        int lineCont = 0;

        StringBuilder builder = new StringBuilder();

        while (!Objects.isNull(line)) {

            Map<Integer, List<BackpackItem>> entityPair = convertLineToEntityPair(line);

            if (!entityPair.isEmpty()) {

                List<String> result = calculate(entityPair);


                builder.append(createOutputLine(result));
            }

            line = readLine(bufferedReader);
            lineCont++;
        }

        closeBufferedReader(bufferedReader);

        return lineCont + "\n-\n" + builder.toString();
    }

    private static String createOutputLine(List<String> result) {

        if (Objects.isNull(result) || result.isEmpty()) {
            return "\n";
        }

        String output = "";
        for (int i = 0; i < result.size(); i++) {
            if (i + 1 == result.size()) {
                output += result.get(i);
            } else {
                output += result.get(i) + ", ";
            }

        }
        return output + "\n";
    }

}
