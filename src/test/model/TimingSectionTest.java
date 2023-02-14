package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimingSectionTest {

    TimingSection t1;
    TimingSection t2;
    TimeSignature fourFour = new TimeSignature(4,4);
    TimeSignature sevenEight = new TimeSignature(7,8);

    @BeforeEach
    void beforeEach() {
        t1 = new TimingSection(0, 120, fourFour);
        t2 = new TimingSection(5000, 100, sevenEight);
    }

    @Test
    void setBPMTest() {
        t1.setBPM(180);
        assertEquals(180, t1.getBPM());
        t2.setBPM(98);
        assertEquals(98, t2.getBPM());
    }

    @Test
    void setTimeTest() {
        t1.setTime(842);
        assertEquals(842, t1.getTime());
        t2.setTime(3081);
        assertEquals(3081, t2.getTime());
    }

    @Test
    void beatLengthTest() {
        assertEquals(500, t1.beatLength());
        assertEquals(600, t2.beatLength());
    }

    @Test
    void setTimeSigTest() {
        t1.setTimeSig(7,8);
        assertEquals(7, t1.getTimesig().getTop());
        assertEquals(8, t1.getTimesig().getBot());
    }
}
