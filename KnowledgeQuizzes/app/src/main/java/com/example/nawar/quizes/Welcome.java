package com.example.nawar.quizes;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.nawar.quizes.model.NetWorkUtilities.FACEBOOK_DEVELOPER;

public class Welcome extends AppCompatActivity {
    @BindView(R.id.play)
    Button play;
    private FirebaseJobDispatcher mDispatcher;
    public static String FACTS_KEY = "com.example.nawar.knowledgebucket.facts_key";
    private String fact;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        scheduleJob();
        if (getIntent() != null && getIntent().hasExtra(FACTS_KEY)) {

             fact = SharedPreferencesMethods.loadSavedPreferencesString(this, getString(R.string.facts_key));
            showSharing();

        }


    }

    private void showSharing() {
        FacebookSdk.setApplicationId(getString(R.string.facebook_key));
        FacebookSdk.sdkInitialize(this);

        shareDialog = new ShareDialog(this);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(FACEBOOK_DEVELOPER))
                .setQuote(fact)
                .build();
        shareDialog.show(content);


    }


    private void scheduleJob() {

        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        final int windowStart =10; // after 10 seconds
        final int windowEnd = (int)TimeUnit.HOURS.toSeconds(24); // Every 24 hours


        Job myJob = mDispatcher.newJobBuilder()
                .setService(FactsJob.class)
                .setTag(getString(R.string.tag))
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(windowStart, windowEnd))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();

        mDispatcher.mustSchedule(myJob);

    }

    @OnClick(R.id.play)
    void play() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

