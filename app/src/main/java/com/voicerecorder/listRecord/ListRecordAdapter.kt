package com.voicerecorder.listRecord

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.voicerecorder.R
import com.voicerecorder.database.entities.RecordingItem
import com.voicerecorder.player.PlayerFragment
import java.io.File
import java.util.concurrent.TimeUnit

class ListRecordAdapter :RecyclerView.Adapter<ListRecordAdapter.ViewHolder>() {
    var recordsData = listOf<RecordingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val recordingItem = recordsData[position]

        val itemDuration: Long = recordingItem.recordLength
        val minutes = TimeUnit.MILLISECONDS.toMinutes(itemDuration)
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(itemDuration)
                - TimeUnit.MINUTES.toSeconds(minutes))

        holder.recordName.text = recordingItem.recordName
        holder.recordLength.text = String.format("%02d:%02d", minutes, seconds)

        holder.cardView.setOnClickListener{
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
    }

    private fun playRecord(filePath: String, context: Context?) {
        val playerFragment: PlayerFragment = PlayerFragment().newInstance(filePath)
        val transaction: FragmentTransaction = (context as FragmentActivity)
            .supportFragmentManager
            .beginTransaction()
        playerFragment.show(transaction, "dialog_playback")
    }

    override fun getItemCount(): Int = recordsData.size

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

        var recordName: TextView = itemView.findViewById(R.id.record_name__item_list_record)
        var recordLength: TextView = itemView.findViewById(R.id.record_time_length__item_list_record)
        var cardView: View = itemView.findViewById(R.id.card_view)

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.item_list_record, parent, false)
                return ViewHolder(
                    view
                )
            }
        }

    }
}