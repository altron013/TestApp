package com.example.project1.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.project1.additonal.launchSafe
import com.example.project1.base.ParentViewModel
import com.example.project1.data.callbacks.ItemClickListener
import com.example.project1.data.models.*
import com.example.project1.network.repository.FeedsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainViewModel(app: Application, private val feedsRepository: FeedsRepository) :
    ParentViewModel() {

    private val _productsList = MutableLiveData<List<Any>>()
    val productsList: LiveData<List<Any>>
        get() = _productsList

    private val _servicesList = MutableLiveData<List<Any>>()
    val servicesList: LiveData<List<Any>>
        get() = _servicesList

    private val _promotionsList = MutableLiveData<List<Any>>()
    val promotionsList: LiveData<List<Any>>
        get() = _promotionsList

    private val _smartphonesList1 = MutableLiveData<List<Any>>()
    val smartphonesList1: LiveData<List<Any>>
        get() = _smartphonesList1

    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()

        data class Result(val feeds: Feeds?) : State()
        data class ResultCategories(val cats: List<Category>) : State()

        data class Error(val error: String?) : State()
        data class IntError(val error: Int) : State()
    }

    private val _liveDataFeeds = MutableLiveData<State>()
    val liveDataFeeds: LiveData<State>
        get() = _liveDataFeeds

    private val _liveDataCats = MutableLiveData<State>()
    val liveDataCats: LiveData<State>
        get() = _liveDataCats

    fun getFeeds() {
        _liveDataFeeds.value = State.ShowLoading

        uiScope.launchSafe(::handleError) {
            val result = withContext(Dispatchers.IO) {
                feedsRepository.getFeeds()
            }
            _liveDataFeeds.postValue(
                State.Result(result)
            )
        }
        _liveDataFeeds.value =
            State.HideLoading

    }

    fun getCategories() {
        _liveDataCats.value = State.ShowLoading

        uiScope.launchSafe(::handleError) {
            val result = withContext(Dispatchers.IO) {
                feedsRepository.getCategories()
            }
            _liveDataCats.postValue(
                result?.let { State.ResultCategories(it) }
            )
        }
        _liveDataCats.value =
            State.HideLoading

    }

    override fun handleError(e: Throwable) {

    }

    fun onSuccessFeeds(it: Feeds) {

        _productsList.postValue(it.products)
        _servicesList.postValue(it.services)
        _promotionsList.postValue(it.promotions)
    }

    fun onSuccessCats(list: List<Category>) {
        val listAll = mutableListOf<Any>()
        val tempImagePath = "https://jmart.kz/images/detailed/3828/main.png"


        list.forEach {
            val tempFirst = it
            val mainCategory = Subcategory(
                title = tempFirst.title,
                category_id = tempFirst.category_id,
                url = tempFirst.url,
                image_path = tempImagePath,
                position = "0"
            )
            val subList = mutableListOf<Subcategory>()
            subList.add(mainCategory)

            tempFirst.subcategories?.let {
                it.forEach { subCat ->
                    subList.add(subCat.copy(image_path = tempImagePath))
                }
            }
            val customCat = CustomMainCategory(cats = subList)

            listAll.add(customCat)
        }


        _smartphonesList1.postValue(listAll)
    }

    //listener
    val onItemClickListener = object :
        ItemClickListener {
        override fun onItemClick(item: Todo) {
//            updateTodo(item.copy(actionType = ActionType.UNKNOWN.id, completed = !item.completed))
        }

        override fun onItemMenuClick(item: Todo) {
//            when (item.actionType) {
//                ActionType.UNKNOWN.id -> {
//
//                }
//                ActionType.DELETE.id -> {
////                    deleteTodo(item.copy(actionType = ActionType.UNKNOWN.id))
//                }
//                ActionType.UPDATE.id -> {
////                    updateTodo(item.copy(actionType = ActionType.UNKNOWN.id))
//                }
//            }
        }

    }
}