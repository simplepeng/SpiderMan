package example.simple.spiderman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class CrashViewModel : ViewModel() {

    fun makeCrash() {
//        1 / 0

        viewModelScope.launch(Dispatchers.IO) {
            1 / 0
        }

//        thread {
//            1 / 0
//        }
    }
}