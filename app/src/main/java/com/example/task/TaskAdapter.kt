package com.example.task

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.task.add.Add
import com.example.task.repository.Repository
import com.example.task.room.Entiti
import com.example.task.room.ListDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskAdapter(val context:Context, var list:ArrayList<Entiti>):Adapter<TaskAdapter.Viewholder>(){
    private lateinit var databse: ListDatabase
    private lateinit var rep: Repository
    class Viewholder(val v:View):RecyclerView.ViewHolder(v){
        val title=v.findViewById<TextView>(R.id.task)
        val date=v.findViewById<TextView>(R.id.date1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view=LayoutInflater.from(context).inflate(R.layout.custom_item,parent,false)
        return Viewholder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
       val i=list[position]
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, android.R.anim.slide_in_left)
        holder.itemView.startAnimation(animation)
        holder.title.text=i.title
        holder.date.text=i.date
        holder.itemView.setOnClickListener {
            val intent=Intent(context,Add::class.java)
            intent.putExtra("list",i)
            context.startActivity(intent)
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun item_delete(position: Int) {
        if (position in 0 until list.size) {
            databse = ListDatabase(context)
            rep = Repository(databse)
            val item = list[position]
            GlobalScope.launch {
                rep.remove(item)
            }
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun set_data(task: ArrayList<Entiti>) {
        this.list = task
        notifyDataSetChanged()
    }
}