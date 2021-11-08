package uz.gita.bankup.domain.repository

import kotlinx.coroutines.flow.Flow

interface MainScreenRepository {
    fun openEditAccount()
    fun openMyCards()
    fun logout() :Flow<Result<Unit>>
}