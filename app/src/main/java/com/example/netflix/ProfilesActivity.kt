package com.example.netflix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.netflix.databinding.ActivityProfilesBinding

class ProfilesActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var profilesComponents = arrayOf(binding.profileA, binding.profileB,
            binding.profileC, binding.profileD)
        profilesComponents.forEach {
            val imageRes = when (it.id) {
                R.id.profile_a -> R.mipmap.profile_1
                R.id.profile_b -> R.mipmap.profile_2
                R.id.profile_c -> R.mipmap.profile_3
                R.id.profile_d -> R.mipmap.profile_4
                else -> R.mipmap.profile_1
            }

            it.setOnClickListener {
                startActivity(
                    Intent(this, HomeActivity::class.java)
                        .putExtra("PROFILE_RES", imageRes)
                )
                finish()
            }
        }
    }
}