package app.m2i.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import app.m2i.quiz.model.DatabaseHelper;

public class QuestionFormActivity extends Activity implements View.OnClickListener {

    private Button cancelButton, validateButton;
    private Switch answerSwitch;
    private EditText questionEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form);

        questionEditText = findViewById(R.id.edtitTextForm);
        answerSwitch = findViewById(R.id.goodAnswerSwitch);
        cancelButton = findViewById(R.id.cancelFormButton);
        validateButton = findViewById(R.id.validateFormButton);

        validateButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intentionResult = new Intent();
        if (v == validateButton) {

            validateForm();

            setResult(RESULT_OK, intentionResult);

        } else if (v == cancelButton) {
            setResult(RESULT_CANCELED, intentionResult);
        }
        finish();
    }

    private void validateForm() {

        // Récupération de la saisie

        String questionText = questionEditText.getText().toString();
        Boolean goodAnswer = answerSwitch.isChecked();

        // Insertion dans l base de donnée avec la classe databaseHelper

        DatabaseHelper dbH = new DatabaseHelper(this);
        dbH.insert(questionText, goodAnswer);

    }


}
