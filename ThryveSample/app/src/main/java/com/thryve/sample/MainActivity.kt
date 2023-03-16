package com.thryve.sample

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.thryve.connector.module_gfit.GFitConnector
import com.thryve.connector.module_gfit.data.GFitDataType
import com.thryve.connector.recognition.RecognitionConnector
import com.thryve.connector.sdk.CoreConnector
import com.thryve.connector.sdk.auth.KeychainAssistant
import com.thryve.connector.sdk.extension.showMessage
import com.thryve.connector.sdk.logger.Logger
import com.thryve.connector.sdk.logger.TAG
import com.thryve.connector.sdk.model.user.Gender
import com.thryve.connector.sdk.model.user.User
import com.thryve.connector.sdk.model.user.UserInformation
import com.thryve.connector.sdk.rx.doInBackground
import com.thryve.connector.shealth.SHealthConnector
import com.thryve.connector.shealth.model.SHealthDataType
import com.thryve.sample.backchannel.BackChannelDialogConstantValue
import com.thryve.sample.backchannel.BackChannelDialogDailyDynamic
import com.thryve.sample.bgm.BGMActivity
import com.thryve.sample.cardio.CardioActivity
import com.thryve.sample.webview.WebViewActivity
import com.thryve.sample.webview.WebViewActivityDirectConnection
import com.thryve.sample.webview.WebViewActivityDirectRevoke
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.*

class MainActivity : AppCompatActivity() {
    private var textViewAccessToken: TextView? = null
    private var progressDialog: ProgressDialog? = null
    private var selectedDataSource = 1
    private var connector: CoreConnector? = null
    private var rConnector: RecognitionConnector? = null
    private var sConnector: SHealthConnector? = null
    private var gFitConnector: GFitConnector? = null
    private var accessToken: String? = null

    private val gFitDataTypes = listOf(
        GFitDataType.TYPE_STEP_COUNT_DELTA,
        GFitDataType.AGGREGATE_STEP_COUNT_DELTA,
        GFitDataType.TYPE_HEART_RATE_BPM,
        GFitDataType.TYPE_SLEEP_SEGMENT
    )

