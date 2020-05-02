package weather.fastcampus.firebase

import android.app.Activity
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
    private val APP_ID = "09c8dfc52b7541d33c528d09a55e2c18"
    private val UNITS = "metric"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)


        setting.setOnClickListener {
            startActivity(AccountSettingActivity::class.java)
            //requestWeather()
            getLocationInfo()
        }
    }

    //위치 받아오는 함수

    private fun getLocationInfo(){
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this@WeatherActivity, //SDK가 23 이상이고 위치권한이 없으면
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE) //위치권한에 동의할 것인지 물어라

        } else {
            Log.d("else", "elseelse")
            val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            if(location != null) {
                Log.d("loc", "location : $location")
                val latitude = location.latitude
                val longitude = location.longitude
                requestWeatherInfoOfLocation(latitude, longitude)
                Log.d("please", "lat : $latitude log : $longitude")
            } else {
                Log.d("else", "else속의 else")
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    3000L,
                    0F,
                    this )
                locationManager.removeUpdates(this)
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PERMISSION_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                getLocationInfo()
            }
        }
    }

    //Location 메소드들

    override fun onLocationChanged(location: Location?) {
        Log.d("locationchange", "location이 바귐")
        val lat = location?.latitude
        val lon = location?.longitude
        if(lat != null && lon != null){
            requestWeatherInfoOfLocation(lat, lon)
        }

        Log.d("weathher", "2 $lat $lon")
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }


    //요청 함수

    private fun requestWeatherInfoOfLocation(lat : Double, lon : Double){
        (application as WeatherApplication)
            .requestService()
            ?.getweatherInfoOfCoordinates(lat, lon, APP_ID, UNITS)
            ?.enqueue(object : Callback<TotalWeather>{
                override fun onFailure(call: Call<TotalWeather>, t: Throwable) {
                    Log.d("fucking", "실패다 장애야")
                }
                override fun onResponse(call: Call<TotalWeather>, response: Response<TotalWeather>) {

                    if(response.isSuccessful){
                        val totalWeather = response.body()
                        Log.d("tttt", "weather : ${totalWeather?.main?.temp}")
                    }

                }
            })
    }


    private fun requestWeather(){
        (application as WeatherApplication)
            .requestService()
            ?.getWeatherInfoOfLocation("London", APP_ID)
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
