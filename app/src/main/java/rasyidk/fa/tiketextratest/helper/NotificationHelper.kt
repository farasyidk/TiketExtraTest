package rasyidk.fa.tiketextratest.helper

import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat
import rasyidk.fa.tiketextratest.R
import java.lang.ref.WeakReference

class NotificationHelper {

    private var mContext: WeakReference<Context>


    constructor(context: Context) {
        this.mContext = WeakReference(context)
    }

    fun createUploadingNotification() {
        val mBuilder = NotificationCompat.Builder(mContext.get())
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_upload)
        mBuilder.setContentTitle(mContext.get()?.getString(R.string.notification_progress))

        mBuilder.setAutoCancel(true)

        val mNotificationManager = mContext.get()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(mContext.get()?.getString(R.string.app_name).hashCode(), mBuilder.build())

    }

    fun createUploadedNotification() {
        val mBuilder = NotificationCompat.Builder(mContext.get())
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_gallery)
        mBuilder.setContentTitle(mContext.get()?.getString(R.string.notification_success))

        mBuilder.setContentText("Foto berhasil diperbarui")

        val mNotificationManager = mContext.get()?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(mContext.get()?.getString(R.string.app_name).hashCode(), mBuilder.build())
    }

    fun createFailedUploadNotification() {
        val mBuilder = NotificationCompat.Builder(mContext.get())
        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert)
        mBuilder.setContentTitle(mContext.get()!!.getString(R.string.notification_fail))


        mBuilder.setAutoCancel(true)

        val mNotificationManager = mContext.get()!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(mContext.get()?.getString(R.string.app_name).hashCode(), mBuilder.build())
    }
}