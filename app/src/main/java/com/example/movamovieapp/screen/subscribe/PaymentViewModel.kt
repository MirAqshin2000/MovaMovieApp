package com.example.movamovieapp.screen.subscribe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.R
import com.example.movamovieapp.di.MovieRepository
import com.example.movamovieapp.model.CardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {


    val cards = MutableLiveData<List<CardModel>>()


    fun getCards() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCards().collect {
                cards.postValue(it)

            }

        }

    }

    fun addCard(name: String, number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCard(
                CardModel(
                    cardName = name, cardNumber = number.toString(),
                    cardImage = R.drawable.mastercard,

                    selected = false,)
            )
            getCards()

    }

    }

    fun deleteCard(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCard(id)
            getCards()
        }

    }
}