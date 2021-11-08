package uz.gita.bankup.domain.repository

import uz.gita.bankup.data.api.enum.StartScreenEnum

interface SplashRepository  {
fun openScreen(): StartScreenEnum

}