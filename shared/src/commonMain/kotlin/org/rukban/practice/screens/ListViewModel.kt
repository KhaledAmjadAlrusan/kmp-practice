package org.rukban.practice.screens

import org.rukban.practice.data.MuseumObject
import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.rickclephas.kmp.observableviewmodel.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import org.rukban.practice.domain.MuseumRepository


class ListViewModel(
    private val museumRepository: MuseumRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ListViewState>(ListViewState.Loading)
    val state: StateFlow<ListViewState> = _state

    fun dispatch(intent: ListIntent) {
        when (intent) {
            ListIntent.LoadMuseums -> loadMuseums()
        }
    }

    private fun loadMuseums() {
        viewModelScope.launch {
            _state.emit(ListViewState.Loading)

            try {
                val data = museumRepository.getMuseums()

                _state.emit(ListViewState.Content(data))
            } catch (e: Exception) {
                _state.emit(ListViewState.Error(e.message ?: "Unknown error"))
            }
        }
    }
}



sealed class ListIntent {
    data object LoadMuseums : ListIntent()
}

sealed class ListViewState{
    data object Loading : ListViewState()
    data class Error(val message: String) : ListViewState()
    data class Content(val museums: List<MuseumObject>) : ListViewState()
}
