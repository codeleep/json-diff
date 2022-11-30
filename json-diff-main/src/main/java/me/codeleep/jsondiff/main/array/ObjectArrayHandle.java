package me.codeleep.jsondiff.main.array;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.function.Function;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.common.utils.DiffUtils;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.main.HandleExampleFactory;
import me.codeleep.jsondiff.main.utils.ComparedUtil;
import me.codeleep.jsondiff.main.utils.NodeTypeUtil;
import me.codeleep.jsondiff.spi.handle.array.AbstractArrayHandle;
import me.codeleep.jsondiff.spi.handle.object.AbstractObjectHandle;
import me.codeleep.jsondiff.spi.model.object.AbstractDiffJsonObject;
import me.codeleep.jsondiff.spi.model.object.DiffJsonObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import static me.codeleep.jsondiff.common.model.Constant.FINDING_ANOMALY;

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
    public DiffProcessResultStack compareKeepOrder(Object[] expect, Object[] actual) {
        DiffProcessResultStack result = DiffProcessResultStack.build();
        for (int i = 0; i < expect.length; i++) {
            DiffJsonObject expectItem = (DiffJsonObject)expect[i];
            DiffJsonObject actualItem = (DiffJsonObject)actual[i];
            try {
                DiffProcessResultStack handle = HandleExampleFactory.handle(expectItem, actualItem);
                handle.setExpectNodeName(DiffUtils.formatArrayIndex(i));
                handle.setActualNodeName(DiffUtils.formatArrayIndex(i));
                result.addStack(handle);
            }catch (Exception e) {
                // 抛出异常记录对比失败
                result.addDefects(new Defects()
                        .setActual(actualItem)
                        .setExpect(expectItem)
                        .setIllustrateTemplate(
                                FINDING_ANOMALY,
                                RunTimeDataFactory.getPath(result.getId(), true),
                                RunTimeDataFactory.getPath(result.getId(), false),
                                e.getMessage()
                        ));
            }
        }
        return result;
    }


    /**
     * 忽略顺序
     * 1. 可以根据用户指定的字段进行匹配
     * 2. 不指定排序字段的时候, 时间复杂度是2n。需要n*n比对
     * @param expect
     * @param actual
     */
    @Override
    public DiffProcessResultStack compareIgnoreOrder(Object[] expect, Object[] actual) {

        /**
         * 1. 遍历期望数组。在 actual 数组中查找。
         *       - 如果找到。给该元素打上标签；后续数组匹配不允许再使用
         *       - 如果都没有找到。就记录错误值
         * 2. 将actual 没有被匹配到的。和 expect 没有匹配到的一一比对
         */
        boolean[] actualSign = new boolean[actual.length];
        boolean[] expectSign = new boolean[expect.length];

        DiffProcessResultStack result = DiffProcessResultStack.build();
        // 获取用户指定的keys
        Function keyFunction = RunTimeDataFactory.getOptionInstance().getKeyFunction();
        HashSet<String>  keys = null;
        if (keyFunction != null) {
            // TODO RunTimeDataFactory.getPath(result.getId(), true) 得不到正确的返回值。业务result并未关联到差异树中
            keys = keyFunction.apply(RunTimeDataFactory.getPath(result.getId(), true), RunTimeDataFactory.getPath(result.getId(), false));
        }

        // 过滤完全一致的对象
        for (int i = 0; i < expect.length; i++) {
            int index = getCompareObject(expect[i], actual, Arrays.copyOf(actualSign, actualSign.length), keys);
            if (index >= 0) {
                actualSign[index] = true;
                expectSign[i] = true;
            }
        }

        // 过滤通过


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
    private int getCompareObject(Object expect, Object[] actuals, boolean[] actualSign, HashSet<String> keys) {
        int index = -1;
        for (int i = 0; i < actuals.length; i++) {
            if (isMatchComparison((AbstractDiffJsonObject) expect, (AbstractDiffJsonObject) actuals[i], actualSign[i], keys)) {
                continue;
            }
            try {
                DiffProcessResultStack handle = HandleExampleFactory.handle(expect, actuals[i]);
                if ((handle.getDefectsList() == null || handle.getDefectsList().isEmpty()) && (handle.getStacks() == null || handle.getStacks().isEmpty())) {
                    index = i;
                    break;
                }
            }catch (Exception ignored) {

            }
        }
        return index;
    }

    /**
     * 判断两个对象是否符合比较条件
     * @param expect
     * @param actual
     * @return
     */
    private boolean isMatchComparison(AbstractDiffJsonObject expect, AbstractDiffJsonObject actual, boolean actualSign, HashSet<String> keys) {
        // 都是空对象
        if (expect.size() == 0 && actual.size() == 0) {
            return false;
        }
        if (keys == null || keys.size() == 0) {
            return actualSign;
        }
        return ComparedUtil.isItWorthComparing(expect, actual, keys);
    }



    /**
     * 将数组元素对比建立映射关系
     * @param expect 期望对象数组
     * @param actual 实际对象数组
     * @return 对比的连线图。HashMap<expectIndex, actualIndex>
     *     null: 代表全匹配
     */
    private HashMap<Integer, Integer> buildRelationship(Object[] expect, Object[] actual, long id) {
        HashMap<Integer, Integer> relations = new HashMap<>();
        // 指定联系字段。该字段的value只允许是value类型
        Function keyFunction = RunTimeDataFactory.getOptionInstance().getKeyFunction();
        HashSet<String> keys = keyFunction.apply(RunTimeDataFactory.getPath(id, true), RunTimeDataFactory.getPath(id, false));
        // 防止空指针
        if (keys == null || keys.size() == 0) {
            return null;
        }
        // 校验key的元素
        for (int i = 0; i < expect.length; i++) {
            AbstractDiffJsonObject expectJsonObject  = (AbstractDiffJsonObject) expect[i];
            AbstractDiffJsonObject actualJsonObject  = (AbstractDiffJsonObject) actual[i];
            for (String key: keys) {
                if (NodeTypeUtil.isPrimitiveType(expectJsonObject.get(key)) && NodeTypeUtil.isPrimitiveType(actualJsonObject.get(key))) {
                    throw new JsonDiffException("The value of a key returned by Function is not allowed to be a complex type");
                }
            }
        }
        // 根据keys进行对象联系
        for (int i = 0; i < expect.length; i++) {
            AbstractDiffJsonObject expectJsonObject = (AbstractDiffJsonObject) expect[i];
            AbstractDiffJsonObject actualJsonObject = (AbstractDiffJsonObject) actual[i];




        }





    }


    /**
     * 统计元素出现的次数
     * @param expects 期望数组
     * @param actuals 实际数组
     * @return 返回统计结果
     */
    private HashMap<Integer, ObjectCount> arrayConversion(Object[] expects, Object[] actuals, HashSet<String> keys) {
        HashMap<Object, ObjectCount> map = new HashMap<>();


        for (int i = 0; i < expects.length; i++) {
            AbstractDiffJsonObject expectJsonObject = (AbstractDiffJsonObject) expects[i];
            int hashCode = expectJsonObject.toJsonString().hashCode();
            if (map.containsKey(hashCode)) {
                map.put(ex, new ObjectCount(ex));
            }
            ObjectCount objectCount = map.get(ex);
            objectCount.risingExpectCount(ex);
            objectCount.setExpectIndex(i);
        }

        for (int i = 0; i < actuals.length; i++) {
            Object ac = actuals[i];
            if (map.containsKey(ac)) {
                map.put(ac, new ObjectCount(ac));
            }
            ObjectCount objectCount = map.get(ac);
            objectCount.risingActualCount(ac);
            objectCount.setActualIndex(i);
        }

        return map;
    }

}