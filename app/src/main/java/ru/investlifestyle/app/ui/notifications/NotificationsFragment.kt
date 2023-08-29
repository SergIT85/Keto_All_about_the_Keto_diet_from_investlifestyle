package ru.investlifestyle.app.ui.notifications

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import ru.investlifestyle.app.App
import ru.investlifestyle.app.databinding.FragmentNotificationsBinding
import ru.investlifestyle.app.ui.ViewModelFactoryTest

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

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(NotificationsViewModel::class.java)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}