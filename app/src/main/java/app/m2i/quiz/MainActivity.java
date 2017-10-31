package app.m2i.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import app.m2i.quiz.model.Question;


public class MainActivity extends Activity implements View.OnClickListener{

    private Question currentQuestion;
    private TextView questionTextView;
    private Button trueButton;
    private Button falseButton;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciation de la question
        currentQuestion = new Question("Java c'est prise de tête ?", true);
        // Affichage de la question
        questionTextView = findViewById(R.id.questionLabel);
        questionTextView.setText(currentQuestion.getText());

        // Récupération des références aux bouttons
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);


        // Gestion des evenements sur les boutons
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);





    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {



        if (v == falseButton){
            checkAnswer(false);
        }else if (v == trueButton){
            checkAnswer(true);
        }


    }

    /**
     *
     * @param b
     */
    private void checkAnswer(boolean b) {
        String message;

        if (b == currentQuestion.getGoodAnswer()){
            message = "Bonne réponse";
        }else {
            message = "Mauvaise réponse";
        }
        // Affichage du message

       TextView resutMessage = findViewById(R.id.resultLabel);
        resutMessage.setText(message);
    }
}
