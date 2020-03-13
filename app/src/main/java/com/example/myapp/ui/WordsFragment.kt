package com.example.myapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.adapter.WordsAdapter
import com.example.myapp.db.WordDB
import kotlinx.android.synthetic.main.fragment_words.*
import kotlinx.coroutines.launch

class WordsFragment : BaseFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_words,
        container,
        false
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRV()
        setOnClickListener()
    }

    private fun initRV(){
        rv_words_fragment.setHasFixedSize(true)
        rv_words_fragment.layoutManager = LinearLayoutManager(context)

        launch {
            context?.let{
                val words = WordDB(it).getWordDao().getAllWords()
                rv_words_fragment.adapter = WordsAdapter(words)
            }
        }
    }

    private fun setOnClickListener(){
        button_add.setOnClickListener {
            val action = WordsFragmentDirections.actionAddWord()

            Navigation.findNavController(it).navigate(action)
        }
    }

}
