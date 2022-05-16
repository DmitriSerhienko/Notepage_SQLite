package dvserh.notepage_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lessonsqlite.db.MyDbManager
import dvserh.notepage_sqlite.databinding.ActivityMainBinding
import dvserh.notepage_sqlite.db.MyAdapter


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val myDbManager = MyDbManager(this)
    val myAdapter = MyAdapter(ArrayList(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDB()
        fillAdapter()
    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    fun onClickNew(view: View) {
        val i = Intent(this, EditActivity::class.java)
        startActivity(i)
    }

    fun init (){
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = myAdapter
    }

    fun fillAdapter(){
        val list = myDbManager.readDbData()
        myAdapter.updateAdapter(list)
        if (list.size > 0) {
            binding.tvNoElements.visibility = View.GONE
        } else{
            binding.tvNoElements.visibility = View.VISIBLE
        }
    }

}