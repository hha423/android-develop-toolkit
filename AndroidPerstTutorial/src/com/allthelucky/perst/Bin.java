package com.allthelucky.perst;

import org.garret.perst.Persistent;

/**
 * ä¿¡æ?¯ç±»
 */
public class Bin extends Persistent {
    public   String number;
    public   String message;
    
    public Bin() {

    }
    
    @Override
    public String toString() {
        return this.number+","+this.message;
    }
}
