package fr.ece.travel_mate

import HotelDetailsViewModel
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.activityViewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.DocumentSnapshot

import fr.ece.travel_mate.placeholder.PlaceholderContent.PlaceholderItem
import fr.ece.travel_mate.databinding.FragmentHotelImageBinding
import java.net.URL

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyHotelImageRecyclerViewAdapter(
    private val values: List<String>,
    private val model: HotelDetailsViewModel
) : RecyclerView.Adapter<MyHotelImageRecyclerViewAdapter.ViewHolder>() {
    private val imgsSrc: ArrayList<Bitmap> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentHotelImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.imgView.setOnClickListener {
            model.selectCarouselItem(imgsSrc[position])
            Log.d(TAG, "boom")
        }
        LoadImages(holder.imgView, imgsSrc).execute(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHotelImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgView = binding.imgCarouselItem
    }

    class LoadImages(private var imageView: ImageView, private var values: ArrayList<Bitmap>) :
        AsyncTask<String?, Void?, Bitmap?>() {

        override fun onPostExecute(result: Bitmap?) {
            imageView.setImageBitmap(result)
        }

        override fun doInBackground(vararg url: String?): Bitmap? {
            var bimage: Bitmap? = null
            try {
                Log.d(TAG, "GANG : " + url[0])
                val imgUrl = URL(url[0]).openStream()
                bimage = BitmapFactory.decodeStream(imgUrl)
                values.add(bimage)

            } catch (e: Exception) {
                Log.e("Error Message", e.message!!)
                e.printStackTrace()
            }
            return bimage
        }
    }

}