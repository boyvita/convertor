package com.github.boyvita.convertor.controller;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class MenuMaster {
    public static final String COURSES = "Курсы валют";
    public static final String CONVERTER = "Конвертер";
    public static final String HISTORY = "История";

    public static void createMenu(Menu menu) {
        menu.add(COURSES);
        menu.add(CONVERTER);
        menu.add(HISTORY);
    }

    public static void redirect(Context applicationContext, CharSequence title) {
       if (title.equals(COURSES)) {
           Intent intent = new Intent(applicationContext.getApplicationContext(), MainActivity.class);
           applicationContext.startActivity(intent);
       }
        if (title.equals(CONVERTER)) {
            Intent intent = new Intent(applicationContext.getApplicationContext(), ConverterActivity.class);
            applicationContext.startActivity(intent);
        }
        if (title.equals(HISTORY)) {
            Intent intent = new Intent(applicationContext.getApplicationContext(), HistoryActivity.class);
            applicationContext.startActivity(intent);
        }
    }
}
