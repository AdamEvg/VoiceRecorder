package com.voicerecorder.listRecord

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.voicerecorder.R
import com.voicerecorder.database.entities.RecordingItem
import com.voicerecorder.player.PlayerFragment
import com.voicerecorder.removeDialog.RemoveDialogFragment
import java.io.File
import java.util.concurrent.TimeUnit

class ListRecordAdapter : RecyclerView.Adapter<ListRecordAdapter.ViewHolder>() {

    var data = listOf<RecordingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.itemView.context
        val recordingItem = data[position]
        val itemDuration: Long = recordingItem.recordLength
        val minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration)
        val seconds =
            TimeUnit.MILLISECONDS.toSeconds(itemDuration) - TimeUnit.MINUTES.toSeconds(minutes)

        holder.vName.text = recordingItem.recordName
        holder.vLength.text = String.format("%02d:%02d", minutes, seconds)

        holder.cardView.setOnClickListener {
            val filePath = recordingItem.recordPath
            val file = File(filePath)
            if (file.exists()) {
                try {
                    playRecord(filePath, context)
                } catch (e: Exception) {
                }

            } else {
                Toast.makeText(context, "Аудиофайл не найден", Toast.LENGTH_SHORT).show()
            }
        }

        holder.button.setOnClickListener {
            removeItemDialog(recordingItem, context)
        }

    }

    private fun playRecord(filePath: String, context: Context?) {
        val playerFragment: PlayerFragment = PlayerFragment().newInstance(filePath)
        val transaction: FragmentTransaction = (context as FragmentActivity)
            .supportFragmentManager
            .beginTransaction()
        playerFragment.show(transaction, "dialog_playback")
    }

     fun removeItemDialog(
         recordingItem: RecordingItem,
         context: Context?
     ) {
        val removeDialogFragment: RemoveDialogFragment =
            RemoveDialogFragment()
                .newInstance(
                    recordingItem.recordId,
                    recordingItem.recordPath
                )
        val transaction: FragmentTransaction =
            (context as FragmentActivity)
                .supportFragmentManager
                .beginTransaction()
        removeDialogFragment.show(transaction, "dialog_remove")
        notifyDataSetChanged()
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vName: TextView = itemView.findViewById(R.id.record_name__item_list_record)
        var vLength: TextView = itemView.findViewById(R.id.record_time_length__item_list_record)
        var cardView: View = itemView.findViewById(R.id.card_view)
        var button: Button = itemView.findViewById(R.id.delete_button__item_list_record)
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view: View = layoutInflater.inflate(R.layout.item_list_record, parent, false)
                return ViewHolder(view)
            }
        }
    }
}












