package org.sara.interfaces.model;

public class Requirement implements Cloneable {

    public Requirement(int id, int type, int priority) {
        this.id = id;
        this.type = type;
        this.priority = priority;
    }

    public int getID() {
        return this.id;
    }

    public int getType() {
        return this.type;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public Object clone() {
        return new Requirement(this.id, this.type, this.priority);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Requirement && this.id == ((Requirement) other).id;
    }

    private final int id;
    private final int type;
    private final int priority;
}
