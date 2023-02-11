package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    Song s1;
    Song s2;
    TimingSection t1;
    TimingSection t2;
    TimingSection t3;
    TimeSignature fourFour = new TimeSignature(4,4);
    TimeSignature sevenEight = new TimeSignature(7,8);
    TimeSignature sixFour = new TimeSignature(6,4);
    @BeforeEach
    void beforeEach() {
        t1 = new TimingSection(0, 120, fourFour);
        t2 = new TimingSection(5000, 100, sevenEight);
        t3 = new TimingSection(10000, 180, sixFour);
        s1 = new Song("A", "aaa");
        s2 = new Song("B", "bbb");

        s1.addSection(t1);
        s1.addSection(t2);
        s1.addSection(t3);
    }


    @Test
    void removeTest() {
        s1.removeSection(t1);
        assertEquals(2, s1.getTimingSections().size());
    }

    @Test
    void findTest() {
        assertEquals(t2, s1.find(8000));
        assertEquals(t3, s1.find(5000000));

    }

    @Test
    void findSigTest() {
        assertEquals(sevenEight, s1.findSig(8000));
        assertEquals(sixFour, s1.findSig(5000000));
    }

    @Test void setterTest() {
        assertEquals("A", s1.getTitle());
        assertEquals("aaa", s1.getArtist());

        s1.setTitle("No Signal");
        s1.setArtist("Chon");
        assertEquals("No Signal", s1.getTitle());
        assertEquals("Chon", s1.getArtist());
    }

}