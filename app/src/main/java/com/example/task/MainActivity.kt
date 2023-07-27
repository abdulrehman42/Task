package com.example.task

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.add.Add
import com.example.task.add.AddfragmentViewModel
import com.example.task.add.viewmodelFactory
import com.example.task.databinding.ActivityMainBinding
import com.example.task.repository.Repository
import com.example.task.room.Entiti
import com.example.task.room.ListDatabase


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var viewmodel:AddfragmentViewModel
    private lateinit var databse: ListDatabase
    private lateinit var rep: Repository
    lateinit var adpter:TaskAdapter
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window?.statusBarColor =this.getColor(R.color.teal_200);
        databse = ListDatabase(this)
        rep = Repository(databse)
        binding.addBtn.setOnClickListener {
            val intent=Intent(this,Add::class.java)
            startActivity(intent)
        }
        viewmodel = ViewModelProvider(this, viewmodelFactory(rep)).get(AddfragmentViewModel::class.java)
        in_it()
}
    @SuppressLint("NotifyDataSetChanged")
    private fun in_it() {
        adpter= TaskAdapter(this,ArrayList<Entiti>())
        viewmodel.getAllNotes().observe(this) {
            adpter.set_data(it as ArrayList<Entiti>)
        }
        binding.recyclerviewTask.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter=adpter
        }
        val itemTouchHelperCallback = SwipeToDeleteCallback(adpter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewTask)
        adpter.notifyDataSetChanged()

    }

    }
