package com.thiagomonteiro.cards.presentation.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.thiagomonteiro.cards.databinding.FragmentCardsBinding
import com.thiagomonteiro.cards.framework.imageloader.ImageLoader
import com.thiagomonteiro.cards.presentation.common.ArgsConstants
import com.thiagomonteiro.cards.presentation.common.getGenericAdapterOf
import com.thiagomonteiro.cards.presentation.detail.DetailViewArg
import com.thiagomonteiro.cards.presentation.enums.CardSetType
import com.thiagomonteiro.cards.presentation.extensions.toCardItemList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
            CardsViewHolder.create(it, imageLoader) { card ->

                val directions = CardsFragmentDirections
                    .actionCardsFragmentToDetailFragment(
                        card.name,
                        DetailViewArg(
                            card.cardId,
                            card.name,
                            card.cardSet,
                            card.image?: ""
                        )
                    )

                findNavController().navigate(directions)
            }
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
        observeUiState()

        val cardSet = getCurrentCardSet().ifEmpty {
            CardSetType.BASIC_CARD_SET.queryParameter
        }
        viewModel.getCardsBySet(cardSet)
    }

    private fun initCardsAdapter() {
        binding.recyclerCards.run {
            setHasFixedSize(true)
            adapter = cardsAdapter
        }
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

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperCards.displayedChild = when (uiState) {
                is CardsViewModel.UiState.Loading -> {
                    setShimmerVisibility(true)
                    FLIPPER_CHILD_LOADING
                }

                is CardsViewModel.UiState.Success -> {
                    lifecycleScope.launch {
                        cardsAdapter.submitList(uiState.cards.toCardItemList())
                    }
                    setShimmerVisibility(false)
                    FLIPPER_CHILD_CARDS
                }

                is CardsViewModel.UiState.Empty -> {
                    setShimmerVisibility(false)
                    FLIPPER_CHILD_EMPTY
                }

                is CardsViewModel.UiState.Error -> {
                    setShimmerVisibility(false)
                    binding.includeViewCardsErrorState.buttonRetry.setOnClickListener {
                        viewModel.getCardsBySet(CardSetType.CLASSIC_CARD_SET.queryParameter)
                    }
                    FLIPPER_CHILD_ERROR
                }

                else -> {
                    FLIPPER_CHILD_ERROR
                }
            }
        }
    }

    private fun getCurrentCardSet(): String {
        var currentCardSet = ""

        val currentCardSetParameter = ArgsConstants.CURRENT_CARD_SET_PARAMETER
        val result = findNavController().currentBackStackEntry?.savedStateHandle?.get<Bundle>(
            currentCardSetParameter
        )
        if (result != null) {
            val cardSet = result.getString(currentCardSetParameter)
            cardSet?.let {
                currentCardSet = it
            }
        }
        return currentCardSet
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
        private const val FLIPPER_CHILD_EMPTY = 2
        private const val FLIPPER_CHILD_ERROR = 3
    }

}