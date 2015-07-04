package org.puc.rio.inf1636.hglm.war;

import java.awt.Dimension;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.puc.rio.inf1636.hglm.war.model.Card;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Territory;

import com.google.gson.Gson;

public class Util {
	
	public static void loadTerritories(Map map) {
		String jsonContent;
		try {
			jsonContent = readFile("resources/territoryBounds.json",
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		@SuppressWarnings("unchecked")
		java.util.Map<String, List<List<Double>>> territories = new Gson()
				.fromJson(jsonContent, java.util.Map.class);
		Iterator<java.util.Map.Entry<String, List<List<Double>>>> it = territories.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<String, List<List<Double>>> pair = (java.util.Map.Entry<String, List<List<Double>>>) it
					.next();
			List<List<Double>> values = pair.getValue();
			List<Point2D.Double> points = new LinkedList<Point2D.Double>();
			for (List<Double> point : values) {
				points.add(new Point2D.Double(
						(double) point.get(0)
								* WarGame.getInstance().getWarFrame().getMapPanel().coordinatesMultiplierX,
						(double) point.get(1)
								* WarGame.getInstance().getWarFrame().getMapPanel().coordinatesMultiplierY));
			}
			map.addTerritory(new Territory(pair.getKey(), points));
			it.remove();
		}
	}
	public static void loadDeck() {
		String jsonContent;
		try {
			jsonContent = readFile("resources/TerritoryType.json",
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		@SuppressWarnings("unchecked")
		java.util.Map<String, Double> cards = new Gson()
				.fromJson(jsonContent, java.util.Map.class);
		Iterator<java.util.Map.Entry<String, Double>> it = cards.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Double> pair = (java.util.Map.Entry<String, Double>) it
					.next();
			String name = pair.getKey();
			int value = pair.getValue().intValue();
			System.out.println(name +" " + pair.getValue().toString());
			Card c = new Card(WarGame.getInstance().getMap().getTerritoryByName(name),value);
			WarGame.getInstance().insertCardOnly(c);
		}
			
	}
	
    public static ArrayList<Line2D.Double> getLineSegments(GeneralPath p){
       
        ArrayList<double[]> linePoints = new ArrayList<>();
        ArrayList<Line2D.Double> lineSegments = new ArrayList<>();
             
        double[] coords = new double[6];    
       
        for(PathIterator pi = p.getPathIterator(null); !pi.isDone(); pi.next()){
            // The type will be SEG_LINETO, SEG_MOVETO, or SEG_CLOSE
            // since p is composed of straight lines
            int type = pi.currentSegment(coords);
           
            // We record a double array of {segment type, x coord, y coord}
            double[] pathIteratorCoords = {type,coords[0],coords[1]};
            linePoints.add(pathIteratorCoords);
        }
       
        double[] start = new double[3]; // To record where each polygon starts
 
        for (int i = 0; i < linePoints.size(); i++) {
            // If we're not on the last point, return a line from this point to the next
            double[] currentElement = linePoints.get(i);
 
            // We need a default value in case we've reached the end of the ArrayList
            double[] nextElement = {-1, -1, -1};
            if (i < linePoints.size() - 1) {
                nextElement = linePoints.get(i + 1);
            }
 
            // Make the lines
            if (currentElement[0] == PathIterator.SEG_MOVETO) {
                start = currentElement; // Record where the polygon started to close it later
            }
 
            if (nextElement[0] == PathIterator.SEG_LINETO) {
                lineSegments.add(
                        new Line2D.Double(
                            currentElement[1], currentElement[2],
                            nextElement[1], nextElement[2]
                        )
                    );
            } else if (nextElement[0] == PathIterator.SEG_CLOSE) {
                lineSegments.add(
                        new Line2D.Double(
                            currentElement[1], currentElement[2],
                            start[1], start[2]
                        )
                    );
            }
        }
        return lineSegments;
    }

	public static String readFile(String path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static Dimension getGameSize() {
		return java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
	}
}
