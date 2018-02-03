package tott.eshep.slot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import java.io.IOException;
import java.io.InputStream;

import tott.eshep.slot.repository.Request;
import tott.eshep.slot.repository.impl.DoteImpl;
import tott.eshep.slot.game.GameScreen;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImageScreen extends Activity {

    public static final String STRING = "STRING";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String text = readTextFile(this, "data.txt");
        request(text);
    }

    private void request(String text) {
        Request.provideApiModule().check().enqueue(new Callback<DoteImpl>() {
            @Override
            public void onResponse(@NonNull Call<DoteImpl> call, @NonNull Response<DoteImpl> response) {
                if (response.isSuccessful()) {
                    DoteImpl casinoModel = response.body();
                    if (casinoModel != null) {
                        if (casinoModel.getResult()) {
                            setConfiguration(casinoModel.getUrl());
                        } else {
                            openGameScreen();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DoteImpl> call, Throwable t) {
                openGameScreen();
            }
        });
    }

    public String readTextFile(Context c, String name) {
        InputStream input;
        try {
            AssetManager assetManager = c.getAssets();
            input = assetManager.open("helloworld.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String text = new String(buffer);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setConfiguration(String configuration) {
        Intent intent = getIntent();
        if (intent != null) {
            String transform = configuration;
            Uri data = intent.getData();
            if (data != null && data.getEncodedQuery() != null) {
                String QUERY_1 = "cid";
                String QUERY_2 = "partid";
                if (data.getEncodedQuery().contains(QUERY_1)) {
                    String queryValueFirst = data.getQueryParameter(QUERY_1);
                    transform = transform.replace(queryValueFirst, "cid");
                } else if (data.getEncodedQuery().contains(QUERY_2)) {
                    String queryValueSecond = data.getQueryParameter(QUERY_2);
                    transform = transform.replace(queryValueSecond, "partid");
                }
                openPrepareScreen(transform);
            } else {
                openPrepareScreen(transform);
            }

        } else {
            openPrepareScreen(configuration);
        }
    }


    private void openPrepareScreen(String url) {
        Intent intent = new Intent(this, PrepareScreen.class);
        intent.putExtra(STRING, url);
        startActivity(intent);
        finish();
    }


    private void openGameScreen() {
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
        finish();
    }
}
