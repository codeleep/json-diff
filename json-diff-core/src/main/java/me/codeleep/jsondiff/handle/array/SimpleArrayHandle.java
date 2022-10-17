package me.codeleep.jsondiff.handle.array;

import me.codeleep.jsondiff.exception.JsonDiffException;
import me.codeleep.jsondiff.handle.RunTimeDataFactory;
import me.codeleep.jsondiff.model.Defects;

import java.util.Arrays;

/**
 * @author: codeleep
 * @createTime: 2022/07/17 16:42
 * @description: 简单类型的处理器，数组元素是基本类型
 */
public class SimpleArrayHandle extends AbstractArrayHandle {


    /**
     * 不忽略顺序
     * @param expect
     * @param actual
     */
    @Override
    protected void compareKeepOrder(Object[] expect, Object[] actual) {
        for (int i = 0; i < expect.length; i++) {
            if(actual[i] == null && expect[i] == null) {
                continue;
            }
            try{
                if (actual[i] == null || !actual[i].equals(expect[i])) {
                    throw new JsonDiffException("diff");
                }
            }catch (Exception e) {
                Defects defects = new Defects()
                        .setActual(actual[i])
                        .setExpect(expect[i])
                        .setIllustrate(String.format("The %d element is inconsistent", i))
                        .setIndexPath(String.format("%s[%d]", getCurrentPath(), i));
                RunTimeDataFactory.getResultInstance().addDefects(defects);
            }
        }
    }


    /**
     * 忽略顺序
     * @param expect
     * @param actual
     */
    @Override
    protected void compareIgnoreOrder(Object[] expect, Object[] actual) {

        boolean[] actualSign = new boolean[actual.length];
        boolean[] expectSign = new boolean[expect.length];

        for (int i = 0; i < expect.length; i++) {
            RunTimeDataFactory.getCurrentPathInstance().push(String.format("[%d]", i));
            int index = getCompareItem(expect[i], actual, Arrays.copyOf(actualSign, actualSign.length));
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
            Defects defects = new Defects()
                    .setActual(actual[j])
                    .setExpect(expect[i])
                    .setIllustrate(String.format("The %d element is inconsistent", i))
                    .setIndexPath(getCurrentPath());
            RunTimeDataFactory.getResultInstance().addDefects(defects);
            RunTimeDataFactory.getCurrentPathInstance().pop();
        }

    }

    private int getCompareItem(Object expect, Object[] actuals, boolean[] actualSign) {
        int index = -1;
        for (int i = 0; i < actuals.length; i++) {
            if (actualSign[i]) {
                continue;
            }
            if (expect.equals(actuals[i])) {
                return i;
            }
        }
        return index;
    }

}
