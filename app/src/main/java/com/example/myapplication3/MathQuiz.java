package com.example.myapplication3;

import java.util.ArrayList;
import java.util.List;

public class MathQuiz {
    private List<MathOperation> operations;
    private int score;

    public MathQuiz() {
        operations = new ArrayList<>();
        score = 0;
    }

    public void addOperation(MathOperation operation) {
        operations.add(operation);
    }

    public List<MathOperation> getOperations() {
        return operations;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score++;
    }
}