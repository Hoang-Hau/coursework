package uk.ac.gre.wm50.coursework1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyView> {




    Context context;
    ArrayList expense_id, expense_type, expense_amount, expense_date, trip_id;





    ExpenseAdapter(Context context,
                  ArrayList expense_id,
                  ArrayList expense_type,
                  ArrayList expense_amount,
                  ArrayList expense_date,
                  ArrayList trip_id) {

        this.context = context;
        this.expense_id = expense_id;
        this.expense_type = expense_type;
        this.expense_amount = expense_amount;
        this.expense_date = expense_date;
        this.trip_id = trip_id;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.expense_row, parent,false);
            return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {

        holder.expense_id_txt.setText(String.valueOf(expense_id.get(position)));
        holder.expense_type_txt.setText(String.valueOf(expense_type.get(position)));
        holder.expense_amount_txt.setText(String.valueOf(expense_amount.get(position)));
        holder.expense_date_txt.setText(String.valueOf(expense_date.get(position)));



    }


    @Override
    public int getItemCount() {return expense_id.size();}
    public static class MyView extends RecyclerView.ViewHolder {

        TextView expense_id_txt, expense_type_txt, expense_amount_txt, expense_date_txt, trip_id_txt;

        public MyView(@NonNull View itemView) {
            super(itemView);

            expense_id_txt = itemView.findViewById(R.id.expense_id_txt);
            expense_type_txt = itemView.findViewById(R.id.expense_type_txt);
            expense_amount_txt = itemView.findViewById(R.id.expense_amount_txt);
            expense_date_txt = itemView.findViewById(R.id.expense_date_txt);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);


        }

    }
}

