package uz.gita.bankup.domain.repositoryImpl

import uz.gita.bankup.data.api.pref.SharedPref
import uz.gita.bankup.domain.repository.SplashRepository
import uz.gita.bankup.data.api.enum.StartScreenEnum
import javax.inject.Inject

class SplashRepositoryImpl @Inject constructor(private val pref : SharedPref): SplashRepository {
    override fun openScreen(): StartScreenEnum = pref.startScreen
}