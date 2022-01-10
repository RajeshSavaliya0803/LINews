package com.example.linews.utils

//sealed class UIState(data: Any? = null,msg: String? = null) {
//    object Loading : UIState(data = null, msg = null)
//    data class Success(val data: Any) : UIState(data = data,msg = null)
//    data class Failed(val msg: String) : UIState(data = null, msg = msg)
//}

sealed class UIState<T> (val data: T? = null, val msg: String? = null) {
    class Loading<T>(loading: T) : UIState<T>(loading)
    class Success<T>(data: T) : UIState<T>(data)
    class Failed<T>(msg: String?,data: T? = null):UIState<T>(data,msg)
}