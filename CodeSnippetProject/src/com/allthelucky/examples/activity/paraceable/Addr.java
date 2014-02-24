package com.allthelucky.examples.activity.paraceable;

import java.io.Serializable;

/**
 * 地址信息类
 * 
 * @author pxw
 * 
 */
public class Addr implements Serializable {
    private static final long serialVersionUID = 170775953902767421L;
    public String addr;
    public String post;
    
    public String getAddr() {
        return addr;
    }
    
    public Addr() {

    }
    
    public Addr(Addr a) {
        this.addr = a.addr;
        this.post = a.post;
    }

    @Override
    public String toString() {
        return addr + "," + post;
    }

}
