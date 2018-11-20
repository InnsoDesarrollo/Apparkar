package com.innso.apparkar.arch

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.innso.apparkar.di.component.DaggerViewModelComponent
import com.innso.apparkar.di.component.ViewModelComponent
import com.innso.apparkar.ui.InnsoApplication
import com.innso.apparkar.ui.events.SnackBarEvent
import com.innso.apparkar.ui.factories.SnackBarFactory
import com.innso.apparkar.arch.extension.ActiveMutableLiveData
import com.innso.apparkar.arch.models.DialogParams
import com.innso.apparkar.arch.models.FinishActivityModel
import com.innso.apparkar.arch.models.StartActivityModel
import com.innso.apparkar.util.ErrorUtil
import io.reactivex.disposables.CompositeDisposable

open class AndroidViewModel : ViewModel() {

    internal val disposables = CompositeDisposable()

    internal val loader = MutableLiveData<Boolean>()

    internal val closeView = MutableLiveData<FinishActivityModel>()

    internal val snackBar = MutableLiveData<SnackBarEvent>()

    internal val alertDialog = ActiveMutableLiveData<DialogParams>()

    internal val startActivity = ActiveMutableLiveData<StartActivityModel>()

    private val children = ArrayList<BaseViewModel>()

    internal fun getComponent(): ViewModelComponent {
        return DaggerViewModelComponent.builder()
                .appComponent(getApplication().getAppComponent())
                .build()
    }

    private fun getApplication(): InnsoApplication {
        return InnsoApplication.get()
    }

    internal fun showSnackBarError(message: String) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, message, Snackbar.LENGTH_LONG)
    }

    internal fun showSnackBarError(message: Int) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, getApplication().getString(message), Snackbar.LENGTH_LONG)
    }

    internal fun showSnackBarMessage(@SnackBarFactory.SnackBarType typeSnackBar: String, message: String, duration: Int) {
        snackBar.postValue(SnackBarEvent(typeSnackBar, message, duration))
    }

    internal fun showSnackBarMessage(@SnackBarFactory.SnackBarType typeSnackBar: String, message: Int, duration: Int) {
        snackBar.postValue(SnackBarEvent(typeSnackBar, getApplication().getString(message), duration))
    }

    open fun showServiceError(throwable: Throwable) {
        showSnackBarMessage(SnackBarFactory.TYPE_ERROR, ErrorUtil.getMessageError(throwable), Snackbar.LENGTH_LONG)
        loader.postValue(false)
    }

    override fun onCleared() {
        disposables.clear()
        children.forEach { it.clearDisposables() }
        super.onCleared()
    }

    fun hideLoading() {
        loader.postValue(false)
    }

    fun showLoading() {
        loader.postValue(true)
    }

    fun onCloseView() {
        closeView.postValue(FinishActivityModel(Activity.RESULT_OK))
    }

    internal fun addChild(childViewModel: BaseViewModel) {
        children.add(childViewModel)
        disposables.addAll(childViewModel.observableShowLoading().subscribe { loader.value = it },
                childViewModel.startActivityEvent().subscribe { startActivity.setValue(it) },
                childViewModel.observableSnackBar().subscribe { snackBar.value = it })
    }

    /**
     * Restore Instance
     */

    open fun onSaveInstanceState(): Bundle = Bundle()

    open fun onRestoreInstanceState(savedInstanceState: Bundle) {}

    /**
     * LiveData
     */

    fun loaderState(): LiveData<Boolean> = loader

    fun snackBarMessage(): LiveData<SnackBarEvent> = snackBar

    fun showAlertDialog(): LiveData<DialogParams> = alertDialog

    fun closeView(): LiveData<FinishActivityModel> = closeView

    fun startActivity(): LiveData<StartActivityModel> = startActivity

}
