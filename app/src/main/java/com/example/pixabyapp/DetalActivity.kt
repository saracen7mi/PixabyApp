package com.example.pixabyapp

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detal.*

class DetalActivity : AppCompatActivity() {

    var mydownload: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detal)

        val db = intent
        val creator = db.getStringExtra("detailcreator")
        val imege = db.getStringExtra("detailimage")
        val coment = db.getIntExtra("coments", 0)
        Picasso.get().load(imege).into(imageDetail)
        descText.setText(creator)
        likeText.setText("coments" + coment)

        botomDetail.setOnClickListener {
            val menager = DownloadManager.Request(Uri.parse(imege))
                .setTitle("Download Pixaby 2020")
                .setDescription("Pixaby Image")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
            var downloadMenager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            mydownload = downloadMenager.enqueue(menager)
        }

        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                var id = p1!!.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == mydownload) {
                    Toast.makeText(applicationContext, "Download Complited", Toast.LENGTH_LONG)
                        .show()
                }
            }

        }
        registerReceiver(broadcast, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}