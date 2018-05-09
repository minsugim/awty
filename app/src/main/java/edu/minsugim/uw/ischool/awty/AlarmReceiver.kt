package edu.minsugim.uw.ischool.awty

import android.content.Intent
import android.widget.Toast
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.valdesekamdem.library.mdtoast.MDToast


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val extras : Bundle = intent.extras
        val number = extras.getString("number")
        val message = extras.getString("message")
        val text = context.getString(R.string.textNumber)
        MDToast.makeText(context, text + " " + number + "\n" + message, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show()
    }
}