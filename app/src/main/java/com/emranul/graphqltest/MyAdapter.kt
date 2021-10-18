package com.emranul.graphqltest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emranul.graphqltest.databinding.ItemBinding

class MyAdapter(val myData:List<MyFirstQueryTryQuery.Data1>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.title.text = myData[position].title
        holder.binding.description.text = myData[position].description
    }

    override fun getItemCount(): Int {
        return myData.size
    }
}