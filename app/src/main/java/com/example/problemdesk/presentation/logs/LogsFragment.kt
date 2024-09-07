import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.databinding.FragmentLogsBinding
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.presentation.logs.LogRecyclerViewAdapter
import com.example.problemdesk.presentation.logs.LogsViewModel
import kotlinx.coroutines.launch

//TODO bind with navigation+requestId

class LogsFragment(private val requestId: Int) : Fragment() {
    private var _binding: FragmentLogsBinding? = null
    private val binding get() = _binding!!

    private val logsViewModel: LogsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        logsViewModel.cards.observe(viewLifecycleOwner, Observer { logs: List<RequestLog> ->
            (binding.logsRv.adapter as? LogRecyclerViewAdapter)?.logs = logs
        })

        lifecycleScope.launch {
            logsViewModel.loadLogs(requestId)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //::handleCardClick binding RV click logic with fragment
//        binding.logsRv.adapter = LogRecyclerViewAdapter(::handleCardClick)
        //TODO what to do with that
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}