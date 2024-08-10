package com.example.problemdesk.presentation.problemform

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.problemdesk.domain.models.Specialization
import com.example.problemdesk.domain.models.Workplace

class SpecializationAdapter(context: Context, specializations: Array<Specialization>) :
    ArrayAdapter<Specialization>(context, android.R.layout.simple_spinner_item, specializations) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val specialization = getItem(position)
        view.findViewById<TextView>(android.R.id.text1).text = specialization?.name
        return view
    }
}

class WorkplaceAdapter(context: Context, workplaces: Array<Workplace>) :
    ArrayAdapter<Workplace>(context, android.R.layout.simple_spinner_item, workplaces) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val workplace = getItem(position)
        view.findViewById<TextView>(android.R.id.text1).text = workplace?.name
        return view
    }
}


fun getSpecializationArray(): Array<Specialization> {
    return arrayOf(
        Specialization("Выберите тип проблемы...", 0),
        Specialization("Электрика", 1),
        Specialization("Инструменты", 2),
        Specialization("Санитарно-бытовые условия", 3),
        Specialization("Безопасность", 4),
        Specialization("Документооборот", 5)
    )
}

fun getWorkplaceArray(): Array<Workplace> {
    return arrayOf(
        Workplace("Выберите участок...", 0),
        Workplace("№1", 1),
        Workplace("№2", 2),
        Workplace("№3", 3),
        Workplace("№4", 4),
        Workplace("№5", 5),
        Workplace("№6", 6),
        Workplace("№7", 7),
        Workplace("№8", 8)
    )
}

