package com.github.takahirom.rxjava2kotlincoroutines

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_person.*

class PersonItem(private val person:Person):Item(){
  override fun bind(viewHolder: ViewHolder, position: Int) {
    viewHolder.first_name.text = person.name.first
    viewHolder.email.text = person.email
  }

  override fun getLayout() = R.layout.item_person
}
