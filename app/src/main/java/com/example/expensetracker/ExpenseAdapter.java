package com.example.expensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private ArrayList<Expense> expenses;
    private ViewExpensesActivity activity;

    public ExpenseAdapter(ArrayList<Expense> expenses, ViewExpensesActivity activity) {
        this.expenses = expenses;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.tvExpense.setText(expense.getName() + " - ₹" + expense.getAmount() + " (" + expense.getDate() + ")");

        holder.btnEdit.setOnClickListener(v -> activity.editExpense(expense));
        holder.btnDelete.setOnClickListener(v -> activity.deleteExpense(expense.getId()));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpense;
        Button btnEdit, btnDelete;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpense = itemView.findViewById(R.id.tvExpense);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
