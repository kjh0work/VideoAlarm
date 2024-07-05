package com.example.videoalarm

import java.time.LocalDateTime

data class Alarm(
    val name: String,
    val time: LocalDateTime,
    val isActive : Boolean,
) {
    val dateList: MutableList<Int> = mutableListOf<Int>(0,0,0,0,0,0,0)

    fun setDateList(list : MutableList<Int>){
        for( (i,v) in list.withIndex()){
            dateList.set(i, v)
        }
    }

    fun dateListToString () : String {
        var sum : Int = 0
        val dateString : List<String> = listOf("월","화","수","목","금","토","일")
        var result : String = ""

        for( (i, v) in dateList.withIndex()){
            if(v == 1){
                result += dateString[i] + " "
                sum++
            }
        }

        if(sum == 0){
            return if(time.isAfter(LocalDateTime.now())){
                "오늘"
            } else "내일"
        }
        else if(sum == 7) return "매일"

        return result
    }
}
