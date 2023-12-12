package persistence;

import model.Exercise;
import model.Plan;
import model.WorkoutPlanTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WorkoutPlanTracker wpt = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkoutPlanTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkoutPlanTracker.json");
        try {
            WorkoutPlanTracker wpt = reader.read();
            assertEquals(0, wpt.numPlans());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkoutPlanTracker() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkoutPlanTracker.json");
        try {
            WorkoutPlanTracker wpt = reader.read();
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
            fail("Couldn't read from file");
        }
    }
}