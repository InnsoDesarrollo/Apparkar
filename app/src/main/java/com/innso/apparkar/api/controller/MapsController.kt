package com.innso.apparkar.api.controller

import com.google.android.gms.maps.model.LatLng
import com.innso.apparkar.R
import com.innso.apparkar.api.models.maps.MapsResponse
import com.innso.apparkar.api.service.MapsApi
import com.innso.apparkar.provider.ResourceProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapsController @Inject constructor(private val mapsApi: MapsApi, private val resourceProvider: ResourceProvider) {

    fun getAddressDescription(lat: Double, lgn: Double): Observable<String> {
        val latLognFormat = lat.toString() + "," + lgn
        return mapsApi.getAddress(resourceProvider.getString(R.string.google_api_key), latLognFormat)
                .subscribeOn(Schedulers.io())
                .flatMap<String> { this.getAddress(it) }
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getAddress(mapsResponse: MapsResponse): Observable<String> {
        val result = mapsResponse.results
        var address = ""
        if (result != null && !result.isEmpty()) {
            address = result[0].formattedAddress
        }
        return Observable.just(address)
    }

    fun getLocationByAddress(address: String): Observable<LatLng> {
        return mapsApi.getLatLgn(resourceProvider.getString(R.string.google_api_key), address).subscribeOn(Schedulers.io())
                .flatMap { this.getLocation(it) }
    }

    private fun getLocation(mapsResponse: MapsResponse): Observable<LatLng> {
        val result = mapsResponse.results
        var locaton: LatLng? = null
        if (result != null && !result.isEmpty()) {
            val addressLocation = result[0].location
            locaton = LatLng(addressLocation.latitude, addressLocation.longitude)
        }
        return Observable.just(locaton!!)
    }

}
