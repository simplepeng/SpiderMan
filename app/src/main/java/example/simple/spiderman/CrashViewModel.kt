package example.simple.spiderman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simple.spiderman.SpiderMan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrashViewModel : ViewModel() {

    fun makeCrash() {
//        1 / 0
//        val text: String? = null;
//        text!!.toUpperCase();

        viewModelScope.launch(Dispatchers.IO) {
            1 / 0
//            val text: String? = null;
//            text!!.toUpperCase();
        }

//        thread {
//            1 / 0
//        }
    }

    fun makeCrashWithTryCatch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                1 / 0
            } catch (e: Exception) {
                SpiderMan.show(e)
            }
        }
    }
}