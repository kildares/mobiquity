package com.mobiquityinc.packer;

import com.mobiquityinc.entities.BackpackItem;

import java.util.Comparator;

/**
 * Compares BackpackItems. Used to sort items in a list to run the main algorithm
 */
public class BackPackItemComparator implements Comparator<BackpackItem> {

    @Override
    public int compare(BackpackItem o1, BackpackItem o2) {

        if (o1.getWeight() > o2.getWeight()) {
            return 1;
        } else if (o2.getWeight() > o1.getWeight()) {
            return -1;
        } else {
            return 0;
        }
    }
}
