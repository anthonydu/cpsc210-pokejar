package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonTools {
    public static List<JSONObject> objectsFromArray(JSONArray jsonArray) {
        return new ArrayList<JSONObject>() {{
            for (int i = 0; i < jsonArray.length(); i++) {
                add(jsonArray.getJSONObject(i));
            }
        }};
    }

    public static List<String> stringsFromArray(JSONArray jsonArray) {
        return new ArrayList<String>() {{
            for (int i = 0; i < jsonArray.length(); i++) {
                add(jsonArray.getString(i));
            }
        }};
    }
}
