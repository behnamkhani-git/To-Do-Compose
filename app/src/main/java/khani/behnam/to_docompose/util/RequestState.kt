package khani.behnam.to_docompose.util

// I think we use out, so that we can assign Idle, Loading,
// Success and Error to a type of RequestState
sealed class RequestState<out T>{
    object Idle: RequestState<Nothing>()
    object Loading: RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val error: Throwable): RequestState<Nothing>()
}
