package model;

import java.util.ArrayList;

/**
 * A list of Teams.
 *
 * @author Anthony Du
 */
public class TeamList extends ArrayList<Team> {
    /**
     * Generates a String representing this TeamList.
     *
     * @return a String representing this TeamList
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.size(); i++) {
            result += i + " " + this.get(i) + "\n";
        }
        return result.trim();
    }
}
