package com.example.studyapp

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.android_item_row.view.*
import kotlinx.android.synthetic.main.kotlin_item_row.view.*

class AndroidRecyclerViewAdapter(private var topics: ArrayList<Topic>, val context: Context)
    :RecyclerView.Adapter<AndroidRecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val androidDBHelper by lazy { AndroidDBHelper(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.android_item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val topic = topics[position]
        holder.itemView.apply {
            tvAndroidTitle.text = topic.title
            tvAndroidInfo.text = topic.info
            cvAndroidReview.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(context)

                dialogBuilder.setMessage(topic.explanation)
                    .setNegativeButton("Ok", DialogInterface.OnClickListener{ dialog, _ -> dialog.cancel()})

                val alart = dialogBuilder.create()
                alart.setTitle(topic.title)
                alart.show()
            }

            editAndroid.setOnClickListener { showEditAlert(topic) }
            deleteAndroid.setOnClickListener { showDeleteAlert(topic.id) }
        }
    }

    override fun getItemCount() = topics.size

    private fun showEditAlert(topic: Topic){
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val etTitle = EditText(context)
        val etInfo = EditText(context)
        val etExplain = EditText(context)

        etTitle.setText(topic.title)
        etInfo.setText(topic.info)
        etExplain.setText(topic.explanation)

        layout.addView(etTitle)
        layout.addView(etInfo)
        layout.addView(etExplain)

        val dialog = android.app.AlertDialog.Builder(context)
            .setTitle("Edit Topic")
            .setView(layout)
            .setPositiveButton("Edit"){_,_ ->
                val title = etTitle.text.toString()
                val info = etInfo.text.toString()
                val explain = etExplain.text.toString()
                val topic = Topic(topic.id,title,info,explain)
                androidDBHelper.updateTopic(topic)
                updateAdapter()
            }
            .setNegativeButton("Cancel"){dialogFace,_ -> dialogFace.cancel()}
            .create()
        dialog.show()
    }

    private fun showDeleteAlert(id:Int){
        val dialog = android.app.AlertDialog.Builder(context)
            .setTitle("Delete Topic")
            .setMessage("Do You Want To Delete This Topic?")
            .setPositiveButton("Yes"){_,_ ->
                androidDBHelper.deleteTopic(id)
                updateAdapter()
            }
            .setNegativeButton("Cancel"){dialogFace,_ -> dialogFace.cancel()}
            .create()
        dialog.show()
    }

    fun updateAdapter(){
        topics = androidDBHelper.getAllTopics()
        notifyDataSetChanged()
    }
}