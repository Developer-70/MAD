package com.example.bank_management_app;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.util.Log;



import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BANKING_DB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "USERS";
    private static final String TABLE_TRANSACTIONS = "TRANSACTIONS";
    private static final String USER_ID = "id";
    private static final String TRANSACTION_ID = "tr_id";
    private static final String TR_ACC_NO = "tr_acc_no";
    private static final String TRANSACTION_DATE_TIME = "tr_date_time";
    private static final String TRANSACTION_TYPE = "tr_type";
    private static final String TRANSACTION_AMOUNT = "tr_amount";

    private static final String NAME = "name";
    private static final String CONTACT_NO = "contact_no";
    private static final String EMAIL_ID = "email_id";
    private static final String BALANCE = "balance";
    private static final String ACC_NO = "acc_no";

    public String login_account_no = "900000001";


    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE "+ TABLE_USERS + "(" + USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ NAME +" TEXT NOT NULL,"+ CONTACT_NO +" TEXT UNIQUE, " +EMAIL_ID+" TEXT, "+ BALANCE + " INTEGER" +")");
//        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
//                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                NAME + " TEXT NOT NULL, " +
//                CONTACT_NO + " TEXT UNIQUE, " +
//                EMAIL_ID + " TEXT, " +
//                BALANCE + " INTEGER, " +
//                ACC_NO + " TEXT UNIQUE"   +
//                ")");
//        db.execSQL("CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
//                TRANSACTION_ID+" PRIMARY KEY AUTOINCREMENT, " +
//                TR_ACC_NO +" TEXT NOT NULL, " +
//                TRANSACTION_DATE_TIME+ " TEXT NOT NULL, " +
//                TRANSACTION_TYPE+ " TEXT NOT NULL, " +
//                TRANSACTION_AMOUNT+ " INTEGER NOT NULL, " +
//                "FOREIGN KEY(ACC_NO) REFERENCES " + TABLE_USERS + "(" + ACC_NO + ")" +
//                ")");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_USERS);
        onCreate(db);

    }

    public void addUser(String name, String contact_no, String email_id, int balance, String acc_no)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(CONTACT_NO, contact_no);
        values.put(EMAIL_ID, email_id);
        values.put(BALANCE, balance);
        values.put(ACC_NO, acc_no);

        db.insert(TABLE_USERS, null, values);
    }

    public void addTransaction(String acc_no, String tr_date_time, String tr_type, int tr_amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ACC_NO, acc_no);
        cv.put(TRANSACTION_DATE_TIME, tr_date_time);
        cv.put(TRANSACTION_TYPE, tr_type);
        cv.put(TRANSACTION_AMOUNT, tr_amount);

        db.insert(TABLE_TRANSACTIONS, null, cv);

    }



    public ArrayList<ContactModel> fetch_data()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_USERS,null);
        ArrayList<ContactModel> arrUser_data = new ArrayList<>();
        while (cursor.moveToNext())
        {
            ContactModel model = new ContactModel();
            model.id = cursor.getInt(0);
            model.name = cursor.getString(1);
            model.contact_no = cursor.getString(2);
            model.email_id = cursor.getString(3);
            model.balance = cursor.getInt(4);
            model.account_no = cursor.getString(5);

            arrUser_data.add(model);

        }
        cursor.close();
        return arrUser_data;

    }

//    public void updateBalance(ContactModel contactmodel)
//    {
//
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        cv.put(BALANCE, contactmodel.balance);
//
//        database.update(TABLE_USERS, cv,ACC_NO+"="+contactmodel.account_no, null);
//
//    }

    public void updateBalance(ContactModel contactmodel)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(BALANCE, contactmodel.balance);

        db.update(TABLE_USERS, cv, ACC_NO+"="+contactmodel.account_no,null);

    }

    public int getBalance(String accountNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        int balance = -1; // Initialize balance to a default value (-1 indicates balance not found)

        // Define the raw SQL query to retrieve the balance
        String sqlQuery = "SELECT " + BALANCE + " FROM " + TABLE_USERS +
                " WHERE " + ACC_NO + " = ?";

        // Execute the raw SQL query
        Cursor cursor = db.rawQuery(sqlQuery, new String[]{accountNumber});

        // Check if the cursor contains any rows
        if (cursor.moveToFirst()) {
            // Retrieve the balance from the cursor
            balance = cursor.getInt(0); // Assuming balance is the first (and only) column in the result set
        }

        // Close the cursor and database connection
        cursor.close();
        db.close();

        return balance;
    }


    public Boolean valid_cheque_no(String cheque)
    {
        String expected_format = "chxxxx";
        String entered_format = cheque.substring(0,6);
        String expected_end = cheque.substring(6);
        if(entered_format==expected_format || isValidInt(expected_end))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean valid_acc_no(String acc_no)
    {
        if(acc_no.charAt(0)=='9' && acc_no.length()==9)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean isValidInt(String str) {
        try {
            int number = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



        public String get_date_time() {
            // Get the current date and time
            Calendar calendar = Calendar.getInstance();

            // Define the format you want for the string representation
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

            // Format the current date and time into a string
            String formattedDateTime = formatter.format(calendar.getTime());

            // Print the formatted date and time
            return formattedDateTime;
        }

//        public ArrayList<TR_ContactModel> fetchTransaction()
//        {
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_TRANSACTIONS,null);
//            ArrayList<TR_ContactModel> transactions = new ArrayList<>();
//
//            while(cursor.moveToNext())
//            {
//                TR_ContactModel model = new TR_ContactModel();
//                model.id = cursor.getInt(0);
//                model.acc_no = cursor.getString(1);
//                model.tr_date_time = cursor.getString(2);
//                model.type = cursor.getString(3);
//                model.amount = cursor.getInt(4);
//                model.acc_no = cursor.getString(5);
//
//                transactions.add(model);
//            }
//            cursor.close();
//            return transactions;
//        }
public ArrayList<TR_ContactModel> fetchTransaction() {
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE ACC_NO = ?";
    Cursor cursor = db.rawQuery(query, new String[]{login_account_no});
    ArrayList<TR_ContactModel> transactions = new ArrayList<>();

    while (cursor.moveToNext()) {
        TR_ContactModel model = new TR_ContactModel();
        model.id = cursor.getInt(cursor.getColumnIndexOrThrow("tr_id"));
        model.acc_no = cursor.getString(cursor.getColumnIndexOrThrow("acc_no"));
        model.tr_date_time = cursor.getString(cursor.getColumnIndexOrThrow("tr_date_time"));
        model.type = cursor.getString(cursor.getColumnIndexOrThrow("tr_type"));
        model.amount = cursor.getInt(cursor.getColumnIndexOrThrow("tr_amount"));

        transactions.add(model);
    }
    cursor.close();
    return transactions;
}


    public ArrayList<String> FetchAccountNumbers() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ACC_NO FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> accountNumbers = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                String accNo = cursor.getString(cursor.getColumnIndexOrThrow("acc_no"));
                accountNumbers.add(accNo);
            }
        } catch (Exception e) {
            Log.e("FetchAccountNumbers", "Error fetching account numbers: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return accountNumbers;
    }






}





