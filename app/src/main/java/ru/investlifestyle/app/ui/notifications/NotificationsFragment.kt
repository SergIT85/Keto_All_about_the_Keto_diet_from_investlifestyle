package ru.investlifestyle.app.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import javax.inject.Inject
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentNotificationsBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest
import ru.investlifestyle.app.ui.notifications.adapter.NotificationAdapter

@androidx.paging.ExperimentalPagingApi
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val component by lazy {
        (requireActivity().application as App).daggerAppComponent
    }
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelFactoryTest
    private lateinit var viewModel: NotificationsViewModel
    lateinit var notificationAdapter: NotificationAdapter

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(NotificationsViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingAdapter()
        observeStateScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingAdapter() {
        notificationAdapter = NotificationAdapter(requireContext())
        binding.rvNotificationLikePost.adapter = notificationAdapter
    }

    // ///// дальше доделать эту функцию!
    private fun observeStateScreen() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.notificationState.collect { state ->
                    when (state) {
                        is NotificationState.Load -> {
                            isShimmer(true)
                        }
                        is NotificationState.LoadedSavePostsAndUserName -> {
                            binding.requestNameCabinet.visibility = ViewGroup.GONE
                            isShimmer(false)
                            notificationAdapter.submitList(state.postList)
                        }
                        is NotificationState.LoadedUserNameOnly -> {
                            isShimmer(false)
                        }
                        is NotificationState.LoadedSavePostsOnly -> {
                            saveUserName()
                            isShimmer(false)
                            notificationAdapter.submitList(state.postList)
                        }

                        NotificationState.UserNameAndSavePostIsEmpty -> {
                            saveUserName()
                            isShimmer(false)
                            viewModel.initialNotificationScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initialNotificationScreen()
    }

    private fun isShimmer(isSimmer: Boolean) {
        binding.ntfDetailShimmerLayout.isVisible = isSimmer
        binding.rvNotificationLikePost.isVisible = !isSimmer
        binding.linerNotification.isVisible = !isSimmer
    }

    private fun saveUserName() {
        binding.btnSaveName.setOnClickListener {
            if (binding.edTName.text.isNotEmpty()) {
                viewModel.saveUserName(binding.edTName.text.toString())
                viewModel.initialNotificationScreen()
            } else {
                Toast.makeText(context, "Введите имя", Toast.LENGTH_LONG).show()
            }
        }
    }
}