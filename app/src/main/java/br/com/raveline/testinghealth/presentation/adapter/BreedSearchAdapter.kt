package br.com.raveline.testinghealth.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.databinding.ItemBreedSearchAdapterBinding
import br.com.raveline.testinghealth.presentation.fragment.SearchDogsFragmentDirections
import br.com.raveline.testinghealth.utils.DogsDiffUtil

class BreedSearchAdapter : RecyclerView.Adapter<BreedSearchAdapter.MyViewHolder>() {

    private var breedSearchList = emptyList<BreedBySearchItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemBreedSearchAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val breed = breedSearchList[position]
        holder.bind(breed)
    }

    override fun getItemCount(): Int = breedSearchList.size

    class MyViewHolder(private val binding: ItemBreedSearchAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(breed: BreedBySearchItem) {
            binding.tvNameBreedSearchAdapter.text = breed.name
            binding.tvOriginBreedSearchAdapter.text = breed.origin
            binding.tvGroupBreedSearchAdapter.text = breed.breedGroup
            binding.cLayoutBreedAdapter.setOnClickListener {
                val action =
                    SearchDogsFragmentDirections.actionSearchDogsFragmentToDetailsFragment(null,breed)
                binding.cLayoutBreedAdapter.findNavController().navigate(action)
            }
        }
    }

    fun setData(breeds: List<BreedBySearchItem>) {
        val breedsDiffUtil = DogsDiffUtil(breedSearchList, breeds)
        val diffUtilResult = DiffUtil.calculateDiff(breedsDiffUtil)
        breedSearchList = breeds
        diffUtilResult.dispatchUpdatesTo(this)

    }
}