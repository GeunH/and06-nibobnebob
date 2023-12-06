package com.avengers.nibobnebob.presentation.ui.main.mypage.wishlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avengers.nibobnebob.domain.model.base.BaseState
import com.avengers.nibobnebob.domain.usecase.restaurant.DeleteMyWishRestaurantUseCase
import com.avengers.nibobnebob.domain.usecase.restaurant.GetMyWishListUseCase
import com.avengers.nibobnebob.presentation.ui.main.mypage.mapper.toMyWishListData
import com.avengers.nibobnebob.presentation.ui.main.mypage.model.UiMyWishData
import com.avengers.nibobnebob.presentation.util.Constants.ERROR_MSG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyWishUiState(
    val wishList: List<UiMyWishData> = emptyList(),
    val filterOption: String = "최신순",
    val isEmpty: Boolean = false,
)

sealed class MyWishEvent {
    data class NavigateToRestaurantDetail(val id: Int) : MyWishEvent()
    data class NavigateToRestaurantAdd(val item: UiMyWishData) : MyWishEvent()
    data class ShowSnackMessage(val msg: String) : MyWishEvent()
    data class ShowToastMessage(val msg: String) : MyWishEvent()
}

@HiltViewModel
class MyWishListViewModel @Inject constructor(
    private val getMyWishListUseCase: GetMyWishListUseCase,
    private val deleteMyWishRestaurantUseCase: DeleteMyWishRestaurantUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyWishUiState())
    val uiState: StateFlow<MyWishUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MyWishEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<MyWishEvent> = _events.asSharedFlow()


    fun myWishList(
        limit: Int? = null,
        page: Int? = null,
        sort: String? = null,
    ) {
        getMyWishListUseCase(sort = sort).onEach { wish ->
            when (wish) {
                is BaseState.Success -> {
                    wish.data.wishRestaurantItemsData?.let {
                        _uiState.update { state ->
                            val wishList = it.map { restaurant ->
                                restaurant.toMyWishListData()
                            }
                            state.copy(
                                wishList = wishList,
                                filterOption = if (sort == "TIME_ASC") "오래된순" else "최신순",
                                isEmpty = wishList.isEmpty()
                            )
                        }
                    }
                }

                else -> _events.emit(MyWishEvent.ShowSnackMessage(ERROR_MSG))
            }
        }.launchIn(viewModelScope)
    }


    fun showDetail(id: Int) {
        viewModelScope.launch { _events.emit(MyWishEvent.NavigateToRestaurantDetail(id)) }
    }

    fun addMyList(item: UiMyWishData) {
        viewModelScope.launch {
            _events.emit(
                if (item.isMy) MyWishEvent.ShowToastMessage("이미 나의 맛집 리스트에 추가된 맛집입니다.")
                else MyWishEvent.NavigateToRestaurantAdd(item)
            )
        }
    }

    fun deleteWishList(id: Int) {
        deleteMyWishRestaurantUseCase(id).onEach {
            when (it) {
                is BaseState.Success -> {
                    _events.emit(MyWishEvent.ShowToastMessage("삭제 되었습니다."))
                    myWishList()
                }

                is BaseState.Error -> {
                    _events.emit(MyWishEvent.ShowSnackMessage(ERROR_MSG))
                }
            }
        }.launchIn(viewModelScope)
    }

//    private fun updateList(deleteId : Int){
//        _uiState.update { state ->
//            state.copy(
//                myList = state.myList.filter { it.id != deleteId }.map { it }
//            )
//        }
//    }

}