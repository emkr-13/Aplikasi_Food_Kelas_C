package com.example.food

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import com.example.food.CameraView

class CameraActivity : AppCompatActivity() {
    lateinit var proximitySensor: Sensor
    lateinit var sensorManager: SensorManager
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        1
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if(proximitySensor == null){
            Toast.makeText(this, "No proximty sensor found in device...", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            sensorManager.registerListener(
                proximitySensorEventListener,
                proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        try {
            mCamera = Camera.open()
        }catch (e:Exception){
            Log.d("Error", "Failed to get Camera" + e.message)
        }
        if (mCamera != null){
            mCameraView = CameraView(this, mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)
        }
        @SuppressLint("MissingInflatedId", "LocalSuppress") val imageClose =
            findViewById<View>(R.id.imgClose) as ImageButton
        imageClose.setOnClickListener{view: View? -> System.exit(0)}
    }

    var proximitySensorEventListener: SensorEventListener? = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0f) {
                    try {
                        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)
                    }catch (e:Exception){
                        Log.d("Error", "Failed to get Camera" + e.message)
                    }
                    if (mCamera != null){
                        mCameraView = CameraView(this@CameraActivity, mCamera!!)
                        val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
                        camera_view.addView(mCameraView)
                    }
                    @SuppressLint("MissingInflatedId", "LocalSuppress") val imageClose =
                        findViewById<View>(R.id.imgClose) as ImageButton
                    imageClose.setOnClickListener{view: View? -> System.exit(0)}
                } else {
                }
            }
        }
    }
}