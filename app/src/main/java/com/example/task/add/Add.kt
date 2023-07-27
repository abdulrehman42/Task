package com.example.task.add

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.task.R
import com.example.task.databinding.ActivityAddBinding
import com.example.task.repository.Repository
import com.example.task.room.Entiti
import com.example.task.room.ListDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class Add : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var viewmodel:AddfragmentViewModel
    private lateinit var databse: ListDatabase
    private lateinit var rep: Repository
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.window?.statusBarColor =this.getColor(R.color.teal_200);
        val list: Entiti? =intent.getParcelableExtra("list")
        if (list!=null)
        {
            edit_data(list)
            binding.toolbarFirm.setNavigationOnClickListener {
                finish()
            }
        }else {
            supportActionBar?.hide()
            supportActionBar?.customView = binding.toolbarFirm
            binding.toolbarFirm.setNavigationOnClickListener {
                finish()
            }
            binding.layoutDate.setEndIconOnClickListener {
                showDatePickerDialog()
            }
            binding.date.setOnClickListener{
                showDatePickerDialog()
            }
            databse = ListDatabase(this)
            rep = Repository(databse)
            viewmodel =
                ViewModelProvider(this, viewmodelFactory(rep)).get(AddfragmentViewModel::class.java)
            val items = arrayOf("AM", "PM")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
            binding.spinner1.adapter = adapter
            binding.saveBtn.setOnClickListener {
                insert_Data()
            }
        }
    }

    private fun edit_data(list: Entiti) {
        databse = ListDatabase(this)
        rep = Repository(databse)
        binding.title.setText(list.title)
        binding.date.setText(list.date)
        binding.hour.setText(list.hour)
        binding.minute.setText(list.mint)
        val items = arrayOf("AM", "PM")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        binding.spinner1.adapter = adapter
        binding.saveBtn.setOnClickListener {
            val title=binding.title.text.toString()
            val date=binding.date.text.toString()
            val value=binding.spinner1.selectedItem.toString()
            val hour=binding.hour.text.toString()
            val minute=binding.minute.text.toString()
            if (hour.isEmpty())
            {
                Toast.makeText(this,"Doesn't set hour", Toast.LENGTH_SHORT).show()
            }else if (minute.isEmpty())
            {
                Toast.makeText(this,"Doesn't set minute", Toast.LENGTH_SHORT).show()
            }else if (date.isEmpty())
            {
                Toast.makeText(this,"please Select the date", Toast.LENGTH_SHORT).show()

            }
            else
            {
                val entity= Entiti(list.id,title,date, hour = hour, mint = minute+value)
                lifecycleScope.launch {
                    rep.update(entity)
                }
                Toast.makeText(this@Add, "Task Update successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun insert_Data() {
        val title = binding.title.text.toString()
        val value = binding.spinner1.selectedItem.toString()
        val hour = binding.hour.text.toString()
        val minute = binding.minute.text.toString()
        val get =
            getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE)
        val dat = get.getString("date", null)
        binding.date.setText(dat)
        val date = dat
        if (hour.isEmpty()) {
            Toast.makeText(this, "Doesn't set hour", Toast.LENGTH_SHORT).show()
        } else if (minute.isEmpty()) {
            Toast.makeText(this, "Doesn't set minute", Toast.LENGTH_SHORT).show()
        } else if (date?.isEmpty() == true) {
            Toast.makeText(this, "please Select the date", Toast.LENGTH_SHORT).show()

        } else {
            val entity = Entiti(0, title, date, hour = hour, mint = minute + value)
            lifecycleScope.launch {
                viewmodel.insertNote(entity)
            }
            Toast.makeText(this@Add, "Task successfully save ", Toast.LENGTH_SHORT).show()
            finish()
            val editor =
                getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit()
            editor.putString("date", null)
            editor.apply()
        }
    }
    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = formatDate(selectedYear, selectedMonth, selectedDay)
                binding.date.setText(selectedDate)
                val editor =
                    getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE).edit()
                editor.putString("date", selectedDate)
                editor.apply()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }
    private fun formatDate(year: Int, month: Int, day: Int): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return sdf.format(calendar.time)
    }
}
