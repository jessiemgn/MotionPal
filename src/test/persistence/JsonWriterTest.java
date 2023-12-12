package persistence;

import model.Exercise;
import model.Plan;
import model.WorkoutPlanTracker;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            WorkoutPlanTracker wpt = new WorkoutPlanTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkoutPlanTracker() {
        try {
            WorkoutPlanTracker wpt = new WorkoutPlanTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkoutPlanTracker.json");
            writer.open();
            writer.write(wpt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkoutPlanTracker.json");
            wpt = reader.read();
            assertEquals(0, wpt.numPlans());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkoutPlanTracker() {
        try {
            WorkoutPlanTracker wpt = new WorkoutPlanTracker();

            Plan plan = new Plan(1, 1,true);
            plan.addExercise(new Exercise("Burpees",1,1,1,1), true);
            plan.addExercise(new Exercise("Push Up",1,1,1,1), true);
            wpt.addPlan(plan);

            plan = new Plan(1, 2,false);
            plan.addExercise(new Exercise("Burpees",2,2,2,2), true);
            plan.addExercise(new Exercise("Push Up",2,2,2,2), true);
            wpt.addPlan(plan);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkoutPlanTracker.json");
            writer.open();
            writer.write(wpt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkoutPlanTracker.json");
            wpt = reader.read();
            List<Plan> plans = wpt.getPlans();
            assertEquals(2, plans.size());

            checkPlan(plans.get(0),  1,1,true);
            List<Exercise> exercises1 = plans.get(0).getExercises();
            checkExercise(exercises1.get(0),"Burpees",1,1,1,1);
            checkExercise(exercises1.get(1),"Push Up",1,1,1,1);

            checkPlan(plans.get(1),  1,2,false);
            List<Exercise> exercises2 = plans.get(1).getExercises();
            checkExercise(exercises2.get(0),"Burpees",2,2,2,2);
            checkExercise(exercises2.get(1),"Push Up",2,2,2,2);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}