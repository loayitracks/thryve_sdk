package com.thryve.sample.backchannel

import android.content.Context
import android.widget.Spinner
import com.thryve.connector.sdk.CoreConnector
import com.thryve.sample.MainActivity
import com.thryve.sample.R
import java.io.IOException
import java.util.*
import kotlin.math.roundToLong

class BackChannelDialogDailyDynamic(activityContext: Context?) : BackChannelDialog(activityContext!!) {
    override val message: String
        get() = activityContext.getString(R.string.dialog_text_daily_dynamic)

    override val spinnerTypes: List<String>
        get() {
            val types: MutableList<String> = ArrayList()
            types.add("Steps (1000)")
            types.add("ActivityDuration (1100)")
            types.add("HeartRate (3000)")
            return types
        }

    override fun handleInput(spinner: Spinner?, inputString: String?) {
        var dailyDynamicValueType = 0
        val typeString = spinner!!.selectedItem as String
        if (typeString.startsWith("Steps")) {
            dailyDynamicValueType = 1000
        } else if (typeString.startsWith("ActivityDuration")) {
            dailyDynamicValueType = 1100
        } else if (typeString.startsWith("HeartRate")) {
            dailyDynamicValueType = 3000
        }
        val value = java.lang.Long.valueOf(inputString!!).toFloat().roundToLong()
        BackChannelAsyncTaskDailyDynamic(activityContext, dailyDynamicValueType, value).execute()
    }

    private class BackChannelAsyncTaskDailyDynamic internal constructor(context: Context?, dailyDynamicValueType: Int, value: Long) : BackChannelAsyncTask(context!!, dailyDynamicValueType, value) {
        @Throws(IOException::class)
        override fun callInterface(): Boolean {
            run { return CoreConnector(context.applicationContext, MainActivity.APP_ID, MainActivity.partnerUserId).uploadDailyDynamicValue(Date(), valueType, value) }
        }
    }
}