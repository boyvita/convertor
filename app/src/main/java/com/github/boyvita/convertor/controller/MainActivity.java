package com.github.boyvita.convertor.controller;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.boyvita.convertor.R;
import com.github.boyvita.convertor.util.XMLParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView usd, eur, jpy, date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private class CurrentCourseSetter extends AsyncTask<String, Void, XMLParser> {
        @Override
        protected XMLParser doInBackground(String... args) {
            String date = (String) args[0];
            try {
                XMLParser parser = new XMLParser(date);
                return parser;
            } catch (Exception e) {
                return null;
            }
        }
        protected void onPostExecute(XMLParser parser) {
            if (parser == null) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Troubles with network", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            } else {
                Double usdInfo = parser.get("USD");
                Double eurInfo = parser.get("EUR");
                Double jpyInfo = parser.get("JPY");
                if (usdInfo != null) {
                    usd.setText(String.format("%.4f", usdInfo));
                } else {
                    usd.setText("There isn't info");
                }
                if (eurInfo != null) {
                    eur.setText(String.format("%.4f", eurInfo));
                } else {
                    eur.setText("There isn't info");
                }
                if (jpyInfo != null) {
                    jpy.setText(String.format("%.4f", jpyInfo));
                } else {
                    jpy.setText("There isn't info");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usd = (TextView) findViewById(R.id.usd);
        eur = (TextView) findViewById(R.id.eur);
        jpy = (TextView) findViewById(R.id.jpy);
        date = (TextView) findViewById(R.id.date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        date.setText(dateFormat.format(currentDate));
        mDateSetListener = new DatePickerDialogListener();
        new CurrentCourseSetter().execute("");
    }

    public void onButtonClicked(View v) {
        String s = date.getText().toString();
        if (Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}", s)) {
            usd.setText("...");
            eur.setText("...");
            jpy.setText("...");
            new CurrentCourseSetter().execute(s);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Неверный синтаксис", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
        }
    }

    public void showDatePicker(View v) {
        Locale locale = new Locale("ru", "Ru");
        Locale.setDefault(locale);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setText("Установить дату");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuMaster.createMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuMaster.redirect(getApplicationContext(), item.getTitle());
        return super.onOptionsItemSelected(item);
    }

    class DatePickerDialogListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Log.d("MYAPP", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
            String mDate = month + "/" + day + "/" + year;
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            Date targetDate = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date currentDate = new Date();
            date.setText(dateFormat.format(targetDate));
        }
    }

}
