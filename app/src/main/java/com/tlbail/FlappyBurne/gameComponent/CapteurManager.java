package com.tlbail.FlappyBurne.gameComponent;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tlbail.FlappyBurne.Activity.GameView;

import java.util.ArrayList;
import java.util.List;

public class CapteurManager extends GameComponent implements SensorEventListener  {


    private AppCompatActivity appCompatActivity;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private float lux;
    public List<OnReadableLigth> onReadableLigths;

    /**
     * s'occupe des capteurs si pas de lumière donne l'info qu'il faut passé le background en mode nuit
     * @param onReadableLigth
     */

    public void addListener(OnReadableLigth onReadableLigth){
        onReadableLigths.add(onReadableLigth);
    }


    public void unsubScribe(OnReadableLigth onReadableLigth){
        onReadableLigths.remove(onReadableLigth);
    }

    public CapteurManager(GameView gameView) {
        super(gameView);
        this.appCompatActivity = getAppCompatActivity();
        sensorManager = (SensorManager) appCompatActivity.getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lux = 0;
        onReadableLigths = new ArrayList<>();

    }


    @Override
    public void stop() {
        super.stop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void start() {
        super.start();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                lux = event.values[0];
                Log.i("lux", String.valueOf(lux));
                for(OnReadableLigth onReadableLigth: onReadableLigths){
                    onReadableLigth.onReadableLight(lux);
                }
            }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void resume() {
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
