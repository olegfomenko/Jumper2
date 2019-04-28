package com.jumper.main;

import com.jumper.main.States.State;

import java.util.EmptyStackException;
import java.util.Stack;

public class GameStateManager {
    private Stack<State> stack;

    public GameStateManager() {
        stack = new Stack<State>();
    }

    public void push(State st) {
        stack.push(st);
    }

    public int size() {
        return stack.size();
    }

    public void pop() {
        try {
            stack.pop();
        } catch(EmptyStackException e) {}
    }

    public State peek() {
        try {
            return stack.peek();
        } catch (EmptyStackException e) {
            return null;
        }
    }
}
