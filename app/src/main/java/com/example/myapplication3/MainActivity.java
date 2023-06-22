package com.example.myapplication3;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements MathQuizAdapter.OnAnswerSelectedListener {
    private RecyclerView recyclerView;
    private MathQuizAdapter adapter;

    private MathQuiz mathQuiz;
    private SharedPreferences sharedPreferences;
    private static final String SCORE_KEY = "score";

    private RadioGroup answerChoicesRadioGroup;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MathQuizAdapter(this);
        recyclerView.setAdapter(adapter);

        answerChoicesRadioGroup = findViewById(R.id.answer_choices_radiogroup);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        mathQuiz = generateMathQuiz();
        adapter.setMathQuiz(mathQuiz);

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonClicked();
            }
        });
    }

    private MathQuiz generateMathQuiz() {
        MathQuiz quiz = new MathQuiz();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int num1 = random.nextInt(10) + 1;
            int num2 = random.nextInt(10) + 1;
            String operator = "";

            int operatorIndex = random.nextInt(3);
            switch (operatorIndex) {
                case 0:
                    operator = "+";
                    break;
                case 1:
                    operator = "-";
                    break;
                case 2:
                    operator = "*";
                    break;
            }

            MathOperation operation = new MathOperation(num1, num2, operator);
            quiz.addOperation(operation);
        }

        return quiz;
    }

    @Override
    public void onAnswerSelected(int selectedAnswer) {
        adapter.setSelectedAnswer(selectedAnswer);
    }

    public void onSubmitButtonClicked() {
        MathOperation currentOperation = mathQuiz.getOperations().get(adapter.getCurrentQuestionIndex());
        int correctAnswer = (int) Math.round(currentOperation.getAnswer());

        int selectedAnswer = adapter.getSelectedAnswer();
        if (selectedAnswer != -1) {
            if (selectedAnswer == correctAnswer) {
                int score = sharedPreferences.getInt(SCORE_KEY, 0) + 1;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(SCORE_KEY, score);
                editor.apply();

                String message = "Correct! Your score: " + score;
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                adapter.moveToNextQuestion();
                if (adapter.getCurrentQuestionIndex() == adapter.getItemCount() - 1) {
                    // Quiz completed
                    Toast.makeText(this, "Quiz completed! Your final score: " + score, Toast.LENGTH_LONG).show();
                }
            } else {
                // User answered incorrectly, show a message
                String message = "Incorrect! Try again.";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } else {
            // No answer selected, show a message
            String message = "Please select an answer.";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        // Clear the selected answer in the RadioGroup
        answerChoicesRadioGroup.clearCheck();
    }
}
