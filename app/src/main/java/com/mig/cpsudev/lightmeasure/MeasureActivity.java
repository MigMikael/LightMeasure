package com.mig.cpsudev.lightmeasure;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MeasureActivity extends AppCompatActivity {

    TextView valueTextView, maxValueTextView;
    ProgressBar lightMeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        maxValueTextView = (TextView) findViewById(R.id.maxValueTextView);
        valueTextView = (TextView) findViewById(R.id.valueTextView);
        lightMeter = (ProgressBar) findViewById(R.id.lightMeter);

        if (lightSensor == null){
            Toast.makeText(MeasureActivity.this, "Device Not Support Light Sensor", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            float max = lightSensor.getMaximumRange();
            lightMeter.setMax((int) max);
            maxValueTextView.setText("Max : " + String.valueOf(max) + " Lux");

            sensorManager.registerListener(lightSensorEvenListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    SensorEventListener lightSensorEvenListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT){
                final float currentReading = event.values[0];
                lightMeter.setProgress((int) currentReading);
                valueTextView.setText("Now : " + String.valueOf(currentReading) + " Lux");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
