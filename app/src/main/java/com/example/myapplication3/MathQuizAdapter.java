package com.example.myapplication3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MathQuizAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private MathQuiz mathQuiz;
    private int currentQuestionIndex = 0;
    private int selectedAnswer = -1; // Initialize with an invalid value
    private OnAnswerSelectedListener answerSelectedListener;

    public MathQuizAdapter(OnAnswerSelectedListener listener) {
        this.answerSelectedListener = listener;
    }

    public void setMathQuiz(MathQuiz mathQuiz) {
        this.mathQuiz = mathQuiz;
        notifyDataSetChanged();
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void moveToNextQuestion() {
        currentQuestionIndex++;
        selectedAnswer = -1; // Reset selected answer when moving to the next question
        notifyDataSetChanged();
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view, answerSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        List<MathOperation> operations = mathQuiz.getOperations();
        if (position < operations.size()) {
            MathOperation operation = operations.get(position);
            String question = operation.getOperand1() + " " + operation.getOperator() + " " + operation.getOperand2() + " = ?";
            List<String> answerChoices = operation.getAnswerChoices(); // Get the answer choices for the operation
            holder.bind(question, true, answerChoices);
            holder.setRadioButtonChecked(selectedAnswer); // Set the selected answer in the RadioGroup
        } else {
            // Handle the case when there are no more questions
            String noQuestionMessage = "No more questions available";
            holder.bind(noQuestionMessage, false, null);
        }
    }

    @Override
    public int getItemCount() {
        if (mathQuiz != null) {
            return mathQuiz.getOperations().size() + 1; // Add 1 for the final message
        }
        return 0;
    }

    public interface OnAnswerSelectedListener {
        void onAnswerSelected(int selectedAnswer);
    }
}
