package com.thiagomonteiro.cards.presentation.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thiagomonteiro.cards.databinding.FragmentCardsBinding
import com.thiagomonteiro.cards.presentation.common.getGenericAdapterOf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : Fragment() {

    private var _binding: FragmentCardsBinding? = null
    private val binding: FragmentCardsBinding get() = _binding!!

    private val viewModel: CardsViewModel by viewModels()

    private val cardsAdapter by lazy {
        getGenericAdapterOf {
            CardsViewHolder.create(it)
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

        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.flipperCards.displayedChild = when (uiState){
                is CardsViewModel.UiState.Loading -> {
                    setShimmerVisibility(true)
                    FLIPPER_CHILD_LOADING
                }
                is CardsViewModel.UiState.Success -> {
                    cardsAdapter.submitList(uiState.cardSet.basic.map {
                        CardItem(it.id.toInt(), it.name)
                    })
                    setShimmerVisibility(false)
                    FLIPPER_CHILD_CHARACTERS
                }
                is CardsViewModel.UiState.Error -> {
                    setShimmerVisibility(false)
                    binding.includeViewCardsErrorState.buttonRetry.setOnClickListener {
                        viewModel.getAllCards()
                    }
                    FLIPPER_CHILD_ERROR
                }
                else -> { FLIPPER_CHILD_ERROR }
            }
        }

        viewModel.getAllCards()
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
        private const val FLIPPER_CHILD_CHARACTERS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }

}