package pk.codebase.loginregisterwithrestapis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    TextView directToRegister;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupUI();
        setupListeners();


    }

    private void setupUI() {
        email = findViewById(R.id.emailLog);
        password = findViewById(R.id.passwordLog);
        directToRegister = findViewById(R.id.directToRegisterPage);
        login = findViewById(R.id.login);
    }

    private void setupListeners() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUsername();
            }
        });

        directToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
 void checkUsername(){
        boolean isValid = true;
        if(isEmpty(email) && !isEmail(email)){
            email.setError("Write correct email");
            isValid = false;
        }

        if (isEmpty(password)) {
            password.setError("You must enter password to login!");
            isValid = false;
        }
 }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


}