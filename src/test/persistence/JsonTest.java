package persistence;

import model.TimeSignature;
import model.TimingSection;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Uses JsonSerializationDemo as a template
public class JsonTest {


    protected void checkSection(int offset, double bpm, int top, int bot, TimingSection section) {
        assertEquals(offset, section.getTime());
        assertEquals(bpm, section.getBPM());
        assertEquals(top, section.getTimesig().getTop());
        assertEquals(bot, section.getTimesig().getBot());
    }
}
