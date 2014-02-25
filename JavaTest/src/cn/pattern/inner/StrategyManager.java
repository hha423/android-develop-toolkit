package cn.pattern.inner;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy manager
 * 
 * @author pxw
 * 
 */
public class StrategyManager {
    List<Strategy> list = new ArrayList<Strategy>();

    public void add(Strategy strategy) {
        list.add(strategy);
    }

    public Taker getTaker() {
        return new StrategyTaker();
    }

    final class StrategyTaker implements Taker {
        private int location = 0;

        @Override
        public Strategy getStrategy() {
            return list.get(location++);
        }
    }

}
