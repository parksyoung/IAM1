package edu.sungshin.iam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth; //파이어베이스 인증 처리
    private DatabaseReference mDB; // 실시간 데이터베이스
    private  EditText id, pw, pwcheck;
    private Button check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance().getReference("IAM");

        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);
        pwcheck = findViewById(R.id.pwcheck);
        check = findViewById(R.id.register);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 처리 시작
                String strId = id.getText().toString();
                String strPw = pw.getText().toString();
                String strpwcheck = pwcheck.getText().toString();

                //FirebaseAuth 진행
                mAuth.createUserWithEmailAndPassword(strId, strPw).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPw);

                            //setValue : database에 insert 하는 것
                            mDB.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);

                            Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 계정이거나 올바르지 않은 형식입니다. 다시 입력해주십시오.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                /*
               switch (view.getId()){
                    case R.id.check:
                        signUp();
                        break;
                }*/
            }
        });
    }
/*
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
            // 비밀번호와 비밀번호 확인이 일치X
            else {
                Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
        // 아이디, 비밀번호, 비밀번호 확인 중 하나라도 공백일 때
        else {
            Toast.makeText(RegisterActivity.this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }*/

}