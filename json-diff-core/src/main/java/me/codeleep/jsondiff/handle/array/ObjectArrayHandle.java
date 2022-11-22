package me.codeleep.jsondiff.handle.array;

import me.codeleep.jsondiff.spi.object.DiffJsonObject;
import me.codeleep.jsondiff.spi.function.Function;
import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.handle.object.AbstractObjectHandle;
import me.codeleep.jsondiff.model.Defects;
import me.codeleep.jsondiff.utils.ComparedUtil;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author: codeleep
 * @createTime: 2022/07/17 16:42
 * @description: 元素是对象的处理器
 */
public class ObjectArrayHandle extends AbstractArrayHandle {


    /**
     * 不忽略顺序
     * @param expect
     * @param actual
     */
    @Override
    public void compareKeepOrder(Object[] expect, Object[] actual) {
        for (int i = 0; i < expect.length; i++) {
            DiffJsonObject expectItem = (DiffJsonObject)expect[i];
            DiffJsonObject actualItem = (DiffJsonObject)actual[i];
            try {
                RunTimeDataFactory.getCurrentPathInstance().push(String.format("[%d]", i));
                AbstractObjectHandle handle = (AbstractObjectHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getObjectHandleClass(expectItem, actualItem));
                handle.handle(expectItem , actualItem);
            }catch (Exception e) {
                Defects defects = new Defects()
                        .setActual(actualItem)
                        .setExpect(expectItem)
                        .setIllustrate(String.format("The %d element is inconsistent", i))
                        .setIndexPath(String.format("%s[%d]", getCurrentPath(), i));
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }finally {
                RunTimeDataFactory.getCurrentPathInstance().pop();
            }
        }
    }


    /**
     * 忽略顺序
     * 1. 可以根据用户指定的字段进行匹配
     * 2. 不指定排序字段的时候, 时间复杂度是2n。需要n*n比对
     * @param expect
     * @param actual
     */
    @Override
    public void compareIgnoreOrder(Object[] expect, Object[] actual) {

        /**
         * 1. 遍历期望数组。在 actual 数组中查找。
         *       - 如果找到。给该元素打上标签；后续数组匹配不允许再使用
         *       - 如果都没有找到。就记录错误值
         * 2. 将actual 没有被匹配到的。和 expect 没有匹配到的一一比对
         */
        boolean[] actualSign = new boolean[actual.length];
        boolean[] expectSign = new boolean[expect.length];

        // 获取用户指定的keys
        Function<String, Stack<String>> keyFunction = RunTimeDataFactory.getOptionInstance().getKeyFunction();
        Stack<String> keys = null;
        if (keyFunction != null) {
            keys = keyFunction.apply(JsonDiffUtil.convertPath(getCurrentPath(), RunTimeDataFactory.getTempDataInstance().getPath()));
        }

        for (int i = 0; i < expect.length; i++) {
            RunTimeDataFactory.getCurrentPathInstance().push(String.format("[%d]", i));
            int index = getCompareObject(expect[i], actual, Arrays.copyOf(actualSign, actualSign.length), keys);
            if (index >= 0) {
                actualSign[index] = true;
                expectSign[i] = true;
            }
            RunTimeDataFactory.getCurrentPathInstance().pop();
        }


        // 遍历未匹配到的元素
        int i,j = 0;
        for (i = 0; i < expectSign.length; i++) {
            if (expectSign[i]) {
                continue;
            }
            RunTimeDataFactory.getCurrentPathInstance().push(String.format("[%d]", i));
            // 找到j的位置
            for (j = 0; j < actualSign.length; j++) {
                if (!actualSign[j]) {
                    break;
                }
            }
            if (j >= actualSign.length) {
                break;
            }
            try {
                actualSign[j] = true;
                expectSign[i] = true;
                AbstractObjectHandle handle = (AbstractObjectHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getObjectHandleClass((DiffJsonObject) expect[i], (DiffJsonObject) actual[j]));
                handle.handle((DiffJsonObject) expect[i], (DiffJsonObject) actual[j]);
            }catch (Exception ignored) {
                Defects defects = new Defects()
                        .setActual(actual[j])
                        .setExpect(expect[i])
                        .setIllustrate(String.format("The %d element is inconsistent", i))
                        .setIndexPath(getCurrentPath());
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }
            RunTimeDataFactory.getCurrentPathInstance().pop();
        }
    }


    /**
     * 根据expect找出actual中与其匹配的。
     * @param expect
     * @param actuals
     * @return 未匹配到返回 -1
     */
    private int getCompareObject(Object expect, Object[] actuals, boolean[] actualSign, Stack<String> keys) {
        int index = -1;
        RunTimeDataFactory.getTempDataInstance().setAddDiff(false);
        for (int i = 0; i < actuals.length; i++) {
            if (isMatchComparison((DiffJsonObject) expect, (DiffJsonObject) actuals[i], actualSign[i], keys)) {
                continue;
            }
            try {
                RunTimeDataFactory.getTempDataInstance().clear();
                AbstractObjectHandle handle = (AbstractObjectHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getObjectHandleClass((DiffJsonObject) expect, (DiffJsonObject) actuals[i]));
                handle.handle((DiffJsonObject) expect, (DiffJsonObject) actuals[i]);
                if (RunTimeDataFactory.getTempDataInstance().isDefectsEmpty()) {
                    index = i;
                    break;
                }
            }catch (Exception ignored) {

            }finally {
                RunTimeDataFactory.getTempDataInstance().clear();
            }
        }
        RunTimeDataFactory.getTempDataInstance().setAddDiff(true);
        return index;
    }

    /**
     * 判断两个对象是否符合比较条件
     * @param expect
     * @param actual
     * @return
     */
    private boolean isMatchComparison(DiffJsonObject expect, DiffJsonObject actual, boolean actualSign, Stack<String> keys) {
        // 都是空对象
        if (expect.size() == 0 && actual.size() == 0) {
            return false;
        }
        if (keys == null || keys.size() == 0) {
            return actualSign;
        }
        return ComparedUtil.isItWorthComparing(expect, actual, keys);
    }

}