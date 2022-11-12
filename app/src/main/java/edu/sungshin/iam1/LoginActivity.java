package edu.sungshin.iam1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_forgot;
    Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_forgot = (Button) findViewById(R.id.btn_forgot);
        btn_signin = (Button) findViewById(R.id.btn_signin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "혼자 사는 당신! 환영합니다!",
                            Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}