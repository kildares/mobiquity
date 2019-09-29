package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;
import com.mobiquityinc.exception.APIException;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.mobiquityinc.packer.APIFileReader.*;
import static com.mobiquityinc.packer.LineConverter.convertLineToEntityPair;
import static com.mobiquityinc.packer.PackageCalculator.calculate;

public class Packer {

    private Packer() {
    }

    public static String pack(String filePath) throws APIException {

        File file = getValidatedFile(filePath);

        BufferedReader bufferedReader = getBufferedReader(file);

        String output = "";
        String line = readLine(bufferedReader);
        int lineCont = 0;
        while (!Objects.isNull(line)) {

            Map<Integer, List<BackpackItem>> entityPair = convertLineToEntityPair(line);

            output = calculate(entityPair);

            line = readLine(bufferedReader);
            lineCont++;
        }

        closeBufferedReader(bufferedReader);

        return lineCont + "\n-\n" + output;
    }

}
