package persistence;

import model.Exercise;
import model.Plan;
import model.WorkoutPlanTracker;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workout plan from file and returns it;
    public WorkoutPlanTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWorkRoom(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses workout plan from JSON object and returns it
    private WorkoutPlanTracker parseWorkRoom(JSONObject jsonObject) {
        WorkoutPlanTracker wpt = new WorkoutPlanTracker();
        addPlans(wpt, jsonObject);
        return wpt;
    }

    // MODIFIES: wpt
    // EFFECTS: parses plans from JSON object and adds them to workout plan
    private void addPlans(WorkoutPlanTracker wpt, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("plans");
        for (Object json : jsonArray) {
            JSONObject plan = (JSONObject) json;
            addPlan(wpt, plan);
        }
    }

    // MODIFIES: wpt
    // EFFECTS: parses plan from JSON object and adds it to workout plan
    private void addPlan(WorkoutPlanTracker wpt, JSONObject jsonObject) {
        int week = jsonObject.getInt("week");
        int day = jsonObject.getInt("day");
        boolean complete = jsonObject.getBoolean("complete");
        Plan plan = new Plan(week, day, complete);
        addExercises(plan,jsonObject);
        wpt.addPlan(plan);
    }

    // MODIFIES: wpt
    // EFFECTS: parses exercises from JSON object and adds it to workout plan
    private void addExercises(Plan plan, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exercises");
        for (Object json : jsonArray) {
            JSONObject exercise = (JSONObject) json;
            addExercise(plan, exercise);
        }
    }

    // MODIFIES: wpt
    // EFFECTS: parses exercise from JSON object and adds it to workout plan
    private void addExercise(Plan plan, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int sets = jsonObject.getInt("sets");
        int reps = jsonObject.getInt("reps");
        int weight = jsonObject.getInt("weight");
        int duration = jsonObject.getInt("duration");
        plan.addExercise(new Exercise(name,sets,reps,weight,duration), false);
    }
}
