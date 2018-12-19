package com.hansque.commands;

public class CommandArgument<T> {

    private T value;

    public CommandArgument(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

}
