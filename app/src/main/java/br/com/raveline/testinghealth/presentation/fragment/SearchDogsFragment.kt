package br.com.raveline.testinghealth.presentation.fragment

import android.os.Bundle
import android.view.*
import android.view.animation.OvershootInterpolator
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.testinghealth.R
import br.com.raveline.testinghealth.databinding.FragmentSearchDogsBinding
import br.com.raveline.testinghealth.presentation.adapter.BreedSearchAdapter
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModel
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModelFactory
import br.com.raveline.testinghealth.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import javax.inject.Inject

@AndroidEntryPoint
class SearchDogsFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var searchBinding: FragmentSearchDogsBinding

    @Inject
    lateinit var breedViewModelFactory: BreedViewModelFactory

    @Inject
    lateinit var breedsAdapter: BreedSearchAdapter

    lateinit var breedViewModel: BreedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        breedViewModel = ViewModelProvider(this, breedViewModelFactory)[BreedViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        searchBinding = FragmentSearchDogsBinding.inflate(inflater, container, false)
        searchBinding.lifecycleOwner = this
        setupRecyclerView()
        setHasOptionsMenu(true)
        return searchBinding.root
    }

    private fun setupRecyclerView() {
        searchBinding.recyclerViewSearchFragment.apply {
            setHasFixedSize(true)
            itemAnimator = SlideInRightAnimator(OvershootInterpolator(2f))
            adapter = breedsAdapter
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchQueryByRequest(query)
        }

        hideKeyboard()
        return true
    }

    private fun searchQueryByRequest(query: String) {
        breedViewModel.getBreedsBySearch(query).observe(viewLifecycleOwner, { response ->

            breedsAdapter.setData(response)


        })
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

}