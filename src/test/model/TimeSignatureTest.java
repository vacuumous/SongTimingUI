package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeSignatureTest {

    TimeSignature t1;
    TimeSignature t2;
    @BeforeEach
    void beforeEach() {
        t1 = new TimeSignature(1,4);
        t2 = new TimeSignature(7,8);
    }

    @Test
    void setTopTest() {
        t1.setTop(5);
        assertEquals(5,t1.getTop());
        t2.setTop(1);
        assertEquals(1,t2.getTop());
    }

    @Test
    void setBotTest() {
        t1.setBot(16);
        assertEquals(16,t1.getBot());
        t1.setBot(4);
        assertEquals(4,t1.getBot());
    }


}
