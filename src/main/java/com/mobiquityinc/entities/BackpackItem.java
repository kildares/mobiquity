package com.mobiquityinc.entities;

/**
 * This entity represents each backpack item, containing the index, weight and cost read from the file
 */
public class BackpackItem {

    Integer index;
    Integer weight;
    Integer cost;


    public BackpackItem(Integer index, Integer weight, Integer cost) {
        this.index = index;
        this.weight = weight;
        this.cost = cost;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

}
