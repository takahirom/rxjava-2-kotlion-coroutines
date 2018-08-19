package com.github.takahirom.rxjava2kotlincoroutines

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.shopify.livedataktx.nonNull
import com.shopify.livedataktx.observe
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.persons_recycler

class MainActivity : AppCompatActivity() {
  private val personsViewModel: PersonsViewModel by lazy {
    ViewModelProviders.of(this).get(PersonsViewModel::class.java)
  }
  private val groupAdapter = GroupAdapter<ViewHolder>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
    persons_recycler.adapter = groupAdapter

    personsViewModel.persons.nonNull().observe(this) { persons ->
      groupAdapter.update(persons.map(::PersonItem))
    }
    personsViewModel.errors.nonNull().observe(this) { exception ->
      Snackbar.make(toolbar, exception.message?: "Unknown Error", Snackbar.LENGTH_LONG).show()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
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
}
