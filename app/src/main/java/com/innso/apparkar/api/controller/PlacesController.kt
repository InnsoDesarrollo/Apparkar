package com.innso.apparkar.api.controller

import com.innso.apparkar.api.models.BasePlace
import com.innso.apparkar.api.models.Parking
import com.innso.apparkar.api.service.PlacesApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PlacesController @Inject constructor(private val placesApi: PlacesApi) {

    private val STATUS_ATTRIBUTE = "\"status\""
    private val STATUS_PENDING_REVIEW = "\"pending_review\""
    private val STATUS_APPROVED = "\"approved\""

    fun getParkingSlots(): Single<List<BasePlace>> =
            placesApi.getParkingData(STATUS_ATTRIBUTE, STATUS_APPROVED)
                    .subscribeOn(Schedulers.io())
                    .flatMap(this::convertMapToList)

    fun getPetrolStations(): Single<List<BasePlace>> = Single.just(ArrayList())

    fun getOtherPlacesSlots(): Single<List<BasePlace>> =
            placesApi.getParkingData(STATUS_ATTRIBUTE, STATUS_PENDING_REVIEW)
                    .subscribeOn(Schedulers.io())
                    .flatMap { this.convertMapToList(it) }
                    .observeOn(AndroidSchedulers.mainThread())

    private fun convertMapToList(data: Map<String, Parking>): Single<List<BasePlace>> {
        return Single.just(ArrayList<BasePlace>(data.values))
    }

    fun addParkingSlot(parking: Parking): Single<Parking> =
            placesApi.addNewParking(parking)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

}
