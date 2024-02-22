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

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    TextView directToRegister;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLog);
        password = findViewById(R.id.passwordLog);
        directToRegister = findViewById(R.id.directToRegisterPage);
        login = findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkDataEntered();
                Intent intent = new Intent(Login.this,MainActivity.class);
                startActivity(intent);
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


    void checkDataEntered(){


        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();



        if (isEmpty(email) && isEmpty(password)) {
            email.setError("Field required");
            password.setError("Field required");
        } else if (isEmpty(password)) {
            password.setError("Field required");
        } else {
            loginUser(userEmail, userPassword);
        }

        if (isEmailValid(email) == false){
            email.setError("Enter valid email!");
        }
    }
// void checkUsername(){
//        boolean isValid = true;
//        if(isEmpty(email) && !isEmail(email)){
//            email.setError("Write correct email");
//            isValid = false;
//        }
//
//        if (isEmpty(password)) {
//            password.setError("You must enter password to login!");
//            isValid = false;
//        }
// }
     private void loginUser(String email, String password){
         HttpRequest request = new HttpRequest();
         request.setOnResponseListener(new HttpRequest.OnResponseListener() {
             @Override
             public void onResponse(HttpResponse response) {
                 if (response.code == HttpResponse.HTTP_OK) {
                     System.out.println(response.toJSONObject());
                     JSONObject obj = response.toJSONObject();
                     Toast.makeText(Login.this, "User logged in", Toast.LENGTH_SHORT).show();
                     try {
                         String token = obj.getString("access_token");
                         Log.e("token", token);
                     } catch (JSONException e) {
                         throw new RuntimeException(e);
                     }
                 }else {
                     Toast.makeText(Login.this, "User not logged in! " + response.code, Toast.LENGTH_SHORT).show();
                     Log.e("error", response.toString() + ",.,." + response.code + ",.,." + response.reason);

                 }
             }
         });
         request.setOnErrorListener(new HttpRequest.OnErrorListener() {
             @Override
             public void onError(HttpError error) {
                 // There was an error, deal with it
                 Log.e("error", error.toString() + ",.,." + error.code + ",.,." + error.reason);
                 Toast.makeText(Login.this, "User not logged in!" + error.code +",.,."+ error.toString(), Toast.LENGTH_SHORT).show();
             }
         });

         JSONObject json;
         try {
             json = new JSONObject();
             json.put("email", email);
             json.put("password", password);
         } catch (JSONException ignore) {
             return;
         }
         request.post("http://192.168.0.180:8080/login", json);
     }
    boolean isEmailValid(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


}