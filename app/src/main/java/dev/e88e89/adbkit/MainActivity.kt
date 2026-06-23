package dev.e88e89.adbkit

import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.DynamicColors

open class MainActivity : AppCompatActivity() {

    private lateinit var radioGroupImmediate: RadioGroup
    private lateinit var radioGroupBoot: RadioGroup
    private lateinit var cardPermHint: MaterialCardView
    private var isUpdatingUI = false

    protected open val layoutResId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        radioGroupImmediate = findViewById(R.id.radio_group_immediate)
        radioGroupBoot = findViewById(R.id.radio_group_boot)
        cardPermHint = findViewById(R.id.card_perm_hint)

        radioGroupImmediate.setOnCheckedChangeListener { group, checkedId ->
            if (isUpdatingUI) return@setOnCheckedChangeListener
            val selected = group.findViewById<RadioButton>(checkedId)
            val value = selected.tag.toString().toInt()
            AdbSettingsManager.applyAdbValue(this, value)
            refreshStatus()
        }

        radioGroupBoot.setOnCheckedChangeListener { group, checkedId ->
            if (isUpdatingUI) return@setOnCheckedChangeListener
            val selected = group.findViewById<RadioButton>(checkedId)
            val value = selected.tag.toString().toInt()
            AdbSettingsManager.getPrefs(this).edit().putInt(AdbSettingsManager.KEY_ADB_VALUE, value).apply()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshStatus()
    }

    private fun refreshStatus() {
        isUpdatingUI = true

        // Permission hint
        val hasPermission = AdbSettingsManager.hasPermission(this)
        cardPermHint.visibility = if (hasPermission) android.view.View.GONE else android.view.View.VISIBLE

        // Immediate state
        var globalValue = AdbSettingsManager.getGlobalAdbState(this)
        if (globalValue != 0 && globalValue != 1) {
            globalValue = 2
        }
        
        val radioImmMap = mapOf(
            0 to findViewById<RadioButton>(R.id.radio_imm_0),
            1 to findViewById<RadioButton>(R.id.radio_imm_1),
            2 to findViewById<RadioButton>(R.id.radio_imm_2)
        )
        radioImmMap[globalValue]?.isChecked = true

        // Boot target
        val prefs = AdbSettingsManager.getPrefs(this)
        val bootValue = prefs.getInt(AdbSettingsManager.KEY_ADB_VALUE, AdbSettingsManager.DEFAULT_ADB_VALUE)
        
        val radioBootMap = mapOf(
            0 to findViewById<RadioButton>(R.id.radio_boot_0),
            1 to findViewById<RadioButton>(R.id.radio_boot_1),
            2 to findViewById<RadioButton>(R.id.radio_boot_2)
        )
        radioBootMap[bootValue]?.isChecked = true

        isUpdatingUI = false
    }
}
