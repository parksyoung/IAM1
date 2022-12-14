package edu.sungshin.HomeAlone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

                if(strId.length()>0 && strPw.length()>0 && strpwcheck.length()>0){
                    if(strPw.equals(strpwcheck)) {
                        //비밀번호 일치 시 FirebaseAuth 진행
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
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity.this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}