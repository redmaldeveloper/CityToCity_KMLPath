/*
    This class was created by Redmal on 3/11/2019.

    This class will utilize JSoup to grab
    coordinates for two cities specified
    by a user, and create a KML file
    showing a path between the two points.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class CityToCity {
    private String coordString;

    public CityToCity(String city1, String city2) throws IOException{
        setCoordString(getFormattedCoords(getCityCoords(city1)) + ",0\n" + getFormattedCoords(getCityCoords(city2)));
    }

    public void setCoordString(String s){
        coordString = s;
    }

    public String getCoordString(){
        return coordString;
    }

    // get the coordinates of the city specified by the user
    public String getCityCoords(String userSpecifiedCity) throws IOException {
        String cityCoords = "";

        String googleCoordsURL = "http://www.google.com/search?hl=en&btn=1&q=" + userSpecifiedCity + " coordinates";

        Document coordinateSearchResults = Jsoup.connect(googleCoordsURL).get();

        Elements coordResults = coordinateSearchResults.getElementsByClass("Z0LcW");


        //filter headers that contain getUserTopic
        GrabCoordsLoop: for (Element coordTextResults : coordResults){
            if (coordTextResults.text() != null){
                cityCoords = coordTextResults.text();
                break GrabCoordsLoop;
            }
        }
        return cityCoords;
    }


    // pass a coordset result here to return it stripped appropriately
    public String getFormattedCoords(String coords){
        // 40.7128° N, 74.0060° W
        coords = coords.replace("°", "");
        coords = coords.replace("N", "");
        coords = coords.replace("E", "");
        coords = coords.replace("S", "");
        coords = coords.replace("W", "");
        coords = coords.replaceAll("\\s","");
        // 40.7128,74.0060

        return coords;
    }
}
