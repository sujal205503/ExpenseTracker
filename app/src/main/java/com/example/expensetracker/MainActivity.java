package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etName, etAmount, etDate;
    Button btnAdd, btnView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        db = new DatabaseHelper(this);

        btnAdd.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String amountText = etAmount.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (name.isEmpty() || amountText.isEmpty() || date.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean inserted = db.insertExpense(name, amount, date);
            if (inserted) {
                Toast.makeText(MainActivity.this, "Expense Added", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etAmount.setText("");
                etDate.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Failed to Add Expense", Toast.LENGTH_SHORT).show();
            }
        });

        btnView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ViewExpensesActivity.class);
            startActivity(intent);
        });
    }
}
