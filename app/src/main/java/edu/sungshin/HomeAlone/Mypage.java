package edu.sungshin.HomeAlone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Mypage extends AppCompatActivity {
    FirebaseAuth mAuth;
    ImageView imageView1;
    Button logout, delAccount;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        mAuth = FirebaseAuth.getInstance();
        imageView1 = findViewById(R.id.imageView2);
        logout = findViewById(R.id.logout); // 로그아웃버튼
        delAccount = findViewById(R.id.delAccount); // 회원탈퇴버튼

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageView1.setImageURI(uri);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Mypage.this);
                    dlg.setTitle("프로필 사진 변경"); //제목
                    dlg.setMessage("프로필 사진이 변경되었습니다."); // 메시지
//                버튼 클릭시 동작
                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                            //토스트 메시지
                            Toast.makeText(Mypage.this,"확인을 누르셨습니다.",Toast.LENGTH_SHORT).show();
                        }
                    });
                    dlg.show();
                }
                break;
        }
    }
}