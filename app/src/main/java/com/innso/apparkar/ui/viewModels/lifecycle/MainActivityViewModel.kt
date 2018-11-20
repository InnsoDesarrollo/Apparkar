package com.innso.apparkar.ui.viewModels.lifecycle

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.innso.apparkar.api.controller.MapsController
import com.innso.apparkar.api.controller.PlacesController
import com.innso.apparkar.api.models.BasePlace
import com.innso.apparkar.arch.AndroidViewModel
import javax.inject.Inject

class MainActivityViewModel : AndroidViewModel() {

    private val parkingPlaces = MutableLiveData<List<BasePlace>>()

    private val mapLocation = MutableLiveData<LatLng>()

    @Inject
    lateinit var placesController: PlacesController

    @Inject
    lateinit var mapsController: MapsController

    init {
        getComponent().inject(this)
        updateParkingSlots()
    }

    fun updateParkingSlots() {
        disposables.add(placesController.parkingSlots
                .doOnSubscribe { showLoading() }
                .doFinally { hideLoading() }
                .subscribe({ parkingPlaces.postValue(it) }, this::showServiceError))
    }

    fun findLocationByAddress(address: String) {
        disposables.add(mapsController.getLocationByAddress(address)
                .doOnSubscribe { showLoading() }
                .doFinally { hideLoading() }
                .subscribe({ mapLocation.postValue(it) }, this::showServiceError))
    }

    /**
     * Live Data
     */

    fun onParkingPlacesChange(): LiveData<List<BasePlace>> = parkingPlaces

    fun onMapLocationChange(): LiveData<LatLng> = mapLocation
}