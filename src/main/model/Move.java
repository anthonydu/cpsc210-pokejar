package model;

public class Move {
    private String name;
    private Type type;
    private boolean isStatus;

    public Move(String name, Type type, boolean isStatus) {
        this.name = name;
        this.type = type;
        this.isStatus = isStatus;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isStatus() {
        return isStatus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setStatus(boolean isStatus) {
        this.isStatus = isStatus;
    }

    @Override
    public String toString() {
        String str = "";
        if (this.name.length() >= 16) {
            str += this.name.substring(0, 14) + "… ";
        } else {
            str += this.name + " ".repeat(16 - this.name.length());
        }
        return str
                + this.type.name() + " ".repeat(16 - this.type.name().length())
                + (this.isStatus ? "Status" : "Attacking");
    }
}
