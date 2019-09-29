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

            //output = calculateSet(entityPair);

            line = readLine(bufferedReader);
            lineCont++;
        }

        closeBufferedReader(bufferedReader);

        return lineCont + "\n-\n" + output;
    }


    private static String processLine(String line) {

        String[] data = line.trim().split(":");

        if (data.length > 2 || !data[0].matches("[0-9]*")) {
            return "";
        }

        int weight = Integer.valueOf(data[0]);

        if (weight <= 0 || weight > 100) {
            return "";
        }

        //      StringUtils.substringBetween(data[0], )
        //    data[1].

        return null;
    }

}
