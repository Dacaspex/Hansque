package com.hansque.commands.argument;

public class Argument {

    private String name;
    private String description;
    private Type type;
    private Constraint constraint;

    public Argument(String name, String description, Type type) {
        this(name, description, type, Constraint.REQUIRED);
    }

    public Argument(String name, String description, Type type, Constraint constraint) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.constraint = constraint;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return type;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public enum Type {
        INT,
        STRING
    }

    public enum Constraint {
        REQUIRED,
        OPTIONAL
    }
}
