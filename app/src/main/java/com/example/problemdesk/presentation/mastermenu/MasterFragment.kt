package com.example.problemdesk.presentation.mastermenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.problemdesk.databinding.FragmentMasterBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.Card
import com.example.problemdesk.domain.OLDMODELSrefactor.Specialization
import com.example.problemdesk.domain.OLDMODELSrefactor.Status
import com.example.problemdesk.domain.OLDMODELSrefactor.Workplace
import com.example.problemdesk.presentation.CardRecyclerViewAdapter


class MasterFragment : Fragment() {

    private var _binding: FragmentMasterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMasterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //::handleCardClick binding RV click logic with fragment
        binding.masterRv.adapter = CardRecyclerViewAdapter(::handleCardClick)


        //TODO delete mocking
        val cards = listOf(
            Card("Принято", "01.07.24", "Инструменты", "№1", "сломалась болгарка"),
            Card("В работе", "30.06.24", "Безопасность", "№2", "проблема с проводкой"),
            Card("Отменено", "01.07.24", "Документооборот", "№3", "проблема с документами"),
            Card("На рассмотрении", "02.07.24", "Санитарно-бытовые условия", "№4", "закончилось мыло"),
            Card("Выполнено", "27.06.24", "Санитарно-бытовые условия", "№4", "поменять лампочку")

//            Card(Status.APPROVED, "01.07.24", Specialization.INSTRUMENTS, Workplace.N1, "сломалась болгарка"),
//            Card(Status.IN_PROGRESS, "30.06.24", Specialization.SAFETY, Workplace.N2, "проблема с проводкой"),
//            Card(Status.CANCELLED, "01.07.24", Specialization.DOCUMENTS, Workplace.N3, "проблема с документами"),
//            Card(Status.UNCHECKED, "02.07.24", Specialization.SANITARY_CONDITIONS, Workplace.N4, "закончилось мыло"),
//            Card(Status.COMPLETED, "27.06.24", Specialization.SANITARY_CONDITIONS, Workplace.N4, "поменять лампочку")
        )
        (binding.masterRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
    }

    private fun handleCardClick(card: Card) {
        //TODO delete mocking
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}