package com.andmar.todo.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import com.andmar.todo.AndroidAlarmScheduler
import com.andmar.todo.data.TodoRepository
import com.andmar.todo.data.AlarmItem 

class TodoReminderViewModel(
    savedStateHandle: SavedStateHandle,
    private val todoRepository: TodoRepository,
    private val schedular: AndroidAlarmScheduler
): ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[TodoDetailsDestination.itemIdArg])

    var todoReminderState by mutableStateOf(TodoReminderState())
        private set
        
    var todoItem by mutableStateOf(TodoUiState())
       
    fun updateTodoReminderState(todoReminderDate: TodoReminderDate) {
        todoReminderState = TodoReminderState(todoReminderDate = todoReminderDate)
    }

    init {
        getTime()
        
         viewModelScope.launch {
            todoItem = todoRepository.getIdTodo(itemId)
                .filterNotNull()
                .first()
                .toTodoUiState(true)
        }
    }

    fun setTimeReminder(): String {
        var dayOfMonth = LocalDateTime.now().dayOfMonth
        
        val dateReminder = if(todoReminderState.todoReminderDate.day.toInt() == dayOfMonth) {
            "Сегодня в " +
            "${todoReminderState.todoReminderDate.hour}:" +
            "${todoReminderState.todoReminderDate.minute}"
        } else if (todoReminderState.todoReminderDate.day.toInt() == ++dayOfMonth) {
            "Завтра в " +
            "${todoReminderState.todoReminderDate.hour}:" +
            "${todoReminderState.todoReminderDate.minute}"
        } else {
            "${todoReminderState.todoReminderDate.day} в " +
            "${todoReminderState.todoReminderDate.hour}:" +
            "${todoReminderState.todoReminderDate.minute}"
        }
         return dateReminder   
    }
    
    var alarmItem by mutableStateOf(AlarmState())
    
   suspend fun getAlarmItem() {
        alarmItem = todoRepository.getItemIdAlarm(itemId)
            .filterNotNull()
            .first()
            .toAlarmState()
    }
    
    fun updateAlarm(alarmDetails: AlarmDetails) {
        alarmItem = AlarmState(alarmDetails = alarmDetails)
    }
    
    val alarmListState: StateFlow<AlarmListState> = 
        todoRepository.getAllAlarm().map { AlarmListState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = AlarmListState()
            )
            
    fun setReminder() {
        val item = setAlarmItem()
            
         viewModelScope.launch {
            todoRepository.insertAlarm(item)
            getAlarmItem()
            alarmItem.alarmDetails.toAlarmItem().let(schedular::schedule)
        }   
    }
    
    fun setAlarmItem(): AlarmItem {
        val item = AlarmItem(
            time = LocalDateTime.now()
                .withYear(todoReminderState.todoReminderDate.year.toIntOrNull() ?: 0)
                .withMonth(monthNameToNumber(todoReminderState.todoReminderDate.month))
                .withDayOfMonth(todoReminderState.todoReminderDate.day.toIntOrNull() ?: 0)
                .withHour(todoReminderState.todoReminderDate.hour.toIntOrNull() ?: 0)
                .withMinute(todoReminderState.todoReminderDate.minute.toIntOrNull() ?: 0)
                .withSecond(0),
            isRepeat = alarmItem.alarmDetails.isRepeat,
            timeUnit = alarmItem.alarmDetails.timeUnit,
            interval = alarmItem.alarmDetails.interval,
            message = todoItem.todoDetails.todoTitle,
            todoItemId = itemId,
            timeReminderMessage = setTimeReminder()
        )
        return item
    }
    
    fun getTime() {
        val localDateTime = LocalDateTime.now()
        
        todoReminderState = TodoReminderState(todoReminderDate = TodoReminderDate(
            year = localDateTime.year.toString(),
            month = localDateTime.month.toString(),
            day = localDateTime.dayOfMonth.toString(),
            hour = localDateTime.hour.toString(),
            minute = localDateTime.minute.toString(),
        ))
    }
    
    fun monthNameToNumber(monthName: String): Int {
        val monthMap = mapOf(
            "JANUARY" to 1,
            "FEBRUARY" to 2,
            "MARCH" to 3,
            "APRIL" to 4,
            "MAY" to 5,
            "JUNE" to 6,
            "JULY" to 7,
            "AUGUST" to 8,
            "SEPTEMBER" to 9,
            "OCTOBER" to 10,
            "NOVEMBER" to 11,
            "DECEMBER" to 12
        )
        return monthMap[monthName.uppercase()] ?: 0
    }
    
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TodoReminderState(
    val todoReminderDate: TodoReminderDate = TodoReminderDate(),
    val isShowButton: Boolean = false
)

data class TodoReminderDate(
    val year: String = "",
    val month: String = "",
    val day: String = "",
    val hour: String = "",
    val minute: String = ""
)

data class AlarmListState(val alarmList: List<AlarmItem> = listOf())

data class AlarmState(
    val alarmDetails: AlarmDetails = AlarmDetails()
)

data class AlarmDetails(
    val id: Int = 0,
    val time: LocalDateTime = LocalDateTime.now(),
    val isRepeat: Boolean = false,
    val timeUnit: TimeUnit = TimeUnit.MINUTES,
    val interval: Long = 0,
    val message: String = "",
    val todoItemId: Int = 0,
    val timeReminderMessage: String = ""
)

fun AlarmDetails.toAlarmItem(): AlarmItem = AlarmItem(
    id = id,
    time = time,
    isRepeat = isRepeat,
    timeUnit = timeUnit,
    interval = interval,
    message = message,
    todoItemId = todoItemId,
    timeReminderMessage = timeReminderMessage
)

fun AlarmItem.toAlarmDetails(): AlarmDetails = AlarmDetails(
    id = id,
    time = time,
    isRepeat = isRepeat,
    timeUnit = timeUnit,
    interval = interval,
    message = message,
    todoItemId = todoItemId,
    timeReminderMessage = timeReminderMessage
)

fun AlarmItem.toAlarmState(): AlarmState = AlarmState(
    alarmDetails = this.toAlarmDetails()
)