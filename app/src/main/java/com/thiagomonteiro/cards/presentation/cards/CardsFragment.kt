package com.thiagomonteiro.cards.presentation.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun initCardsAdapter() {
        binding.recyclerCards.run {
            setHasFixedSize(true)
            adapter = cardsAdapter
        }
    }

}