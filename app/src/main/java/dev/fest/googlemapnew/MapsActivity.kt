package dev.fest.googlemapnew

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPolylineClickListener,
    GoogleMap.OnPolygonClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val myWay = googleMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    LatLng(53.909947, 27.561044),
                    LatLng(53.908721, 27.559703),
                    LatLng(53.908086, 27.561248),
                    LatLng(53.904278, 27.566355),
                    LatLng(53.900613, 27.558882),
                    LatLng(53.898132, 27.561993),
                    LatLng(53.896553, 27.558169),
                    LatLng(53.895855, 27.559044),
                )
        )
        myWay.tag = "myWay"
        stylePolyline(myWay)

        val myArea = googleMap.addPolygon(
            PolygonOptions()
                .clickable(true)
                .add(
                    LatLng(53.895855, 27.559044),
                    LatLng(53.895950, 27.559141),
                    LatLng(53.896159, 27.559446),
                    LatLng(53.896244, 27.559741),
                    LatLng(53.896314, 27.560144),
                    LatLng(53.896276, 27.560600),
                    LatLng(53.896137, 27.560997),
                    LatLng(53.895908, 27.561276),
                    LatLng(53.895677, 27.561400),
                    LatLng(53.894745, 27.561196),
                    LatLng(53.894413, 27.560783),
                    LatLng(53.894242, 27.560327),
                    LatLng(53.894185, 27.559801),
                    LatLng(53.894274, 27.559308),
                    LatLng(53.894397, 27.559018),
                    LatLng(53.894691, 27.558739),
                    LatLng(53.895238, 27.558691),
                    LatLng(53.895674, 27.558868),
                    LatLng(53.895943, 27.559131),
                )
        )
        myArea.tag = "myArea"
        stylePolygon(myArea)

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(53.900273, 27.563222), 14f))
        googleMap.setOnPolylineClickListener(this)
        googleMap.setOnPolygonClickListener(this)
    }

    private fun stylePolyline(polyline: Polyline) {
        polyline.startCap = CustomCap(
            BitmapDescriptorFactory.fromResource(R.drawable.start), 50f
        )
        polyline.endCap = ButtCap()
        polyline.jointType = JointType.BEVEL
    }

    override fun onPolylineClick(polyline: Polyline) {
        Toast.makeText(this, "Route type ${polyline.tag}", Toast.LENGTH_SHORT).show()
    }

    override fun onPolygonClick(polygon: Polygon) {
        var color = polygon.strokeColor xor 0x00ffffff
        polygon.strokeColor = color
        color = polygon.fillColor xor 0x00ffffff
        polygon.fillColor = color
        Toast.makeText(this, "Area type ${polygon.tag}", Toast.LENGTH_SHORT).show()
    }

    private val COLOR_GREEN_ARGB = -0xc771c4
    private val COLOR_PURPLE_ARGB = -0x7e387c
    private val POLYGON_STROKE_WIDTH_PX = 8
    private val PATTERN_DASH_LENGTH_PX = 20
    private val PATTERN_GAP_LENGTH_PX = 20
    private val DASH: PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())
    private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())
    private val PATTERN_POLYGON_ALPHA = listOf(GAP, DASH)

    private fun stylePolygon(polygon: Polygon) {
        var pattern: List<PatternItem>?
        polygon.apply {
            pattern = PATTERN_POLYGON_ALPHA
            strokeColor = COLOR_GREEN_ARGB
            fillColor = COLOR_PURPLE_ARGB
            strokePattern = pattern
            strokeWidth = POLYGON_STROKE_WIDTH_PX.toFloat()
        }
    }
}

