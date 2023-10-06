package com.cs407.lab4milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    boolean stopThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton= findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startDownload(v);
            }
        });


        Button stopButton= findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                stopDownload(v);
            }
        });

    }

    public class ExampleRunnable implements Runnable {
        @Override
        public void run(){
             mockFileDownloader();
        }
    }

    public void mockFileDownloader() {
        runOnUiThread(new Runnable()  {
            @Override
            public void run () {
                startButton.setText("DownLoading...");
            }
        });

        for (int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress + 10) {
            if(stopThread){
                runOnUiThread (new Runnable () {
                    @Override
                    public void run() {
                        startButton. setText ("Start");
                    }
                });
                return;
            }

            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run () {
                    startButton.setText("DownLoading..." + finalDownloadProgress + "%");
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Start");
            }
        });
    }

    public void startDownload (View view){
        stopThread=false;
        ExampleRunnable runnable = new ExampleRunnable ();
        new Thread(runnable).start();
    }

    public void stopDownload (View view) {
        stopThread = true;
    }
}
