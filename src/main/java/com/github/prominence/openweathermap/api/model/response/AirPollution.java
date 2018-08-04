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
 *
 */

package com.github.prominence.openweathermap.api.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode
public class AirPollution {

    @Getter
    @Setter
    private String time;

    @Getter
    @Setter
    @JSONField(name = "location")
    private Coordinates coordinates;

    @JSONField(name = "data")
    @Getter
    @Setter
    private List<AirPollutionInfo> airPollutionInfo;

    public Date getCalculationDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            return format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "AirPollution[Date: " + getCalculationDate() + "; Coordinates: " + coordinates + "]" + "\n" + airPollutionInfo;
    }

    @EqualsAndHashCode
    public static class AirPollutionInfo {

        @Getter
        @Setter
        private float precision;

        @Getter
        @Setter
        private short pressure;

        @Getter
        @Setter
        private float value;

        @Override
        public String toString() {
            return "Value: " + value;
        }
    }

    @EqualsAndHashCode
    public static class Coordinates {

        @Getter
        @Setter
        private float latitude;

        @Getter
        @Setter
        private float longitude;

        @Override
        public String toString() {
            return "latitude=" + latitude + ", longitude=" + longitude;
        }
    }
}