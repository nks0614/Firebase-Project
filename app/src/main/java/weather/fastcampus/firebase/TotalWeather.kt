package weather.fastcampus.firebase

import java.io.Serializable

class TotalWeather(
    var main : Main? = null,
    var weatherList : ArrayList<Weather>? = null

):Serializable

class Weather(
    var description : String? = null,
    var icon : String? = null,
    var id : Int? = null,
    var main : String? = null
):Serializable

class Main(
    var humidity : Int? = null,
    var pressure : Int? = null,
    var temp : Double? = null,
    var temp_max : Double? = null,
    var temp_min : Double? = null
):Serializable