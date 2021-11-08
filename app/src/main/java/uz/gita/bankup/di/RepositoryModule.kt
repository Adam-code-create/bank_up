package uz.gita.bankup.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bankup.domain.repository.*
import uz.gita.bankup.domain.repository.auth.LoginRepository
import uz.gita.bankup.domain.repository.auth.RegisterRepository
import uz.gita.bankup.domain.repository.auth.VerifySmsRepository
import uz.gita.bankup.domain.repository.card.AddAllCardRepository
import uz.gita.bankup.domain.repository.card.AddBankCardRepository
import uz.gita.bankup.domain.repository.card.EditCardRepository
import uz.gita.bankup.domain.repository.card.VerifyCardRepository
import uz.gita.bankup.domain.repository.profile.ProfileRepository
import uz.gita.bankup.domain.repository.transfer.SendMoneyRepository
import uz.gita.bankup.domain.repositoryImpl.*
import uz.gita.bankup.domain.repositoryImpl.auth.LoginRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.auth.RegisterRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.auth.VerifyCodeRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.card.AddAllCardRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.card.AddBankCardRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.card.EditCardRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.card.VerifyCardRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.profile.ProfileRepositoryImpl
import uz.gita.bankup.domain.repositoryImpl.transfer.SendMoneyRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun registerRepository(repository : RegisterRepositoryImpl) : RegisterRepository


    @Binds
    @Singleton
    abstract fun smsRepository(repository : VerifyCodeRepositoryImpl) : VerifySmsRepository


    @Binds
    @Singleton
    abstract fun addCardRepository(repository : AddBankCardRepositoryImpl) : AddBankCardRepository

    @Binds
    @Singleton
    abstract fun getAllCardsRepository(repository : AddAllCardRepositoryImpl) : AddAllCardRepository

    @Binds
    @Singleton
    abstract fun verifyCardRepository(repository : VerifyCardRepositoryImpl) : VerifyCardRepository

    @Binds
    @Singleton
    abstract fun editCardRepository(repository : EditCardRepositoryImpl) : EditCardRepository

    @Binds
    @Singleton
    abstract fun splashRepository(repository :SplashRepositoryImpl) :SplashRepository


    @Binds
    @Singleton
    abstract fun loginRepository(repository : LoginRepositoryImpl) : LoginRepository


    @Binds
    @Singleton
    abstract fun sendMoneyRepository(repository : SendMoneyRepositoryImpl) : SendMoneyRepository


    @Binds
    @Singleton
    abstract fun profileRepository(repository : ProfileRepositoryImpl) : ProfileRepository


    @Binds
    @Singleton
    abstract fun mainScreenRepository(repository : MainScreenRepositoryImpl) : MainScreenRepository

}
