package com.example.bc_parking.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bc_parking.R
import com.example.bc_parking.domain.ParkItem

class ParkItemActivity: AppCompatActivity(), ParkItemFragment.OnEditingFinishedListener {


    private var screenMode = MODE_UNKNOWN
    private var parkItemId = ParkItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_park_item)
        parseIntent()
        if (savedInstanceState== null) {
            launchRightMode()
        }
    }

    override fun onEditingFinished() {
        finish()
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ParkItemFragment.newInstanceEditItem(parkItemId)
            MODE_ADD -> ParkItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.p_item_container_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(PARK_ITEM_ID)) {
                throw RuntimeException("Param park item id is absent")
            }
            parkItemId = intent.getIntExtra(PARK_ITEM_ID, ParkItem.UNDEFINED_ID)
        }
    }


    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val PARK_ITEM_ID = "extra_park_item_Id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ParkItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, parkItemId: Int): Intent {
            val intent = Intent(context, ParkItemActivity::class.java)
            intent.putExtra(SCREEN_MODE, MODE_EDIT)
            intent.putExtra(PARK_ITEM_ID, parkItemId)
            return intent
        }

    }
}
