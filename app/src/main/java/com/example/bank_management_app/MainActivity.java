package com.example.bank_management_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String DATABASE_NAME = "BANKING_DB";
    String TABLE_TRANSACTIONS = "TRANSACTIONS";
    String TRANSACTION_ID = "tr_id";
    String TR_ACC_NO = "tr_acc_no";
    String TRANSACTION_DATE_TIME = "tr_date_time";
    String TRANSACTION_TYPE = "tr_type";
    String TRANSACTION_AMOUNT = "tr_amount";
    String TABLE_USERS = "USERS";
    String ACC_NO = "acc_no";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDBHelper dbHelper = new MyDBHelper(this);


        Button deposit_Button = findViewById(R.id.deposit);
        Button transaction_button = (Button) findViewById(R.id.transaction_btn);

        transaction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTransactionHistoryActivity();
            }
        });

        deposit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDepositActivity();

            }
        });
    }

    // Method to fetch data from TRANSACTIONS table and log it


    public void openDepositActivity() {
        Intent intent = new Intent(this, Activity_Deposit.class);
        startActivity(intent);
    }

    public void openTransactionHistoryActivity()
    {
        Intent intent = new Intent(this, Transaction_History.class);
        startActivity(intent);
    }
}


