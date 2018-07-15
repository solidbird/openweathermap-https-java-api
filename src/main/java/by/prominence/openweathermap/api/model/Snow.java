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

package by.prominence.openweathermap.api.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Objects;

public class Snow {

    @JSONField(name = "all")
    // Snow volume for the last 3 hours
    private byte snowVolumeLast3Hrs;

    public byte getSnowVolumeLast3Hrs() {
        return snowVolumeLast3Hrs;
    }

    public void setSnowVolumeLast3Hrs(byte snowVolumeLast3Hrs) {
        this.snowVolumeLast3Hrs = snowVolumeLast3Hrs;
    }

    public String getUnit() {
        return "mm";
    }

    @Override
    public String toString() {
        return "Snow(last 3 hrs): " + snowVolumeLast3Hrs + ' ' + getUnit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snow snow = (Snow) o;
        return snowVolumeLast3Hrs == snow.snowVolumeLast3Hrs;
    }

    @Override
    public int hashCode() {

        return Objects.hash(snowVolumeLast3Hrs);
    }
}