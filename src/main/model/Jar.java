package model;

import util.TeamList;

public class Jar {
    private Box box;
    private TeamList teams;

    public Jar() {
        this.box = new Box();
        this.teams = new TeamList();
    }

    public Box getBox() {
        return box;
    }

    public TeamList getTeams() {
        return teams;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public void setTeams(TeamList teams) {
        this.teams = teams;
    }
}
