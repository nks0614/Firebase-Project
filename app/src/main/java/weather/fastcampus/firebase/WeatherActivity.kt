package weather.fastcampus.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)



        setting.setOnClickListener {
            startActivity(AccountSettingActivity::class.java)
            requestWeather()
        }
    }

    private fun requestWeather(){
        (application as WeatherApplication)
            .requestService()
            ?.getWeatherInfoOfLocation("London", "09c8dfc52b7541d33c528d09a55e2c18")
            ?.enqueue(object : Callback<JsonObject>{
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("request","요청 결과 : "+response.body())
                }
            })
    }

}
