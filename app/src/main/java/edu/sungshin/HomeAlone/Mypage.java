package edu.sungshin.HomeAlone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.net.IDN;

public class Mypage extends AppCompatActivity {
    FirebaseAuth mAuth;
    ImageView imageView1;
    TextView tv_id;
    Button logout, delAccount;
    String TAG = "MainActivity";
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Id = ((LoginActivity)LoginActivity.context_main).strId;

        mAuth = FirebaseAuth.getInstance();
        imageView1 = findViewById(R.id.imageView2);
        logout = findViewById(R.id.logout); // 로그아웃버튼
        delAccount = findViewById(R.id.delAccount); // 회원탈퇴버튼
        tv_id = findViewById(R.id.tv_id);

        tv_id.setText(Id + "님");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃 버튼
                mAuth.signOut();
                Intent intent = new Intent(Mypage.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        delAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //탈퇴처리기능()
                mAuth.getCurrentUser().delete();
                Intent intent = new Intent(Mypage.this, LoginActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(), "회원탈퇴 완료되셨습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }

}