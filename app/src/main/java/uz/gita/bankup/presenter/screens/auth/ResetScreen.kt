package uz.gita.bankup.presenter.screens.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bankup.R
import uz.gita.bankup.databinding.ScreenPasswordResetBinding
@AndroidEntryPoint
class ResetScreen :Fragment(R.layout.screen_password_reset) {
    private val viewBinding by viewBinding (ScreenPasswordResetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}