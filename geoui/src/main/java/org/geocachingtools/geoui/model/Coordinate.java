/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

/**
 *
 * @author Thomas
 */
public class Coordinate {

    private boolean n;
    private boolean e;
    private double y1;
    private double y2;
    private double y3;
    private double x1;
    private double x2;
    private double x3;

    public double getDoubleCoordinateX() {
        return n ? (((x3 / 60) + x2) / 60) + x1 : ((((x3 / 60) + x2) / 60) + x1) + -1;
    }

    public double getDoubleCoordinateY() {
        return e ? (((y3 / 60) + y2) / 60) + y1 : ((((y3 / 60) + y2) / 60) + y1) + -1;
    }

    public Coordinate() {
    }

    public Coordinate(boolean n, boolean e, int y1, int y2, int y3, int x1, int x2, int x3) {
        this.n = n;
        this.e = e;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
    }

    public boolean isN() {
        return n;
    }

    public void setN(boolean n) {
        this.n = n;
    }

    public boolean isE() {
        return e;
    }

    public void setE(boolean e) {
        this.e = e;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public double getY3() {
        return y3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }
}
