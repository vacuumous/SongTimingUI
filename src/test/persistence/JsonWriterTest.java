package persistence;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

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
        JsonWriter writer = new JsonWriter("./data/testWriterEmptySong.json");
        String title = "no title";
        String artist = "unknown";
    }
}
