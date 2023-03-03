package persistence;

import org.json.JSONObject;

// Uses JsonSerializationDemo as a template
public interface Writable {

    // EFFECTS: returns this in Json format
    JSONObject toJson();
}
