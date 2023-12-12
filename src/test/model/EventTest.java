package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("e");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("e", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "e", e.toString());
    }

    @Test
    public void testEquals() {
        Event event1 = new Event("a");
        Event event2 = new Event("b");
        Event event3 = new Event("a");

        assertTrue(event1.equals(event3));
        assertFalse(event2.equals(event3));
        assertFalse(event2.equals("b"));
        assertFalse(event3.equals(null));
    }

    @Test
    public void testHashcode() {
        assertNotNull(e.hashCode());
    }
}
