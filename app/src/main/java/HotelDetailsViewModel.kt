import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.auth.User


class HotelDetailsViewModel : ViewModel() {
    val selected = MutableLiveData<List<String>>()
    val selectedCarouselItem = MutableLiveData<Bitmap>()

    fun select(item: List<String>) {
        selected.value = item
    }

    fun selectCarouselItem(item: Bitmap) {
        selectedCarouselItem.value = item
    }



}