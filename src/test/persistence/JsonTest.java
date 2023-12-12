package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkExercise(Exercise exercise, String name, int sets, int reps, int weight, int duration) {
        assertEquals(name, exercise.getName());
        assertEquals(sets, exercise.getSets());
        assertEquals(reps, exercise.getReps());
        assertEquals(weight, exercise.getWeight());
        assertEquals(duration, exercise.getDuration());
    }

    protected void checkPlan(Plan plan, int week, int day, boolean complete) {
        assertEquals(week, plan.getWeek());
        assertEquals(day, plan.getDay());
        assertEquals(complete, plan.isComplete());
    }
}
