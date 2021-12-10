package br.com.raveline.testinghealth.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.raveline.testinghealth.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var detailsBinding: FragmentDetailsBinding

    private val argsBreed by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        detailsBinding.lifecycleOwner = this

        setupDisplayData()

        return detailsBinding.root
    }

    private fun setupDisplayData() {
        try {
            if (argsBreed.breeds != null) {
                val breed = argsBreed.breeds
                detailsBinding.tvNameDetailFragment.text = breed?.name
                if (breed?.origin == null) {
                    detailsBinding.tvOriginDetailFragment.text = "Origin Unknown"
                } else {
                    detailsBinding.tvOriginDetailFragment.text = breed.origin
                }

                if (breed?.temperament == null) {
                    detailsBinding.tvTemperamentDetailsFragment.text = "Temperament Unknown"
                } else {
                    detailsBinding.tvTemperamentDetailsFragment.text = breed.temperament
                }

            } else if (argsBreed.breedsSearch != null) {
                val breed = argsBreed.breedsSearch
                detailsBinding.tvNameDetailFragment.text = breed?.name

                if (breed?.origin == null) {
                    detailsBinding.tvOriginDetailFragment.text = "Origin Unknown"
                } else {
                    detailsBinding.tvOriginDetailFragment.text = breed.origin
                }

                if (breed?.temperament == null) {
                    detailsBinding.tvTemperamentDetailsFragment.text = "Temperament Unknown"
                } else {
                    detailsBinding.tvTemperamentDetailsFragment.text = breed.temperament
                }
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Some information are not here. Sorry!", Toast.LENGTH_SHORT)
                .show()
        }
    }

}