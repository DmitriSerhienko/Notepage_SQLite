package dvserh.notepage_sqlite


import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lessonsqlite.db.MyDbManager
import dvserh.notepage_sqlite.databinding.EditActivityBinding
import dvserh.notepage_sqlite.db.ListItem
import dvserh.notepage_sqlite.db.MyIntentConstance

class EditActivity : AppCompatActivity() {
    lateinit var binding: EditActivityBinding
    var tempImageUri = "empty"
    val myDbManager = MyDbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMyIntents()
    }

    override fun onResume() {
        super.onResume()
        myDbManager.openDB()
    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == "key") {            //моя строка
//            binding.imMainImage.setImageURI(data?.data)
//            tempImageUri = data?.data.toString()
//        }
//    }


    fun onClickAddImage(view: View) {
        binding.mainImageLayout.visibility = View.VISIBLE
        binding.fbAddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        binding.mainImageLayout.visibility = View.GONE
        binding.fbAddImage.visibility = View.VISIBLE
    }

    fun onClickChooseImage(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        intent.putExtra("key", intent.type) // моя строка
        setResult(RESULT_OK, intent)  // моя строка
        startActivity(intent) // моя строка

    }


    fun onClickSave(view: View) {
        val myTitle = binding.edTitle.text.toString()
        val myDesc = binding.edDesc.text.toString()

        if (myTitle != "" && myDesc != "") {
            myDbManager.insertToDb(myTitle, myDesc, tempImageUri)
            finish()
        }
    }

    fun getMyIntents() {
        val i = intent

        if (i != null) {

            if (i.getStringExtra(MyIntentConstance.I_TITLE_KEY) != null) {
                binding.fbAddImage.visibility = View.GONE
                binding.edTitle.setText(i.getStringExtra(MyIntentConstance.I_TITLE_KEY))
                binding.edDesc.setText(i.getStringExtra(MyIntentConstance.I_DESC_KEY))
                binding.imMainImage.setImageResource(R.drawable.bk_1)
                if (i.getStringExtra(MyIntentConstance.I_URI_KEY) != "empty") {
                    binding.mainImageLayout.visibility = View.VISIBLE
                    binding.imMainImage.setImageURI(Uri.parse(i.getStringExtra(MyIntentConstance.I_URI_KEY)))
                    binding.imButtonDelete.visibility = View.GONE
                    binding.imButtonEditImage.visibility = View.GONE

                }
            }
        }


    }
}