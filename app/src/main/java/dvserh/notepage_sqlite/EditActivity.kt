package dvserh.notepage_sqlite


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lessonsqlite.db.MyDbManager
import dvserh.notepage_sqlite.databinding.EditActivityBinding

class EditActivity : AppCompatActivity() {
    lateinit var binding: EditActivityBinding
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun onClickAddImage(view: View) {
        binding.mainImageLayout.visibility = View.VISIBLE
        binding.fbAddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        binding.mainImageLayout.visibility = View.GONE
        binding.fbAddImage.visibility = View.VISIBLE
    }
//    fun onClickChooseImage(view: View) {
//        val intent = Intent1 ()
//        //intent.type = "image/*"
//        intent.putExtra("key1","image/*" ) // моя строка
//        setResult(RESULT_OK, intent)  // моя строка
//    }
    fun onClickSave(view: View){
        val myTitle = binding.edTitle.text.toString()
        val myDesc = binding.edDesc.text.toString()

        if (myTitle != "" && myDesc != ""){
            myDbManager.insertToDb(myTitle, myDesc, tempImageUri)
        }
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDB()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

}