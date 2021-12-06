package com.example.workoutplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.workoutplaner.Workouts.WorkoutActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavigationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static Context context;
    CallbackManager callbackManager;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
             imageView = (Bitmap) extras.get("data");
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(imageView)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            ShareDialog.show(this, content);
        }
    }


    public void onWorkoutClick(View view) {
        Intent intent = new Intent(this, WorkoutActivity.class);
        this.startActivity(intent);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    public void onPedometerClick(View view) {
        Intent intent = new Intent(this, PedometerActivity.class);
        this.startActivity(intent);
    }

    public void onProfileClick(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        this.startActivity(intent);
    }

    public void onFBClick(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/1277550729425565"));
        startActivity(browserIntent);
    }

    public void onInstaClick(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/?hl=en"));
        startActivity(browserIntent);
    }

    public void onTwitterClick(View view) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/?lang=en"));
        startActivity(browserIntent);
    }

    public void onLogoutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            Log.i("loged", "loged out");
        }
        else
        {
            Log.i("loged ", "still loged ");
        }
        finishAffinity();

        Intent login = new Intent(this, OfflineActivity.class);
        startActivity(login);


    }

}
