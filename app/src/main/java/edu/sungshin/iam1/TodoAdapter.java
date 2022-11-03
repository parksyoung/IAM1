package edu.sungshin.iam1;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Adapter는 main과 recyclerview를 연결

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    private ArrayList<Todo> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView textView_todo_item;
        protected Button delete, finish;
        protected int num = 0;

        public ViewHolder(View itemView){
            super(itemView);

            this.textView_todo_item = itemView.findViewById(R.id.list_item);
            this.delete = itemView.findViewById(R.id.delete);
            this.finish = itemView.findViewById(R.id.finish);

            //delete 버튼
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        mData.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
            //finish 버튼
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    num++;
                    if(num % 2 == 0){
                        finish.setBackgroundColor(Color.parseColor("#41729F"));
                    }else{
                        finish.setBackgroundColor(Color.RED);
                    }
                }
            });
        }
    }

    TodoAdapter(ArrayList<Todo> list) {
        mData = list;
    }

    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_recyclerchecklist, parent, false);
        TodoAdapter.ViewHolder vh = new TodoAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(TodoAdapter.ViewHolder holder, int position) {
        holder.textView_todo_item.setText(mData.get(position).getTodoName());
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }
}