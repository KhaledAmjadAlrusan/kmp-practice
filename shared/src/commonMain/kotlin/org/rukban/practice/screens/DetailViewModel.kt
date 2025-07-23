package org.rukban.practice.screens

import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.rickclephas.kmp.observableviewmodel.stateIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import org.rukban.practice.data.MuseumObject
import org.rukban.practice.domain.MuseumRepository

class DetailViewModel(
    private val museumRepository: MuseumRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DetailViewState>(DetailViewState.Loading)

    @NativeCoroutinesState
    val state: StateFlow<DetailViewState> = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DetailViewState.Loading)

    fun dispatch(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.LoadMuseum -> loadMuseum(intent.objectId)
        }
    }

    private fun loadMuseum(objectId: Int) {
        viewModelScope.launch {
            _state.emit(DetailViewState.Loading)
            try {
                val museumObject = museumRepository.getMuseum(objectId)
                _state.emit(DetailViewState.Content(museumObject))
            } catch (e: Exception) {
                _state.emit(DetailViewState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}

sealed class DetailIntent {
    data class LoadMuseum(val objectId: Int) : DetailIntent()
}

sealed class DetailViewState{
    data object Loading : DetailViewState()
    data class Error(val message: String) : DetailViewState()
    data class Content(val museumObject: MuseumObject?) : DetailViewState()
}
