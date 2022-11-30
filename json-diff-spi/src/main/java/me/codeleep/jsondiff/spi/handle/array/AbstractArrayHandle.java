package me.codeleep.jsondiff.spi.handle.array;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.spi.model.array.AbstractDiffJsonArray;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 15:25
 * @description: 抽象数组处理器
 */
public abstract class AbstractArrayHandle implements ArrayHandle {

    @Override
    public DiffProcessResultStack handle(Object expect, Object actual){
        return handleArray((AbstractDiffJsonArray)expect, (AbstractDiffJsonArray) actual);
    }

    /**
     * 抽象数组对比的一些公共逻辑
     * @param expectArray 期望json对象
     * @param actualArray 实际json对象
     * @return 返回对比的结果
     */
    @Override
    public DiffProcessResultStack handleArray(AbstractDiffJsonArray expectArray, AbstractDiffJsonArray actualArray) {
        // TODO 忽略的path
        int expectLen = expectArray.size();
        int actualLen = actualArray.size();

        if (actualLen != expectLen) {
            return null;
        }
        // 空数组
        if (actualLen == 0) {
            return null;
        }

        // 转化数组, 遍历加速
        Object[] expect = expectArray.toArray();
        Object[] actual = actualArray.toArray();

        if (RunTimeDataFactory.getOptionInstance().isIgnoreOrder()) {
            return compareIgnoreOrder(expect, actual);
        }else {
            return compareKeepOrder(expect, actual);
        }
    }

    /**
     * 对比保持顺序的对比
     * @param expect 期望数组
     * @param actual 实际数组
     * @return 返回对比的结果
     */
    protected abstract DiffProcessResultStack compareKeepOrder(Object[] expect, Object[] actual);

    /**
     * 对比忽略顺序的对比
     * @param expect 期望数组
     * @param actual 实际数组
     * @return 返回对比的结果
     */
    protected abstract DiffProcessResultStack compareIgnoreOrder(Object[] expect, Object[] actual);


    /**
     * 判断两个对象是否一致
     * @param obj1
     * @param obj2
     * @return 是否相等
     */
    protected boolean equalsItem(Object obj1, Object obj2) {
        throw new JsonDiffException("The element equality method must be implemented");
    }

    /**
     * 记录每个元素所出现的次数
     */
    protected class ObjectCount{

        private Object item;

        private int expectCount = 0;
        private int expectIndex = -1;

        private int actualCount = 0;
        private int actualIndex = -1;

        public ObjectCount(Object o) {
            this.item = o;
        }

        public ObjectCount risingExpectCount(Object target) {
            if (equalsItem(item, target)) {
                this.expectCount ++;
            }
            return this;
        }

        public ObjectCount risingActualCount(Object target) {
            if (equalsItem(item, target)) {
                this.actualCount ++;
            }
            return this;
        }

        public ObjectCount fallingExpectCount(Object target) {
            if (equalsItem(item, target)) {
                this.expectCount --;
            }
            return this;
        }

        public ObjectCount fallingActualCount(Object target) {
            if (equalsItem(item, target)) {
                this.actualCount --;
            }
            return this;
        }

        public int getExpectIndex() {
            return expectIndex;
        }

        public void setExpectIndex(int expectIndex) {
            this.expectIndex = expectIndex;
        }

        public int getActualIndex() {
            return actualIndex;
        }

        public void setActualIndex(int actualIndex) {
            this.actualIndex = actualIndex;
        }

        public int getExpectCount() {
            return expectCount;
        }

        public void setExpectCount(int expectCount) {
            this.expectCount = expectCount;
        }

        public int getActualCount() {
            return actualCount;
        }

        public void setActualCount(int actualCount) {
            this.actualCount = actualCount;
        }
    }

}
