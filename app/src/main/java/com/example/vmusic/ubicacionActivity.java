package com.example.vmusic;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

public class ubicacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ubicacion);

        // Configuración del mapa usando preferencias
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        // Referencia al MapView
        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);


        GeoPoint ipSantoTomasPoint = new GeoPoint(-33.4493141, -70.6624069);
        GeoPoint parquePoint = new GeoPoint(-33.4625076, -70.6600286);


        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(ipSantoTomasPoint);


        Marker marcadorSantoTomas = new Marker(mapView);
        marcadorSantoTomas.setPosition(ipSantoTomasPoint);
        marcadorSantoTomas.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marcadorSantoTomas.setTitle("IP Santo Tomas, Chile");
        marcadorSantoTomas.setSnippet("Un Instituto Tomista");
        mapView.getOverlays().add(marcadorSantoTomas);


        Marker marcadorParque = new Marker(mapView);
        marcadorParque.setPosition(parquePoint);
        marcadorParque.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marcadorParque.setTitle("Parque O'Higgins");
        marcadorParque.setSnippet("Un Parque");
        mapView.getOverlays().add(marcadorParque);


        Polyline linea = new Polyline();
        linea.addPoint(ipSantoTomasPoint);
        linea.addPoint(parquePoint);
        linea.setColor(0xFF0000FF);
        linea.setWidth(5);
        mapView.getOverlayManager().add(linea);


        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        String[] mapTypes = {"Mapa Normal", "Mapa de Transporte", "Mapa Topográfico"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapter);


        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mapView.setTileSource(TileSourceFactory.MAPNIK);  // Mapa normal
                        break;
                    case 1:
                        mapView.setTileSource(new XYTileSource("PublicTransport", 0, 18, 256, ".png",
                                new String[]{"https://tile.memomaps.de/tilegen/"}));
                        break;
                    case 2:
                        mapView.setTileSource(new XYTileSource("USGS_Satellite", 0, 18, 256, ".png",
                                new String[]{"https://a.tile.opentopomap.org/",
                                        "https://b.tile.opentopomap.org/",
                                        "https://c.tile.opentopomap.org/"}));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}

