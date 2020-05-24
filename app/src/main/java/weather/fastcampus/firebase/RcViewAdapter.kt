package weather.fastcampus.firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RcViewAdapter(val context: Context, val itemList: ArrayList<item>) : RecyclerView.Adapter<RcViewAdapter.Holder>(){

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo = itemView.findViewById<ImageView>(R.id.photo)
        val country = itemView.findViewById<TextView>(R.id.country)
        val temp = itemView.findViewById<TextView>(R.id.temp)


        fun bind (item: item, context: Context) {
            /* dogPhoto의 setImageResource에 들어갈 이미지의 id를 파일명(String)으로 찾고,
            이미지가 없는 경우 안드로이드 기본 아이콘을 표시한다.*/
            if (item.photo != "") {
                val resourceId = context.resources.getIdentifier(item.photo, "drawable", context.packageName)
                photo?.setImageResource(resourceId)
            } else {
                photo?.setImageResource(R.mipmap.ic_launcher)
            }
            /* 나머지 TextView와 String 데이터를 연결한다. */
            country?.text = item.country
            temp?.text = item.temp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(itemList[position], context)
    }

}