package com.example.nawar.quizes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nawar.quizes.R;
import com.example.nawar.quizes.model.Questions;
import com.example.nawar.quizes.model.Result;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Questions_Activity extends AppCompatActivity {
    @BindView(R.id.answerA)
    Button answerA;
    @BindView(R.id.answerB)
    Button answerB;
    @BindView(R.id.answerC)
    Button answerC;
    @BindView(R.id.answerD)
    Button answerD;
    @BindView(R.id.question)
    TextView questionTv;
    @BindView(R.id.timer)
    TextView mTextView;
    @BindView(R.id.score)
    TextView scoreTV;
    @BindView(R.id.heart1)
    ImageView heart1;
    @BindView(R.id.heart2)
    ImageView heart2;
    @BindView(R.id.heart3)
    ImageView heart3;
    @BindView(R.id.adView)
    AdView mAdView;
    int btnId;
    Questions mQestions;
    int index;
    int score = 0;
    int attempts = 3;
    int high_score;
    boolean answer;
    ArrayList<Result> results = new ArrayList<>();
    boolean paused = false;
    long timeDifference;
    CountDownTimer countDownTimer;
    long mMillisUntilFinished = 60000;
    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questins__activty);
        ButterKnife.bind(this);
        index = 0;
        scoreTV.setText(String.valueOf(score));
        paused = false;
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(getString(R.string.timer_key))) {

                mMillisUntilFinished = savedInstanceState.getLong(getString(R.string.timer_key));
            }

            if (savedInstanceState.containsKey(getString(R.string.result))) {
                results = savedInstanceState.getParcelableArrayList(getString(R.string.result));
            }
            if (savedInstanceState.containsKey(getString(R.string.index_key))) {
                index = savedInstanceState.getInt(getString(R.string.index_key));
            }
            attachDtaToUi(mMillisUntilFinished);


            if (savedInstanceState.containsKey(getString(R.string.score_key))) {
                scoreTV.setText(String.valueOf(savedInstanceState.getInt(getString(R.string.score_key))));
            }


            if (savedInstanceState.containsKey(getString(R.string.attemps_key))) {
                attempts=savedInstanceState.getInt(getString(R.string.attemps_key));
                if (attempts == 2) {
                    heart3.setVisibility(View.INVISIBLE);
                }

                if (attempts == 1) {
                    heart3.setVisibility(View.INVISIBLE);
                    heart2.setVisibility(View.INVISIBLE);

                }


            }
            if (savedInstanceState.containsKey(getString(R.string.btnKey)) && savedInstanceState.containsKey(getString(R.string.answer_key))) {

                Button button = findViewById((savedInstanceState.getInt(getString(R.string.btnKey))));
                answer=savedInstanceState.getBoolean(getString(R.string.answer_key));
                if (answer)
                    button.setBackgroundResource(R.drawable.right_bg);
                else
                    button.setBackgroundResource(R.drawable.wrong_bg);
                showNext();

            }
        } else {

            if (getIntent() != null && getIntent().hasExtra(getString(R.string.result))) {
                results = getIntent().getParcelableArrayListExtra(getString(R.string.result));
                Collections.shuffle(results.get(index).getAnswers());

            }
            if (results.size() > 0)
                attachDtaToUi(60000);


        }


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);
    }

    @OnClick(R.id.answerA)
    public void clickA() {
        btnId = R.id.answerA;

        buttonClicked(R.id.answerA, answerA);

    }

    @OnClick(R.id.answerB)
    public void clickB() {
        btnId = R.id.answerB;


        buttonClicked(R.id.answerB, answerB);

    }

    @OnClick(R.id.answerC)
    public void clickC() {
        btnId = R.id.answerC;

        buttonClicked(R.id.answerC, answerC);
    }

    @OnClick(R.id.answerD)
    public void clickD() {
        btnId = R.id.answerD;

        buttonClicked(R.id.answerD, answerD);

    }

    private static class MyHandler extends Handler {
    }

    private final MyHandler mHandler = new MyHandler();

    public class MyRunnable implements Runnable {
        private final WeakReference<Activity> mActivity;

        public MyRunnable(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            if (index < results.size()) {
                index++;
            }
            attachDtaToUi(60000);

        }
    }

    private void attachDtaToUi(long startAt) {
        if(countDownTimer!=null)
        countDownTimer.cancel();
        startCount(startAt);
        clicked = false;
        questionTv.setText((Html.fromHtml(results.get(index).getQuestion()).toString()));
        answerA.setText(Html.fromHtml(results.get(index).getAnswers().get(0)).toString());
        answerB.setText(Html.fromHtml(results.get(index).getAnswers().get(1)).toString());
        answerC.setText(Html.fromHtml(results.get(index).getAnswers().get(2)).toString());
        answerD.setText(Html.fromHtml(results.get(index).getAnswers().get(3)).toString());
        answerA.setTag(results.get(index).getAnswers().get(0));
        answerB.setTag(results.get(index).getAnswers().get(1));
        answerC.setTag(results.get(index).getAnswers().get(2));
        answerD.setTag(results.get(index).getAnswers().get(3));
        answerA.setClickable(true);
        answerB.setClickable(true);
        answerC.setClickable(true);
        answerD.setClickable(true);
        answerA.setBackgroundResource(R.drawable.answer_bg);
        answerB.setBackgroundResource(R.drawable.answer_bg);
        answerC.setBackgroundResource(R.drawable.answer_bg);
        answerD.setBackgroundResource(R.drawable.answer_bg);


    }

    private void buttonClicked(int id, Button button) {

        answerA.setClickable(false);
        answerB.setClickable(false);
        answerC.setClickable(false);
        answerD.setClickable(false);
        clicked = true;

        if (button.getText().equals(results.get(index).getCorrectAnswer())) {
            setRight();
            button.setBackgroundResource(R.drawable.right_bg);
            answer = true;
            if (index < results.size()) {
                showNext();
            } else {
                finishGame();
            }


        } else {
            answer = false;

            setAtemps();
            attempts--;
            button.setBackgroundResource(R.drawable.wrong_bg);
            setWrong();

        }
    }

    private void setRight() {
        answer = true;
        score += 10;
        scoreTV.setText(String.valueOf(score));
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.right);
        mp.start();

    }

    private void setWrong() {
        answer = false;
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
        mp.start();
    }

    public void setAtemps() {
        if (attempts == 3) {
            heart3.setVisibility(View.INVISIBLE);


            showNext();

        } else if (attempts == 2) {
            heart2.setVisibility(View.INVISIBLE);


            showNext();


        } else if (attempts == 1) {
            heart1.setVisibility(View.INVISIBLE);

            finishGame();
        }

    }

    private void showNext() {

        MyRunnable mRunnable = new MyRunnable(this);
        mHandler.postDelayed(mRunnable, 2000);
    }

    public boolean checkHighScore(String category) {
        if (SharedPreferencesMethods.loadSavedPreferences(this, category) > 0) {
            if (score > SharedPreferencesMethods.loadSavedPreferences(this, category)) {
                high_score = score;
                SharedPreferencesMethods.savePreferences(this, category, score);
                return true;
            } else {
                high_score = SharedPreferencesMethods.loadSavedPreferences(this, category);
            }

        }
        if (SharedPreferencesMethods.loadSavedPreferences(this, category) == 0 && score > 0) {
            high_score = score;

            SharedPreferencesMethods.savePreferences(this, category, score);
            return true;
        }


        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    public void finishGame() {
        Intent intent = new Intent(Questions_Activity.this, GameOver.class);
        String category = results.get(0).getCategory();
        intent.putExtra(getString(R.string.category), results.get(0).getCategory());
        intent.putExtra(getString(R.string.score), score);
        if (checkHighScore(category)) {
            intent.putExtra(getString(R.string.new_high_score), "");
        }
        intent.putExtra(getString(R.string.high_sore), high_score);

        startActivity(intent);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (btnId != 0&&clicked)
            outState.putInt(getString(R.string.btnKey), btnId);
        outState.putLong(getString(R.string.timer_key), mMillisUntilFinished);
        if(clicked)
            outState.putBoolean(getString(R.string.answer_key), answer);
        outState.putInt(getString(R.string.score_key), score);
        outState.putInt(getString(R.string.index_key), index);
        outState.putInt(getString(R.string.attemps_key), attempts);
        outState.putParcelableArrayList(getString(R.string.result), results);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paused)
            startCount(mMillisUntilFinished);
    }

    private void startCount(long startAt) {
        countDownTimer = new CountDownTimer(startAt, 1000) {

            public void onTick(long millisUntilFinished) {

                mMillisUntilFinished = millisUntilFinished;

                if (millisUntilFinished < 10000)
                    mTextView.setTextColor(getResources().getColor(R.color.wrongColor));
                mTextView.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                finishGame();
            }
        }.start();

    }

}

