package com.example.workoutplaner;

import android.content.Intent;
import android.view.MenuItem;
import android.content.Context;

public class MenuHandler {
    public static void HandleMenuClick(MenuItem item, Context context) {
        switch(item.getItemId()) {
            case R.id.item1: {
                Intent intent = new Intent(context, NavigationActivity.class);
                context.startActivity(intent);
            }
        }
    }
}
