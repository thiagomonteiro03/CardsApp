package com.thiagomonteiro.cards.presentation.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thiagomonteiro.cards.databinding.FragmentCardsBinding
import com.thiagomonteiro.cards.framework.imageloader.ImageLoader
import com.thiagomonteiro.cards.presentation.common.getGenericAdapterOf
import com.thiagomonteiro.cards.presentation.enums.CardSetType
import com.thiagomonteiro.cards.presentation.extensions.toCardItemList
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CardsFragment : Fragment() {

    @Inject
    lateinit var imageLoader: ImageLoader

    private var _binding: FragmentCardsBinding? = null
    private val binding: FragmentCardsBinding get() = _binding!!

    private val viewModel: CardsViewModel by viewModels()

    private val cardsAdapter by lazy {
        getGenericAdapterOf {
            CardsViewHolder.create(it, imageLoader)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCardsBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCardsAdapter()

        setupButtonListeners()

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperCards.displayedChild = when (uiState){
                is CardsViewModel.UiState.Loading -> {
                    setShimmerVisibility(true)
                    FLIPPER_CHILD_LOADING
                }
                is CardsViewModel.UiState.Success -> {
                    cardsAdapter.submitList(uiState.cards.toCardItemList())
                    setShimmerVisibility(false)
                    FLIPPER_CHILD_CARDS
                }
                is CardsViewModel.UiState.Error -> {
                    setShimmerVisibility(false)
                    binding.includeViewCardsErrorState.buttonRetry.setOnClickListener {
                        viewModel.getCardsBySet(CardSetType.CLASSIC_CARD_SET.queryParameter)
                    }
                    FLIPPER_CHILD_ERROR
                }
                else -> { FLIPPER_CHILD_ERROR }
            }
        }

        // Loading one random cardSet because this feature is not yet implemented
        viewModel.getCardsBySet(CardSetType.CLASSIC_CARD_SET.queryParameter)
    }

    private fun setupButtonListeners() {
        with(binding){
            CardSetType.BASIC_CARD_SET.let { type ->
                buttonCardSetBasic.text = getString(type.label)
                buttonCardSetBasic.setOnClickListener {
                    viewModel.getCardsBySet(type.queryParameter)
                }
            }

            CardSetType.CLASSIC_CARD_SET.let { type ->
                buttonCardSetClassic.text = getString(type.label)
                buttonCardSetClassic.setOnClickListener {
                    viewModel.getCardsBySet(type.queryParameter)
                }
            }

            CardSetType.NAXXRAMAS_CARD_SET.let { type ->
                buttonCardSetNaxxramas.text = getString(type.label)
                buttonCardSetNaxxramas.setOnClickListener {
                    viewModel.getCardsBySet(type.queryParameter)
                }
            }
        }
    }

    private fun initCardsAdapter() {
        binding.recyclerCards.run {
            setHasFixedSize(true)
            adapter = cardsAdapter
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewCardsLoadingState.shimmerCards.run {
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
        private const val FLIPPER_CHILD_CARDS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }

}