package edu.sungshin.iam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;


import edu.sungshin.iam1.R;

import java.util.ArrayList;
import java.util.Random;

public class Community extends AppCompatActivity implements View.OnClickListener, MemoViewListener {

    private ArrayList<MemoItem> memoItems = null;
    private MemoAdapter memoAdapter = null;
    private String username = null;
    private String uid = null;
    private FirebaseAuth mAuth; //파이어베이스 인증 처리
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        init();
        initView();
    }


    @Override
    public void onItemClick(int position, View view)
    {

    }

    private void init()
    {
        mAuth = FirebaseAuth.getInstance();
        memoItems = new ArrayList<>();
        //realtimedatabase add
        username = "user_" + new Random().nextInt(1000);

        //realtimedatabase add2
        /*
        Button userbtn = findViewById(R.id.reguser);
        userbtn.setOnClickListener(this);
        */

        //realtimedatabase add3
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            this.uid = user.getUid();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.memobtn:
                regMemo();
                break;
            case R.id.cleanbtn:
                cleanMemo();
                break;
        }
    }



    private void initView()
    {
        Button regbtn = (Button)findViewById(R.id.memobtn);
        Button cleanbtn = (Button)findViewById(R.id.cleanbtn);
        regbtn.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.memolist);
        memoAdapter = new MemoAdapter(memoItems, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memoAdapter);
    }

    private void regMemo()
    {
        //realtimedatabase add3
        if (uid == null)
        {
            Toast.makeText(this,
                    "메모를 추가하기 위해서는 인증이 되어야합니다. 인증 후 다시 진행해주세요.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        EditText titleedit = (EditText) findViewById(R.id.memotitle);
        EditText contentsedit = (EditText) findViewById(R.id.memocontents);

        if(titleedit.getText().toString().length() == 0 ||
                contentsedit.getText().toString().length() == 0)
        {
            Toast.makeText(this,
                    "메모 제목 또는 메모 내용이 입력되지 않았습니다. 입력 후 다시 시작해주세요.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        MemoItem item = new MemoItem();
        item.setMemotitle(titleedit.getText().toString());
        item.setMemocontents(contentsedit.getText().toString());

        //realtimedatabase add
        //2) 등록시 실시간 데이터베이스에 "memo"를 만들고 push()함수로 메모의 내용을 업로드
        // Firebase 클라이언트는 새 하위 요소마다 고유 키를 생성하는 push() 함수를 제공
        // 고유 키가 필요없다면 setValue를 사용해서 데이터 등록 하는 것이 바람직
        //memoItems.add(item);
        //memoAdapter.notifyDataSetChanged();
        //databaseReference.child("memo").push().setValue(item);

        //realtimedatabase add3
        databaseReference.child("memo").child(uid).push().setValue(item);
        AlertDialog.Builder dlg = new AlertDialog.Builder(Community.this);
        dlg.setTitle("메모 작성 완료"); //제목
        dlg.setMessage("작성하신 메모가 리스트에 추가되었습니다."); // 메시지
//                버튼 클릭시 동작
        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //토스트 메시지
                Toast.makeText(Community.this,"확인을 누르셨습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //realtimedatabase add
        addChildEvent();
    }

    //realtimedatabase add
    //3) 메모가 추가되면 onStart() 함수에서 등록한 데이터베이스 이벤트 리스너를 통해 등록된 결과를 전달받음
    private void addChildEvent() {
        if (uid == null)
        {
            Toast.makeText(this,
                    "메모를 조회하기 위해서는 인증이 되어야합니다. 인증 후 다시 진행해주세요.",
                    Toast.LENGTH_LONG).show();
            return;
        }
        //realtimedatabase add3
        databaseReference.child("memo").child(uid).addChildEventListener(new ChildEventListener()
                //databaseReference.child("memo").addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Log.d("MemoActivity", "addChildEvent in");
                MemoItem item = dataSnapshot.getValue(MemoItem.class);

                memoItems.add(item);
                memoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
    //realtimedatabase add2
    private void writeNewUser() {
        //realtimedatabase add3 --파이어베이스 인증정보로 사용자등록
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String name = user.getDisplayName();
            String email = user.getEmail();
            String uid = user.getUid();
            Log.d("MemoActivity", "name = " + name);
            Log.d("MemoActivity", "email = " + email);
            Log.d("MemoActivity", "uid = " + uid);

            UserAccount userInfo = new UserAccount();
            userInfo.setPassword("1234");
            userInfo.setIdToken(name);
            userInfo.setEmailId(email);

            databaseReference.child("users").child(uid).setValue(userInfo);
        }
        else
        {
            // No user is signed in
            Log.d("MemoActivity", "user null");
        }
    }
    private void addValueEventListener() {
        //realtimedatabase add2
        //4) 데이터가 변경될때마다 호출 되는 리스너
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Log.d("addValueEventListener", "Value = " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
    private void cleanMemo(){
        databaseReference.child("memo").child(uid).removeValue();
        Toast.makeText(getApplicationContext(), "메모 초기화 완료되셨습니다.",Toast.LENGTH_SHORT).show();
    }
}