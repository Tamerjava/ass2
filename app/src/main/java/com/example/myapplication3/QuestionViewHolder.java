package com.example.myapplication3;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
    private TextView questionTextView;
    private RadioGroup answerChoicesRadioGroup;
    private MathQuizAdapter.OnAnswerSelectedListener answerSelectedListener;

    public QuestionViewHolder(@NonNull View itemView, MathQuizAdapter.OnAnswerSelectedListener listener) {
        super(itemView);
        questionTextView = itemView.findViewById(R.id.question_textview);
        answerChoicesRadioGroup = itemView.findViewById(R.id.answer_choices_radiogroup);
        answerChoicesRadioGroup.setOnCheckedChangeListener(this);
        answerSelectedListener = listener;
    }

    public void bind(String question, boolean showRadioButtons, List<String> answerChoices) {
        questionTextView.setText(question);

        if (showRadioButtons) {
            answerChoicesRadioGroup.setVisibility(View.VISIBLE);
            answerChoicesRadioGroup.clearCheck(); // Clear any previously selected answer

            if (answerChoices != null && answerChoices.size() == 4) {
                RadioButton choice1 = (RadioButton) answerChoicesRadioGroup.getChildAt(0);
                RadioButton choice2 = (RadioButton) answerChoicesRadioGroup.getChildAt(1);
                RadioButton choice3 = (RadioButton) answerChoicesRadioGroup.getChildAt(2);
                RadioButton choice4 = (RadioButton) answerChoicesRadioGroup.getChildAt(3);

                choice1.setText(answerChoices.get(0));
                choice2.setText(answerChoices.get(1));
                choice3.setText(answerChoices.get(2));
                choice4.setText(answerChoices.get(3));
            }
        } else {
            answerChoicesRadioGroup.setVisibility(View.GONE);
        }
    }


    public void setRadioButtonChecked(int selectedAnswer) {
        if (selectedAnswer != -1) {
            RadioButton radioButton = (RadioButton) answerChoicesRadioGroup.getChildAt(selectedAnswer - 1);
            if (radioButton != null) {
                radioButton.setChecked(true);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int selectedAnswer = answerChoicesRadioGroup.indexOfChild(group.findViewById(checkedId)) + 1;
        answerSelectedListener.onAnswerSelected(selectedAnswer);
    }
}
