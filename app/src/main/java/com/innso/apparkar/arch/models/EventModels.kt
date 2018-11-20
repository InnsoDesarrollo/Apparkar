package com.innso.apparkar.arch.models

import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View

data class StartActivityModel(val activity: Class<*>) {

    var bundle: Bundle? = null

    var code: Int = 0

    constructor(activity: Class<*>, bundle: Bundle) : this(activity) {
        this.bundle = bundle
    }

    constructor(activity: Class<*>, code: Int) : this(activity) {
        this.code = code
    }

    constructor(activity: Class<*>, bundle: Bundle, code: Int) : this(activity) {
        this.bundle = bundle
        this.code = code
    }
}

data class FinishActivityModel(val code: Int) {

    var intent: Intent? = null

    constructor(code: Int, intent: Intent) : this(code) {
        this.intent = intent
    }
}

data class DialogParams(val title: Int, val message: Int, val icon: Int, val cancelable: Boolean = false,
                   @StringRes val positiveText: Int, val positiveAction: View.OnClickListener)