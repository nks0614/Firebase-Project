package weather.fastcampus.firebase

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity(), LocationListener {

    private val PERMISSION_REQUEST_CODE = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        getLocationInfo()

        setting.setOnClickListener {
            startActivity(AccountSettingActivity::class.java)
            requestWeather()
        }
    }

    private fun getLocationInfo(){
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this@WeatherActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE)

        } else {

            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            if(location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                Log.d("please", "lat : $latitude log : $longitude")
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0L,
                    0F,
                    this )
                locationManager.removeUpdates(this)
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        val lat = location?.latitude
        val log = location?.longitude

        Log.d("weathher", "2 $lat $log")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

    private fun requestWeather(){
        (application as WeatherApplication)
            .requestService()
            ?.getWeatherInfoOfLocation("London", "09c8dfc52b7541d33c528d09a55e2c18")
            ?.enqueue(object : Callback<TotalWeather>{
                override fun onFailure(call: Call<TotalWeather>, t: Throwable) {

                }

                override fun onResponse(call: Call<TotalWeather>, response: Response<TotalWeather>) {
                    val totalWeather = response.body()
                    Log.d("test", "main : "+totalWeather?.main?.temp)
                }
            })
    }

}
