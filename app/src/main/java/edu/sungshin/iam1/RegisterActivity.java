package edu.sungshin.iam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.check:
                        signUp();
                        break;
                }
            }
        });
    }

    private void signUp() {
        String id = ((EditText)findViewById(R.id.id)).getText().toString();
        String password = ((EditText)findViewById(R.id.pw)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.pwcheck)).getText().toString();

        // 아이디, 비밀번호, 비밀번호 확인이 공백이 아닐 때
        if(id.length() > 0 && password.length() > 0 && passwordCheck.length() > 0){
            // 비밀번호와 비밀번호 확인이 일치할 때
            if(password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(id, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if(task.getException().toString() != null){
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
            else {
                Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RegisterActivity.this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}