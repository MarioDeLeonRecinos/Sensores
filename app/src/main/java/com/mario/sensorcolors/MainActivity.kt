package com.mario.sensorcolors

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    private val TAG: String? = "MainActivity"
    private lateinit var sensorManager: SensorManager
    private var accSensor: Sensor? = null
    private var proxSensor: Sensor? = null

    private var x: Int = 0
    private var y: Int = 0
    private var z: Int = 0
    private var nx: Int = 0
    private var ny: Int = 0
    private var nz: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        proxSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            //no he podido implementar con el sensro de luz
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {

                if (event.values[0] < 0.5) {
                    linearlayout.setBackgroundColor(Color.GREEN)
                    Log.d(TAG, "light" + event.values[0])
                }

            }
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {

                xValue.text = event.values[0].toString()
                yValue.text = event.values[1].toString()
                zValue.text = event.values[2].toString()

                nx = event.values[0].toInt()
                ny = event.values[1].toInt()
                nz = event.values[2].toInt()

                if (nx.plus(x) > nx.plus(5)) {
                    linearlayout.setBackgroundColor(Color.DKGRAY)
                }
                if (nx.plus(x) < nx.minus(5)) {
                    linearlayout.setBackgroundColor(Color.MAGENTA)
                }

                x = event.values[0].toInt()
                y = event.values[1].toInt()
                z = event.values[2].toInt()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, proxSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}
