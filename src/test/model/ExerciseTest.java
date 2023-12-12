package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for Exercise class
class ExerciseTest {
    private Exercise exercise;

    @BeforeEach
    void runBefore() {
        exercise = new Exercise("Burpees",1,1,1,1);
    }

    @Test
    public void testConstructor() {
        assertEquals("Burpees", exercise.getName());
        assertEquals(1, exercise.getSets());
        assertEquals(1, exercise.getReps());
        assertEquals(1, exercise.getWeight());
        assertEquals(1, exercise.getDuration());
    }

    @Test
    public void testSetName() {
        exercise.setName("RDL");
        assertEquals("RDL", exercise.getName());
    }

    @Test
    public void testSetSets() {
        exercise.setSets(2);
        assertEquals(2, exercise.getSets());
    }

    @Test
    public void testSetReps() {
        exercise.setReps(2);
        assertEquals(2, exercise.getReps());
    }

    @Test
    public void testSetWeight() {
        exercise.setWeight(2);
        assertEquals(2, exercise.getWeight());
    }

    @Test
    public void testSetDuration() {
        exercise.setDuration(2);
        assertEquals(2, exercise.getDuration());
    }
}