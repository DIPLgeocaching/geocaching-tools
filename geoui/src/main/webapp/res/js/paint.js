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

    var painting = false;
    var prev = {x: 0, y: 0};
    var overlay = undefined;
    var currentTool = 'pensil';


    function getMousePos(canvas, evt) {
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    }

    var startPaint = function (evt) {
        painting = true;
        prev = getMousePos(overlay, evt);
    };

    var drawWith = function (evt, color, size, op = 'source-over') {
        if (painting) {
            var ctx = overlay.getContext('2d');
            var pos = getMousePos(overlay, evt);

            ctx.strokeStyle = color;
            ctx.globalCompositeOperation = op;
            ctx.lineWidth = size;
            ctx.lineJoin = 'round';
            ctx.beginPath();
            ctx.moveTo(prev.x, prev.y);
            ctx.lineTo(pos.x, pos.y);
            ctx.closePath();
            ctx.stroke();

            prev = pos;
    }
    };
    var endPaint = function (e) {
        painting = false;
    };
    var drawChooser = function (evt) {
        if (currentTool === 'rubber') {
            drawWith(evt, '#fff', 53, 'destination-out');
        }
        if (currentTool === 'pensil') {
            drawWith(evt, '#df4b26', 5);
        }
    };

    self.init = function (canvas) {
        if (overlay !== undefined)
            return;
        overlay = canvas;
        overlay.addEventListener("mousedown", startPaint, false);
        overlay.addEventListener("mouseup", endPaint, false);
        window.addEventListener("mousemove", drawChooser, false);
        window.addEventListener("mouseup", endPaint, false);
    };

    self.setCurrentTool = function (tool) {
        currentTool = tool;
    };
    
}(window.paint = window.paint || {}));