package com.example.problemdesk.presentation.myproblems

import com.example.problemdesk.domain.models.Card

interface SortStrategy {
    fun sort(items: List<Card>): List<Card>
}

class SortByAreaId : SortStrategy {
    override fun sort(items: List<Card>): List<Card> {
        return items.sortedBy { it.areaId }
    }
}

class SortByStatusId : SortStrategy {
    override fun sort(items: List<Card>): List<Card> {
        return items.sortedByDescending { it.statusId }
    }
}

class SortByTime : SortStrategy {
    override fun sort(items: List<Card>): List<Card> {
        return items.sortedBy { it.createdAt }
    }
}

class CardListManager(private var sortStrategy: SortStrategy) {
    fun setSortStrategy(strategy: SortStrategy) {
        sortStrategy = strategy
    }

    fun getSortedCards(cards: List<Card>): List<Card> {
        return sortStrategy.sort(cards)
    }
}


