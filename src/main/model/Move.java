package model;

public class Move {
    private String name;
    private Type type;

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
