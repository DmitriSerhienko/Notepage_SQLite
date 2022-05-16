package dvserh.notepage_sqlite


import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lessonsqlite.db.MyDbManager
import dvserh.notepage_sqlite.databinding.EditActivityBinding
import dvserh.notepage_sqlite.db.MyIntentConstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {
    lateinit var binding: EditActivityBinding
    private var editLauncher: ActivityResultLauncher<Intent>? =
        null                                                    //- переписать что б картинка тянулась с памяти телефона
    var id = 0
    var isEditState = false
    var tempImageUri =
        "empty"                                             //- переписать что б картинка тянулась с памяти телефона
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


    override fun onActivityResult(                                                 // не работает подгружение картинки с телефона
        requestCode: Int,                                                         // - переписать что б картинка тянулась с памяти телефона
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (resultCode == Activity.RESULT_OK) {
                binding.imMainImage.setImageURI(data?.data)
                tempImageUri = data?.data.toString()
                contentResolver.takePersistableUriPermission(data?.data!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        }
    }


    fun onClickAddImage(view: View) {
        binding.mainImageLayout.visibility = View.VISIBLE
        binding.fbAddImage.visibility = View.GONE
    }

    fun onClickDeleteImage(view: View) {
        binding.mainImageLayout.visibility = View.GONE
        binding.fbAddImage.visibility = View.VISIBLE
    }


    fun onClickChooseImage(view: View) {                                      // - переписать что б картинка тянулась с памяти телефона
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        intent.putExtra("key", intent.type)                                 // моя строка
        setResult(RESULT_OK, intent)                                                // моя строка
        startActivity(intent)                                                       // моя строка
    }


    fun onClickSave(view: View) {
        val myTitle = binding.edTitle.text.toString()
        val myDesc = binding.edDesc.text.toString()

        if (myTitle != "" && myDesc != "") {

            CoroutineScope(Dispatchers.Main).launch {
                if (isEditState) {
                    myDbManager.updateIten(myTitle, myDesc, tempImageUri.toString(), id, getCurTime() )
                } else {
                    myDbManager.insertToDb(myTitle, myDesc, tempImageUri.toString(), getCurTime())
                }
                finish()
            }

        }
    }

    fun getMyIntents() {
        binding.fbEdit.visibility = View.GONE
        val i = intent

        if (i != null) {

            if (i.getStringExtra(MyIntentConstance.I_TITLE_KEY) != null) {
                binding.fbAddImage.visibility = View.GONE
                binding.edTitle.setText(i.getStringExtra(MyIntentConstance.I_TITLE_KEY))
                isEditState = true
                binding.edTitle.isEnabled = false
                binding.edDesc.isEnabled = false
                binding.fbEdit.visibility = View.VISIBLE
                binding.edDesc.setText(i.getStringExtra(MyIntentConstance.I_DESC_KEY))
                id = i.getIntExtra(MyIntentConstance.I_ID_KEY, 0)
                binding.imMainImage.setImageResource(R.drawable.bk_1)                                //моя строка
                if (i.getStringExtra(MyIntentConstance.I_URI_KEY) != "empty") {

                    binding.mainImageLayout.visibility = View.VISIBLE
                   tempImageUri = i.getStringExtra(MyIntentConstance.I_URI_KEY)!!     //- переписать что б картинка тянулась с памяти телефона
                 binding.imMainImage.setImageURI(Uri.parse(tempImageUri))          //- переписать что б картинка тянулась с памяти телефона
                    binding.imButtonDelete.visibility = View.GONE
                    binding.imButtonEditImage.visibility = View.GONE

                }
            }
        }
    }

    fun onEditEnable(view: View) {
        binding.edTitle.isEnabled = true
        binding.edDesc.isEnabled = true
        binding.fbEdit.visibility = View.GONE
        binding.fbAddImage.visibility = View.VISIBLE
        if (tempImageUri == "empty") return                     // - переписать что б картинка тянулась с памяти телефона
        binding.imButtonEditImage.visibility = View.VISIBLE
        binding.imButtonDelete.visibility = View.VISIBLE
    }

    private fun getCurTime(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yy kk:mm", Locale.getDefault())
        return formatter.format(time)
    }
}