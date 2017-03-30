/**
 * The MIT License (MIT)
 *
 * Copyright (c) [2016] [Geocaching-Tools: Stefan Kurzbauer, Jakob Geringer,
 * Thomas Rapberger, Lukas Wallenboeck, Simon Lehner-Dittenberger]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

(function (self, undefined) {

    self.filterGreyscale = function (pixels) {
        var d = pixels.data;
        for (var i = 0; i < d.length; i += 4) {
            var r = d[i];
            var g = d[i + 1];
            var b = d[i + 2];
            // CIE luminance for the RGB
            var v = 0.2126 * r + 0.7152 * g + 0.0722 * b;
            d[i] = d[i + 1] = d[i + 2] = v;
        }
        return pixels;
    };
    self.filterPickColor = function (pixels, rr, gg, bb, tolerance) {
        var d = pixels.data;
        var count = 0;
        for (var i = 0; i < d.length; i += 4) {
            var r = d[i] - rr;
            var g = d[i + 1] - gg;
            var b = d[i + 2] - bb;
            var distance = Math.cbrt((r * r) + (g * g) + (b * b));
            var found = distance <= tolerance;

            d[i] = d[i + 1] = d[i + 2] = found ? 255 : 0;
            d[i + 3] = 125;
            count += found ? 1 : 0;
        }
        return pixels;
    };
    var filterConvoluteCalculateSum = function (pixels, conv, sx, sy) {
        var width = pixels.width;
        var height = pixels.height;
        var size = Math.round(Math.sqrt(conv.length));
        var result = {r: 0, g: 0, b: 0, a: 0};
        for (var x = 0; x < size; x++) {
            for (var y = 0; y < size; y++) {
                var index = ((sy + y) * width + (sx + x)) * 4;
                var weight = conv[y * size + x];
                result.r += pixels.data[index + 0] * weight;
                result.g += pixels.data[index + 1] * weight;
                result.b += pixels.data[index + 2] * weight;
                result.a += pixels.data[index + 3] * weight;
            }
        }
        return result;
    };
    self.filterConvolute = function (pixels, conv, offset = 0) {
        var tmpcanvas = document.createElement('canvas');
        var ctx = tmpcanvas.getContext('2d');
        var width = pixels.width;
        var height = pixels.height;
        var result = ctx.createImageData(width, height);
        var size = Math.round(Math.sqrt(conv.length));
        var sizehalf = Math.floor(size / 2);
        for (var x = Math.max(sizehalf, offset); x < width - sizehalf; x++) {
            for (var y = sizehalf; y < height - sizehalf; y++) {
                var index = (y * width + x) * 4;
                var color = filterConvoluteCalculateSum(pixels, conv, x - sizehalf, y - sizehalf);
                result.data[index + 0] = color.r;
                result.data[index + 1] = color.g;
                result.data[index + 2] = color.b;
                result.data[index + 3] = color.a;
            }
        }

        return result;
    };

    self.filterSobel = function (source) {
        vertical = self.filterConvolute(source,
                [-1, 0, 1,
                    -2, 0, 2,
                    -1, 0, 1]);
        horizontal = self.filterConvolute(source,
                [-1, -2, -1,
                    0, 0, 0,
                    1, 2, 1]);
        for (var i = 0; i < source.data.length; i += 4) {
            // make the vertical gradient red
            var v = Math.abs(vertical.data[i]);
            source.data[i] = v;
            // make the horizontal gradient green
            var h = Math.abs(horizontal.data[i]);
            source.data[i + 1] = h;
            // and mix in some blue for aesthetics
            source.data[i + 2] = (v + h) / 4;
            source.data[i + 3] = 255; // opaque alpha
        }
        return source;
    };
    self.filterEdge = function (source) {
        return self.filterConvolute(source,
                [-1, -1, -1,
                    -1, 8, -1,
                    -1, -1, -1]);
    };
    self.filterGaussian = function (source, offset = 0) {
        var array = [
            1, 4, 6, 4, 1,
            4, 16, 24, 16, 4,
            6, 24, 36, 34, 6,
            4, 16, 24, 16, 4,
            1, 4, 6, 4, 1
        ];
        for (var i = 0; i < array.length; i++) {
            array[i] /= 256.0;
        }
        return self.filterConvolute(source, array, offset);
    };

    self.filterMark = function (pixels, style = '#fff', size = 30) {
        var tmpcanvas = document.createElement('canvas');
        var ctx = tmpcanvas.getContext('2d');
        var width = pixels.width;
        var height = pixels.height;
        tmpcanvas.width = width;
        tmpcanvas.height = height;
        ctx.clearRect(0, 0, width, height);
        ctx.strokeStyle = style;
        ctx.lineWidth = size;
        ctx.lineJoin = 'round';
        for (var x = 0; x < width; x++) {
            for (var y = 0; y < height; y++) {
                var index = (y * width + x) * 4;
                var grey = (pixels.data[index] + pixels.data[index + 1] + pixels.data[index + 2]) / 3;
                if (grey > 127) {
                    ctx.beginPath();
                    ctx.moveTo(x, y);
                    ctx.lineTo(x + 1, y + 1);
                    ctx.closePath();
                    ctx.stroke();
                }
            }
        }
        return ctx.getImageData(0, 0, width, height);
    };



}(window.stereo = window.stereo || {}));