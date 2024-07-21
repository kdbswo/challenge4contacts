package com.loci.challenge4contacts

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loci.challenge4contacts.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val contactsList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val status = ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS")
        if (status == PackageManager.PERMISSION_GRANTED) {
            Log.d("text", "permission denied")
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>("android.permission.READ_CONTACTS"),
                100
            )
            Log.d("text", "permission denied")
        }


        val list = getPhoneContactsList()
        Log.d("list", list.toString())
        dataInit()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("test", "permission granted")
        } else {
            Log.d("test", "permission denied")
        }
    }

    @SuppressLint("Range")
    private fun getPhoneContactsList(): MutableList<Contact> {

        val cr = contentResolver
        val cursor = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            null
        )

        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add(Contact(id.toInt(), name, null, number))
            }
        }

        cursor.close()
        return contactsList
    }


    private fun dataInit() {
        val id = contactsList.last().id

        contactsList.add(Contact(id + 1, "name1", R.drawable.profile01, "010-3548-5684"))
        contactsList.add(Contact(id + 2, "name2", R.drawable.profile02, "010-6512-9831"))
        contactsList.add(Contact(id + 3, "name3", R.drawable.profile03, "010-5616-0668"))
        contactsList.add(Contact(id + 4, "name4", R.drawable.profile04, "010-8755-2384"))
        contactsList.add(Contact(id + 5, "name5", R.drawable.profile05, "010-0165-5156"))
        contactsList.add(Contact(id + 6, "name6", R.drawable.profile06, "010-5468-9875"))
        contactsList.add(Contact(id + 7, "name7", R.drawable.profile07, "010-8498-1202"))
        contactsList.add(Contact(id + 8, "name8", R.drawable.profile08, "010-1324-6187"))
        contactsList.add(Contact(id + 9, "name9", R.drawable.profile09, "010-8418-7253"))
        contactsList.add(Contact(id + 10, "name10", R.drawable.profile10, "010-3685-3518"))
    }
}