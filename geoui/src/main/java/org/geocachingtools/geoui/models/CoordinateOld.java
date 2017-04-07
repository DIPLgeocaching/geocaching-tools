/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * This class is nessesary to convert all intputs from the user to a double.
 * When a coordiante is inserted there are to radio buttons for N/S and E/W and
 * six text fields. The instance variabels represent these inputfields.
 *
 * @author Thomas
 */
public class CoordinateOld {

    private boolean n;// N/S
    private boolean e;// E/W
    private int y1;// d from N/S
    private int y2;// ' from N/S
    private int y3;// " from N/S
    private int x1;// d from E/W
    private int x2;// ' from E/W
    private int x3;// " from E/W

    /**
     * This method calculates the x-coordinate from the user input
     *
     * @return double coordinate
     */
    public double getDoubleCoordinateX() {
        return n ? (((x3 / 60) + x2) / 60) + x1 : ((((x3 / 60) + x2) / 60) + x1) + -1;
    }

    /**
     * This method calculates the x-coordinate from the user input
     * @return double coordinate
     */
    public double getDoubleCoordinateY() {
        return e ? (((y3 / 60) + y2) / 60) + y1 : ((((y3 / 60) + y2) / 60) + y1) + -1;
    }
    
    /**
     * Validates if the userinput is in a valid range
     * @return true if it is in a valid range
     *         false if it isn't in a valid range
     */
    public boolean validateCoordinate() {
        return (this.getDoubleCoordinateY() > 90.0
                || this.getDoubleCoordinateY() < -90.0
                || this.getDoubleCoordinateX() > 180.0
                || this.getDoubleCoordinateX() < -180.0);
    }

    /**
     * This method encryptes a coordinate (double) with SHA-256
     * @param coord the coordinate to encrypt
     * @return the encrypted coordinate as a String
     */
    public String hashCoordinate(double coord) {
        MessageDigest md;
        String c = String.valueOf(coord);
        String hashedCoord = "";

        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] array = md.digest(c.getBytes(StandardCharsets.UTF_8));
            hashedCoord = Arrays.toString(array);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("NoSuchAlgorithmException in hashCoordinate\n" + ex);
        }
        return hashedCoord;
    }

    /*
    Constructor
    */
    
    public CoordinateOld() {
    }

    public CoordinateOld(boolean n, boolean e, int y1, int y2, int y3, int x1, int x2, int x3) {
        this.n = n;
        this.e = e;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
    }
    /*
    Getter and Setter
    */
    
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

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getY3() {
        return y3;
    }

    public void setY3(int y3) {
        this.y3 = y3;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }
}
