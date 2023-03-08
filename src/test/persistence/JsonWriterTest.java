package persistence;

import model.Song;
import model.TimeSignature;
import model.TimingSection;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Uses JsonSerializationDemo as a template
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidDestination() {
        JsonWriter writer = new JsonWriter("./data/illegal:filena a \0");
        try {
            writer.open();
            fail("Expected FileNotFoundException");
        } catch (FileNotFoundException f) {
            // pass
        }
    }

    @Test
    void testWriterEmptySong() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySong.json");
            Song song = new Song("no title", "unknown");
            writer.open();
            writer.write(song);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySong.json");
            song = reader.read();
            assertEquals("no title", song.getTitle());
            assertEquals("unknown", song.getArtist());
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
    }

    @Test
    public void testWriterArbitrarySong() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterComplexSong.json");
            Song song = new Song("Goodbye", "toe");
            TimingSection t1 = new TimingSection(50, 123, new TimeSignature(4,4));
            TimingSection t2 = new TimingSection(100020, 122.5, new TimeSignature(3,4));
            song.addSection(t1);
            song.addSection(t2);
            writer.open();
            writer.write(song);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterComplexSong.json");
            song = reader.read();
            assertEquals("Goodbye", song.getTitle());
            assertEquals("toe", song.getArtist());

            assertEquals(2, song.getTimingSections().size());

            assertEquals(50, song.getTimingSections().get(0).getTime());
            assertEquals(123, song.getTimingSections().get(0).getBPM());
            assertEquals(4, song.getTimingSections().get(0).getTimesig().getTop());
            assertEquals(4, song.getTimingSections().get(0).getTimesig().getBot());

            assertEquals(100020, song.getTimingSections().get(1).getTime());
            assertEquals(122.5, song.getTimingSections().get(1).getBPM());
            assertEquals(3, song.getTimingSections().get(1).getTimesig().getTop());
            assertEquals(4, song.getTimingSections().get(1).getTimesig().getBot());
        } catch (IOException e) {
            fail("No exception should be thrown");
        }
    }
}
