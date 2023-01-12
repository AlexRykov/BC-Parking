package com.example.bc_parking.presentation


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bc_parking.R
import com.example.bc_parking.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ParkItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var parkListAdapter: ParkListAdapter
    private var parkItemContainer: FragmentContainerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        parkItemContainer = findViewById(R.id.p_item_container_item_container)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.parkList.observe(this) {
            parkListAdapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            if (isStraightMode()) {
                val intent = ParkItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else launchFragment(ParkItemFragment.newInstanceAddItem())
        }
    }


    override fun onEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }


    private fun isStraightMode(): Boolean{
        return parkItemContainer == null
    }

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.p_item_container_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val rvParkList = findViewById<RecyclerView>(R.id.rv_park_list)
        with(rvParkList) {
            parkListAdapter = ParkListAdapter()
            adapter = parkListAdapter
            recycledViewPool.setMaxRecycledViews(
                ParkListAdapter.VIEW_TYPE_ENABLED,
                ParkListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ParkListAdapter.VIEW_TYPE_DISABLED,
                ParkListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickListener()
        setupClickListener()
        setupSwipeListener(rvParkList)
    }

    private fun setupSwipeListener(rvParkList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = parkListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteParkItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvParkList)
    }

    private fun setupClickListener() {
        parkListAdapter.onShopItemClickListener = {
            if(isStraightMode()) {
                val intent = ParkItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            } else launchFragment(ParkItemFragment.newInstanceEditItem(it.id))
        }
    }

    private fun setupLongClickListener() {
        parkListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}