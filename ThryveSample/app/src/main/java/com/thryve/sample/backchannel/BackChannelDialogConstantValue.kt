package com.thryve.sample.backchannel

import android.content.Context
import android.widget.Spinner
import com.thryve.connector.sdk.CoreConnector
import com.thryve.sample.MainActivity
import com.thryve.sample.R
import java.util.*
import kotlin.math.roundToLong

class BackChannelDialogConstantValue(activityContext: Context?) : BackChannelDialog(activityContext!!) {
    override val message: String
        get() = activityContext.getString(R.string.dialog_text_constant_value)

    override val spinnerTypes: List<String>
        get() {
            val types: MutableList<String> = ArrayList()
            types.add("SmokingStatus (9050)")
            types.add("SmokingHistory (9051)")
            return types
        }

    override fun handleInput(spinner: Spinner?, inputString: String?) {
        var constantValueType = 0
        val typeString = spinner!!.selectedItem as String
        if (typeString.startsWith("SmokingStatus")) {
            constantValueType = 9050
        } else if (typeString.startsWith("SmokingHistory")) {
            constantValueType = 9051
        }
        val value = java.lang.Long.valueOf(inputString!!).toFloat().roundToLong()
        BackChannelAsyncTaskConstantValue(activityContext, constantValueType, value).execute()
    }

    private class BackChannelAsyncTaskConstantValue internal constructor(context: Context?, dailyDynamicValueType: Int, value: Long) : BackChannelAsyncTask(context!!, dailyDynamicValueType, value) {
        override fun callInterface(): Boolean {
            run { return CoreConnector(context.applicationContext, MainActivity.APP_ID, MainActivity.partnerUserId).uploadConstantValue(valueType, value) }
        }
    }
}