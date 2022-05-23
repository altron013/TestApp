package com.example.project1.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.base.ParentFragment
import org.koin.android.ext.android.inject

class MainFragment : ParentFragment() {

    private lateinit var navController: NavController
    private val viewModel: MainViewModel by inject()

    private lateinit var rvProducts: RecyclerView
    private lateinit var rvServices: RecyclerView
    private lateinit var rvPromotions: RecyclerView


    private lateinit var rvSmart1: RecyclerView

    private val productsAdapter by lazy {
        MainAdapter(itemClickListener = viewModel.onItemClickListener)
    }
    private val servicesAdapter by lazy {
        MainAdapter(itemClickListener = viewModel.onItemClickListener)
    }
    private val promotionAdapter by lazy {
        MainAdapter(itemClickListener = viewModel.onItemClickListener)
    }

    private val smart1Adapter by lazy {
        MainAdapter(itemClickListener = viewModel.onItemClickListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setAdapter()
        setData()
    }

    private fun setAdapter() {
        rvProducts.adapter = productsAdapter
        rvServices.adapter = servicesAdapter
        rvPromotions.adapter = promotionAdapter

        rvSmart1.adapter = smart1Adapter
    }

    override fun bindViews(view: View) = with(view) {
        navController = Navigation.findNavController(this)

        rvProducts = findViewById(R.id.rvProducts)
        rvServices = findViewById(R.id.rvServices)
        rvPromotions = findViewById(R.id.rvPromotions)
        rvSmart1 = findViewById(R.id.rvSmart1)

        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager2 =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val linearLayoutManager3 =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val gridLayoutManager = GridLayoutManager(context, 4)

        rvProducts.layoutManager = linearLayoutManager
        rvServices.layoutManager = gridLayoutManager
        rvPromotions.layoutManager = linearLayoutManager2

        rvSmart1.layoutManager = linearLayoutManager3


    }

    override fun setData() {
        observeViewModel()

        viewModel.getFeeds()
        viewModel.getCategories()
    }

    private fun observeViewModel() {

        viewModel.productsList.observe(viewLifecycleOwner,
            Observer {
                productsAdapter.addItems(it)
            })

        viewModel.servicesList.observe(viewLifecycleOwner,
            Observer {
                servicesAdapter.addItems(it)
            })

        viewModel.promotionsList.observe(viewLifecycleOwner,
            Observer {
                promotionAdapter.addItems(it)
            })

        viewModel.smartphonesList1.observe(viewLifecycleOwner,
            Observer {
                smart1Adapter.addItems(it)
            })

        viewModel.liveDataFeeds.observe(
            viewLifecycleOwner,
            Observer { result ->
                when (result) {
                    is MainViewModel.State.Result -> {
                        result.feeds?.let { viewModel.onSuccessFeeds(it) }
                    }
                    is MainViewModel.State.Error -> {
                    }
                }
            }
        )

        viewModel.liveDataCats.observe(
            viewLifecycleOwner,
            Observer { result ->
                when (result) {
                    is MainViewModel.State.ResultCategories -> {
                        result.cats?.let { viewModel.onSuccessCats(it) }
                    }
                    is MainViewModel.State.Error -> {
                    }
                }
            }
        )
    }

}