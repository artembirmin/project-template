/*
 * ProjectTemplate
 *
 * Created by artembirmin on 28/3/2023.
 */

package com.incetro.projecttemplate.presentation.userstory

import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.incetro.projecttemplate.R
import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.geojson.Feature
import com.mapbox.geojson.LineString
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.maps.extension.style.layers.addLayerBelow
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.symbolLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.layers.properties.generated.IconPitchAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.IconRotationAlignment
import com.mapbox.maps.extension.style.layers.properties.generated.Visibility
import com.mapbox.maps.extension.style.sources.addSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.utils.ColorUtils
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.animation.camera
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement
import timber.log.Timber
import java.lang.ref.WeakReference
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Tracks the user location on screen, simulates a navigation session.
 */
class SpinningRadarLayerActivityV10 : AppCompatActivity() {

    private lateinit var locationPermissionHelper: LocationPermissionHelper

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = MapView(this)
        setContentView(mapView)
        locationPermissionHelper = LocationPermissionHelper(WeakReference(this))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }
    }

    private fun onMapReady() {
        mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        ) {
            initLocationComponent()
            setupGesturesListener()
            addRadar(it)
        }
    }

    val radarRadiusInMeters = 100
    var oldRadarRadiusPx = 100
    private var iconSpinningAnimator: ValueAnimator? = null
    private val mapboxMap by lazy { mapView.getMapboxMap() }

    fun addRadar(style: Style) {
        mapView.camera.addCameraZoomChangeListener {

            val newRad = getRadarRadiusPx() * 2
            val coef = newRad.toDouble() / oldRadarRadiusPx
            Timber.d("RADAR addOnCameraIdleListener" +
                    " newRad = $newRad," +
                    " olfRadarRadiusPx = $oldRadarRadiusPx," +
                    " coef = $coef," +
                    "")

            mapboxMap.getStyle { style ->
                val locationComponentRadarBackgroundLayer =
                        style.getLayerAs<SymbolLayer>(SpinningRadarActivity.SPINNING_RADAR_LAYER_ID)
                locationComponentRadarBackgroundLayer?.apply {
                    iconSize(coef)
                }
            }
        }


        val radarBitmap by lazy {
            oldRadarRadiusPx = 500 * 2
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_radar)
                ?.toBitmap(width = oldRadarRadiusPx, height = oldRadarRadiusPx)
        }
        Timber.d("RADAR radarBitmap = ${radarBitmap!!.width}")
        val radarDrawable by lazy {
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_radar
            )
        }

        val radarNewBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_radar)
        Timber.d("RADAR radarDrawable = ${radarDrawable!!.intrinsicWidth}")
        Timber.d("RADAR radarImage = ${radarNewBitmap!!.width}")
        oldRadarRadiusPx = radarNewBitmap.width

        style.addImage(SpinningRadarActivity.SPINNING_RADAR_IMAGE_ID,
            radarNewBitmap
        )

        val sourceId = "mapbox-location-source"
        val source = geoJsonSource(sourceId)
        style.addSource(source)

        val layer = symbolLayer(
            SpinningRadarActivity.SPINNING_RADAR_LAYER_ID,
            sourceId
        ) {
            visibility(Visibility.VISIBLE)
            iconImage(SpinningRadarActivity.SPINNING_RADAR_IMAGE_ID)
            iconIgnorePlacement(true)
            textAllowOverlap(true)
            iconRotationAlignment(IconRotationAlignment.MAP)
            iconPitchAlignment(IconPitchAlignment.MAP)
            minZoom(0.0)
        }

