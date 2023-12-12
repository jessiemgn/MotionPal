package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for WorkoutPlanTracker class
class WorkoutPlanTrackerTest {
    private WorkoutPlanTracker workoutPlanTracker;
    private Plan plan1;
    private Plan plan2;

    @BeforeEach
    void runBefore() {
        workoutPlanTracker = new WorkoutPlanTracker();

        plan1 = new Plan(1,1, true);
        Exercise exc11 = new Exercise("Squats", 1, 1, 1, 1);
        Exercise exc12 = new Exercise("RDL", 1, 1, 1, 1);
        plan1.addExercise(exc11, true);
        plan1.addExercise(exc12, true);
        workoutPlanTracker.addPlan(plan1);

        plan2 = new Plan(1,2);
        Exercise exc21 = new Exercise("Burpees", 2, 2, 2, 2);
        Exercise exc22 = new Exercise("Push Up", 2, 2, 2, 2);
        plan2.addExercise(exc21, true);
        plan2.addExercise(exc22, true);
        workoutPlanTracker.addPlan(plan2);
    }

    @Test
    public void testGetPlan() {
        assertEquals(plan1, workoutPlanTracker.getPlan(1,1));
    }

    @Test
    public void testGetPlan2() {
        assertEquals(plan2, workoutPlanTracker.getPlan(1,2));
    }

    @Test
    public void testGetPlanNull() {
        assertNull(workoutPlanTracker.getPlan(2,1));
    }

    @Test
    public void testGetPlanNull2() {
        assertNull(workoutPlanTracker.getPlan(2,2));
    }

    @Test
    public void testAddNewPlan() {
        assertTrue(workoutPlanTracker.addNewPlan().checkWeekDay(1,3));
    }

    @Test
    public void testGetFirstIncompletePlan() {
        assertEquals(workoutPlanTracker.getFirstIncompletePlan(), plan2);
    }

    @Test
    public void testGetFirstIncompletePlanNull() {
        plan2.completePlan();
        assertNull(workoutPlanTracker.getFirstIncompletePlan());
    }

    @Test
    public void testGetPlans() {
        List<Plan> plans = workoutPlanTracker.getPlans();
        assertEquals(plans.get(0), plan1);
        assertEquals(plans.get(1), plan2);
    }

    @Test
    public void testSummary() {
        String sum = plan1.getName() + "-"
                + (plan1.isComplete()?"Completed":"Not Complete") + "\n"
                + plan1.summary() + "\n\n"
                + plan2.getName() + "-"
                + (plan2.isComplete()?"Completed":"Not Complete") + "\n"
                + plan2.summary() + "\n\n";
        assertEquals(sum, workoutPlanTracker.summary());
    }

    @Test
    public void testAddPlans() {
        assertEquals(2, workoutPlanTracker.getPlans().size());
        assertEquals(plan1, workoutPlanTracker.getPlans().get(0));
        assertEquals(plan2, workoutPlanTracker.getPlans().get(1));
    }

    @Test
    public void testNumPlans() {
        assertEquals(2, workoutPlanTracker.numPlans());
    }


}