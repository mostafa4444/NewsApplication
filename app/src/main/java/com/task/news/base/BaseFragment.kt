package com.task.news.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.task.news.local.preference.PreferencesManager
import com.task.news.utils.NetworkConnection
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() , View.OnClickListener {

    @Inject
    lateinit var connectivityManager: NetworkConnection

    @Inject
    lateinit var sharedPrefs: PreferencesManager

    var lang = ""

    var isNetwork: Boolean = false

    var isArabicLanguage = false

    open var dialogDismissAction: (() -> Unit)? = null

    protected var baseViewModel: VM? = null

    lateinit var baseViewBinding : VB

    protected abstract fun initView()

    protected abstract fun getContentView(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseViewBinding = DataBindingUtil.inflate(inflater , getContentView() , container , false)
        return baseViewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        baseViewModel?.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetworkConnection()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        initView()
        subscribeLiveData()
    }

    private fun observeNetworkConnection() {
        connectivityManager.observe(viewLifecycleOwner, { isAvailable ->
            when (isAvailable) {
                true -> {
                    baseViewModel?.networkStatus = true
                    isNetwork = true
                }
                false ->{
                    baseViewModel?.networkStatus = false
                    isNetwork = false
                }
            }
        })
    }

    protected abstract fun initializeViewModel()

    private fun showLongToast(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    private fun showShortToast(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }


//    fun showError(error: String, btnTitle: String?, myAction: (() -> Unit)?) {
//        CustomAlertDialog.showMyCustomDialog(
//            requireActivity() as AppCompatActivity,
//            CustomAlertDialog.Type.ERROR,
//            btnTitle!!,
//            error,
//            myAction
//        )
//    }

//    private fun showSuccess(msg: String, btnTitle: String?) {
//        if (btnTitle.isNullOrEmpty()){
//            CustomAlertDialog.showMyCustomDialog(
//                requireActivity() as AppCompatActivity, CustomAlertDialog.Type.SUCCESS, this.getString(
//                    R.string.ok
//                ), msg, dialogDismissAction
//            )
//        }else{
//            CustomAlertDialog.showMyCustomDialog(
//                requireActivity() as AppCompatActivity,
//                CustomAlertDialog.Type.SUCCESS,
//                btnTitle,
//                msg,
//                dialogDismissAction
//            )
//        }
//    }

//    fun showProceedDismiss(msg: String, btnTitle: String?, myAction: (() -> Unit)?) {
//        CustomAlertDialog.showDismissProceedDialog(
//            requireActivity() as AppCompatActivity,
//            msg,
//            btnTitle!!,
//            myAction
//        )
//    }

    override fun onDestroy() {
        super.onDestroy()
        baseViewModel?.stop()
    }

    protected open fun subscribeLiveData() {
        baseViewModel?.errorDialog?.observe(viewLifecycleOwner, {
//            showError(it, this.getString(R.string.ok), null)
        })
        baseViewModel?.successDialog?.observe(viewLifecycleOwner,  {
//            showSuccess(it, this.getString(R.string.ok))
        })
    }

}


