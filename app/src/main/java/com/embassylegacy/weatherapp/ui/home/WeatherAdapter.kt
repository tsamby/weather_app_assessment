package com.embassylegacy.weatherapp.ui.home


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.embassylegacy.weatherapp.R
import com.embassylegacy.weatherapp.model.ForecastWeatherResponse
import com.embassylegacy.weatherapp.utils.DateFormatter
import kotlin.math.roundToInt


class WeatherAdapter(val fiveDayWeatherList: ForecastWeatherResponse)
    : RecyclerView.Adapter<WeatherAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : WeatherAdapter.ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_view,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {

        val date: ArrayList<String> = DateFormatter.getDayOfWeek(
            fiveDayWeatherList.list?.get(0)?.dtTxt,
            position + 1
        )?.split(" ") as ArrayList<String>

        holder.day.text = date[1]

        val i: Int = getDatePosition(date[0] + " 12:00:00",fiveDayWeatherList)
        val temp = fiveDayWeatherList.list!![i].main!!.tempMax?.let { it.roundToInt().toInt() }
        holder.temp.text = (temp.toString() + "\u00B0")
        holder.description.text = fiveDayWeatherList.list!![i].weather!![0].description

//        val weatherIcon: Int = WeatherIconManager.getIcon(
//            fiveDayWeatherList.list[i].weather!![0].icon,
//            fiveDayWeatherList.list[i].weather!![0].description
//        )
//        Glide.with(context).load(weatherIcon).into(weatherIconView)





    }

    override fun getItemCount(): Int {
        return if (fiveDayWeatherList.list!!.isNotEmpty()) 5 else 0
    }


    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

        val day = itemView.findViewById<TextView>(R.id.textView_day)
        val temp = itemView.findViewById<TextView>(R.id.textView_temp)
        val description = itemView.findViewById<TextView>(R.id.txtDescription)
        val imageViewIcon = itemView.findViewById<ImageView>(R.id.imageViewIcon)


    }

    /**
     * OpenWeatherMap/Forecast5 returns a weather list with multiple entries for
     * each day at differing times.
     * The goal is to get a list with single entries for each day, meaning we target
     * a single time instance in this case "12:00:00"
     * This method finds the index or position of a list item with some date and
     * time = "12:00:00" hence returning unique entries
     */
    private fun getDatePosition(date: String, forecastWeatherList:ForecastWeatherResponse): Int {
        var index = 0
        for (i in forecastWeatherList.list?.indices!!) {
            if (date == forecastWeatherList.list[i].dtTxt) {
                index = i
                break
            }
        }
        return index
    }
}

