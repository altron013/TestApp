package com.example.project1.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project1.R
import com.example.project1.base.ParentViewHolder
import com.example.project1.data.callbacks.ItemClickListener
import com.example.project1.data.models.*


class MainAdapter(private val itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<ParentViewHolder>() {

    private val VIEW_TYPE_ERROR = 0
    private val VIEW_TYPE_PRODUCT = 1
    private val VIEW_TYPE_SERVICES = 2
    private val VIEW_TYPE_PROMOTION = 3

    private val VIEW_TYPE_SUBCATEGORY = 4
    private val VIEW_TYPE_CATEGORY = 5

    private val allServices = ArrayList<Any>()


    fun clearAll() {
        allServices.clear()
        notifyDataSetChanged()
    }


    fun addItems(list: List<Any>) {
        allServices.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_PRODUCT -> ProductsViewHolder(
                inflater.inflate(R.layout.adapter_product, parent, false)
            )
            VIEW_TYPE_SERVICES -> ServicesViewHolder(
                inflater.inflate(R.layout.adapter_services, parent, false)
            )
            VIEW_TYPE_PROMOTION -> PromotionsViewHolder(
                inflater.inflate(R.layout.adapter_promotion, parent, false)
            )
            VIEW_TYPE_SUBCATEGORY -> CategoriesViewHolder(
                inflater.inflate(R.layout.adapter_market, parent, false)
            )
            VIEW_TYPE_CATEGORY -> MainCategoriesViewHolder(
                inflater.inflate(R.layout.adapter_subcat, parent, false)
            )
            else -> throw Throwable("invalid view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Product -> {
                return VIEW_TYPE_PRODUCT
            }
            is Service -> {
                return VIEW_TYPE_SERVICES
            }
            is Promotion -> {
                return VIEW_TYPE_PROMOTION
            }
            is Subcategory -> {
                return VIEW_TYPE_SUBCATEGORY
            }
            is CustomMainCategory -> {
                return VIEW_TYPE_CATEGORY
            }
            else -> {
                return VIEW_TYPE_ERROR
            }
        }
    }

    override fun getItemCount(): Int = allServices.size

    fun getItem(position: Int): Any? {
        return allServices[position]
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        when (holder) {
            is ProductsViewHolder -> {
                val item = allServices[position] as Product
                holder.bind(item)
                holder.setItemClick(item)
                holder.setMenuClick(item)
            }

            is ServicesViewHolder -> {
                val item = allServices[position] as Service
                holder.bind(item)
                holder.setItemClick(item)
                holder.setMenuClick(item)
            }

            is PromotionsViewHolder -> {
                val item = allServices[position] as Promotion
                holder.bind(item)
                holder.setItemClick(item)
                holder.setMenuClick(item)
            }

            is CategoriesViewHolder -> {
                val item = allServices[position] as Subcategory
                holder.bind(item)
                holder.setItemClick(item)
                holder.setMenuClick(item)
            }

            is MainCategoriesViewHolder -> {
                val item = allServices[position] as CustomMainCategory
                holder.bind(item)
                holder.setItemClick(item)
                holder.setMenuClick(item)
            }

        }
    }

    inner class MainCategoriesViewHolder(view: View) : ParentViewHolder(view) {
        private val rvCats: RecyclerView = view.findViewById(R.id.rvSmart1)

        fun bind(customMainCategory: CustomMainCategory) {
            val productsAdapter by lazy {
                MainAdapter(itemClickListener = itemClickListener)
            }
            val gridLayoutManager2 = GridLayoutManager(itemView.context, 3)

            rvCats.adapter = productsAdapter
            rvCats.layoutManager = gridLayoutManager2

            productsAdapter.addItems(customMainCategory.cats)
        }

        fun setItemClick(customMainCategory: CustomMainCategory) {
//            cbState.setOnClickListener {
//                itemClickListener.onItemClick(item)
//            }
        }

        fun setMenuClick(customMainCategory: CustomMainCategory) {
//            vDots.setOnClickListener {
//                itemClickListener.onItemMenuClick(item)
//            }
        }

        override fun clear() {}
    }

    inner class CategoriesViewHolder(view: View) : ParentViewHolder(view) {
        private val ivLogo: ImageView = view.findViewById(R.id.iv_market_logo)
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)

        fun bind(subcategory: Subcategory) {
            Glide.with(itemView.context)
                .load(subcategory.image_path)
                .into(ivLogo)

            tvTitle.text = subcategory.title
        }

        fun setItemClick(subcategory: Subcategory) {
//            cbState.setOnClickListener {
//                itemClickListener.onItemClick(item)
//            }
        }

        fun setMenuClick(subcategory: Subcategory) {
//            vDots.setOnClickListener {
//                itemClickListener.onItemMenuClick(item)
//            }
        }

        override fun clear() {}
    }

    inner class PromotionsViewHolder(view: View) : ParentViewHolder(view) {
        private val ivLogo: ImageView = view.findViewById(R.id.iv_promotion_logo)

        fun bind(promotion: Promotion) {
            Glide.with(itemView.context)
                .load(promotion.image)
                .into(ivLogo)
        }

        fun setItemClick(promotion: Promotion) {
//            cbState.setOnClickListener {
//                itemClickListener.onItemClick(item)
//            }
        }

        fun setMenuClick(promotion: Promotion) {
//            vDots.setOnClickListener {
//                itemClickListener.onItemMenuClick(item)
//            }
        }

        override fun clear() {}
    }

    inner class ServicesViewHolder(view: View) : ParentViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val ivLogo: ImageView = view.findViewById(R.id.iv_services_logo)

        fun bind(service: Service) {

            ivLogo.setImageResource(R.drawable.ic_product_kasko)
            ivLogo.setImageResource(
                when (service.type) {
                    ActionType.GARANT.id ->{
                        R.drawable.ic_garant_42px
                    }
                    ActionType.QR.id ->{
                        R.drawable.ic_qr_42px
                    }
                    ActionType.MARKET.id ->{
                        R.drawable.ic_mart_42px
                    }
                    ActionType.INVEST.id ->{
                        R.drawable.ic_invest_42px
                    }
                    ActionType.JUNIOR.id ->{
                        R.drawable.ic_junior_42px
                    }
                    ActionType.TICKETS.id ->{
                        R.drawable.ic_ticket_42px
                    }
                    ActionType.MOBILE.id ->{
                        R.drawable.ic_j_mobile_42px
                    }
                    ActionType.GOV.id ->{
                        R.drawable.ic_gov_42px
                    }
                    else ->{
                        R.drawable.ic_product_kasko
                    }
                }
            )
            tvTitle.text = service.title
        }

        fun setItemClick(service: Service) {
//            cbState.setOnClickListener {
//                itemClickListener.onItemClick(item)
//            }
        }

        fun setMenuClick(service: Service) {
//            vDots.setOnClickListener {
//                itemClickListener.onItemMenuClick(item)
//            }
        }

        override fun clear() {}
    }

    inner class ProductsViewHolder(view: View) : ParentViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val ivLogo: ImageView = view.findViewById(R.id.iv_product_logo)

        fun bind(product: Product) {
            Glide.with(itemView.context)
                .load(product.preview)
                .into(ivLogo)
            tvTitle.text = product.title
        }

        fun setItemClick(product: Product) {
//            cbState.setOnClickListener {
//                itemClickListener.onItemClick(item)
//            }
        }

        fun setMenuClick(product: Product) {
//            vDots.setOnClickListener {
//                itemClickListener.onItemMenuClick(item)
//            }
        }

        override fun clear() {}
    }

}