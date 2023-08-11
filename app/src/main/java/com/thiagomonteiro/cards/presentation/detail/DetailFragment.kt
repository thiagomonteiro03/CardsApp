package com.thiagomonteiro.cards.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.thiagomonteiro.cards.databinding.FragmentDetailBinding
import com.thiagomonteiro.cards.framework.imageloader.ImageLoader
import com.thiagomonteiro.cards.presentation.common.ArgsConstants
import com.thiagomonteiro.core.domain.model.Card
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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

        loadImageCard(detailViewArg)
        setCurrentCardSet(detailViewArg.cardSet)
        observeDetailUiState(detailViewArg)

        viewModel.loadData(detailViewArg.cardId)
    }

    private fun loadImageCard(detailViewArg: DetailViewArg) {
        binding.imageCard.run {
            imageLoader.load(this, detailViewArg.imageUrl)
        }
    }

    private fun setCurrentCardSet(cardSet: String) {
        val result = Bundle().apply {
            putString(ArgsConstants.CURRENT_CARD_SET_PARAMETER, cardSet)
        }
        findNavController().previousBackStackEntry?.savedStateHandle
            ?.set(ArgsConstants.CURRENT_CARD_SET_PARAMETER, result)
    }

    private fun observeDetailUiState(detailViewArg: DetailViewArg) {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperDetail.displayedChild = when (uiState) {
                is DetailViewModel.DetailUiState.Loading -> {
                    setShimmerVisibility(true)
                    FLIPPER_CHILD_LOADING
                }

                is DetailViewModel.DetailUiState.Success -> {
                    setsViewData(uiState.card)

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
    }

    private fun setsViewData(card: Card) {
        with(binding) {
            tvSetCard.text = card.cardSet
            tvType.text = card.type
            tvFaction.text = card.faction ?: " - "
            tvRarity.text = card.rarity ?: " - "
            tvAttack.text = card.attack?.toString() ?: " - "
            tvCost.text = card.cost?.toString() ?: " - "
            tvHealth.text = card.health?.toString() ?: " - "
            tvShortDescription.text = card.shortDescription ?: " - "
            tvFlavorDescription.text = card.flavorDescription ?: " - "
        }
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