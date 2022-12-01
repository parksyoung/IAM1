package edu.sungshin.HomeAlone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.CheckBox;
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
    private CheckBox checkBox;
    private boolean saveLoginData;
    private String Id, Pwd;
    private SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        id = (EditText) findViewById(R.id.edit_id);
        pw = (EditText) findViewById(R.id.edit_pwd);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if (saveLoginData) {
            id.setText(Id);
            pw.setText(Pwd);
            checkBox.setChecked(saveLoginData);
        }

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance().getReference("IAM");

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
                                save();
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
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", id.getText().toString().trim());
        editor.putString("PWD", pw.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        Id = appData.getString("ID", "");
        Pwd = appData.getString("PWD", "");
    }
}