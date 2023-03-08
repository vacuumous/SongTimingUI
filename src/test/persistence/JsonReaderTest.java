package persistence;

import model.Song;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Uses JsonSerializationDemo as a template
public class JsonReaderTest extends JsonTest {


    @Test
    void testNonexistentFile() {
        JsonReader reader = new JsonReader("./data/null.json");
        try {
            Song song = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptySong() {
        JsonReader reader = new JsonReader("./data/TestEmptySong.json");
        try {
            Song song = reader.read();
            assertEquals("Title", song.getTitle());
            assertEquals("Artist", song.getArtist());
            assertTrue(song.getTimingSections().isEmpty());
        } catch (IOException e) {
            fail("Error in reading file");
        }
    }

    @Test
    void testBasicSong() {
        JsonReader reader = new JsonReader("./data/TestSimpleSong.json");
        try {
            Song song = reader.read();
            assertEquals("Generic Song", song.getTitle());
            assertEquals("Generic Artist", song.getArtist());
            assertEquals(1, song.getTimingSections().size());
            checkSection(5, 180, 4, 4, song.getTimingSections().get(0));
        } catch (IOException e) {
            fail("Error in reading file");
        }
    }

    @Test
    void testComplexSong() {
        JsonReader reader = new JsonReader("./data/TestComplexSong.json");
        try {
            Song song = reader.read();
            assertEquals("Madiba", song.getTitle());
            assertEquals("Shaun Martin", song.getArtist());
            assertEquals(4, song.getTimingSections().size());
            checkSection(5, 180, 4, 4, song.getTimingSections().get(0));
            checkSection(400, 177.7, 4, 4, song.getTimingSections().get(1));
            checkSection(581, 100, 7, 8, song.getTimingSections().get(2));
            checkSection(681, 100, 6, 8, song.getTimingSections().get(3));
        } catch (IOException e) {
            fail("Error in reading file");
        }
    }
}
