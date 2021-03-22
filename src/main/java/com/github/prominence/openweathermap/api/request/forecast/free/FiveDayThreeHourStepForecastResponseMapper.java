/*
 * Copyright (c) 2021 Alexey Zinchenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.prominence.openweathermap.api.request.forecast.free;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.*;
import com.github.prominence.openweathermap.api.model.forecast.*;
import com.github.prominence.openweathermap.api.model.forecast.Location;
import com.github.prominence.openweathermap.api.model.forecast.Rain;
import com.github.prominence.openweathermap.api.model.forecast.Snow;
import com.github.prominence.openweathermap.api.model.forecast.Temperature;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Official API response documentation.
 * Parameters(but the real response can differ):
 * --- cod Internal parameter
 * --- message Internal parameter
 * --- cnt A number of timestamps returned in the API response
 * --- list
 *      |- list.dt Time of data forecasted, unix, UTC
 *      |- list.main
 *          |- list.main.temp Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
 *          |- list.main.feels_like This temperature parameter accounts for the human perception of weather. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
 *          |- list.main.temp_min Minimum temperature at the moment of calculation. This is minimal forecasted temperature (within large megalopolises and urban areas), use this parameter optionally. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
 *          |- list.main.temp_max Maximum temperature at the moment of calculation. This is maximal forecasted temperature (within large megalopolises and urban areas), use this parameter optionally. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit.
 *          |- list.main.pressure Atmospheric pressure on the sea level by default, hPa
 *          |- list.main.sea_level Atmospheric pressure on the sea level, hPa
 *          |- list.main.grnd_level Atmospheric pressure on the ground level, hPa
 *          |- list.main.humidity Humidity, %
 *          |- list.main.temp_kf Internal par
 *      |- list.weather
 *          |- list.weather.id Weather condition id
 *          |- list.weather.main Group of weather parameters (Rain, Snow, Extreme etc.)
 *          |- list.weather.description Weather condition within the group. You can get the output in your language.
 *          |- list.weather.icon Weather icon id
 *      |- list.clouds
 *          |- list.clouds.all Cloudiness, %
 *      |- list.wind
 *          |- list.wind.speed Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
 *          |- list.wind.deg Wind direction, degrees (meteorological)
 *      |- list.visibility Average visibility, metres
 *      |- list.pop Probability of precipitation
 *      |- list.rain
 *          |- list.rain.3h Rain volume for last 3 hours, mm
 *      |- list.snow
 *          |- list.snow.3h Snow volume for last 3 hours
 *      |- list.sys
 *          |- list.sys.pod Part of the day (n - night, d - day)
 *      |- list.dt_txt Time of data forecasted, ISO, UTC
 * --- city
 *      |- city.id City ID
 *      |- city.name City name
 *      |- city.coord
 *          |- city.coord.lat City geo location, latitude
 *          |- city.coord.lon City geo location, longitude
 *      |- city.country Country code (GB, JP etc.)
 *      |- city.timezone Shift in seconds from UTC
 */
public class FiveDayThreeHourStepForecastResponseMapper {

    private final UnitSystem unitSystem;

    public FiveDayThreeHourStepForecastResponseMapper(UnitSystem unitSystem) {
        this.unitSystem = unitSystem;
    }

    public Forecast mapToForecast(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Forecast forecast;
        try {
            JsonNode root = objectMapper.readTree(json);
            forecast = mapToForecast(root);
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse Forecast response");
        }

        return forecast;
    }

    private Forecast mapToForecast(JsonNode root) {
        Forecast forecast = new Forecast();
        forecast.setLocation(parseLocation(root.get("city")));

        List<WeatherForecast> forecasts = new ArrayList<>(root.get("cnt").asInt());

        JsonNode forecastListNode = root.get("list");
        forecastListNode.forEach(forecastNode -> forecasts.add(parseWeatherForecast(forecastNode)));

        forecast.setWeatherForecasts(forecasts);

        return forecast;
    }

