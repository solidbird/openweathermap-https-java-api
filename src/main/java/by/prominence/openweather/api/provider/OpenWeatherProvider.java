/*
 * Copyright (c) 2018 Alexey Zinchenko
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

package by.prominence.openweather.api.provider;

import by.prominence.openweather.api.exception.DataNotFoundException;
import by.prominence.openweather.api.exception.InvalidAuthTokenException;
import by.prominence.openweather.api.model.Coordinates;
import by.prominence.openweather.api.model.OpenWeatherResponse;

public interface OpenWeatherProvider {

    OpenWeatherResponse getByCityId(String id) throws InvalidAuthTokenException, DataNotFoundException;
    OpenWeatherResponse getByCityName(String name) throws InvalidAuthTokenException, DataNotFoundException;
    OpenWeatherResponse getByCoordinates(double latitude, double longitude) throws InvalidAuthTokenException, DataNotFoundException;
    OpenWeatherResponse getByCoordinates(Coordinates coordinates) throws InvalidAuthTokenException, DataNotFoundException;
    OpenWeatherResponse getByZIPCode(String zipCode, String countryCode) throws InvalidAuthTokenException, DataNotFoundException;

    OpenWeatherProvider setLanguage(String language);
    OpenWeatherProvider setUnit(String unit);
    OpenWeatherProvider setAccuracy(String accuracy);

}
