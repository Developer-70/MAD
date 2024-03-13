package com.example.bank_management_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;
import java.util.ArrayList;


public class Activity_Deposit extends AppCompatActivity {
    MyDBHelper dbHelper = new MyDBHelper(this);
    private ProgressBar progressBar;
    private static final int PROGRESS_BAR_DELAY = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Button deposit_money = (Button) findViewById(R.id.depositButton);
        EditText acc_no;
        EditText s_acc_no;
        EditText deposit;

        deposit_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ArrayList<ContactModel> arrUser_data = dbHelper.fetch_data();

                EditText acc_no = (EditText) findViewById(R.id.accountNumberEditText);
                EditText depositstr = (EditText) findViewById(R.id.amountEditText);
                EditText cheque_no = (EditText) findViewById(R.id.chequeNumberEditText);
                EditText s_acc_no = (EditText) findViewById(R.id.sendaccountNumberEditText);
                int flag = 0;

                String senders_acc_no = s_acc_no.getText().toString();

                progressBar = findViewById(R.id.progressBar);
                String chequeno = cheque_no.getText().toString();


                String account_number = acc_no.getText().toString(); // Get text from first EditText and store it as a String

                int depositAmount = 0; // Initialize deposit amount to 0
                ArrayList<String> accountNumbers = dbHelper.FetchAccountNumbers();
                for (String accNo : accountNumbers) {
                    if (accNo.equals(senders_acc_no)) {
                        flag = 1;
                        break;
                    } else {
                        flag = 0;
                    }
                }
                if (flag == 1) {
                    // Check if the text in the second EditText is not empty
                    if (!depositstr.getText().toString().isEmpty() && !acc_no.getText().toString().isEmpty() && !cheque_no.getText().toString().isEmpty() && !s_acc_no.getText().toString().isEmpty()) {
                        depositAmount = Integer.parseInt(depositstr.getText().toString()); // Convert text to integer and store in depositAmount

                        if (dbHelper.valid_cheque_no(chequeno)) {
                            if (dbHelper.valid_acc_no(account_number)) {

                                ContactModel model = new ContactModel();
                                model.account_no = account_number;
                                model.balance = dbHelper.getBalance(account_number) + depositAmount;
                                dbHelper.updateBalance(model);

                                ContactModel model1 = new ContactModel();
                                model1.account_no = senders_acc_no;
                                model1.balance = dbHelper.getBalance(senders_acc_no) - depositAmount;
                                dbHelper.updateBalance(model);

                                progressBar.setVisibility(View.VISIBLE); // Show the progress bar


                                // Use Handler to hide the progress bar after 2 seconds
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBar.setVisibility(View.INVISIBLE); // Hide the progress bar
                                    }
                                }, PROGRESS_BAR_DELAY);

                                String curr_date_time = dbHelper.get_date_time();
                                try {
                                    Thread.sleep(500); // Sleep for 2 seconds (2000 milliseconds)
                                    dbHelper.addTransaction(account_number, curr_date_time, "Deposit", depositAmount);
                                    dbHelper.addTransaction(senders_acc_no, curr_date_time,"Transfer", depositAmount);

                                    Toast.makeText(getApplicationContext(), depositAmount + " Was successfully Deposited", Toast.LENGTH_SHORT).show();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                //ArrayList<TR_ContactModel> transactions = dbHelper.fetchTransaction();

//                                for (int i = 0; i < arrUser_data.size(); i++) {
//                                    Log.d("User data", "ID: " + arrUser_data.get(i).id + " Name:" + arrUser_data.get(i).name + " Contact_no: " + arrUser_data.get(i).contact_no + " Email Id: " + arrUser_data.get(i).email_id + " Balance: " + arrUser_data.get(i).balance + " Account no: " + arrUser_data.get(i).account_no);
//
//                                }

//                                for (int i = 0; i < transactions.size(); i++) {
//                                    Log.d("User data", "ID: " + transactions.get(i).id + " Transaction Acc No: " + transactions.get(i).acc_no + " Date Time: " + transactions.get(i).tr_date_time + " Type: " + transactions.get(i).type + " Amount: " + transactions.get(i).amount);
//
//                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Enter a valid Account number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Enter a valid Cheque number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please fill all Fields", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    Toast.makeText(getApplicationContext(), "Please Enter valid Sender Account no", Toast.LENGTH_SHORT).show();
                }

            }


        });


    }
}