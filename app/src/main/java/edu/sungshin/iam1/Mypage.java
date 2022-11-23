package edu.sungshin.iam1;

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

public class Mypage extends AppCompatActivity {
    ImageView imageView1;
    Button button1;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        imageView1 = findViewById(R.id.imageView2);
        button1 = findViewById(R.id.prof_btn);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
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