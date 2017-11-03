package app.m2i.quiz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SolutionActivity extends Activity implements View.OnClickListener {

    // Déclaration des variables passés en parametres depuis l'activité précédente
    private int quetionIndex;
    private Boolean questionAnswer;
    private String questionTextValue;

    // Déclaration des variables pour afficher la bonne réponse
    private TextView goodAnswerLabel1;
    private TextView goodAnswerLabel2;
    private TextView goodAnswerLabel3;


    // Récupération des Champs Texte


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        // Récupération des données passées dans l'intention

        Bundle params = this.getIntent().getExtras();
        quetionIndex = params.getInt("questionIndex");
        questionAnswer = params.getBoolean("questionAnswer");
        questionTextValue = params.getString("questionAnswerTextValue");
        // Récupération des Champs Texte
        goodAnswerLabel1 = findViewById(R.id.goodAnswerResultLabel1);
        goodAnswerLabel2 = findViewById(R.id.goodAnswerResultLabel2);
        goodAnswerLabel3 = findViewById(R.id.goodAnswerResultLabel3);

        goodAnswerLabel1.setText("La réponse à la question N°: " + quetionIndex);
        goodAnswerLabel2.setText(questionTextValue + ": ");
        goodAnswerLabel3.setText(questionAnswer.toString());


    }

    @Override
    public void onClick(View view) {

    }
}
