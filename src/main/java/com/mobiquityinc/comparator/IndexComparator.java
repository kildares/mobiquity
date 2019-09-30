package com.mobiquityinc.comparator;

import java.util.Comparator;

/**
 * Compares BackpackItems. Used to sort items in a list to run the main algorithm
 */
public class IndexComparator implements Comparator<String> {


    @Override
    public int compare(String o1, String o2) {

        Integer i1 = Integer.parseInt(o1);
        Integer i2 = Integer.parseInt(o2);

        if (i1 > i2) {
            return 1;
        } else if (i2 > i1) {
            return -1;
        } else {
            return 0;
        }
    }
}
