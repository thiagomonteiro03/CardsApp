package com.thiagomonteiro.cards.usecase

import com.nhaarman.mockitokotlin2.whenever
import com.thiagomonteiro.core.data.repository.CardsRepository
import com.thiagomonteiro.core.usecase.GetCardByIdUseCase
import com.thiagomonteiro.core.usecase.GetCardByIdUseCaseImpl
import com.thiagomonteiro.core.usecase.base.ResultStatus
import com.thiagomonteiro.testing.MainCoroutineRule
import com.thiagomonteiro.testing.model.CardFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetCardsByIdUseCaseImplTest {

    @get:Rule
    val mainCoroutinesRule = MainCoroutineRule()

    @Mock
    lateinit var repository: CardsRepository

    private lateinit var  getCardByIdUseCase: GetCardByIdUseCase

    private val card = CardFactory().createBasic(CardFactory.Basic.InnerRage)

    @Before
    fun setUp(){
        getCardByIdUseCase = GetCardByIdUseCaseImpl(
            repository,
            mainCoroutinesRule.testDispatcherProvider
        )
    }

    @Test
    fun `should return Success from ResultStatus when get card request return success`() =
        runTest {
            // Arrange
            whenever(repository.getCardById(card.id)).thenReturn(card)

            // Act
            val result = getCardByIdUseCase
                .invoke(GetCardByIdUseCase.GetCardByIdParams(card.id))

            // Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            Assert.assertTrue(resultList[1] is ResultStatus.Success)

        }

    @Test
    fun `should return Error from ResultStatus when get card request returns error`() =
        runTest {
            // Arrange
            whenever(repository.getCardById(card.id)).thenAnswer { throw Throwable() }

            // Act
            val result = getCardByIdUseCase
                .invoke(GetCardByIdUseCase.GetCardByIdParams(card.id))

            // Assert
            val resultList = result.toList()
            assertEquals(ResultStatus.Loading, resultList[0])
            Assert.assertTrue(resultList[1] is ResultStatus.Error)
        }
}