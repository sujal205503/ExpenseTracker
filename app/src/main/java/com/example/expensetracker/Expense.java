package com.example.expensetracker;

public class Expense {
    private int id;
    private String name;
    private double amount;
    private String date;

    public Expense(int id, String name, double amount, String date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}
