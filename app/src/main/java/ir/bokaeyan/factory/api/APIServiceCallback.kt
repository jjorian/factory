package ir.bokaeyan.factory.api


interface APIServiceCallback
{
    fun <T> onStartCal(result: Result<T>)

}