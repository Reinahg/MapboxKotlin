package com.valeria.mapboxproject.ui.components.map

import androidx.compose.runtime.Composable
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location

@Composable
fun MapInitialSetup(mapViewportState: MapViewportState) {
    MapEffect(Unit) { mapView ->
        mapView.location.updateSettings {
            locationPuck = createDefault2DPuck(withBearing = true)
            enabled = true
            puckBearing = PuckBearing.COURSE
            puckBearingEnabled = true
        }
        mapViewportState.transitionToFollowPuckState()
    }
}