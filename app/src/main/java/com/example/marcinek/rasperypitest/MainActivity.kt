package com.example.marcinek.rasperypitest

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager
import java.io.IOException

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */

private const val LED_PIN_NAME = "BCM18" // !!! Change to the pin name you use.
private val TAG = MainActivity::class.java.simpleName

class MainActivity : Activity() {

    private var ledGpio: Gpio? = null
    private lateinit var ledSwitch: Switch


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = PeripheralManager.getInstance()
        try {
            ledGpio = manager.openGpio(LED_PIN_NAME)

            ledGpio?.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH)

            ledSwitch = findViewById(R.id.ledSwitch) as Switch

            ledSwitch.isChecked = ledGpio!!.value

/*            ledSwitch.setOnCheckedChangeListener { _, isChecked ->
                Log.d(TAG, "Led is ${if (isChecked) "on" else "off"}")
                ledGpio?.value = isChecked*/

                       ledSwitch.setOnCheckedChangeListener { _, unChecked ->
                Log.d(TAG, "Led is ${if (unChecked) "on" else "off"}")
                ledGpio?.value = unChecked


            }
        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e)
            finish()
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        try {
            ledGpio?.value = false
            ledGpio?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error on PeripheralIO API", e)
        }
    }

}
