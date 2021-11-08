package com.example.workoutplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import java.util.List;

public class NavigationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void onWorkoutClick(View view) {
        Intent intent = new Intent(this, RVWorkout.class);
        this.startActivity(intent);
    }
    private void sharePhotoToFacebook()
    {
        Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.images);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Nice Place.....")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();


        ShareApi.share(content,null);
       // Toast.makeText("Facebook Photo Upload Complated",Toast.LENGTH_LONG).show();

    }

    public void onFBClick(View view) {
        /*
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fb.me"));
        startActivity(browserIntent);*/
        sharePhotoToFacebook();


        /*final String urlFb = "fb://page/"+"417037106750570";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlFb));

        // If a Facebook app is installed, use it. Otherwise, launch
        // a browser
        final PackageManager packageManager = getPackageManager();
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() == 0) {
            final String urlBrowser = "https://www.fb.me";
            intent.setData(Uri.parse(urlBrowser));
        }

        startActivity(intent);*/
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

        System.exit(0);

        /*
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);*/

        Intent intent = new Intent(this, LoginFragment.class);
        startActivity(intent);

        // Intent intent = new Intent(this, LoginFragment.class);
        //   startActivity(intent);

    }

}