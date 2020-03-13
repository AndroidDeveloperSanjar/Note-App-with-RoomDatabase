package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.db.Word
import com.example.myapp.ui.WordsFragmentDirections
import kotlinx.android.synthetic.main.item_word_rv.view.*

class  WordsAdapter(
    private val words: MutableList<Word>
) : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_word_rv,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = words.size

    fun removeAt(position: Int){
        words.removeAt(position)
        notifyItemRemoved(position)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.view.tv_id_word.text = "${words[position].id})  "
        holder.view.tv_date_and_time_word.text = words[position].writedTime
        holder.view.tv_eng_word.text = words[position].engWord
        holder.view.tv_translate_word.text = words[position].translateWord

        holder.view.setOnClickListener {

            val action = WordsFragmentDirections.actionAddWord()
            action.word = words[position]
            Navigation.findNavController(it).navigate(action)

        }
    }

    class WordsViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}