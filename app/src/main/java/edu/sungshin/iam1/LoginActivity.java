package edu.sungshin.iam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; //파이어베이스 인증 처리
    private DatabaseReference mDB; // 실시간 데이터베이스
    private EditText id, pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance().getReference("IAM");

        id = findViewById(R.id.edit_id);
        pw = findViewById(R.id.edit_pwd);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_signin = (Button) findViewById(R.id.btn_signin);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strId = id.getText().toString();
                String strPw = pw.getText().toString();

                if(strId.length()>0 && strPw.length()>0){
                    mAuth.signInWithEmailAndPassword(strId, strPw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //로그인 성공
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "혼자 사는 당신! 환영합니다!",Toast.LENGTH_SHORT).show();
                                finish(); // 현재 액티비티 파괴
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "현재 존재하는 계정이 아닙니다.아이디랑 비밀번호를 다시 확인 하시거나 회원가입을 하세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }

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