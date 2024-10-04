package org.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class is responsible for displaying the map.
 * It uses the JXMapViewer library to display the map.
 * The map is displayed using the coordinates provided.
 * The coordinates are extracted from the JSON response.
 */
public class MapViewer {
    private static final Logger logger = LoggerFactory.getLogger(MapViewer.class);
    private static final JXMapViewer mapViewer = new JXMapViewer();
    private static final JFrame frame = new JFrame("Map Viewer");
    private static final TileFactoryInfo info = new OSMTileFactoryInfo();
    private static final DefaultTileFactory tileFactory = new DefaultTileFactory(info);

    public void displayMap(List<Map<String, String>> coordinates) {
        if (coordinates.isEmpty()) {
            logger.error("No coordinates provided");
            return;
        }

        List<GeoPosition> geoPositions = new ArrayList<>();
        Set<Waypoint> waypoints = new HashSet<>();

        for (Map<String, String> coord : coordinates) {
            double latitude = Double.parseDouble(coord.get("lat"));
            double longitude = Double.parseDouble(coord.get("lon"));
            GeoPosition geoPosition = new GeoPosition(latitude, longitude);
            geoPositions.add(geoPosition);
            waypoints.add(new DefaultWaypoint(geoPosition));

            logger.info("Latitude: {}, Longitude: {}", coord.get("lat"), coord.get("lon"));
        }

        mapViewer.setTileFactory(tileFactory);
        tileFactory.setThreadPoolSize(8);

        RoutePainter routePainter = new RoutePainter(geoPositions);

        // Create a waypoint painter using the waypoints set
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);

        mapViewer.setZoom(5);

        // Enable drag (pan) functionality
        PanMouseInputListener panListener = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(panListener);
        mapViewer.addMouseMotionListener(panListener);

        // Enable zoom with mouse scroll
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

        // Optional: Add a double-click listener to center the map
        mapViewer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    GeoPosition position = mapViewer.convertPointToGeoPosition(e.getPoint());
                    mapViewer.setCenterPosition(position);
                }
            }
        });

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<>();
        //painters.add(routePainter); // Uncomment this line to display the direct route
        painters.add(waypointPainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>(painters);
        mapViewer.setOverlayPainter(painter);

        mapViewer.setAddressLocation(geoPositions.getFirst());

        // Set up the frame for the map viewer
        frame.add(mapViewer);
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.png"))).getImage());
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
