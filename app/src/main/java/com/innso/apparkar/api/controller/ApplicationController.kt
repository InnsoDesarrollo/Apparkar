package com.innso.apparkar.api.controller

import com.innso.apparkar.BuildConfig
import com.innso.apparkar.api.models.app.GeneralInformation
import com.innso.apparkar.api.service.ApplicationApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApplicationController @Inject constructor(private val applicationApi: ApplicationApi) {

    fun forceUpdate(): Single<Boolean> {
        return applicationApi.appVersion
                .subscribeOn(Schedulers.io())
                .map<Boolean> { this.validateForceUpdate(it) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun validateForceUpdate(information: GeneralInformation): Boolean {
        val serverVersion = information.buildVersion
        return serverVersion > BuildConfig.VERSION_CODE
    }
}
