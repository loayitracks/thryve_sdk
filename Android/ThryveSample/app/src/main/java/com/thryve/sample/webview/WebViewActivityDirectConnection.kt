package com.thryve.sample.webview

import android.widget.Toast
import com.thryve.connector.sdk.logger.Logger
import com.thryve.connector.sdk.network.ConnectorCallback
import com.thryve.connector.sdk.CoreConnector
import com.thryve.sample.MainActivity
import com.thryve.sample.R

class WebViewActivityDirectConnection : WebViewActivity() {
    override fun loadWebView() {
        if (intent.hasExtra("DataSource")) {
            val connector = CoreConnector(this, MainActivity.APP_ID, MainActivity.partnerUserId)
            connector.handleDataSourceDirectConnection(true, intent.getIntExtra("DataSource", 1), false, webView!!, object : ConnectorCallback() {
                override fun onResult(accessToken: String?, url: String?, dataSourceType: Int, connected: Boolean, message: String?) {
                    Logger.d("DIRECT_CONNECTION", url.toString())
                    Toast.makeText(
                            this@WebViewActivityDirectConnection,
                            getString(R.string.direct_connection_result, dataSourceType, accessToken, connected, message),
                            Toast.LENGTH_LONG).show()
                    finish()
                }

                override fun onError(accessToken: String?, url: String?, dataSourceType: Int, message: String?) {
                    Logger.w("DIRECT_CONNECTION", url)
                    Logger.w(WebViewActivityDirectConnection::class.java.simpleName,
                            getString(R.string.direct_connection_error, dataSourceType, accessToken, message))
                    finish()
                }

                override fun onSocialLoginError(accessToken: String?, url: String?, dataSourceType: Int, message: String?) {
                    Logger.w("DIRECT_CONNECTION", url)
                    Logger.w(WebViewActivityDirectConnection::class.java.simpleName,
                        getString(R.string.direct_connection_error_social, dataSourceType, accessToken, message))
                }
            })
        }
    }
}