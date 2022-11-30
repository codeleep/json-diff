package me.codeleep.jsondiff.spi.handle.object;

import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.spi.model.object.AbstractDiffJsonObject;

import java.util.Set;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 15:25
 * @description: 抽象数组处理器
 */
public abstract class AbstractObjectHandle implements ObjectHandle {


    @Override
    public DiffProcessResultStack handle(Object expect, Object actual) {
        return handleObject((AbstractDiffJsonObject)expect, (AbstractDiffJsonObject) actual);
    }

    /**
     * 抽象对象对比的一些公共逻辑
     * @param expectObject 期望json对象
     * @param actualObject 实际json对象
     * @return 返回对比结果
     */
    @Override
    public DiffProcessResultStack handleObject(AbstractDiffJsonObject expectObject, AbstractDiffJsonObject actualObject) {

        // TODO 忽略的path

        // 两个都为null
        if (expectObject == null && actualObject == null) {
            return null;
        }
        Set<String> expectKeys = expectObject.keySet();
        Set<String> actualKeys = actualObject.keySet();
        // 空对象
        if (expectKeys.size() == 0 && actualKeys.size() == 0) {
            return null;
        }
        return doObjectHandle(expectObject, actualObject);
    }

    /**
     * 子类更细粒度实现
     * @param expectObject 期望json对象
     * @param actualObject 实际json对象
     * @return 对比结果
     */
    protected abstract DiffProcessResultStack doObjectHandle(AbstractDiffJsonObject expectObject, AbstractDiffJsonObject actualObject);

    /**
     * 描述只存在于一个集合的key
     */
    protected class ExtraKey {

        private boolean expectExit = false;

        private boolean actualExit = false;

        private String key = null;

        public ExtraKey(boolean expectExit, boolean actualExit, String key) {
            this.expectExit = expectExit;
            this.actualExit = actualExit;
            this.key = key;
        }

        public boolean isExpectExit() {
            return expectExit;
        }

        public boolean isActualExit() {
            return actualExit;
        }

        public String getKey() {
            return key;
        }
    }

}
