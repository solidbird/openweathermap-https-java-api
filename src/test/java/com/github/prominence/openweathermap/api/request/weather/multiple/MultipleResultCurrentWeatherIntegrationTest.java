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

package com.github.prominence.openweathermap.api.request.weather.multiple;

import com.github.prominence.openweathermap.api.ApiTest;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.exception.InvalidAuthTokenException;
import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.model.CoordinateRectangle;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MultipleResultCurrentWeatherIntegrationTest extends ApiTest {

    @Test
    public void whenGetMultipleCurrentWeatherByCoordinateRequestAsJava_thenReturnNotNull() {
        final List<Weather> weatherList = getClient()
                .currentWeather()
                .multiple()
                .byRectangle(new CoordinateRectangle(12, 32, 15, 37), 10)
                .language(Language.ROMANIAN)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        assert weatherList != null;
        assert weatherList.size() > 0;
        System.out.println(weatherList);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCoordinateAndServerClusteringRequestAsJava_thenReturnNotNull() {
        final List<Weather> weatherList = getClient()
                .currentWeather()
                .multiple()
                .byRectangle(new CoordinateRectangle(12, 32, 15, 37), 10, true)
                .language(Language.ROMANIAN)
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        assert weatherList != null;
        assert weatherList.size() > 0;
        System.out.println(weatherList);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCitiesInCycleRequestAsJava_thenReturnNotNull() {
        final List<Weather> weatherList = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieve()
                .asJava();

        assert weatherList != null;
        assert weatherList.size() > 0;
        System.out.println(weatherList);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCitiesInCycleAndServerClusteringRequestAsJava_thenReturnNotNull() {
        final List<Weather> weatherList = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10, true)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieve()
                .asJava();

        assert weatherList != null;
        assert weatherList.size() > 0;
        System.out.println(weatherList);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCitiesInCycleRequestAsJson_thenReturnNotNull() {
        final String weather = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieve()
                .asJSON();

        assert weather != null;
        System.out.println(weather);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCitiesInCycleRequestAsXml_thenReturnNotNull() {
        final String weather = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieve()
                .asXML();

        assert weather != null;
        System.out.println(weather);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCitiesInCycleRequestAsHtml_thenReturnNotNull() {
        final String weather = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieve()
                .asHTML();

        assert weather != null;
        System.out.println(weather);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCoordinateAndServerClusteringAsyncRequestAsJava_thenReturnNotNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<List<Weather>> weatherListFuture = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10, true)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieveAsync()
                .asJava();

        assert weatherListFuture != null;
        List<Weather> weatherList = weatherListFuture.get();
        assert weatherList.size() > 0;
        System.out.println(weatherList);
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCoordinateAndServerClusteringAsyncRequestAsXml_thenReturnNotNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> weatherFuture = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10, true)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieveAsync()
                .asXML();

        assert weatherFuture != null;
        System.out.println(weatherFuture.get());
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCoordinateAndServerClusteringAsyncRequestAsJson_thenReturnNotNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> weatherFuture = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10, true)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieveAsync()
                .asJSON();

        assert weatherFuture != null;
        System.out.println(weatherFuture.get());
    }

    @Test
    public void whenGetMultipleCurrentWeatherByCoordinateAndServerClusteringAsyncRequestAsHtml_thenReturnNotNull() throws ExecutionException, InterruptedException {
        final CompletableFuture<String> weatherFuture = getClient()
                .currentWeather()
                .multiple()
                .byCitiesInCycle(new Coordinate(55.5, 37.5), 10, true)
                .language(Language.GERMAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieveAsync()
                .asHTML();

        assert weatherFuture != null;
        System.out.println(weatherFuture.get());
    }

    @Test(expected = InvalidAuthTokenException.class)
    public void whenRequestCurrentWeatherWithInvalidApiKey_thenThrowAnException() {
        OpenWeatherMapClient client = new OpenWeatherMapClient("invalidKey");
        client
                .currentWeather()
                .single()
                .byCityName("London")
                .retrieve()
                .asJSON();
    }

    @Test(expected = NoDataFoundException.class)
    public void whenRequestCurrentWeatherForInvalidLocation_thenThrowAnException() {
        getClient()
                .currentWeather()
                .single()
                .byCityName("InvalidCity")
                .retrieve()
                .asJava();
    }
}