    private WeatherForecast parseWeatherForecast(JsonNode rootNode) {
        WeatherForecast weatherForecast = new WeatherForecast();

        weatherForecast.setForecastTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(rootNode.get("dt").asLong()), TimeZone.getDefault().toZoneId()));
        weatherForecast.setForecastTimeISO(rootNode.get("dt_txt").asText());

        JsonNode weatherNode = rootNode.get("weather").get(0);
        weatherForecast.setState(weatherNode.get("main").asText());
        weatherForecast.setDescription(weatherNode.get("description").asText());
        weatherForecast.setWeatherIconUrl("https://openweathermap.org/img/w/" + weatherNode.get("icon").asText() + ".png");

        JsonNode mainNode = rootNode.get("main");
        weatherForecast.setTemperature(parseTemperature(mainNode));
        weatherForecast.setPressure(parsePressure(mainNode));
        weatherForecast.setHumidity(parseHumidity(mainNode));
        weatherForecast.setClouds(parseClouds(rootNode));
        weatherForecast.setWind(parseWind(rootNode));
        weatherForecast.setRain(parseRain(rootNode));
        weatherForecast.setSnow(parseSnow(rootNode));

        JsonNode sysNode = rootNode.get("sys");
        if (sysNode != null) {
            weatherForecast.setDayTime("d".equals(sysNode.get("pod").asText()) ? DayTime.DAY : DayTime.NIGHT);
        }

        return weatherForecast;
    }

    private Temperature parseTemperature(JsonNode rootNode) {
        final double tempValue = rootNode.get("temp").asDouble();
        Temperature temperature = new Temperature(tempValue, UnitSystem.getTemperatureUnit(unitSystem));

        final JsonNode tempMaxNode = rootNode.get("temp_max");
        if (tempMaxNode != null) {
            temperature.setMaxTemperature(tempMaxNode.asDouble());
        }
        final JsonNode tempMinNode = rootNode.get("temp_min");
        if (tempMinNode != null) {
            temperature.setMinTemperature(tempMinNode.asDouble());
        }
        final JsonNode tempFeelsLike = rootNode.get("fells_like");
        if (tempFeelsLike != null) {
            temperature.setFeelsLike(tempFeelsLike.asDouble());
        }

        return temperature;
    }

    private Pressure parsePressure(JsonNode rootNode) {
        Pressure pressure = new Pressure(rootNode.get("pressure").asDouble());

        final JsonNode seaLevelNode = rootNode.get("sea_level");
        final JsonNode groundLevelNode = rootNode.get("grnd_level");
        if (seaLevelNode != null) {
            pressure.setSeaLevelValue(seaLevelNode.asDouble());
        }
        if (groundLevelNode != null) {
            pressure.setGroundLevelValue(groundLevelNode.asDouble());
        }

        return pressure;
    }

    private Humidity parseHumidity(JsonNode rootNode) {
        return new Humidity((byte) (rootNode.get("humidity").asInt()));
    }

    private Wind parseWind(JsonNode root) {
        final JsonNode windNode = root.get("wind");
        double speed = windNode.get("speed").asDouble();

        Wind wind = new Wind(speed, UnitSystem.getWindUnit(unitSystem));
        final JsonNode degNode = windNode.get("deg");
        if (degNode != null) {
            wind.setDegrees(degNode.asDouble());
        }

        return wind;
    }

    private Rain parseRain(JsonNode root) {
        Rain rain = null;

        final JsonNode rainNode = root.get("rain");
        if (rainNode != null) {
            rain = new Rain();
            final JsonNode threeHourNode = rainNode.get("3h");
            if (threeHourNode != null) {
                rain.setThreeHourRainLevel(threeHourNode.asDouble());
            }
        }

        return rain;
    }

    private Snow parseSnow(JsonNode root) {
        Snow snow = null;

        final JsonNode snowNode = root.get("snow");

        if (snowNode != null) {
            snow = new Snow();
            final JsonNode threeHourNode = snowNode.get("3h");
            if (threeHourNode != null) {
                snow.setThreeHourSnowLevel(threeHourNode.asDouble());
            }
        }

        return snow;
    }

    private Clouds parseClouds(JsonNode rootNode) {
        Clouds clouds = null;

        final JsonNode cloudsNode = rootNode.get("clouds");
        final JsonNode allValueNode = cloudsNode.get("all");
        if (allValueNode != null) {
            clouds = new Clouds((byte) allValueNode.asInt());
        }

        return clouds;
    }

    private Location parseLocation(JsonNode rootNode) {
        Location location = new Location(rootNode.get("id").asInt(), rootNode.get("name").asText());

        final JsonNode timezoneNode = rootNode.get("timezone");
        if (timezoneNode != null) {
            location.setZoneOffset(ZoneOffset.ofTotalSeconds(timezoneNode.asInt()));
        }

        final JsonNode countryNode = rootNode.get("country");
        if (countryNode != null) {
            location.setCountryCode(countryNode.asText());
        }

        final JsonNode sunriseNode = rootNode.get("sunrise");
        final JsonNode sunsetNode = rootNode.get("sunset");
        if (sunriseNode != null) {
            location.setSunrise(LocalDateTime.ofInstant(Instant.ofEpochSecond(sunriseNode.asLong()), TimeZone.getDefault().toZoneId()));
        }
        if (sunsetNode != null) {
            location.setSunset(LocalDateTime.ofInstant(Instant.ofEpochSecond(sunsetNode.asLong()), TimeZone.getDefault().toZoneId()));
        }

        final JsonNode coordNode = rootNode.get("coord");
        if (coordNode != null) {
            location.setCoordinate(parseCoordinate(coordNode));
        }

        final JsonNode populationNode = rootNode.get("population");
        if (populationNode != null) {
            location.setPopulation(populationNode.asLong());
        }

        return location;
    }

    private Coordinate parseCoordinate(JsonNode rootNode) {
        JsonNode latitudeNode = rootNode.get("lat");
        JsonNode longitudeNode = rootNode.get("lon");
        if (latitudeNode != null && longitudeNode != null) {
            return new Coordinate(latitudeNode.asDouble(), longitudeNode.asDouble());
        }
        return null;
    }
}
