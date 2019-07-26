package edu.wit.mobileapp.mylightsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mgr;
    private Sensor light;
    private TextView text;
    private StringBuilder msg = new StringBuilder(2048);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        light = mgr.getDefaultSensor(Sensor.TYPE_LIGHT);
        text = findViewById(R.id.text);
    }

    @Override
    protected void onResume() {
        mgr.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }


    @Override
    protected void onPause() {
        mgr.unregisterListener(this, light);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        msg.append("Got a sensor event: " + event.values[0] + " SI lux units\n");
        text.setText(msg);
        text.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        msg.insert(0, sensor.getName() + " accuracy changed: " + i +
                (i == 1 ? " (LOW)" : (i == 2 ? " (MED" : " (HIGH)")) + "\n");
        text.setText(msg);
        text.invalidate();

    }
}
