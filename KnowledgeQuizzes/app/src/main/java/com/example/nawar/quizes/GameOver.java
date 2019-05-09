package com.example.nawar.quizes;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nawar.quizes.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameOver extends AppCompatActivity {
 @BindView(R.id.new_high_score)
    TextView new_high_score;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.high_score)
    TextView high_score;
    @BindView(R.id.genre)
    TextView category;
    @BindView(R.id.back_to_game)
    Button back_to_game;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        ButterKnife.bind(this);
        if(getIntent().hasExtra(getString(R.string.new_high_score))){
            new_high_score.setVisibility(View.VISIBLE);

        }
        if(getIntent().hasExtra(getString(R.string.category))){
            category.setText(getIntent().getStringExtra(getString(R.string.category)));
        }
        if(getIntent().hasExtra(getString(R.string.score))){
            score.setText(String.valueOf(getIntent().getIntExtra(getString(R.string.score),0)));
        }
        if(getIntent().hasExtra(getString(R.string.high_sore))){
            high_score.setText(String.valueOf(getIntent().getIntExtra(getString(R.string.high_sore),0)));
        }

    }
    @OnClick(R.id.back_to_game)
    public void submit(){
        Intent intent=new Intent(GameOver.this,Categories.class);
        startActivity(intent);
        finish();
    }
}
