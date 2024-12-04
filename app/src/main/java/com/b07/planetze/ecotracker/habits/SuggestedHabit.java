package com.b07.planetze.ecotracker.habits;

public class SuggestedHabit implements Comparable<SuggestedHabit>{
    String header;
    String dailyType;
    String category;
    String description;
    String selectedDescription;
    double potential;
    String tracking;
    String[] keywords;
    boolean visibility;
    boolean selected;

    public SuggestedHabit(String header, String dailyType, String category, String description, String selectedDescription, double potential, String tracking, String[] keywords) {
        this.header = header;
        this.dailyType = dailyType;
        this.category = category;
        this.description = description;
        this.selectedDescription = selectedDescription;
        this.potential = potential;
        this.tracking = tracking;
        this.keywords = keywords;
        this.visibility = false;
        this.selected = true;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int compareTo(SuggestedHabit o) {
        if(this.potential > o.potential){
            return -1;
        }
        else if(this.potential < o.potential){
            return 1;
        }
        return 0;
    }
}
