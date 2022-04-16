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

import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.request.RequestSettings;

/**
 * The forecast requester.
 */
public class FiveDayThreeHourStepForecastRequester {
    private final RequestSettings requestSettings;

    /**
     * Instantiates a new forecast requester.
     *
     * @param requestSettings request settings object.
     */
    public FiveDayThreeHourStepForecastRequester(RequestSettings requestSettings) {
        this.requestSettings = requestSettings;
        this.requestSettings.appendToURL("forecast");
    }

    public FiveDayThreeHourStepForecastRequestCustomizer byCityName(String cityName) {
        requestSettings.putRequestParameter("q", cityName);
        return new FiveDayThreeHourStepForecastRequestCustomizer(requestSettings);
    }

    public FiveDayThreeHourStepForecastRequestCustomizer byCityName(String cityName, String stateCode) {
        requestSettings.putRequestParameter("q", cityName + "," + stateCode);
        return new FiveDayThreeHourStepForecastRequestCustomizer(requestSettings);
    }

    public FiveDayThreeHourStepForecastRequestCustomizer byCityName(String cityName, String stateCode, String countryCode) {
        requestSettings.putRequestParameter("q", cityName + "," + stateCode + "," + countryCode);
        return new FiveDayThreeHourStepForecastRequestCustomizer(requestSettings);
    }

    public FiveDayThreeHourStepForecastRequestCustomizer byCityId(long cityId) {
        requestSettings.putRequestParameter("id", Long.toString(cityId));
        return new FiveDayThreeHourStepForecastRequestCustomizer(requestSettings);
    }

    public FiveDayThreeHourStepForecastRequestCustomizer byCoordinate(Coordinate coordinate) {
        requestSettings.putRequestParameter("lat", String.valueOf(coordinate.getLatitude()));
        requestSettings.putRequestParameter("lon", String.valueOf(coordinate.getLongitude()));
        return new FiveDayThreeHourStepForecastRequestCustomizer(requestSettings);
    }

    public FiveDayThreeHourStepForecastRequestCustomizer byZipCodeAndCountry(String zipCode, String countryCode) {
        requestSettings.putRequestParameter("zip", zipCode + "," + countryCode);
        return new FiveDayThreeHourStepForecastRequestCustomizer(requestSettings);
    }

    public FiveDayThreeHourStepForecastRequestCustomizer byZipCodeInUSA(String zipCode) {
        requestSettings.putRequestParameter("zip", zipCode);
        return new FiveDayThreeHourStepForecastRequestCustomizer(requestSettings);
    }
}
