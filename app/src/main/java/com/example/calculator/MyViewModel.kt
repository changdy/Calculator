package com.example.calculator

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import java.util.*

/**
 * Created by Changdy on 2019/12/19.
 */
class MyViewModel constructor(application: Application, private val handle: SavedStateHandle) :
    AndroidViewModel(application) {
    companion object {
        private const val KEY_HIGH_SCORE = "KEY_HIGH_SCORE"
        private const val KEY_LEFT_NUMBER = "KEY_LEFT_NUMBER"
        private const val KEY_RIGHT_NUMBER = "KEY_RIGHT_NUMBER"
        private const val KEY_OPERATOR = "KEY_OPERATOR"
        private const val KEY_ANSWER = "KEY_ANSWER"
        private const val KEY_RESULT = "KEY_RESULT"
        private const val KEY_RESULT_SCORE = "KEY_RESULT_SCORE"
        private const val KEY_CURRENT_SCORE = "KEY_CURRENT_SCORE"
        private const val SAVE_SHP_DATA_NAME = "SCORE_KEY"
        private val RANDOM = Random()
        private const val MAX_VALUE = 20
    }

    init {
        if (!handle.contains(KEY_HIGH_SCORE)) {
            val shp = getApplication<Application>()
                .getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
            handle.set(KEY_HIGH_SCORE, shp.getInt(KEY_HIGH_SCORE, 0))
            handle.set(KEY_LEFT_NUMBER, 0)
            handle.set(KEY_RIGHT_NUMBER, 0)
            handle.set(KEY_OPERATOR, "+")
            handle.set(KEY_ANSWER, 0)
            handle.set(KEY_CURRENT_SCORE, 0)
        }
    }

    fun getLeftNumber() = handle.getLiveData<Int>(KEY_LEFT_NUMBER)

    fun getRightNumber() = handle.getLiveData<Int>(KEY_RIGHT_NUMBER)

    fun getOperator() = handle.getLiveData<String>(KEY_OPERATOR)

    fun getHighScore() = handle.getLiveData<Int>(KEY_HIGH_SCORE)

    fun getCurrentScore() = handle.getLiveData<Int>(KEY_CURRENT_SCORE)

    fun getAnswer() = handle.getLiveData<Int>(KEY_ANSWER)

    fun generator() {
        var x = RANDOM.nextInt(MAX_VALUE) + 1
        val y = RANDOM.nextInt(x) + 1
        if (RANDOM.nextBoolean()) {
            getOperator().value = "-"
            getAnswer().value = x - y
        } else {
            getOperator().value = "+"
            getAnswer().value = x
            x -= y
        }
        getLeftNumber().value = x
        getRightNumber().value = y
    }

    fun save() {
        getHighScore().value = getCurrentScore().value!!
        getApplication<Application>()
            .getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE)
            .edit()
            .putInt(KEY_HIGH_SCORE, getCurrentScore().value!!)
            .apply()
    }

    fun answerCorrect() {
        val currentValue = getCurrentScore().value!! + 1
        getCurrentScore().value = currentValue
        generator()
    }

    fun getResultMsg() = handle.getLiveData<String>(KEY_RESULT)

    fun getResultScoreMsg() = handle.getLiveData<String>(KEY_RESULT_SCORE)
}