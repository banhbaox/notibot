package com.notibot.app

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationService : NotificationListenerService() {

    companion object {
        private const val TAG = "NotificationService"
        var instance: NotificationService? = null
            private set
        var bleManager: BleManager? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d(TAG, "NotificationService created")
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        Log.d(TAG, "NotificationService destroyed")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.d(TAG, "Notification listener connected")
        sendCurrentNotifications()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        Log.d(TAG, "Notification posted from: ${sbn.packageName}")
        sendCurrentNotifications()
        sendLatestNotification(sbn)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d(TAG, "Notification removed from: ${sbn.packageName}")
        sendCurrentNotifications()
    }

    private fun sendCurrentNotifications() {
        val ble = bleManager ?: return
        if (!ble.isConnected) return

        try {
            val notifications = activeNotifications
            val count = notifications?.size ?: 0
            Log.d(TAG, "Active notifications: $count")

            if (count == 0) {
                ble.sendCount(0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting notifications", e)
        }
    }

    private fun sendLatestNotification(sbn: StatusBarNotification) {
        val ble = bleManager ?: return
        if (!ble.isConnected) return

        try {
            val notification = sbn.notification
            val extras = notification.extras

            // Get app name
            val pm = packageManager
            val appInfo = pm.getApplicationInfo(sbn.packageName, 0)
            val appName = pm.getApplicationLabel(appInfo).toString()

            // Get notification content
            val title = extras.getCharSequence("android.title")?.toString() ?: ""
            val text = extras.getCharSequence("android.text")?.toString() ?: ""

            // Get total count
            val count = activeNotifications?.size ?: 1

            Log.d(TAG, "Sending: $appName - $title - $text")
            ble.sendNotification(count, appName, title, text)

        } catch (e: Exception) {
            Log.e(TAG, "Error processing notification", e)
        }
    }
}
