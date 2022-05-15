package dvserh.notepage_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lessonsqlite.db.MyDbManager
import dvserh.notepage_sqlite.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDB()
        val dataList = myDbManager.readDbData()
        for( item in dataList) {
            binding.tvText.append(item)
            binding.tvText.append("\n")
        }
    }

    fun onClickSave(view: View) {
        binding.tvText.text = " "

        myDbManager.insertToDb(binding.edTitle.text.toString(), binding.edContent.text.toString())
        val dataList = myDbManager.readDbData()
        for( item in dataList) {
            binding.tvText.append(item)
            binding.tvText.append("\n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
}