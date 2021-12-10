package br.com.raveline.testinghealth.presentation.fragment

import android.os.Bundle
import android.view.*
import android.view.View.VISIBLE
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.raveline.testinghealth.R
import br.com.raveline.testinghealth.databinding.FragmentSearchDogsBinding
import br.com.raveline.testinghealth.presentation.adapter.BreedSearchAdapter
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModel
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModelFactory
import br.com.raveline.testinghealth.utils.NetworkListeners
import br.com.raveline.testinghealth.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchDogsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var searchBinding: FragmentSearchDogsBinding

    @Inject
    lateinit var breedViewModelFactory: BreedViewModelFactory

    @Inject
    lateinit var breedsAdapter: BreedSearchAdapter

    private lateinit var breedViewModel: BreedViewModel

    private val networkListeners = NetworkListeners()
    private var isConnected = MutableLiveData(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        breedViewModel = ViewModelProvider(this, breedViewModelFactory)[BreedViewModel::class.java]

    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch {
            delay(2000)
            networkListeners.checkNetworkAvailability(requireContext())

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        searchBinding = FragmentSearchDogsBinding.inflate(inflater, container, false)
        searchBinding.lifecycleOwner = this
        setHasOptionsMenu(true)
        setupRecyclerView()

        lifecycleScope.launch {
            networkListeners.checkNetworkAvailability(requireContext())
                .observe(viewLifecycleOwner, {status ->
                    isConnected.postValue(status)
                })
        }

        return searchBinding.root
    }

    private fun setupRecyclerView() {
        showProgressBar()
        searchBinding.recyclerViewSearchFragment.apply {
            setHasFixedSize(true)
            itemAnimator = SlideInRightAnimator(OvershootInterpolator(2f))
            adapter = breedsAdapter
            visibility = VISIBLE
        }
        hideProgressBar()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchQueryByRequest(query)
        }

        hideKeyboard()
        return true
    }

    private fun searchQueryByRequest(query: String) {

        if(isConnected.value!!){
            breedViewModel.getBreedsBySearch(query).observe(viewLifecycleOwner, { response ->
                breedsAdapter.setData(response)

                if(response.isEmpty()){
                    Toast.makeText(requireContext(), "No data found with this parameters! ", Toast.LENGTH_SHORT).show()
                }
            })
        }else Toast.makeText(requireContext(), "Please, check your internet connection!", Toast.LENGTH_SHORT).show()
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val search = menu.findItem(R.id.searchBreedMenuId)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.queryHint = "Ex: Akita"
        searchView?.setOnQueryTextListener(this)
    }

    private fun hideProgressBar() {
        searchBinding.progressBarSearchFragment.visibility = View.GONE
    }

    private fun showProgressBar() {
        searchBinding.progressBarSearchFragment.visibility = View.VISIBLE
    }
}