package com.feritriatmojo.aksesakselero

//TODO 1 : Mengimport data yang diperlukan dalam aplikasi
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate

//TODO 2 : Class MainActivity dengan implement sensor event listener
class MainActivity : AppCompatActivity(), SensorEventListener {
    //TODO 3 : menggunakan variabel sensorManager dan square didalamnya
    private lateinit var sensorManager: SensorManager
    private lateinit var square: TextView
    //TODO 4 : Memanggil class super onCreate untuk menyelesaikan pembuatan aktivitas
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO 5 : Mengatur keadaan ponsel dalam mode terang
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        square = findViewById(R.id.tv_square)

        setUpSensorStuff()
    }

    private fun setUpSensorStuff() {
        // TODO 6 : Membuat pengelola sensor
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // TODO 7 : Menentukan tipe sensor yang akan digunakan
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }
    //TODO 8 : Sensor Event berisi detail tentang data sensor tertentu yang berubah dari waktu ke waktu.
    override fun onSensorChanged(event: SensorEvent?) {
        // TODO 9 : Mengecek sensor yang sudah terdaftar
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            // TODO 10 : Memiringkan ponsel ke kiri (10) dan kanan (-10)
            val sides = event.values[0]

            // TODO 11 : Memiringkan ponsel ke atas (10), datar (0), terbalik (-10)
            val upDown = event.values[1]

            square.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = upDown * 10
            }

            // TODO 12 : Merubah warna persegi apabila dalam keadaan datar dan sebaliknya
            val color = if (upDown.toInt() == 0 && sides.toInt() == 0) Color.GREEN else Color.BLUE
            square.setBackgroundColor(color)

            square.text = "up/down ${upDown.toInt()}\nleft/right ${sides.toInt()}"
        }
    }
    //TODO 13 : Dipanggil ketika akurasi sensor terdaftar telah berubah
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }
    //TODO 14 : Dipanggil sebelum aktivitas ditutup
    override fun onDestroy() {
        sensorManager.unregisterListener(this)
        super.onDestroy()
    }
}
