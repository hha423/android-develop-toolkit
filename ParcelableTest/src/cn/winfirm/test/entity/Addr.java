package cn.winfirm.test.entity;

import java.io.Serializable;

/**
 * 地址信息类
 * 
 * @author pxw
 * 
 */
public class Addr implements Serializable {
    private static final long serialVersionUID = 170775953902767421L;
    String addr;
    String post;
    
    public String getAddr() {
        return addr;
    }
    
    public Addr() {

    }
    
    public Addr(Addr a) {
        this.addr = a.addr;
        this.post = a.post;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return addr + "," + post;
    }

}
