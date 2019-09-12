package com.yumantha.smartphonesensorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    Sensor accelerometer;

    TextView xValue, yValue, zValue, showStatus;
    Button startButton, stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showStatus = (TextView) findViewById(R.id.showStatus);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);

        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        startButton.setOnClickListener((View.OnClickListener) this);
        stopButton.setOnClickListener((View.OnClickListener) this);

        Log.d(TAG, "onCreate: Initializing sensor services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        showStatus.setText("Status: Not running");
        stopButton.setEnabled(false);

        xValue.setText("X Value: 0.0");
        yValue.setText("Y Value: 0.0");
        zValue.setText("Z Value: 0.0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startButton:
                sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                Log.d(TAG, "Registered accelerometer listener");

                showStatus.setText("Status: Running");

                startButton.setEnabled(false);
                stopButton.setEnabled(true);

                break;
            case R.id.stopButton:
                sensorManager.unregisterListener(this);
                Log.d(TAG, "Unregistered accelerometer listener");

                showStatus.setText("Status: Not running");

                xValue.setText("X Value: 0.0");
                yValue.setText("Y Value: 0.0");
                zValue.setText("Z Value: 0.0");

                stopButton.setEnabled(false);
                startButton.setEnabled(true);

                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "onSensorChanged: X:" + event.values[0] + " Y:" + event.values[1] + " Z:" + event.values[2]);

        xValue.setText("X Value:\t\t" + event.values[0]);
        yValue.setText("Y Value:\t\t" + event.values[1]);
        zValue.setText("Z Value:\t\t" + event.values[2]);
    }
}
