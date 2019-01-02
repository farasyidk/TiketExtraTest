package rasyidk.fa.tiketextratest.core

import android.app.Application
import rasyidk.fa.tiketextratest.core.rest.RxBus

class AppPreferences {
    private lateinit var mRxBus: RxBus


    fun getURL(): String {
        /**
         * Base URL for APIs here
         */
        return "https://api.tiketextra.com/"
    }
}