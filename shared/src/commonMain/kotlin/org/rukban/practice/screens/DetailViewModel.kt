package org.rukban.practice.screens

import org.rukban.practice.data.MuseumObject
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import org.rukban.practice.domain.MuseumRepository

class DetailViewModel(
    private val museumRepository: MuseumRepository
) : ViewModel() {

    private val _state = MutableStateFlow<DetailViewState>(DetailViewState.Loading)
    val state: StateFlow<DetailViewState> = _state

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
