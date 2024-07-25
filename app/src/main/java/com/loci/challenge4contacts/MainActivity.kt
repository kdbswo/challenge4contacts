package com.loci.challenge4contacts

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.loci.challenge4contacts.databinding.ActivityMainBinding

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
            getPhoneContactsList()
            dataInit()
            Log.d("text", "permission granted")
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf("android.permission.READ_CONTACTS"),
                100
            )
            Log.d("text", "permission denied")
        }


        val listAdapter = ListViewAdapter()
        binding.recyclerView.adapter = listAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter.submitList(contactsList)

        Log.d("test", contactsList.toString())

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
    private fun getPhoneContactsList() {

        val cr = contentResolver
        val cursor = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
                ContactsContract.CommonDataKinds.Phone.STARRED

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
                val photo =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                val starred =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED))


                val photoDrawable = photo?.let { uri ->
                    try {
                        val inputStream = contentResolver.openInputStream(Uri.parse(uri))
                        Drawable.createFromStream(inputStream, uri)

                    } catch (e: Exception) {
                        null
                    }
                }
                contactsList.add(Contact(id.toInt(), name, photoDrawable, number, starred))
            }
        }

        cursor.close()
    }


    private fun dataInit() {
        val id = contactsList.maxByOrNull { it.id }?.id ?: 0

        contactsList.add(
            Contact(
                id + 1,
                "name1",
                ContextCompat.getDrawable(this, R.drawable.profile01),
                "010-3548-5684",
                "0"
            )
        )
        contactsList.add(
            Contact(
                id + 2,
                "name2",
                ContextCompat.getDrawable(this, R.drawable.profile02),
                "010-6512-9831",
                "1"
            )
        )
        contactsList.add(
            Contact(
                id + 3,
                "name3",
                ContextCompat.getDrawable(this, R.drawable.profile03),
                "010-5616-0668",
                "0"
            )
        )
        contactsList.add(
            Contact(
                id + 4,
                "name4",
                ContextCompat.getDrawable(this, R.drawable.profile04),
                "010-8755-2384",
                "0"
            )
        )
        contactsList.add(
            Contact(
                id + 5,
                "name5",
                ContextCompat.getDrawable(this, R.drawable.profile05),
                "010-0165-5156",
                "0"
            )
        )
        contactsList.add(
            Contact(
                id + 6,
                "name6",
                ContextCompat.getDrawable(this, R.drawable.profile06),
                "010-5468-9875",
                "1"
            )
        )
        contactsList.add(
            Contact(
                id + 7,
                "name7",
                ContextCompat.getDrawable(this, R.drawable.profile07),
                "010-8498-1202",
                "0"
            )
        )
        contactsList.add(
            Contact(
                id + 8,
                "name8",
                ContextCompat.getDrawable(this, R.drawable.profile08),
                "010-1324-6187",
                "0"
            )
        )
        contactsList.add(
            Contact(
                id + 9,
                "name9",
                ContextCompat.getDrawable(this, R.drawable.profile09),
                "010-8418-7253",
                "0"
            )
        )
        contactsList.add(
            Contact(
                id + 10,
                "name10",
                ContextCompat.getDrawable(this, R.drawable.profile10),
                "010-3685-3518",
                "1"
            )
        )
    }
}