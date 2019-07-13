package com.github.boyvita.convertor.controller;

import android.app.DatePickerDialog;
import android.content.ContextWrapper;
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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.boyvita.convertor.R;
import com.github.boyvita.convertor.model.HistoryItem;
import com.github.boyvita.convertor.util.SharedPreferncesMaster;
import com.github.boyvita.convertor.util.XMLParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class ConverterActivity extends AppCompatActivity {
    private TextView valueFrom, valueTo, date;
    private Spinner spinnerFrom, spinnerTo;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private class Calculator extends AsyncTask<Object, Void, Double> {
        @Override
        protected Double doInBackground(Object... args) {
            ContextWrapper context = (ContextWrapper) args[0];
            String date = (String) args[1];
            String valuteFrom = (String) args[2];
            String valuteTo = (String) args[3];
            Double valueFrom = (Double) args[4];
            XMLParser parser = null;
            Double res;
            try {
                parser = new XMLParser(date);
            }
            catch (Exception e) {
                return null;
            }
            Double dValuteFrom = parser.get(valuteFrom);
            Double dValuteTo = parser.get(valuteTo);
            if (dValuteFrom == null) {
                return new Double(-1);
            }
            if (dValuteTo == null) {
                return new Double(-2);
            }
            res = valueFrom * parser.get(valuteFrom) / parser.get(valuteTo);
            SharedPreferncesMaster master = new SharedPreferncesMaster(context);
            master.addItem(new HistoryItem(valuteFrom, valuteTo, valueFrom, res, date));
            return res;
        }

        protected void onPostExecute(Double res) {
            if (res == null) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Troubles with network", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                return;
            }
            if (res.equals(new Double(-1))) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "There isn't info about the first valute", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                return;
            }
            if (res.equals(new Double(-2))) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "There isn't info about the second valute", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                return;
            }
            valueTo.setText(res.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        valueFrom = findViewById(R.id.valueFrom);
        valueTo = findViewById(R.id.valueTo);
        date = findViewById(R.id.date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        date.setText(dateFormat.format(currentDate));

        ArrayAdapter<CharSequence> adapterFrom = ArrayAdapter.createFromResource(this,
                R.array.valutes, android.R.layout.simple_spinner_item);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerFrom.setAdapter(adapterFrom);

        ArrayAdapter<CharSequence> adapterTo = ArrayAdapter.createFromResource(this,
                R.array.valutes, android.R.layout.simple_spinner_item);
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo = findViewById(R.id.spinnerTo);
        spinnerTo.setAdapter(adapterTo);
        mDateSetListener = new ConverterActivity.DatePickerDialogListener();
    }

    public void onConvertButtonPressed(View v) {
        String date = this.date.getText().toString();
        String valuteFrom = this.spinnerFrom.getSelectedItem().toString();
        String valuteTo = this.spinnerTo.getSelectedItem().toString();
        String valueFromS = this.valueFrom.getText().toString();
        if (Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}", date) && Pattern.matches("\\d+(\\.\\d+)?", valueFromS)) {
            Double valueFrom = new Double(valueFromS);
            valueTo.setText("...");
            new Calculator().execute(this, date, valuteFrom, valuteTo, valueFrom);
        } else {
            this.valueTo.setText("something wrong");
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Неверный синтаксис!", Toast.LENGTH_SHORT);
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

}
