package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a Workout Plan Tracker
public class WorkoutPlanTracker implements Writable {
    private final ArrayList<Plan> plans;

    // EFFECTS: constructs a Workout Plan Tracker with empty plans list
    public WorkoutPlanTracker() {
        this.plans = new ArrayList<>();
    }

    // EFFECTS: return plan for a certain week and day
    public Plan getPlan(int week, int day) {
        for (Plan plan : plans) {
            if (plan.checkWeekDay(week,day)) {
                return plan;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: add new plan for appropriate week and day
    public Plan addNewPlan() {
        int num = plans.size() + 1;
        int week = (num - 1) / 7 + 1;
        int day = (num - 1) % 7 + 1;
        Plan plan = new Plan(week, day);
        plans.add(plan);
        EventLog.getInstance().logEvent(new Event("added new plan."));
        return plan;
    }

    // EFFECTS: return first incomplete plan
    public Plan getFirstIncompletePlan() {
        for (Plan plan : plans) {
            if (!plan.isComplete()) {
                return plan;
            }
        }
        return null;
    }

    // EFFECTS: return plans
    public ArrayList<Plan> getPlans() {
        return plans;
    }

    // EFFECTS: outputs summary of Plans
    public String summary() {
        StringBuilder sum = new StringBuilder();
        for (Plan plan : plans) {
            sum.append(plan.getName()).append("-").append(plan.isComplete() ? "Completed" : "Not Complete")
                    .append("\n");
            sum.append(plan.summary()).append("\n\n");
        }
        return sum.toString();
    }

    // MODIFIES: plan
    // EFFECTS: adds given plan
    public void addPlan(Plan plan) {
        plans.add(plan);
    }

    // EFFECTS: returns size of plans
    public int numPlans() {
        return plans.size();
    }

    @Override
    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("plans", plansToJson());
        return json;
    }

    // EFFECTS: returns plans in this workout plan tracker as a JSON array
    private JSONArray plansToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Plan p : plans) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }
}
