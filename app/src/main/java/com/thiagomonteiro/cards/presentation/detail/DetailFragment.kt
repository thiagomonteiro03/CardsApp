package com.thiagomonteiro.cards.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.thiagomonteiro.cards.databinding.FragmentDetailBinding
import com.thiagomonteiro.cards.framework.imageloader.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.cos

@AndroidEntryPoint
class DetailFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: DetailViewModel by viewModels()

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArg = args.detailViewArg

        binding.imageCard.run {
            imageLoader.load(this, detailViewArg.imageUrl)
        }

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetail.displayedChild = when (uiState){
                is DetailViewModel.DetailUiState.Loading -> {
                    setShimmerVisibility(true)
                    FLIPPER_CHILD_LOADING
                }
                is DetailViewModel.DetailUiState.Success -> {
                    with(binding){
                        uiState.card.run {
                            tvSetCard.text = cardSet
                            tvType.text = type
                            tvFaction.text = faction
                            tvRarity.text = rarity
                            tvAttack.text = attack.toString()
                            tvCost.text = cost.toString()
                            tvHealth.text = health.toString()
                            tvShortDescription.text = shortDescription
                            tvFlavorDescription.text = flavorDescription

                        }
                    }

                    setShimmerVisibility(false)
                    FLIPPER_CHILD_SUCCESS
                }
                is DetailViewModel.DetailUiState.Error -> {
                    setShimmerVisibility(false)
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.loadData(detailViewArg.cardId)
                    }
                    FLIPPER_CHILD_ERROR
                }
                else -> {
                    FLIPPER_CHILD_ERROR
                }
            }
        }

        viewModel.loadData(detailViewArg.cardId)
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCardsLoadingState.shimmerCardDetail.run {
            isVisible = visibility
            if (visibility) {
                startShimmer()
            } else stopShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_SUCCESS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }

}