package br.com.raveline.testinghealth.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.raveline.testinghealth.data.model.BreedsItem
import br.com.raveline.testinghealth.databinding.ItemBreedAdapterBinding
import br.com.raveline.testinghealth.presentation.fragment.DetailsFragmentDirections
import br.com.raveline.testinghealth.presentation.fragment.DogsHomeFragmentDirections
import br.com.raveline.testinghealth.utils.DogsDiffUtil
import com.bumptech.glide.Glide

class BreedsAdapter : RecyclerView.Adapter<BreedsAdapter.MyViewHolder>() {

    private var breedsList = emptyList<BreedsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemBreedAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val breed = breedsList[position]
        holder.bind(breed)
    }

    override fun getItemCount(): Int = breedsList.size

    class MyViewHolder(private val binding: ItemBreedAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(breed: BreedsItem) {
            val uriImage = Uri.parse(breed.image?.url)
            Glide.with(binding.root).load(uriImage).centerCrop().into(binding.ivBreedAdapter)
            binding.tvNameBreedAdapter.text = breed.name
            binding.cLayoutBreedAdapter.setOnClickListener {
                try{
                    val action =
                        DogsHomeFragmentDirections.actionDogsHomeFragmentToDetailsFragment(breed,null)
                    binding.cLayoutBreedAdapter.findNavController().navigate(action)
                }catch (e:Exception){
                    e.message
                }
            }
        }
    }

    fun setData(breeds: List<BreedsItem>) {
        val breedsDiffUtil = DogsDiffUtil(breedsList, breeds)
        val diffUtilResult = DiffUtil.calculateDiff(breedsDiffUtil)
        breedsList = breeds
        diffUtilResult.dispatchUpdatesTo(this)

    }
}