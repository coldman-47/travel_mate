package fr.ece.travel_mate

import android.content.ContentValues.TAG
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.DocumentSnapshot
import fr.ece.travel_mate.data.Hotel

import fr.ece.travel_mate.placeholder.PlaceholderContent.PlaceholderItem
import fr.ece.travel_mate.databinding.FragmentHotelItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyHotelItemRecyclerViewAdapter(
    private val values: MutableList<DocumentSnapshot>
) : RecyclerView.Adapter<MyHotelItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val hotel = Hotel()
        flow {
            Log.d(TAG, "boom")
            emit(hotel.getHotels())
        }.flowOn(Dispatchers.Default)
        return ViewHolder(
            FragmentHotelItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = values.get(position).get("nom").toString()
        holder.locView.text = values.get(position).get("adresse").toString()
        holder.priceView.text = values.get(position).get("tarif").toString()+"€/jour"
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentHotelItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: TextView = binding.hotelName
        val locView: TextView = binding.hotelLocation
        val distView: TextView = binding.hotelDistance
        val priceView: TextView = binding.hotelPrice
        val imgView: ImageView = binding.hotelImg

        /*
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
        */
    }

}