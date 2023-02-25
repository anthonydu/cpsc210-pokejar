package persistence;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * A static class containing utility functions for handling JSON Objects.
 *
 * @author Anthony Du
 */
public final class JsonUtil {
    private JsonUtil() {}

    /**
     * Returns a list of JSONObjects contained in the jsonArray.
     *
     * @param jsonArray the JSONArray to get JSONObjects from
     * @return a list of JSONObjects contained in the jsonArray
     * @throws JSONException if there is no value for the index or if the value is not a JSONObject
     */
    public static List<JSONObject> objectsFromArray(JSONArray jsonArray) throws JSONException {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObjects.add(jsonArray.getJSONObject(i));
        }
        return jsonObjects;
    }

    /**
     * Returns a list of Strings contained in the jsonArray.
     *
     * @param jsonArray the JSONArray to get Strings from
     * @return a list of strings contained in the jsonArray
     * @throws JSONException if there is no string value for the index
     */
    public static List<String> stringsFromArray(JSONArray jsonArray) throws JSONException {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            strings.add(jsonArray.getString(i));
        }
        return strings;
    }
}
