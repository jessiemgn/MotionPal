package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Plan class
class PlanTest {
    private Plan plan;
    private Exercise exc1;
    private Exercise exc2;

    @BeforeEach
    void runBefore() {
        plan = new Plan(1,2);
        exc1 = new Exercise("Squats",1,1,1,1);
        exc2 = new Exercise("RDL",1,1,1,1);
        plan.addExercise(exc1, true);
        plan.addExercise(exc2, true);
    }

    @Test
    public void testConstructor1() {
        Plan plan = new Plan(1,1);
        assertEquals(1, plan.getWeek());
        assertEquals(1, plan.getDay());
        assertFalse(plan.isComplete());
    }

    @Test
    public void testConstructor2() {
        Plan plan = new Plan(1,1,true);
        assertEquals(1, plan.getWeek());
        assertEquals(1, plan.getDay());
        assertTrue(plan.isComplete());
    }

    @Test
    public void testCompletePlan() {
        Plan plan = new Plan(1,1,false);
        plan.completePlan();
        assertTrue(plan.isComplete());
    }

    @Test
    public void testSwitchPlanIncomplete() {
        Plan plan = new Plan(1,1,false);
        plan.switchPlan();
        assertTrue(plan.isComplete());
    }

    @Test
    public void testSwitchPlanComplete() {
        Plan plan = new Plan(1,1,true);
        plan.switchPlan();
        assertFalse(plan.isComplete());
    }

    @Test
    public void testCheckWeekDay() {
        assertTrue(plan.checkWeekDay(1,2));
    }

    @Test
    public void testCheckWeekDay2() {
        assertFalse(plan.checkWeekDay(1,3));
    }

    @Test
    public void testCheckWeekDay3() {
        assertFalse(plan.checkWeekDay(2,1));
    }

    @Test
    public void testCheckWeekDay4() {
        assertFalse(plan.checkWeekDay(3,3));
    }

    @Test
    public void testGetName() {
        assertEquals("Week 1 Day 2", plan.getName());
    }

    @Test
    public void testAddExercises() {
        assertEquals(2, plan.getExercises().size());
        assertEquals(exc1, plan.getExercises().get(0));
        assertEquals(exc2, plan.getExercises().get(1));
    }

    @Test
    public void testGetExerciseCaseMatch() {
        assertNotNull(plan.getExercise(exc1.getName()));
    }

    @Test
    public void testGetExerciseCaseMismatch() {
        assertNotNull(plan.getExercise("sqUats"));
    }

    @Test
    public void testGetExerciseIncorrectName() {
        assertNull(plan.getExercise("plank"));
    }


    // test for removeExercise when running GUI
    @Test
    public void testRemoveExercise() {
        plan.removeExercise(0);
        Exercise exc = plan.getExercise(exc1.getName());
        assertNull(exc);
    }

//    // test for removeExercise when running console
//    @Test
//    public void testRemoveExercise() {
//        assertTrue(plan.removeExercise(exc1.getName()));
//        Exercise exc = plan.getExercise(exc1.getName());
//        assertNull(exc);
//    }
//
//    // test for removeExercise when running console
//    @Test
//    public void testRemoveExerciseNull() {
//        assertFalse(plan.removeExercise("Plank"));
//    }

    @Test
    public void testSummary() {
        String sum = "Workout Plan Summary:\n" +
                String.format("%-25s%-7s%-7s%-15s%s\n", "Exercise", "Sets",
                        "Reps", "Weight (lbs)", "Duration (seconds)") +
                String.format("%-25s%-7d%-7d%-15d%d\n",
                        exc1.getName(),
                        exc1.getSets(),
                        exc1.getReps(),
                        exc1.getWeight(),
                        exc1.getDuration()
                ) +
                String.format("%-25s%-7d%-7d%-15d%d\n",
                        exc2.getName(),
                        exc2.getSets(),
                        exc2.getReps(),
                        exc2.getWeight(),
                        exc2.getDuration()
                );
        assertEquals(sum,plan.summary());
    }
}