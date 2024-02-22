package pk.codebase.loginregisterwithrestapis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import pk.codebase.requests.HttpError;
import pk.codebase.requests.HttpRequest;
import pk.codebase.requests.HttpResponse;

public class Register extends AppCompatActivity {
    EditText username, email, password;
    Button register;
    TextView directToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        directToLogin = findViewById(R.id.directToLoginPage);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
            }

        });

        directToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
    }

    boolean isEmailValid(EditText text) {
        CharSequence email =  text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty (EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    void checkDataEntered(){

        String name = username.getText().toString();
        String userEmail = username.getText().toString();
        String userPassword = username.getText().toString();

        if(isEmpty(username)){
            username.setError("Field required");
            Toast t = Toast.makeText(this,"You must enter username to register!",Toast.LENGTH_SHORT);
            t.show();
        }

        if (isEmpty(username) && isEmpty(email) && isEmpty(password)) {
            username.setError("Field required");
            email.setError("Field required");
            password.setError("Field required");
        } else if (isEmpty(password)) {
            password.setError("Field required");
        } else {
            registerUser(name, userEmail, userPassword);
        }

        if (isEmailValid(email) == false){
            email.setError("Enter valid email!");
        }
    }

    private void registerUser(String userName, String email, String password) {
        HttpRequest request = new HttpRequest();
        request.setOnResponseListener(new HttpRequest.OnResponseListener() {
            @Override
            public void onResponse(HttpResponse response) {
                if (response.code == HttpResponse.HTTP_OK) {
                    System.out.println(response.toJSONObject());
                    Toast.makeText(Register.this, "User registered!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "User not registered! " + response.code, Toast.LENGTH_SHORT).show();
                    Log.e("error", response.toString() + ",.,." + response.code + ",.,." + response.reason);

                }
            }
        });
        request.setOnErrorListener(new HttpRequest.OnErrorListener() {
            @Override
            public void onError(HttpError error) {
                // There was an error, deal with it
                Log.e("error", error.toString() + ",.,." + error.code + ",.,." + error.reason);
                Toast.makeText(Register.this, "User not  registered!" + error.code +",.,."+ error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        JSONObject json;
        try {
            json = new JSONObject();
            json.put("username", userName);
            json.put("email", email);
            json.put("password", password);
        } catch (JSONException ignore) {
            return;
        }
        request.post("http://192.168.0.180:8080/register", json);
    }
}