package com.example.bank_management_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class Transaction_History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        MyDBHelper dbHelper = new MyDBHelper(this);
        ArrayList<TR_ContactModel> transactions = dbHelper.fetchTransaction();

        ArrayList<String> transactionStrings = new ArrayList<>();
        for (TR_ContactModel transaction : transactions) {
            String transactionString = "ID: " + transaction.id +
                    " Transaction Acc No: " + transaction.acc_no +
                    " Date Time: " + transaction.tr_date_time +
                    " Type: " + transaction.type +
                    " Amount: " + transaction.amount;
            transactionStrings.add(transactionString);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionStrings);
        ListView listView = findViewById(R.id.listViewTransactions);

        if (listView != null) { // Ensure ListView is not null before setting adapter
            listView.setAdapter(adapter);
        }
    }
}
