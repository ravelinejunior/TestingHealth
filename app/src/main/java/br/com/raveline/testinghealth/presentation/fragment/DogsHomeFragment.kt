package br.com.raveline.testinghealth.presentation.fragment

import android.os.Bundle
import android.view.*
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.testinghealth.R
import br.com.raveline.testinghealth.data.model.BreedsItem
import br.com.raveline.testinghealth.databinding.FragmentDogsHomeBinding
import br.com.raveline.testinghealth.presentation.adapter.BreedsAdapter
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModel
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_order, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuOrderId -> breedViewModel.getOrderedBreed()
                .observe(viewLifecycleOwner, { items ->
                    breedsAdapter.setData(items)
                    dogsBinding.recyclerViewFragmentsHome.scheduleLayoutAnimation()
                })
        }

        return super.onOptionsItemSelected(item)
    }


}