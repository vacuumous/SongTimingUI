package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    Song s1;
    Song s2;
    Song s3;
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
        s3 = new Song("C", "ccc");
        s1.addSection(t1);
        s1.addSection(t2);
        s1.addSection(t3);

        s3.addSection(t3);
        s3.addSection(t2);
        s3.addSection(t1);
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
        assertEquals(sevenEight, s1.find(8000).getTimesig());
        assertEquals(sixFour, s1.find(5000000).getTimesig());
    }

    @Test
    void setterTest() {
        assertEquals("A", s1.getTitle());
        assertEquals("aaa", s1.getArtist());

        s1.setTitle("No Signal");
        s1.setArtist("Chon");
        assertEquals("No Signal", s1.getTitle());
        assertEquals("Chon", s1.getArtist());
    }
    @Test
    void editTest() {
        s1.editSection(t1, 50, 100, 4, 4);
        assertEquals(t1, s1.find(50));
        assertEquals(50, t1.getTime());
        assertEquals(100, t1.getBPM());
    }



    @Test
    void sortTest() {
        s3.sort();
        assertEquals(t1, s3.getTimingSections().get(0));
        assertEquals(t2, s3.getTimingSections().get(1));
        assertEquals(t3, s3.getTimingSections().get(2));
    }

}