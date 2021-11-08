package uz.gita.bankup.presenter.screens

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import uz.gita.bankup.R
import uz.gita.bankup.data.api.retrofit.request.profile.ResponseProfileInfo
import uz.gita.bankup.databinding.ScreenEditProfilBinding
import uz.gita.bankup.domain.repositoryImpl.profile.ProfileRepositoryImpl
import uz.gita.bankup.presenter.viewmodelimpls.profile.ProfileViewModelImpl
import uz.gita.bankup.presenter.viewmodels.profile.ProfileViewModel
import java.io.File

@AndroidEntryPoint
class EditAccountScreen :Fragment(R.layout.screen_edit_profil) {
    private val vb by viewBinding(ScreenEditProfilBinding::bind)
    private val viewModel :ProfileViewModel by viewModels<ProfileViewModelImpl> ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.editImage.setOnClickListener {
            ImagePicker.with(requireActivity())
                .compress(1024)
                .maxResultSize(512, 512)
                .saveDir(File(requireContext().getExternalFilesDir(null)?.let {
                    it.absolutePath
                }, "MySample"))
                .createIntent { startForProfileImageResult.launch(it) }
//                .start(101)
        }
        viewModel.getInfo()
        
        viewModel.enableLiveData.observe(viewLifecycleOwner,enableLiveDataObserver )
        viewModel.errorLiveData.observe(viewLifecycleOwner,errorLiveDataObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner,progressLiveData)
        viewModel.successAvatarLiveData.observe(viewLifecycleOwner, successAvatarLiveDataObserver)
        viewModel.successInfoLiveData.observe(viewLifecycleOwner, successInfoLiveDataObserver)
        viewModel.successGetAvatarLiveData.observe(viewLifecycleOwner, successGetAvatarLiveDataObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successLiveDataObserver)

    }
    private val enableLiveDataObserver = Observer<Boolean>{
        vb.save.isEnabled = it
    }
    private val errorLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val progressLiveData = Observer<Boolean>{
        if (it)vb.progress.hide()
        else vb.progress.show()
    }
    private val successAvatarLiveDataObserver = Observer<Unit>{
       
    }
    private val successInfoLiveDataObserver = Observer<ResponseProfileInfo>{
        vb.editName.setText(it.data.firstName)
        vb.editLastName.setText(it.data.lastName)
        vb.editPassword.setText(it.data.password)
    }
    private val successGetAvatarLiveDataObserver = Observer<MultipartBody.Companion>{
        /// TODO: 04/11/2021  
    }
    private val successLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                Log.d("TTT", fileUri.toString())
//                mProfileUri = fileUri

                vb.accountImage.setImageURI(fileUri)
//                val file = ImagePicker.
                /// TODO: 04/11/2021  
              //  uploadAvatar(File(FileUtils.getPath(requireContext(), fileUri)))
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}