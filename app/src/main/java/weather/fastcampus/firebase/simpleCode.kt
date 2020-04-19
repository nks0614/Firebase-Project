package weather.fastcampus.firebase

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


fun AppCompatActivity.startActivity(activity: Class<*>) {
    startActivity(Intent(this, activity))
}

fun simpleToastShort(text : String, activity : Activity){
    Toast.makeText(activity,text,Toast.LENGTH_SHORT).show()
}

fun simpleToastLong(text : String, activity : Activity){
    Toast.makeText(activity,text,Toast.LENGTH_LONG).show()
}