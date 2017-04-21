/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.geoui.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * This class represents an invitekey a user can create. With this key an other
 * person can register itselfs to the checker
 *
 * @author Schule
 */
@Entity
@Table(name = "invitekey")
public class Invitekey implements Serializable {

    @Id
    @Column(name = "invkey")
    private String invkey;//key itself

    @OneToOne
    private Gctuser usedby;

    /*
    Construcotr
     */
    public Invitekey() {
    }

    public Invitekey(String invkey) {
        this.invkey = invkey;
    }

    /*
    Getter and Setter
     */
    public String getInvkey() {
        return invkey;
    }

    public void setInvkey(String invkey) {
        this.invkey = invkey;
    }

    public Gctuser getUsedby() {
        return usedby;
    }

    public void setUsedby(Gctuser usedby) {
        this.usedby = usedby;
    }

}
