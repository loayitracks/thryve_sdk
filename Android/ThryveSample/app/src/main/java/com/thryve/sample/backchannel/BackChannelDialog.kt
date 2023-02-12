package com.thryve.sample.backchannel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.text.InputType
import android.widget.*
import com.thryve.connector.sdk.logger.Logger
import com.thryve.connector.sdk.logger.TAG
import com.thryve.sample.R
import java.io.IOException
import java.lang.ref.WeakReference

abstract class BackChannelDialog(var activityContext: Context) {
    fun show() {
        val builder = AlertDialog.Builder(activityContext)
        builder.setTitle(activityContext.getString(R.string.dialog_title))
        builder.setMessage(message)
        val container = LinearLayout(activityContext)
        container.orientation = LinearLayout.VERTICAL
        container.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        container.setPadding(60, 30, 60, 30)
        val spinner = Spinner(activityContext)
        val dataAdapter = ArrayAdapter(activityContext, android.R.layout.simple_spinner_item, spinnerTypes!!)
        spinner.adapter = dataAdapter
        val input = EditText(activityContext)
        input.setSingleLine()
        input.hint = activityContext.getString(R.string.dialog_value_hint)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 30, 0, 0)
        input.layoutParams = params
        input.inputType = InputType.TYPE_CLASS_NUMBER
        container.addView(spinner)
        container.addView(input)
        builder.setView(container)
        builder.setPositiveButton(activityContext.getString(R.string.dialog_upload)) { _, _: Int -> onPositiveButtonClick(spinner, input) }
        builder.setNegativeButton(activityContext.getString(R.string.dialog_cancel), null)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    private fun onPositiveButtonClick(spinner: Spinner, input: EditText) {
        val inputString = input.text.toString()
        if (inputString.isEmpty() || inputString.length > 6) {
            Toast.makeText(activityContext, activityContext.getString(R.string.dialog_invalid_text), Toast.LENGTH_SHORT).show()
            return
        }
        handleInput(spinner, inputString)
    }

    abstract val message: String?
    abstract val spinnerTypes: List<String>?
    abstract fun handleInput(spinner: Spinner?, inputString: String?)
    internal abstract class BackChannelAsyncTask(val context: Context,var valueType: Int,var value: Long) : AsyncTask<Void?, Void?, Boolean>() {

        override fun doInBackground(vararg voids: Void?): Boolean {
            return try {
                callInterface()
            } catch (e: IOException) {
                false
            }
            return false
        }

        override fun onPostExecute(isSuccessful: Boolean) {
            if (isSuccessful) {
                Toast.makeText(context, context.getString(R.string.dialog_upload_success), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, context.getString(R.string.dialog_upload_error), Toast.LENGTH_SHORT).show()
            }
        }

        @Throws(IOException::class)
        abstract fun callInterface(): Boolean
    }
}