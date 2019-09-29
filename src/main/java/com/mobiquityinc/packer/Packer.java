package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import com.mobiquityinc.exception.APIException;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.mobiquityinc.packer.APIFileReader.*;
import static com.mobiquityinc.packer.LineConverter.convertLineToEntityPair;
import static com.mobiquityinc.packer.PackageCalculator.calculate;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {

        File file = getValidatedFile(filePath);

        BufferedReader bufferedReader = getBufferedReader(file);

        StringBuilder output = new StringBuilder();
        String line = readLine(bufferedReader);
        int lineCont = 0;
        while (!Objects.isNull(line)) {

            Map<Integer, List<BackpackItem>> entityPair = convertLineToEntityPair(line);

            Optional<String> result = Optional.empty();
            if (!entityPair.isEmpty()) {

                result = Optional.of(calculate(entityPair));
                output.append(result.get());
            }

            if (result.isPresent()) {
                output.append("\n");
            }

            line = readLine(bufferedReader);
            lineCont++;
        }

        closeBufferedReader(bufferedReader);

        return lineCont + "\n-\n" + output.toString();
    }

}
