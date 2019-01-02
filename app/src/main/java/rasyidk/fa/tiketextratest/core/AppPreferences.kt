package rasyidk.fa.tiketextratest.core

import rasyidk.fa.tiketextratest.core.rest.RxBus

class AppPreferences {
    private lateinit var mRxBus: RxBus

    fun getURL(): String {

        return "https://api.tiketextra.com/"
    }
}