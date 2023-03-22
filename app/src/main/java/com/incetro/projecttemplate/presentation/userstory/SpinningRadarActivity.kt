/*
 * ProjectTemplate
 *
 * Created by artembirmin on 16/3/2023.
 */

package com.incetro.projecttemplate.presentation.userstory

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.incetro.projecttemplate.R
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentConstants.LOCATION_SOURCE
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.Layer
import com.mapbox.mapboxsdk.style.layers.Property
import com.mapbox.mapboxsdk.style.layers.Property.ICON_PITCH_ALIGNMENT_MAP
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.*
import com.mapbox.mapboxsdk.style.layers.SymbolLayer

/**
 * Turn a conical gradient drawable as a [SymbolLayer]'s icon and rotate the icon
 * to create a spinning radar effect.
 */
class SpinningRadarActivity : AppCompatActivity(), OnMapReadyCallback, PermissionsListener {

    private var permissionsManager: PermissionsManager = PermissionsManager(this)
    private lateinit var mapboxMap: MapboxMap
    private var iconSpinningAnimator: ValueAnimator? = null
    private var locationEngine: LocationEngine? = null

    lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this,
            getString(R.string.access_token))

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_lab_spinning_radar)
        mapView = this.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.mapboxMap = mapboxMap
        mapboxMap.setStyle(Style.LIGHT) { style ->
            enableLocationComponent(style)
        }
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationComponent(loadedMapStyle: Style) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            mapboxMap.locationComponent.apply {

                // Activate the LocationComponent with options
                activateLocationComponent(LocationComponentActivationOptions
                    .builder(this@SpinningRadarActivity,
                        loadedMapStyle)
                    .locationComponentOptions(LocationComponentOptions.builder(
                        this@SpinningRadarActivity)
                        .accuracyAnimationEnabled(
                            false)
                        .accuracyAlpha(0f)
                        .enableStaleState(false)
                        .build())
                    .build())

                // Enable to make the LocationComponent visible
                isLocationComponentEnabled = true

                // Set the LocationComponent's camera mode
                cameraMode = CameraMode.TRACKING

                // Set the LocationComponent's render mode
                renderMode = RenderMode.NORMAL

                // Add the conical gradient drawable as a Bitmap

                val radarBitmap by lazy {
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.ic_radar)
                        ?.toBitmap(width = 1000, height = 1000)
                }

                loadedMapStyle.addImage(SPINNING_RADAR_IMAGE_ID,
                    radarBitmap!!
                )

                /**
                 * Create the gradient radar image's SymbolLayer and place is below the
                 * Maps SDK's LocationComponent.
                 */
                val layer = SymbolLayer(SPINNING_RADAR_LAYER_ID,
                    LOCATION_SOURCE)
                    .withProperties(
                        visibility(Property.VISIBLE),
                        iconImage(SPINNING_RADAR_IMAGE_ID),
                        iconSize(1f),
                        iconOpacity(.7f),
                        iconIgnorePlacement(true),
                        iconAllowOverlap(true),
                        textAllowOverlap(true),
                        iconRotationAlignment(Property.ICON_PITCH_ALIGNMENT_MAP),
                        iconPitchAlignment(ICON_PITCH_ALIGNMENT_MAP)
                    ).apply {
                        minZoom = 0f
                    }

                loadedMapStyle.addLayerBelow(
                    layer,
                    "mapbox-location-pulsing-circle-layer")
                startSpinningRadarAnimation()
            }
        } else {
            permissionsManager = PermissionsManager(this).also {
                it.requestLocationPermissions(this)
            }
        }
    }

    /**
     * Set up and start the spinning radar animation. The Android system ValueAnimator emits a new value, which is
     * used as the radar gradient image's rotation value. The value is animated from 0 to 360 because of the
     * direction of the gradient's design and the desire to have it spin clockwise.
     */
    private fun startSpinningRadarAnimation() {
        iconSpinningAnimator?.cancel()
        iconSpinningAnimator = ValueAnimator.ofFloat(0f, 360f).also {
            it.duration = SPINNING_RADAR_IMAGE_SECONDS_PER_SPIN * 400.toLong()
            it.interpolator = LinearInterpolator()
            it.repeatCount = ValueAnimator.INFINITE
            it.addUpdateListener { valueAnimator -> // Retrieve the new animation number to use as the map camera bearing value
                val newIconRotateValue = valueAnimator.animatedValue as Float
//                Log.d("iconSpinningAnimator addUpdateListener", "newIconRotateValue: " + newIconRotateValue)
                mapboxMap.getStyle { style ->
                    val locationComponentRadarBackgroundLayer =
                        style.getLayerAs<Layer>(SPINNING_RADAR_LAYER_ID)
                    locationComponentRadarBackgroundLayer?.setProperties(
                        iconRotate(newIconRotateValue)
                    )
                }
            }
            it.start()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG)
            .show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            mapboxMap.getStyle {
                enableLocationComponent(it)
            }
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG)
                .show()
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        iconSpinningAnimator?.cancel()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val SPINNING_RADAR_LAYER_ID = "SPINNING_RADAR_LAYER_ID"
        private const val SPINNING_RADAR_IMAGE_ID = "SPINNING_RADAR_IMAGE_ID"
        private const val SPINNING_RADAR_IMAGE_SECONDS_PER_SPIN = 10
    }
}