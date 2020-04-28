package weather.fastcampus.firebase


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("data/2.5/weather/")
    fun getWeatherInfoOfLocation(
        @Query("q") location : String,
        @Query("APPID") appID : String
    ): Call<TotalWeather>

}