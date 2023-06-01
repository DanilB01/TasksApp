package ru.tsu.tasksapp.app.photo

object TaskValues {
    private var currentTaskId: Int? = null
    private var isForSingleTask: Boolean? = null

    val getCurrentTaskId: Int?
    get() = currentTaskId

    val getIsForSingleTask: Boolean?
        get() = isForSingleTask

    fun setValues(currentTaskId: Int, isForSingleTask: Boolean) {
        this.currentTaskId = currentTaskId
        this.isForSingleTask = isForSingleTask
    }

    fun removeValues() {
        currentTaskId = null
        isForSingleTask = null
    }
}