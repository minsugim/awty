package edu.minsugim.uw.ischool.awty

import android.content.Intent
import android.widget.Toast
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.util.Log


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val extras : Bundle = intent.extras
        val number = extras.getString("number")
        Log.i("Alarm", number)
        Toast.makeText(context, number + context.getString(R.string.nag), Toast.LENGTH_SHORT).show()
    }
}