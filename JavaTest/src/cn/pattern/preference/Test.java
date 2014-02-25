package cn.pattern.preference;

public class Test  extends ContextWrapper{

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Test().test();
    }

    void test() {
        SharePreference shared = (SharePreferenceImpl) getSystemService("share");
        Editor editor = shared.edit();
        editor.put("hello", "world");
        editor.put("fuck", "you");
        editor.commit();
    }
}
