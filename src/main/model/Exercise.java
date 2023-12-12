package model;

import persistence.Writable;
import org.json.JSONObject;

// Represents an Exercise
public class Exercise implements Writable {
    // add fields to represent changing properties of Exercise
    private String name;
    private int sets;
    private int reps;
    private int weight;
    private int duration;

    // REQUIRES: name must be filled, sets > 0, reps > 0, weight > 0, duration > 0
    // EFFECTS: constructs an Exercise with given name, sets, reps, weight, and duration
    public Exercise(String name, int sets, int reps, int weight, int duration) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.duration = duration;
    }

    // EFFECTS: returns name of Exercise
    public String getName() {
        return name;
    }

    // EFFECTS: returns number of sets of Exercise
    public int getSets() {
        return sets;
    }

    // EFFECTS: returns number of reps of Exercise
    public int getReps() {
        return reps;
    }

    // EFFECTS: returns weight of Exercise
    public int getWeight() {
        return weight;
    }

    // EFFECTS: returns duration of Exercise
    public int getDuration() {
        return duration;
    }

    // REQUIRES: name must be filled
    // MODIFIES: this
    // EFFECTS: sets name of Exercise
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES: sets is > 0
    // MODIFIES: this
    // EFFECTS: sets number of sets of Exercise
    public void setSets(int sets) {
        this.sets = sets;
    }

    // REQUIRES: reps is > 0
    // MODIFIES: this
    // EFFECTS: sets number of reps of Exercise
    public void setReps(int reps) {
        this.reps = reps;
    }

    // REQUIRES: weight is > 0
    // MODIFIES: this
    // EFFECTS: sets weight used for Exercise
    public void setWeight(int weight) {
        this.weight = weight;
    }

    // REQUIRES: duration is > 0
    // MODIFIES: this
    // EFFECTS: sets duration of Exercise
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sets", sets);
        json.put("reps", reps);
        json.put("weight", weight);
        json.put("duration", duration);
        return json;
    }
}
