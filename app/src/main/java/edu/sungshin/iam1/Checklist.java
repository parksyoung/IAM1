package edu.sungshin.iam1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import java.util.ArrayList;

public class Checklist extends AppCompatActivity {

    Button insert;
    EditText todoEdit;
    CalendarView calendarView;
    RecyclerView recyclerView;
    private ArrayList<Todo> todoArrayList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        calendarView = findViewById(R.id.calenderview);
        insert = (Button) findViewById(R.id.insert);
        todoEdit = (EditText) findViewById(R.id.todoList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todoArrayList = new ArrayList<>();
        todoAdapter = new TodoAdapter(todoArrayList);
        recyclerView.setAdapter(todoAdapter);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo newTodo = new Todo(todoEdit.getText().toString());
                todoArrayList.add(newTodo);
                todoAdapter.notifyDataSetChanged();
                todoEdit.setText(null);
            }
        });

    }

}