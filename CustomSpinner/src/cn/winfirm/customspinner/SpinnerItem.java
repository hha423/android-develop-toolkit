package cn.winfirm.customspinner;

/**
 * Spinner 条目信息类
 * 
 * @author pxw
 * 
 */
public class SpinnerItem implements Itemable {

    private String itemName;

    public SpinnerItem() {
        this.itemName = "无数据";
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = name;
    }

    @Override
    public String getItemTitle() {
        return itemName;
    }

    @Override
    public String toString() {
        return itemName;
    }
}
