package fr.ece.travel_mate

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import com.google.firebase.annotations.concurrent.Background
import com.google.firebase.firestore.DocumentSnapshot
import fr.ece.travel_mate.data.Hotel


import fr.ece.travel_mate.placeholder.PlaceholderContent.PlaceholderItem
import fr.ece.travel_mate.databinding.FragmentHotelItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.URL
import kotlin.math.log

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyHotelItemRecyclerViewAdapter(
    private val values: MutableList<DocumentSnapshot>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<MyHotelItemRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentHotelItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = values[position].get("nom").toString()
        holder.locView.text = values[position].get("adresse").toString()
        holder.priceView.text = values[position].get("tarif").toString() + "€/jour"
        holder.itemView.setOnClickListener {
            HotelDetailsFragment(values[position]).show(fragmentManager, "boom")
        }
        LoadImages(holder.imgView).execute((values[position].get("images") as ArrayList<*>)[0].toString())
    }

    @Background
    fun getImg(imgString: String): Bitmap? {
        var imgUrl = URL(imgString)
        return BitmapFactory.decodeStream(
            imgUrl.openConnection().getInputStream()
        )

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHotelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.hotelName
        val locView: TextView = binding.hotelLocation
        val distView: TextView = binding.hotelDistance
        val priceView: TextView = binding.hotelPrice
        val imgView: ImageView = binding.hotelImg
        val itemView: CardView = binding.hotelItemCard
        /*
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
        */
    }

    class LoadImages(private var imageView: ImageView) :
        AsyncTask<String?, Void?, Bitmap?>() {

        override fun onPostExecute(result: Bitmap?) {

            imageView.setImageBitmap(result)
        }

        override fun doInBackground(vararg url: String?): Bitmap? {
            var bimage: Bitmap? = null
            try {
                val imgUrl = URL(url[0]).openStream()
                bimage = BitmapFactory.decodeStream(imgUrl)
            } catch (e: Exception) {
                Log.e("Error Message", e.message!!)
                e.printStackTrace()
            }
            return bimage
        }
    }

}