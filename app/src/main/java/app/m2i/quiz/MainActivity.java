package app.m2i.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.m2i.quiz.model.Question;


public class MainActivity extends Activity implements View.OnClickListener{

    private Question currentQuestion;
    private TextView questionTextView;
    private int currentQuestionIndex = 0;
    private Button trueButton;
    private Button falseButton;
    private List<Question> questionList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instanciation de la question
        //currentQuestion = new Question("Java c'est prise de tête ?", true);
        // Affichage de la question
        questionTextView = findViewById(R.id.questionLabel);
        questionTextView.setText(currentQuestion.getText());

        // Récupération des références aux bouttons
        trueButton = findViewById(R.id.trueButton);
        falseButton = findViewById(R.id.falseButton);


        // Gestion des evenements sur les boutons
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        questionList =new ArrayList<>();
        questionList.add(new Question("Peux-t'on manger du plastique",false));
        questionList.add(new Question("Est-ce que Saïd est toujours à l'heure",false));
        questionList.add(new Question("Est-ce Fafa aimes les hommes ???",true));

        // On définit la question en cours grace a l'index de la question du tableau de question
        currentQuestion = questionList.get(currentQuestionIndex);

        // On appelle la question pour son affichage
        showQuestion();


    }

    private void showQuestion(){
        questionTextView.setText(currentQuestion.getText());
    }




    @Override
    public void onClick(View v) {



        if (v == falseButton){
            checkAnswer(false);
        }else if (v == trueButton){
            checkAnswer(true);
        }


    }


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
