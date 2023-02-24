package util;

import model.Team;
import org.json.JSONArray;
import java.util.ArrayList;

public class TeamList extends ArrayList<Team> {
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); i++) {
            result += i + " " + this.get(i) + "\n";
        }
        return result.trim();
    }

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        for (Team t : this) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
