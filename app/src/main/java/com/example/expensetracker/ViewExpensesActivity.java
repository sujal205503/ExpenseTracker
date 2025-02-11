package com.example.expensetracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ViewExpensesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ExpenseAdapter adapter;
    DatabaseHelper db;
    ArrayList<Expense> expenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expenses);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new DatabaseHelper(this);
        loadExpenses();
    }

    private void loadExpenses() {
        expenses = new ArrayList<>();
        Cursor cursor = db.getAllExpenses();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Expenses Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                expenses.add(new Expense(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3)));
            }
        }

        adapter = new ExpenseAdapter(expenses, this);
        recyclerView.setAdapter(adapter);
    }

    public void editExpense(Expense expense) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Expense");

        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        EditText etEditName = view.findViewById(R.id.etName);
        EditText etEditAmount = view.findViewById(R.id.etAmount);
        EditText etEditDate = view.findViewById(R.id.etDate);

        etEditName.setText(expense.getName());
        etEditAmount.setText(String.valueOf(expense.getAmount()));
        etEditDate.setText(expense.getDate());

        builder.setView(view);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newName = etEditName.getText().toString().trim();
            String amountText = etEditAmount.getText().toString().trim();
            String newDate = etEditDate.getText().toString().trim();

            if (newName.isEmpty() || amountText.isEmpty() || newDate.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            double newAmount = Double.parseDouble(amountText);

            if (db.updateExpense(expense.getId(), newName, newAmount, newDate)) {
                Toast.makeText(this, "Expense Updated Successfully", Toast.LENGTH_SHORT).show();
                loadExpenses(); // Refresh list
            } else {
                Toast.makeText(this, "Failed to Update Expense", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    public void deleteExpense(int id) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Expense")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (db.deleteExpense(id)) {
                        Toast.makeText(this, "Expense Deleted Successfully", Toast.LENGTH_SHORT).show();
                        loadExpenses(); // Refresh list
                    } else {
                        Toast.makeText(this, "Failed to Delete Expense", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}