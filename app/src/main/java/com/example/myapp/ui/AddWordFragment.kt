package com.example.myapp.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.example.myapp.R
import com.example.myapp.db.Word
import com.example.myapp.db.WordDB
import com.example.myapp.util.toast
import kotlinx.android.synthetic.main.fragment_add_word.*
import kotlinx.coroutines.launch

class AddWordFragment : BaseFragment() {

    private var word: Word? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_word, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBtn_type_word.setOnClickListener {
            val dialog = TypeWordDialogFragment()
            dialog.show(childFragmentManager,"dialogFragment")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            word = AddWordFragmentArgs.fromBundle(it).word
            tiet_add_word.setText(word?.engWord)
            tiet_translate_word.setText(word?.translateWord)
        }

        btn_save.setOnClickListener { view ->

            val addWord = tiet_add_word.text.toString().trim()
            val addTranslateWord = tiet_translate_word.text.toString().trim()

            if(addWord.isEmpty()) {
                tiet_add_word.error = "word required"
                tiet_add_word.requestFocus()
                return@setOnClickListener
            }

            if (addTranslateWord.isEmpty()){
                tiet_translate_word.error = "translate word required"
                tiet_translate_word.requestFocus()
                return@setOnClickListener
            }

            launch {
                context?.let {
                    val mWord = Word(addWord, addTranslateWord)

                    if (word == null) {
                        WordDB(it).getWordDao().addWord(mWord)
                        it.toast("Note Saved")
                    } else {
                        mWord.id = word!!.id
                        WordDB(it).getWordDao().updateWord(mWord)
                        it.toast("Note Updated")
                    }

                    val action = AddWordFragmentDirections.actionSaveWord()
                    Navigation.findNavController(view).navigate(action)

                }
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> {
                if (word != null)
                    deleteWord()
                else
                    context?.toast("Cannot Delete")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteWord(){
        AlertDialog.Builder(context!!).apply {
            setTitle("Are you sure ?")
            setMessage("You cannot undo this operation!")
            setPositiveButton("Yes"){ _,_ ->
                launch {
                    WordDB(context).getWordDao().deleteWord(word!!)
                    val action = AddWordFragmentDirections.actionSaveWord()
                    Navigation.findNavController(view!!).navigate(action)
                }
            }
            setNegativeButton("No"){ _, _ ->

            }
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

}