    private val sHealthDataTypes = listOf(
        SHealthDataType.STEP_COUNT,
        SHealthDataType.STEP_DAILY_TREND,
        SHealthDataType.HEART_RATE,
        SHealthDataType.SLEEP,
        SHealthDataType.SLEEP_STAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        connector = CoreConnector(applicationContext, APP_ID, APP_SECRET, partnerUserId, language)
        rConnector = RecognitionConnector(this)
        sConnector = SHealthConnector(this, sHealthDataTypes)
        gFitConnector = GFitConnector(this, gFitDataTypes)
        vSampleSubtitle.text = getString(R.string.logo_subtitle)
        val isSHealthOn = sConnector?.isActive(SHealthConnector.Service.SAMSUNG) ?: false
        if (isSHealthOn) {
            switch_shealth_integration?.isChecked = true
        }
        val isGFitOn = gFitConnector?.isActive(GFitConnector.Service.GOOGLE_FIT) ?: false
        if (isGFitOn) {
            switch_gfit_integration?.isChecked = true
        }
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        textViewAccessToken = findViewById(R.id.text_access_token)
        val editTextPartnerUserId = findViewById<EditText>(R.id.edit_text_partner_user_id)
        editTextPartnerUserId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                partnerUserId = editable.toString()
                connector = CoreConnector(applicationContext, APP_ID, APP_SECRET, partnerUserId, language)
            }

            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}
        })
        initializeProgressDialog()
        initializeSpinner()
        initSamsungIntegration()
        initGFitIntegration()
        initializeWellbeingIndicatorsTracking()
    }

    private fun setupUI() {
        button_cardio.setOnClickListener {
            onClickCardio()
        }
        button_bgm.setOnClickListener {
            onClickBGM()
        }
        switch_gfit_integration.setOnClickListener {
            onClickGFitIntegration()
        }
        switch_shealth_integration.setOnClickListener {
            onClickSHealthIntegration()
        }
        switch_wellbeing_tracking.setOnClickListener {
            onClickWellbeingIndicatorsTracking()
        }
        switch_location_tracking_balanced.setOnClickListener {
            onClickLocationTrackingBalancedPowerAccuracy()
        }
        switch_location_tracking_high.setOnClickListener {
            onClickLocationTrackingHighAccuracy()
        }
        switch_activity_recognition.setOnClickListener {
            onClickActivityRecognition()
        }
        switch_step_detection.setOnClickListener {
            onClickStepDetection()
        }
        button_user_information.setOnClickListener {
            onClickGetUserInformation()
        }
        button_backchannel_userinfo.setOnClickListener {
            onClickBackChannelUserInformation()
        }
        button_backchannel_constant.setOnClickListener {
            onClickBackChannelConstantValue()
        }
        button_backchannel_daily.setOnClickListener {
            onClickBackChannelDailyDynamic()
        }
        button_direct_revoke.setOnClickListener {
            onClickDirectRevoke()
        }
        button_direct_connection.setOnClickListener {
            onClickDirectConnection()
        }
        button_url.setOnClickListener {
            onClickDataSourceURL()
        }
        button_access_token.setOnClickListener {
            onClickAccessToken()
        }
        gfitDailySync.setOnClickListener {
            val startDateDialog = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
                val startDate = Calendar.getInstance()
                startDate.set(year, monthOfYear, dayOfMonth)
                val syncTime = startDate.time
                view.dismiss()
                gFitConnector?.synchroniseDaily(syncTime, Date(), gFitDataTypes) {
                    runOnUiThread {
                        showMessage("GoogleFitModule synchronise daily result: ${it.data}")
                    }
                }
            }
            startDateDialog.maxDate = Calendar.getInstance()
            startDateDialog.show(supportFragmentManager, "Start date")
            showMessage("Pick a start Date")
        }
        gfitEpochSync.setOnClickListener {
            val startDateDialog = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
                val startDate = Calendar.getInstance()
                startDate.set(year, monthOfYear, dayOfMonth)
                val syncTime = startDate.time
                view.dismiss()
                gFitConnector?.synchroniseEpoch(syncTime, Date(), gFitDataTypes) {
                    runOnUiThread {
                        showMessage("GoogleFitModule synchronise epoch result: ${it.data}")
                    }
                }
            }
            startDateDialog.maxDate = Calendar.getInstance()
            startDateDialog.show(supportFragmentManager, "Start date")
            showMessage("Pick a start Date")
        }
        shealthSyncEpoch.setOnClickListener {
            val startDateDialog = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
                val startDate = Calendar.getInstance()
                startDate.set(year, monthOfYear, dayOfMonth)
                val syncTime = startDate.time
                view.dismiss()
                sConnector?.synchroniseEpoch(syncTime, Date(), sHealthDataTypes) {
                    runOnUiThread {
                        showMessage("SHealthModule synchronise epoch result: ${it.data}")
                    }
                }
            }
            startDateDialog.maxDate = Calendar.getInstance()
            startDateDialog.show(supportFragmentManager, "Start date")
            showMessage("Pick a start Date")
        }
        sHealthSyncDaily.setOnClickListener {
            val startDateDialog = DatePickerDialog.newInstance { view, year, monthOfYear, dayOfMonth ->
                val startDate = Calendar.getInstance()
                startDate.set(year, monthOfYear, dayOfMonth)
                val syncTime = startDate.time
                view.dismiss()
                sConnector?.synchroniseDaily(syncTime, Date(), sHealthDataTypes) {
                    runOnUiThread {
                        showMessage("SHealthModule synchronise daily result: ${it.data}")
                    }
                }
            }
            startDateDialog.maxDate = Calendar.getInstance()
            startDateDialog.show(supportFragmentManager, "Start date")
            showMessage("Pick a start Date")
        }
        gfitPermissions.setOnClickListener {
            gFitConnector?.getAcquiredPermissions { permissions ->
                showMessage("GFit permissions are acquired. printing.\n${permissions.data?.joinToString(",\n")}")
            }
            gFitConnector?.arePermissionsAcquired { onResponse ->
                if (onResponse.data == true) {
                    gFitConnector?.getAcquiredPermissions { permissions ->
                        showMessage("GFit permissions are acquired. printing.\n${permissions.data?.joinToString(",\n")}")
                    }
                } else {
                    showMessage("No GFit permissions are acquired.")
                }
            }
        }
        shealthPermissions.setOnClickListener {
            if (sConnector?.arePermissionsAcquired() == true) {
                showMessage("SHealth permissions are acquired. printing.\n${sConnector?.getAcquiredPermissions()?.joinToString(",\n")}")
            } else {
                showMessage("No SHealth permissions are acquired.")
            }
        }
        vSampleSubtitle.text = getString(R.string.logo_subtitle).plus("\nVersion ${BuildConfig.VERSION_NAME} ${BuildConfig.BUILD_TYPE}")
    }

    private fun initSamsungIntegration() {
        if (sConnector?.isSHealthAvailable == true) {
            switch_shealth_integration?.visibility = View.VISIBLE
            val isSHealthOn = sConnector?.isActive(SHealthConnector.Service.SAMSUNG) ?: false
            switch_shealth_integration?.isChecked = isSHealthOn
            shealthSyncEpoch?.visibility = if (isSHealthOn) View.VISIBLE else View.GONE
            sHealthSyncDaily?.visibility = if (isSHealthOn) View.VISIBLE else View.GONE
            shealthPermissions?.visibility = if (isSHealthOn) View.VISIBLE else View.GONE
        }
    }

    private fun initGFitIntegration() {
        if (gFitConnector?.isGFitAvailable == true) {
            switch_gfit_integration?.visibility = View.VISIBLE
            val isGFitOn = gFitConnector?.isActive(GFitConnector.Service.GOOGLE_FIT) ?: false
            switch_gfit_integration?.isChecked = isGFitOn
            gfitDailySync?.visibility = if (isGFitOn) View.VISIBLE else View.GONE
            gfitEpochSync?.visibility = if (isGFitOn) View.VISIBLE else View.GONE
            gfitPermissions?.visibility = if (isGFitOn) View.VISIBLE else View.GONE
        }
    }

    private fun initializeWellbeingIndicatorsTracking() {
        switch_wellbeing_tracking!!.isChecked = rConnector!!.isActive(RecognitionConnector.Service.WELLBEING)
        if (switch_wellbeing_tracking!!.isChecked) {
            handleWellbeingIndicatorsTracking(false)
        }
    }

    private fun initializeProgressDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Please wait...")
        progressDialog?.isIndeterminate = true
        progressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    public override fun onResume() {
        super.onResume()
        try {
            initializeWellbeingIndicatorsTracking()
            if (rConnector!!.isStepDetectionSupported) {
                switch_step_detection.isChecked = rConnector!!.isActive(RecognitionConnector.Service.STEPS)
                if (rConnector!!.isActive(RecognitionConnector.Service.STEPS)) {
                    rConnector!!.startStepDetection()
                }
            } else {
                switch_step_detection.visibility = View.GONE
            }
            switch_activity_recognition.isChecked = rConnector!!.isActive(RecognitionConnector.Service.ACTIVITY)
            if (rConnector!!.isActive(RecognitionConnector.Service.ACTIVITY)) {
                rConnector!!.startActivityRecognition()
            }
            if (rConnector!!.isActive(RecognitionConnector.Service.LOCATION)) {
                if (rConnector!!.isLocationBestAccuracy) {
                    switch_location_tracking_high.isChecked = rConnector!!.isLocationBestAccuracy
                    rConnector!!.startLocationTracking(true)
                    switch_location_tracking_balanced.isEnabled = false
                } else {
                    switch_location_tracking_balanced.isChecked = !rConnector!!.isLocationBestAccuracy
                    rConnector!!.startLocationTracking(false)
                    switch_location_tracking_high.isEnabled = false
                }
            }
            doInBackground({
                return@doInBackground try {
                    KeychainAssistant(this).getAccessToken()
                } catch (e: Exception) {
                    Logger.e(this::class.java.simpleName, e)
                    null
                }
            }, {
                it?.let {
                    textViewAccessToken?.text = ("AccessToken: $it\n(retrieved: ${Date()})").trimIndent()
                }
            })
        } catch (e: IOException) {
            Logger.e(this::class.java.simpleName, e)
        }
    }

    private fun onClickBackChannelUserInformation() {
        UpdateUserInfoAsyncTask(this, connector).execute()
    }

    private fun onClickAccessToken() {
        generateAccessToken()
    }

    private fun onClickDataSourceURL() {
        val intent = Intent(this, WebViewActivity::class.java)
        this.startActivity(intent)
    }

    private fun initializeSpinner() {
        val spinner = findViewById<Spinner>(R.id.spinner_direct_access)
        val sources: MutableList<String> = ArrayList()
        sources.add("Fitbit (1)")
        sources.add("Garmin (2)")
        sources.add("Polar (3)")
        sources.add("Misfit (7)")
        sources.add("Nokia (8)")
        sources.add("GoogleFit (12)")
        sources.add("Omron (16)")
        sources.add("Suunto (17)")
        sources.add("Oura (18)")
        sources.add("iHealth (21)")
        sources.add("SleepAsAndroid (24)")
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sources)
        spinner.adapter = dataAdapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val source = parent.getItemAtPosition(position).toString()
                when {
                    source.startsWith("Fitbit") -> {
                        selectedDataSource = 1
                    }
                    source.startsWith("Garmin") -> {
                        selectedDataSource = 2
                    }
                    source.startsWith("Polar") -> {
                        selectedDataSource = 3
                    }
                    source.startsWith("Misfit") -> {
                        selectedDataSource = 7
                    }
                    source.startsWith("Nokia") -> {
                        selectedDataSource = 8
                    }
                    source.startsWith("GoogleFit") -> {
                        selectedDataSource = 12
                    }
                    source.startsWith("Omron") -> {
                        selectedDataSource = 16
                    }
                    source.startsWith("Suunto") -> {
                        selectedDataSource = 17
                    }
                    source.startsWith("Oura") -> {
                        selectedDataSource = 18
                    }
                    source.startsWith("iHealth") -> {
                        selectedDataSource = 21
                    }
                    source.startsWith("SleepAsAndroid") -> {
                        selectedDataSource = 24
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun onClickDirectConnection() {
        startActivity(Intent(this, WebViewActivityDirectConnection::class.java).putExtra("DataSource", selectedDataSource))
    }

    private fun onClickDirectRevoke() {
        startActivity(Intent(this, WebViewActivityDirectRevoke::class.java).putExtra("DataSource", selectedDataSource))
    }

    private fun onClickStepDetection() {
        ToggleStepDetectionAsyncTask(rConnector).execute()
    }

    private fun onClickActivityRecognition() {
        ToggleActivityRecognitionAsyncTask(rConnector).execute()
    }

    private fun onClickLocationTrackingHighAccuracy() {
        ToggleLocationTrackingAsyncTask(rConnector, true).execute()
        val locationSwitch = findViewById<View>(R.id.switch_location_tracking_balanced)
        locationSwitch.isEnabled = !locationSwitch.isEnabled
    }

    private fun onClickLocationTrackingBalancedPowerAccuracy() {
        ToggleLocationTrackingAsyncTask(rConnector, false).execute()
        val locationSwitch = findViewById<View>(R.id.switch_location_tracking_high)
        locationSwitch.isEnabled = !locationSwitch.isEnabled
    }

    private fun onClickBackChannelDailyDynamic() {
        BackChannelDialogDailyDynamic(this@MainActivity).show()
    }

    private fun onClickBackChannelConstantValue() {
        BackChannelDialogConstantValue(this@MainActivity).show()
    }

    private fun handleWellbeingIndicatorsTracking(isOn: Boolean) {
        if (!isOn) {
            rConnector!!.startWellbeingIndicatorsDetection { isConnected ->
                if (!isConnected && switch_wellbeing_tracking!!.isChecked) {
                    switch_wellbeing_tracking!!.isChecked = false
                }
            }
        } else {
            rConnector!!.stopWellbeingIndicatorsDetection()
        }
    }

    private fun handleSHealthIntegration(isSHealthOn: Boolean) {
        if (!isSHealthOn) {
            val valueTypes: MutableList<SHealthDataType> = ArrayList()
            valueTypes.add(SHealthDataType.STEP_COUNT)
            valueTypes.add(SHealthDataType.HEART_RATE)
            valueTypes.add(SHealthDataType.SLEEP)
            valueTypes.add(SHealthDataType.SLEEP_STAGE)
            sConnector?.authorizeSHealthIntegration(valueTypes, { granted ->
                Logger.i(this::class.java.simpleName, "Permissions are granted: $granted")
                if (granted) {
                    sConnector?.startSHealthIntegration(valueTypes)
                    shealthSyncEpoch?.visibility = View.VISIBLE
                    sHealthSyncDaily?.visibility = View.VISIBLE
                    shealthPermissions?.visibility = View.VISIBLE
                } else {
                    sConnector!!.stopSHealthIntegration()
                    switch_shealth_integration.isChecked = false
                    shealthSyncEpoch?.visibility = View.GONE
                    sHealthSyncDaily?.visibility = View.GONE
                    shealthPermissions?.visibility = View.GONE
                }
            }, { e ->
                Logger.e(this::class.java.simpleName, e)
                sConnector!!.stopSHealthIntegration()
                switch_shealth_integration.isChecked = false
                shealthSyncEpoch?.visibility = View.GONE
                sHealthSyncDaily?.visibility = View.GONE
                shealthPermissions?.visibility = View.GONE
            })
        } else {
            switch_shealth_integration.isChecked = false
            sConnector!!.stopSHealthIntegration()
            shealthSyncEpoch?.visibility =View.GONE
            sHealthSyncDaily?.visibility = View.GONE
            shealthPermissions?.visibility = View.GONE
        }
    }

    private fun handleGfitIntegration(isGFitOn: Boolean) {
        if (!isGFitOn) {
            gFitConnector!!.authorizeGFitIntegration(gFitDataTypes, { authorised ->
                if (!authorised) {
                    switch_gfit_integration.isChecked = false
                    showMessage("GFit integration Unauthorized")
                    gfitDailySync?.visibility = View.GONE
                    gfitEpochSync?.visibility = View.GONE
                    gfitPermissions?.visibility = View.GONE
                } else {
                    showMessage("GFit integration Authorized")
                    gfitDailySync?.visibility = View.VISIBLE
                    gfitEpochSync?.visibility = View.VISIBLE
                    gfitPermissions?.visibility = View.VISIBLE
                }
            }, {
                switch_gfit_integration.isChecked = false
                showMessage("GFit integration Unauthorized")
                gfitDailySync?.visibility = View.GONE
                gfitEpochSync?.visibility = View.GONE
                gfitPermissions?.visibility = View.GONE
            })
        } else {
            gFitConnector?.stopGFitIntegration {
                showMessage("GoogleFitModule stop result: ${it.data}")
            }
            gfitDailySync?.visibility = View.GONE
            gfitEpochSync?.visibility = View.GONE
            gfitPermissions?.visibility = View.GONE
        }
    }

    private fun onClickWellbeingIndicatorsTracking() {
        val isOn = rConnector!!.isActive(RecognitionConnector.Service.WELLBEING)
        handleWellbeingIndicatorsTracking(isOn)
    }

    private fun onClickSHealthIntegration() {
        val isOn = sConnector!!.isActive(SHealthConnector.Service.SAMSUNG)
        handleSHealthIntegration(isOn)
    }

    private fun onClickGFitIntegration() {
        val isOn = gFitConnector!!.isActive(GFitConnector.Service.GOOGLE_FIT)
        handleGfitIntegration(isOn)
    }

    private fun onClickBGM() {
        val intent = Intent(this, BGMActivity::class.java)
        this.startActivity(intent)
    }

    private fun onClickCardio() {
        val intent = Intent(this, CardioActivity::class.java)
        this.startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        gFitConnector!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        gFitConnector!!.onActivityResult(requestCode, resultCode, data)
    }

    private class UpdateUserInfoAsyncTask constructor(activity: MainActivity, private val connector: CoreConnector?) : AsyncTask<Void?, Void?, Boolean>() {
        private val mainActivityReference: WeakReference<MainActivity> = WeakReference(activity)
        override fun onPreExecute() {
            if (!mainActivityReference.get()!!.progressDialog!!.isShowing) {
                mainActivityReference.get()!!.progressDialog!!.show()
            }
        }

        override fun doInBackground(vararg voids: Void?): Boolean {
            val user = User(173, 74.2, "1990-01-18", Gender.MALE.string)
            return connector!!.uploadUserInformation(user)
        }

        override fun onPostExecute(aBoolean: Boolean) {
            if (mainActivityReference.get()!!.progressDialog!!.isShowing) {
                mainActivityReference.get()!!.progressDialog!!.dismiss()
            }
        }
    }

    private fun disconnectSources() {
        if (switch_step_detection.isChecked) {
            switch_step_detection.isChecked = false
            onClickStepDetection()
        }
        if (switch_activity_recognition.isChecked) {
            switch_activity_recognition.isChecked = false
            onClickActivityRecognition()
        }
        if (switch_location_tracking_high.isChecked) {
            switch_location_tracking_high.isChecked = false
            onClickLocationTrackingHighAccuracy()
        }
        if (switch_location_tracking_balanced.isChecked) {
            switch_location_tracking_balanced.isChecked = false
            onClickLocationTrackingBalancedPowerAccuracy()
        }
        if (switch_wellbeing_tracking.isChecked) {
            switch_wellbeing_tracking.isChecked = false
            onClickWellbeingIndicatorsTracking()
        }
        if (switch_shealth_integration.isChecked) {
            switch_shealth_integration.isChecked = false
            onClickSHealthIntegration()
        }
        if (switch_gfit_integration.isChecked) {
            switch_gfit_integration.isChecked = false
            onClickGFitIntegration()
        }
    }

    private fun generateAccessToken() {
        doInBackground({
            try {
                connector?.getAccessToken()
            } catch (e: IOException) {
                Logger.e(MainActivity::class.java.simpleName, e)
                null
            }
        }, { accessToken ->
            progressDialog?.dismiss()
            if (accessToken != null) {
                textViewAccessToken?.text = ("AccessToken: $accessToken (retrieved: ${Date()})").trimIndent()
                Toast.makeText(applicationContext, "Access token updated.", Toast.LENGTH_SHORT).show()
                if (this.accessToken != accessToken) {
                    disconnectSources()
                    this.accessToken = accessToken
                }
            } else {
                Toast.makeText(applicationContext, "Error getting access token.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showProgress() {
        if (progressDialog?.isShowing == false) {
            progressDialog?.show()
        }
    }

    private fun onClickGetUserInformation() {
        showProgress()
        getUserInformation()
    }

    private fun getUserInformation() {
        doInBackground({
            connector?.userInformation
        }) { userInformationMap ->
            progressDialog?.dismiss()
            userInformationMap?.let {
                for (userInformation in it) {
                    var resultString = """
                        Token: ${userInformation.authenticationToken}

                        """.trimIndent()
                    resultString += """
                        Partner ID: ${userInformation.partnerUserID}

                        """.trimIndent()
                    resultString += """
                        Height: ${userInformation.height}

                        """.trimIndent()
                    resultString += """
                        Weight: ${userInformation.weight}

                        """.trimIndent()
                    resultString += """
                        Birthdate: ${userInformation.birthDate}

                        """.trimIndent()
                    resultString += """
                        Gender: ${userInformation.gender}

                        """.trimIndent()
                    resultString += """
                        Connected Sources: ${userInformation.connectedSources}

                        """.trimIndent()
                    Logger.d(UserInformation::class.java.simpleName, resultString)
                    Toast.makeText(applicationContext, resultString, Toast.LENGTH_SHORT).show()
                }
            }
            if (userInformationMap.isNullOrEmpty()) {
                Toast.makeText(applicationContext, "Error getting user information", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private class ToggleStepDetectionAsyncTask constructor(private val connector: RecognitionConnector?) : AsyncTask<Void?, Void?, String?>() {

        override fun doInBackground(vararg voids: Void?): String? {
            if (connector!!.isActive(RecognitionConnector.Service.STEPS)) {
                connector.stopStepDetection()
            } else {
                try {
                    connector.startStepDetection()
                } catch (e: IOException) {
                    Logger.e(this::class.java.simpleName, e)
                }
            }
            return null
        }
    }

    private class ToggleActivityRecognitionAsyncTask(private val connector: RecognitionConnector?) : AsyncTask<Void?, Void?, String?>() {
        override fun doInBackground(vararg voids: Void?): String? {
            if (connector!!.isActive(RecognitionConnector.Service.ACTIVITY)) {
                connector.stopActivityRecognition()
            } else {
                connector.startActivityRecognition()
            }
            return null
        }
    }

    private class ToggleLocationTrackingAsyncTask(private val connector: RecognitionConnector?, private val highAccuracy: Boolean) : AsyncTask<Void?, Void?, String?>() {
        override fun doInBackground(vararg voids: Void?): String? {
            if (connector!!.isActive(RecognitionConnector.Service.LOCATION)) {
                connector.stopLocationTracking()
            } else {
                try {
                    if (highAccuracy) {
                        connector.startLocationTracking(true)
                    } else {
                        connector.startLocationTracking(false)
                    }
                } catch (e: IOException) {
                    Logger.e(this::class.java.simpleName, e)
                }
            }
            return null
        }
    }

    companion object {
        val APP_ID: String = "YiUuHPGmi3annz7k"
        val APP_SECRET: String = "ZWmND5JucC4e3dvwmLeKSnGDdegkc86rbvzyTwc5v6eq92jvn7Wm4KpuD7gfwFXmHGZgW3Fvt5JbDrg9"
        var partnerUserId = ""
        var language = "en"
    }
}