package br.com.raveline.testinghealth.presentation.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.testinghealth.data.model.BreedsItem
import br.com.raveline.testinghealth.databinding.FragmentDogsHomeBinding
import br.com.raveline.testinghealth.presentation.adapter.BreedsAdapter
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModel
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import javax.inject.Inject

@AndroidEntryPoint
class DogsHomeFragment : Fragment() {

    private lateinit var dogsBinding: FragmentDogsHomeBinding

    @Inject
    lateinit var breedViewModelFactory: BreedViewModelFactory

    @Inject
    lateinit var breedsAdapter: BreedsAdapter

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
        dogsBinding = FragmentDogsHomeBinding.inflate(inflater, container, false)

        getDogsBreed()


        return dogsBinding.root
    }

    private fun getDogsBreed() {
        breedViewModel.getBreeds().observe(viewLifecycleOwner, { breeds ->
            if (breeds != null) {
               setupRecyclerView(breeds)
            }
        })
    }

    private fun setupRecyclerView(breedsList: List<BreedsItem>) {
        dogsBinding.recyclerViewFragmentsHome.apply {
            setHasFixedSize(true)
            itemAnimator = SlideInLeftAnimator(OvershootInterpolator(10f))
            breedsAdapter.setData(breedsList)
            adapter = breedsAdapter
        }
    }


}