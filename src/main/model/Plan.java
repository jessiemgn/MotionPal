package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Represents a Workout Plan
public class Plan implements Writable {
    // add fields to represent changing properties of Plan
    private final ArrayList<Exercise> exercises;
    private final int week;
    private final int day;
    private boolean complete;

    // REQUIRES: week >= 1, day >= 1
    // EFFECTS: constructs a Plan with given week, day,
    //          complete status, and empty exercise list
    public Plan(int week, int day) {
        this.week = week;
        this.day = day;
        exercises = new ArrayList<>();
        complete = false;
    }

    // REQUIRES: week >= 1, day >= 1
    // EFFECTS: constructs a Plan with updated week, day,
    //          complete status, and empty exercise list
    public Plan(int week, int day, boolean complete) {
        this.week = week;
        this.day = day;
        this.complete = complete;
        exercises = new ArrayList<>();
    }

    // EFFECTS: return true if plan is complete
    public boolean isComplete() {
        return complete;
    }

    // MODIFIES: this
    // EFFECTS: sets complete status to true when user completes plan
    public void completePlan() {
        complete = true;
    }

    // MODIFIES: this
    // EFFECTS: sets complete status to true when plan is incomplete,
    //          sets complete status to false when plan is complete
    public void switchPlan() {
        complete = !complete;
        EventLog.getInstance().logEvent(new Event("completion status changed."));
    }

    // EFFECTS: return true if given week and day has a Plan
    public boolean checkWeekDay(int week,int day) {
        return (this.week == week && this.day == day);
    }

    // EFFECTS: get the specific week and day for a Plan
    public String getName() {
        return "Week " + week + " Day " + day;
    }

    // EFFECTS: add new exercise
    public void addExercise(Exercise exercise, boolean isNew) {
        exercises.add(exercise);
        if (isNew) {
            EventLog.getInstance().logEvent(new Event(exercise.getName() + " added to plan."));
        }

    }

    // EFFECTS: get name of exercise
    public Exercise getExercise(String name) {
        for (Exercise exercise:exercises) {
            if (exercise.getName().equalsIgnoreCase(name)) {
                return exercise;
            }
        }
        return null;
    }

    // removeExercise for GUI
    // EFFECTS: remove targeted exercise
    public void removeExercise(int i) {
        EventLog.getInstance().logEvent(new Event(exercises.get(i).getName() + " removed from plan."));
        exercises.remove(i);
    }

//    // removeExercise for console
//    // EFFECTS: remove targeted exercise (if it exists)
//    public boolean removeExercise(String name) {
//        Exercise target = getExercise(name);
//        if (target != null) {
//            exercises.remove(target);
//            return true;
//        } else {
//            return false;
//        }
//    }

    // EFFECTS: output the summary of the overall Workout Plan for each day
    public String summary() {
        StringBuilder sum = new StringBuilder();
        sum.append("Workout Plan Summary:\n");
        sum.append(String.format("%-25s%-7s%-7s%-15s%s\n", "Exercise",
                "Sets", "Reps", "Weight (lbs)", "Duration (seconds)"));
        for (Exercise exercise:exercises) {
            sum.append(String.format("%-25s%-7d%-7d%-15d%d\n",
                    exercise.getName(),
                    exercise.getSets(),
                    exercise.getReps(),
                    exercise.getWeight(),
                    exercise.getDuration()
            ));
        }
        return sum.toString();
    }

    // EFFECTS: return exercises
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    // EFFECTS: return week of Plan
    public int getWeek() {
        return week;
    }

    // EFFECTS: return day of Plan
    public int getDay() {
        return day;
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("week", week);
        json.put("day", day);
        json.put("complete", complete);
        json.put("exercises", exercisesToJson());
        return json;
    }

    // EFFECTS: returns exercises in this plan as a JSON array
    private JSONArray exercisesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }
}
