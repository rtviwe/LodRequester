package com.example.roman.lodaddplaction.data

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

const val EARTH_RADIUS = 6_371_000
const val INTERVAL_TO_UPDATE_LOCATION = 15L

val Double.radian: Double
    get() = this * Math.PI / 180

fun findDistance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double = acos(
    sin(lat1.radian) * sin(lat2.radian)
            + cos(lat1.radian) * cos(lat2.radian) * cos(abs(long1 - long2).radian)
) * EARTH_RADIUS

fun getLocation(context: Context): Location? {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val criteria = Criteria()
    val bestProvider = locationManager.getBestProvider(criteria, true)

    return if (ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED)
        locationManager.getLastKnownLocation(bestProvider)
    else null
}

fun observableToUpdateLocation(): Observable<Long> =
    Observable.interval(INTERVAL_TO_UPDATE_LOCATION, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