//        // Create the circle layer to clip the image to the user's location
//        val circleLayer = circleLayer(
//            "mapbox-location-circle-layer",
//            sourceId
//        ) {
//            circleRadius(
//                2000.0
//            )
//            circleColor(Color.WHITE)
//            circleStrokeColor(Color.BLACK)
//        }
//
//// Add the circle layer below the symbol layer
//        loadedMapStyle.addLayerBelow(circleLayer, LocationComponentConstants.LOCATION_INDICATOR_LAYER)
        val innerLineString: LineString = LineString.fromLngLats(
            listOf(
                Point.fromLngLat(0.0, 0.0),
                Point.fromLngLat(0.0, 0.0),
                Point.fromLngLat(0.0, 0.0),
                Point.fromLngLat(0.0, 0.0)
            ))
        val outerLineString: LineString = LineString.fromLngLats(
            listOf(
                Point.fromLngLat(-180.0, -90.0),
                Point.fromLngLat(180.0, -90.0),
                Point.fromLngLat(180.0, 90.0),
                Point.fromLngLat(-180.0, 90.0),
                Point.fromLngLat(-180.0, -90.0)
            )
        )
        val fea = Feature.fromGeometry(Polygon.fromOuterInner(outerLineString, innerLineString))

        val RADAR_OUTER_SOURCE = "RADAR_OUTER_SOURCE"
        val RADAR_OUTER_LAYER = "RADAR_OUTER_LAYER"
        val radarOuterSource = geoJsonSource(RADAR_OUTER_SOURCE) { feature(fea) }
        style.addSource(radarOuterSource)

        val polygonFillLayer: FillLayer = fillLayer(RADAR_OUTER_LAYER, RADAR_OUTER_SOURCE) {
            fillColor(
                ColorUtils.colorToRgbaString(
                    ContextCompat.getColor(applicationContext, R.color.colorBlackTransparent15)
                )
            )
        }

        style.addLayerBelow(
            polygonFillLayer,
            LocationComponentConstants.LOCATION_INDICATOR_LAYER)

        style.addLayerBelow(
            layer,
            LocationComponentConstants.LOCATION_INDICATOR_LAYER)

        ContextCompat.getDrawable(this, R.drawable.ic_radar)?.let { mapView.overlay.add(it) }
        Timber.d("RADAR layers ${style.styleLayers}")
        Timber.d("RADAR layerBelow ${mapView.location.layerBelow}")
        Timber.d("RADAR layerAbove ${mapView.location.layerAbove}")
//        startSpinningRadarAnimation()

    }

    private fun getRadarRadiusPx(): Int {
        val mapBoxMap = mapView.getMapboxMap()
        val options = CameraOptions.Builder()
            .zoom(mapBoxMap.cameraState.zoom)
            .center(mapBoxMap.cameraState.center)
            .build()
        val coordinateBoundsForCamera = mapView.getMapboxMap().coordinateBoundsForCamera(options)

        val nePoint = coordinateBoundsForCamera.northeast
        val swPoint = coordinateBoundsForCamera.southwest

        val mapViewDiagonalPx =
                kotlin.math.sqrt(mapView.width.toDouble().pow(2) + mapView.height.toDouble().pow(2))
        val mapViewDiagonalMeters =
                TurfMeasurement.distance(nePoint, swPoint, TurfConstants.UNIT_METERS)
        val pixelMeter = mapViewDiagonalPx / mapViewDiagonalMeters

        return (radarRadiusInMeters * pixelMeter).roundToInt()
    }

    /**
     * Set up and start the spinning radar animation. The Android system ValueAnimator emits a new value, which is
     * used as the radar gradient image's rotation value. The value is animated from 0 to 360 because of the
     * direction of the gradient's design and the desire to have it spin clockwise.
     */
    private fun startSpinningRadarAnimation() {
        iconSpinningAnimator?.cancel()
        iconSpinningAnimator = ValueAnimator.ofFloat(0f, 360f).also {
            it.duration = SpinningRadarActivity.SPINNING_RADAR_IMAGE_SECONDS_PER_SPIN * 500.toLong()
            it.interpolator = LinearInterpolator()
            it.repeatCount = ValueAnimator.INFINITE
            it.addUpdateListener { valueAnimator -> // Retrieve the new animation number to use as the map camera bearing value
                val newIconRotateValue = valueAnimator.animatedValue as Float
//                Log.d("iconSpinningAnimator addUpdateListener", "newIconRotateValue: " + newIconRotateValue)
                mapboxMap.getStyle { style ->
                    val locationComponentRadarBackgroundLayer =
                            style.getLayerAs<SymbolLayer>(SpinningRadarActivity.SPINNING_RADAR_LAYER_ID)
                    locationComponentRadarBackgroundLayer?.apply {
                        iconRotate(newIconRotateValue.toDouble())
                    }
                }
            }
            it.start()
        }
    }

    private fun setupGesturesListener() {
        mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = AppCompatResources.getDrawable(
                    this@SpinningRadarLayerActivityV10,
                    R.drawable.ic_location_arrow,
                ),
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )

        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(
            onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(
            onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(this, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}