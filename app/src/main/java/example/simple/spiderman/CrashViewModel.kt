package example.simple.spiderman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrashViewModel : ViewModel() {

    fun makeCrash() {
        viewModelScope.launch(Dispatchers.IO) {
            1 / 0
        }
    }
}