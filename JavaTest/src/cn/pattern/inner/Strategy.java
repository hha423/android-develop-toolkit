package cn.pattern.inner;

/**
 * Strategy abstraction class
 * 
 * @author pxw
 * 
 */
public abstract class Strategy {
    private String strategyName;

    public Strategy() {
        strategyName = setName();
    }

    public void show() {
        System.out.println("TAKE:" + strategyName);
    }

    protected abstract String setName();
}
