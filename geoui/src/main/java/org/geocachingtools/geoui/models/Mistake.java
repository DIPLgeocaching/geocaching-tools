/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

/**
 * This class is need to calculate all mistakes that had been done with the same
 * coordinates. This class is need for the statistics. It is nessesary for the
 * 10 most common mistakes.
 *
 * @author Thomas
 */
public class Mistake {

    private double coordinateX; //x-coordinate
    private double coordinateY; //y-coordinate
    private int amount = 0; //amount how often these coordinates had been checked

    /**
     * increases the amount by one
     */
    public void increase() {
        amount = amount + 1;
    }
    
    /*
    construcor
     */
    public Mistake(double x, double y, int a) {
        this.coordinateX = x;
        this.coordinateY = y;
        this.amount = a;
    }

    /*
    Getter and Setter
    */

    public double getCoordinateX() {
        return coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public int getAmount() {
        return amount;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
