/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.util.Calendar;

/**
 * This class represents all checks a cachesolver did, no matter what coordinates are checked.
 * It is nessesary to check if a cachesolver did not had to many solve attempts.
 * @author Thomas
 */
public class Check {
    private int sum;//The sum of all checks a cachesolver did 
    private Calendar cal;//The time when the cachesolver gets new attempts to solve a cache
    
    /**
     * This method increases the sum by one
     */
    public void increase() {
        sum++;
    }

    /*
    Constructor
    */
    public Check(int sum, Calendar cal) {
        this.sum = sum;
        this.cal = cal;
    }
    /*
    Getter and setter
    */
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }
}
