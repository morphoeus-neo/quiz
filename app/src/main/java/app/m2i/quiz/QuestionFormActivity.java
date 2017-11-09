package app.m2i.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;

import app.m2i.quiz.model.DatabaseHelper;

public class QuestionFormActivity extends Activity implements View.OnClickListener {

    private Button cancelButton, validateButton;
    private Switch answerSwitch;
    private EditText questionEditText;
    private TextView questionTextForm;
    private String getQuestionText;
    private String getExtraType;
    private String getSelectedId;
    private TextView title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_form);




        title = findViewById(R.id.titleForm);
        questionEditText = findViewById(R.id.edtitTextForm);
        questionTextForm = findViewById(R.id.questionTextForm);
        answerSwitch = findViewById(R.id.goodAnswerSwitch);
        cancelButton = findViewById(R.id.cancelFormButton);
        validateButton = findViewById(R.id.validateFormButton);

        validateButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        if (Objects.equals(getExtraType, "update") && getSelectedId != null) {
            Bundle params = this.getIntent().getExtras();
            getExtraType = params.getString("doUpdate", "add");
            getSelectedId = params.getString("questionAnswerId");
            getQuestionText = params.getString("questionText");


            title.setText("UPDATE Question");
            questionTextForm.setText("Question d'origine : " + getQuestionText);

        } else {

            questionTextForm.setVisibility(View.INVISIBLE);
            title.setText("ADD Question");
        }


    }

    @Override
    public void onClick(View v) {
        Intent intentionResult = new Intent();
        if (v == validateButton) {
            if (Objects.equals(getExtraType, "update") && getSelectedId != null) {
                validateUpdateForm();
                setResult(RESULT_OK, intentionResult);
            } else {
                validateForm();

                setResult(RESULT_OK, intentionResult);
            }
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

    private void validateUpdateForm() {
        String questionText = questionEditText.getText().toString();
        Boolean goodAnswer = answerSwitch.isChecked();

        // Insertion dans l base de donnée avec la classe databaseHelper

        DatabaseHelper dbH = new DatabaseHelper(this);
        dbH.modify(getSelectedId, questionText, goodAnswer);

    }


}
