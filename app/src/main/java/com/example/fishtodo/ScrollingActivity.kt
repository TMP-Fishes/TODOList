package com.example.fishtodo

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.fishtodo.databinding.ActivityScrollingBinding
import com.example.fishtodo.mongodb.DBManager
import com.example.fishtodo.viewmodel.HomeViewModel

class ScrollingActivity : AppCompatActivity() {
    private val dbManager by lazy {
        DBManager(this)
    }

    private val viewmodel = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        val currentActivityBinding: ActivityScrollingBinding = DataBindingUtil.setContentView (
    this, R.layout.activity_scrolling
        )
        currentActivityBinding.vm = viewmodel
        currentActivityBinding.clickHandler = this

        viewmodel.bind(dbManager.readLatest())
    }

    fun showSnack(view: View) {
        Snackbar.make(view, "Updating...", Snackbar.LENGTH_SHORT).show()
        viewmodel.bind(dbManager.readLatest())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

//    private fun onClickSync(view: View) {
//        viewmodel.bind(dbManager.readLatest()) // TODO: observable? two-way bind?
//        Snackbar.make(view, "Updating...", Snackbar.LENGTH_SHORT)
//            .setAction("Action", null).show() // TODO: uhh, what's the "action" here?
//    }
}