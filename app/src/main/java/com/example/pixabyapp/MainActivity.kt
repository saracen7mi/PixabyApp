package com.example.pixabyapp

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity(), PixabyAdapter.OnItemClickListener {
    private var mRecyclerView: RecyclerView? = null
    private var mPixabyAdapter: PixabyAdapter? = null
    private var mPixabyList: ArrayList<PixabyItem>? = null
    private var mRequestQueue: RequestQueue? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setLayoutManager(GridLayoutManager(this, 2))
        mPixabyList = ArrayList()
        mRequestQueue = Volley.newRequestQueue(this)
        parseJSON()
    }

    private fun parseJSON() {
        val url =
            "https://pixabay.com/api/?key=19455526-5a6257e8a5ae8afdbd3808ab0&q=yellow+flowers&image_type=photo"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("hits")
                    for (i in 0 until jsonArray.length()) {
                        val hit = jsonArray.getJSONObject(i)
                        val creatorName = hit.getString("user")
                        val imageUrl = hit.getString("webformatURL")
                        val likeCount = hit.getInt("comments")
                        mPixabyList!!.add(PixabyItem(imageUrl, creatorName, likeCount))
                    }
                    mPixabyAdapter = PixabyAdapter(this@MainActivity, mPixabyList!!, this)
                    mRecyclerView!!.adapter = mPixabyAdapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> error.printStackTrace() }
        mRequestQueue!!.add(request)
    }

    override fun onItemClick(position: Int) {
        val id = mPixabyList!!.get(position)
        val intent = Intent(this, DetalActivity::class.java)
        intent.putExtra("detailcreator", id.creator)
        intent.putExtra("detailimage", id.imageUrl)
        intent.putExtra("coments", id.comments)
        startActivity(intent)
    }

}