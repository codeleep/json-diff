package me.codeleep.jsondiff.main.array;

import me.codeleep.jsondiff.handle.HandleExampleFactory;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.model.Defects;
import me.codeleep.jsondiff.spi.model.array.DiffJsonArray;
import me.codeleep.jsondiff.utils.JsonDiffUtil;

import java.util.Arrays;

/**
 * @author: codeleep
 * @createTime: 2022/08/12 23:50
 * @description: 数组的元素也是数组的处理器。多维数组
 */
public class MultidimensionalArrayHandle extends AbstractArrayHandle{


    @Override
    protected void compareKeepOrder(Object[] expect, Object[] actual) {
        for (int i = 0; i < expect.length; i++) {
            DiffJsonArray expectItem = (DiffJsonArray)expect[i];
            DiffJsonArray actualItem = (DiffJsonArray)actual[i];
            try {
                RunTimeDataFactory.getCurrentPathInstance().push(String.format("[%d]", i));
                AbstractArrayHandle handle = (AbstractArrayHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getArrayHandleClass(expectItem, actualItem));
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

        for (int i = 0; i < expect.length; i++) {
            RunTimeDataFactory.getCurrentPathInstance().push(String.format("[%d]", i));
            int index = getCompareObject(expect[i], actual, Arrays.copyOf(actualSign, actualSign.length));
            if (index >= 0) {
                actualSign[index] = true;
                expectSign[i] = true;
            }
            RunTimeDataFactory.getCurrentPathInstance().pop();
        }


        // 遍历未匹配到的元素
        int i,j = 0;
        for (i = 0; i < expectSign.length; i++) {
            RunTimeDataFactory.getCurrentPathInstance().push(String.format("[%d]", i));
            if (expectSign[i]) {
                RunTimeDataFactory.getCurrentPathInstance().pop();
                continue;
            }
            // 找到j的位置
            for (j = 0; j < actualSign.length; j++) {
                if (!actualSign[j]) {
                    break;
                }
            }
            try {
                actualSign[j] = true;
                expectSign[i] = true;
                AbstractArrayHandle handle = (AbstractArrayHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getArrayHandleClass((DiffJsonArray) expect[i], (DiffJsonArray) actual[j]));
                handle.handle((DiffJsonArray) expect[i], (DiffJsonArray) actual[j]);
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
    private int getCompareObject(Object expect, Object[] actuals, boolean[] actualSign) {
        int index = -1;
        RunTimeDataFactory.getTempDataInstance().setAddDiff(false);
        for (int i = 0; i < actuals.length; i++) {
            if (actualSign[i]) {
                continue;
            }
            try {
                RunTimeDataFactory.getTempDataInstance().clear();
                AbstractArrayHandle handle = (AbstractArrayHandle) HandleExampleFactory.getHandle(JsonDiffUtil.getArrayHandleClass((DiffJsonArray) expect, (DiffJsonArray) actuals[i]));
                handle.handle((DiffJsonArray) expect, (DiffJsonArray) actuals[i]);
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
}
