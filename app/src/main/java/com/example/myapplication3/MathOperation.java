package com.example.myapplication3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MathOperation {
    private int operand1;
    private int operand2;
    private String operator;

    public MathOperation(int operand1, int operand2, String operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public int getOperand1() {
        return operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public String getOperator() {
        return operator;
    }

    public double getAnswer() {
        double answer = 0.0;
        switch (operator) {
            case "+":
                answer = operand1 + operand2;
                break;
            case "-":
                answer = operand1 - operand2;
                break;
            case "*":
                answer = operand1 * operand2;
                break;
        }
        return answer;
    }

    public List<String> getAnswerChoices() {
        // Generate answer choices based on the operation
        List<String> choices = new ArrayList<>();
        choices.add(String.valueOf(getAnswer())); // Add the correct answer as one of the choices

        // Add other random choices
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            int randomOperand = random.nextInt(10) + 1; // Generate a random operand
            double randomAnswer = 0.0;
            switch (operator) {
                case "+":
                    randomAnswer = operand1 + operand2 + randomOperand;
                    break;
                case "-":
                    randomAnswer = operand1 - operand2 - randomOperand;
                    break;
                case "*":
                    randomAnswer = operand1 * operand2 * randomOperand;
                    break;
            }
            choices.add(String.valueOf(randomAnswer));
        }

        // Shuffle the choices to randomize their order
        Collections.shuffle(choices);
        return choices;
    }
}
