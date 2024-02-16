package com.uogames.innowise.ui.fragmentBottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joeiot.spannedgridlayoutmanager.SpanSize
import com.joeiot.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.uogames.innowise.R
import com.uogames.innowise.databinding.FragmentBottomNavigationBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BottomFragment : Fragment() {

	private var _bind: FragmentBottomNavigationBinding? = null
	private val bind get() = _bind!!

	private val vm by lazy { ViewModelProvider(requireActivity())[BottomViewModel::class.java] }

	private var observer: Job? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		_bind = FragmentBottomNavigationBinding.inflate(inflater, container, false)
		return _bind?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		bind.rvAdapter.adapter = vm.adapterFirst
		bind.bottomNavigation.setOnItemSelectedListener { vm.selectedTab.tryEmit(it.itemId) }
		bind.srlRefresh.setOnRefreshListener {
			if (bind.rvAdapter.adapter == vm.adapterFive) vm.refresh()
			else bind.srlRefresh.isRefreshing = false
		}
		bind.fabClear.setOnClickListener { vm.clear() }
	}

	override fun onStart() {
		super.onStart()
		observer = lifecycleScope.launch {
			launch {
				vm.event.collect {
					loadAdapter(it.selectedTab)
					bind.cpiLoading.visibility = if (it.isLoading && it.isFirstLoad && it.selectedTab == R.id.loading) View.VISIBLE else View.GONE
					if (!it.isLoading && !it.isFirstLoad) bind.srlRefresh.isRefreshing = false
					bind.fabClear.visibility = if (it.size != 0 && it.selectedTab == R.id.loading) View.VISIBLE else View.GONE
					bind.tvEmptyList.visibility = if (it.size == 0 && it.selectedTab == R.id.loading) View.VISIBLE else View.GONE
				}
			}
		}
	}

	private fun loadAdapter(tabID: Int?) {
		when (tabID) {
			R.id.users_first -> {
				bind.rvAdapter.adapter = vm.adapterFirst
				bind.rvAdapter.layoutManager = GridLayoutManager(requireContext(), 1)
			}

			R.id.users_second -> {
				bind.rvAdapter.adapter = vm.adapterSecond
				bind.rvAdapter.layoutManager = GridLayoutManager(requireContext(), 1)
			}

			R.id.roles -> {
				bind.rvAdapter.adapter = vm.roleAdapter
				bind.rvAdapter.layoutManager = GridLayoutManager(requireContext(), 2)
			}

			R.id.asym -> {
				val lm = SpannedGridLayoutManager(requireContext(), RecyclerView.VERTICAL,  3,1/3f)
				lm.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
					when (position % 8) {
						0, 5 -> SpanSize(2, 2)
						3, 7 -> SpanSize(3, 2)
						else -> SpanSize(1, 1)
					}
				}
				bind.rvAdapter.layoutManager = lm
				bind.rvAdapter.adapter = vm.asymAdapter
			}

			R.id.loading -> {
				bind.rvAdapter.layoutManager = GridLayoutManager(requireContext(), 1)
				bind.rvAdapter.adapter = vm.adapterFive
				vm.load()
			}

			else -> {}
		}
	}

	override fun onStop() {
		super.onStop()
		observer?.cancel()
	}


	override fun onDestroyView() {
		super.onDestroyView()
		_bind?.rvAdapter?.adapter = null
		_bind = null
	}


}