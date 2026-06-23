package dev.e88e89.adbkit

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat

object AdbSettingsManager {
    private const val TAG = "AdbSettingsManager"
    const val PREFS_NAME = "adbkit_prefs"
    const val KEY_ADB_VALUE = "adb_value"
    const val DEFAULT_ADB_VALUE = 2

    fun getPrefs(context: Context) =
        context.createDeviceProtectedStorageContext()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun applyAdbSetting(context: Context) {
        val prefs = getPrefs(context)
        val value = prefs.getInt(KEY_ADB_VALUE, DEFAULT_ADB_VALUE)

        if (!hasPermission(context)) {
            Log.e(TAG, "Cannot apply setting, missing WRITE_SECURE_SETTINGS permission")
            return
        }

        try {
            Settings.Global.putInt(
                context.contentResolver,
                Settings.Global.ADB_ENABLED,
                value
            )
            Log.i(TAG, "Global adb_enabled → $value")
        } catch (e: SecurityException) {
            Log.e(TAG, "Global write failed: ${e.message}")
        }

        try {
            @Suppress("DEPRECATION")
            Settings.Secure.putInt(
                context.contentResolver,
                "adb_enabled",
                value
            )
            Log.i(TAG, "Secure adb_enabled → $value")
        } catch (e: SecurityException) {
            Log.e(TAG, "Secure write failed: ${e.message}")
        }
    }

    fun hasPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_SECURE_SETTINGS
        ) == PackageManager.PERMISSION_GRANTED
    }
}
