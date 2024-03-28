package com.food.delivery.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Setter;

import java.util.List;

@Setter
@XmlRootElement(name = "observations")
public class WeatherStationsWrapper {

    private List<WeatherStation> stations;

    @XmlElement(name = "station")
    public List<WeatherStation> getStations() {
        return stations;
    }

}
