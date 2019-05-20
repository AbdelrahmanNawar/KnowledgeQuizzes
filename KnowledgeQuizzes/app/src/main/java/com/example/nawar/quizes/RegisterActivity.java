package com.example.nawar.quizes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.usernameId)
    EditText usernameText;
    @BindView(R.id.passwordId)
    EditText passwordText;
    @BindView(R.id.LoginButtonId)
    Button registerButton;
    @BindView(R.id.signInId)
    TextView signInTextView;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        firebaseAuth = firebaseAuth.getInstance();

        registerButton.setOnClickListener(v -> registerUser());
        signInTextView.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser!= null){
            Intent intent = new Intent(this, Categories.class);
            startActivity(intent);
        }
    }

    public void registerUser (){
        String email = usernameText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Toast.makeText(RegisterActivity.this,"successfully registered", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(RegisterActivity.this, Categories.class));
            }else {
                Log.e("nawar", "onComplete: Failed=" + task.getException().getMessage());
                Toast.makeText(RegisterActivity.this,"failed to register, please try again", Toast.LENGTH_LONG).show();
            }
        });

    }
}
