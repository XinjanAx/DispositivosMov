package com.example.anew.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anew.databinding.FragmentSecondBinding
import com.example.anew.logic.data.MarvelChars
import com.example.anew.logic.marvelLogic.MarvelLogic
import com.example.anew.ui.adapter.MarvelAdapter2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var lmanager: LinearLayoutManager

    private lateinit var rvAdapter: MarvelAdapter2

    private var marvelCharItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

//        arguments?.getString("busqueda")?.let { busqueda ->
//            chargeDataRv(busqueda)
//        }

        binding.editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.editText.windowToken, 0)

                chargeDataRv(textView.text.toString())

                //                val fragment = SecondFragment()
                //                val args = Bundle().apply { putString("busqueda", textView.text.toString()) }
                //                fragment.arguments = args
                //
                //                requireActivity().supportFragmentManager.beginTransaction()
                //                    .replace(R.id.frm_container, fragment)
                //                    .addToBackStack(null)
                //                    .commit()

                return@setOnEditorActionListener true
            }
            false
        }

    }

    fun chargeDataRv(search: String) {

        lifecycleScope.launch(Dispatchers.Main) {

            // lo que se ejecuta en ese contexto regresa al contexto Main
            marvelCharItems = withContext(Dispatchers.IO) {
                // se usa dispatcher io porque se maneja entrada y salida (consumo de api)
                return@withContext MarvelLogic().getMarvelChars(
                    search,
                    20
                )
            }

            rvAdapter = MarvelAdapter2(
                marvelCharItems
            )

            // por medio del apply le decimos que debe hacer el codigo
            // funciona similar que el with
            binding.recycler.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }

        }

    }
}