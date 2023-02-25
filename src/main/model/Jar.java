package model;

/**
 * A Jar object containing a box and a list of teams.
 *
 * @author Anthony Du
 */
public class Jar {
    private Box box;
    private TeamList teams;

    /**
     * Constructs a Jar with an empty Box and an empty TeamList.
     */
    public Jar() {
        this.box = new Box();
        this.teams = new TeamList();
    }

    /**
     * Gets this Jar's box.
     *
     * @return this Jar's box
     */
    public Box getBox() {
        return box;
    }

    /**
     * Gets this Jar's list of teams.
     *
     * @return this Jar's list of teams
     */
    public TeamList getTeams() {
        return teams;
    }

    /**
     * Sets this Jar's box.
     * <p>
     * MODIFIES: this
     *
     * @param box the box to set to
     */
    public void setBox(Box box) {
        this.box = box;
    }

    /**
     * Sets this Jar's list of teams.
     * <p>
     * MODIFIES: this
     *
     * @param teams the list of teams to set to
     */
    public void setTeams(TeamList teams) {
        this.teams = teams;
    }
}
