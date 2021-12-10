package br.com.raveline.testinghealth.presentation.fragment

import android.os.Bundle
import android.view.*
import android.view.animation.OvershootInterpolator
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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

    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var page = 1
    private var pages = 0

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
        breedViewModel.getBreeds(0).observe(viewLifecycleOwner, { breeds ->
            if (breeds != null) {
                breedsAdapter.setData(breeds)
            }

            isLastPage = page == pages
        })

        setupRecyclerView()
    }

    private fun setupRecyclerView(
        layoutManager: StaggeredGridLayoutManager? = null
    ) {
        if(layoutManager != null){
            dogsBinding.recyclerViewFragmentsHome.apply {
                setHasFixedSize(true)
                itemAnimator = SlideInLeftAnimator(OvershootInterpolator(10f))
                setLayoutManager(layoutManager)
                adapter = breedsAdapter
            }
        }else {
            dogsBinding.recyclerViewFragmentsHome.apply {
                setHasFixedSize(true)
                itemAnimator = SlideInLeftAnimator(OvershootInterpolator(10f))
                adapter = breedsAdapter
                addOnScrollListener(this@DogsHomeFragment.onScrollListener)
            }
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

            R.id.menuLayoutId -> {
                setupRecyclerView(
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                )

                breedViewModel.isStaggered.value = true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            //verificar se scroll do recyclerView foi tocado
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
           try{
               if(!breedViewModel.isStaggered.value){
                   val layoutManager =
                       dogsBinding.recyclerViewFragmentsHome.layoutManager as LinearLayoutManager
                   val sizeOfCurrentList = layoutManager.itemCount
                   val visibleItems = layoutManager.childCount
                   //recuperar a position do primeiro item que aparece apos ser scrollado
                   val topPosition = layoutManager.findFirstVisibleItemPosition()
                   val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
                   val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

                   if (shouldPaginate) {
                       page++
                       breedViewModel.getBreeds(page)
                       isScrolling = false
                   }
               }else{
                   val layoutManager =
                       dogsBinding.recyclerViewFragmentsHome.layoutManager as StaggeredGridLayoutManager
                   val sizeOfCurrentList = layoutManager.itemCount
                   val visibleItems = layoutManager.childCount
                   //recuperar a position do primeiro item que aparece apos ser scrollado
                   val topPosition = layoutManager.itemCount
                   val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
                   val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

                   if (shouldPaginate) {
                       page++
                       breedViewModel.getBreeds(page)
                       isScrolling = false
                   }
               }
           }catch (e:Exception){
               val layoutManager =
                   dogsBinding.recyclerViewFragmentsHome.layoutManager as LinearLayoutManager
               val sizeOfCurrentList = layoutManager.itemCount
               val visibleItems = layoutManager.childCount
               //recuperar a position do primeiro item que aparece apos ser scrollado
               val topPosition = layoutManager.findFirstVisibleItemPosition()
               val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
               val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

               if (shouldPaginate) {
                   page++
                   breedViewModel.getBreeds(page)
                   isScrolling = false
               }

               e.printStackTrace()
           }

        }
    }


}