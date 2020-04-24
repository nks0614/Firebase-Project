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

        setUpRetrofit()

        setting.setOnClickListener {
            startActivity(AccountSettingActivity::class.java)
        }
    }


    private fun setUpRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(Service::class.java)
        service.getWeatherInfoOfLocation("London", "09c8dfc52b7541d33c528d09a55e2c18")
            .enqueue(object : Callback<JsonObject>{
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("abc", "response : "+response.body())
                }
            })
    }
}
