package fr.ece.travel_mate

import android.content.ContentValues.TAG
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import fr.ece.travel_mate.data.Hotel
import java.net.URL

/**
 * A fragment representing a list of Items.
 */
class HotelItemFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotels_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val hotelObj = Hotel().getHotels().addOnSuccessListener { result ->
                    run {
                        /*
                        for (doc in result.documents) {

                            var imgUrl =
                                URL(((doc.data?.get("images") as ArrayList<*>)[0].toString()))
                            Log.d(
                                TAG,
                                "boom ${
                                    BitmapFactory.decodeStream(
                                        imgUrl.openConnection().getInputStream()
                                    )
                                }"
                            )
                        }
                        */
                        adapter = MyHotelItemRecyclerViewAdapter(result.documents)
                    }
                }
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HotelItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}