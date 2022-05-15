package dvserh.notepage_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dvserh.notepage_sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}