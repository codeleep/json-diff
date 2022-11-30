package me.codeleep.jsondiff.main.array;

import me.codeleep.jsondiff.common.exception.JsonDiffException;
import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.common.utils.DiffUtils;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.spi.handle.array.AbstractArrayHandle;

import java.util.HashMap;
import java.util.Map;

import static me.codeleep.jsondiff.common.model.Constant.DATA_INCONSISTENT;
import static me.codeleep.jsondiff.common.model.Constant.FINDING_ANOMALY;

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
    protected DiffProcessResultStack compareKeepOrder(Object[] expect, Object[] actual) {
        DiffProcessResultStack result = DiffProcessResultStack.build();
        for (int i = 0; i < expect.length; i++) {
            Object ex = expect[i];
            Object ac = actual[i];
            DiffProcessResultStack build = DiffProcessResultStack.build(DiffUtils.formatArrayIndex(i), DiffUtils.formatArrayIndex(i));
            if(ex == null && ac == null) {
                continue;
            }
            try{
                if (ac == null || !ac.equals(ex)) {
                    throw new JsonDiffException("diff");
                }
            }catch (JsonDiffException|NullPointerException e) {
                // 发现差异
                build.addDefects(new Defects().setActual(ac).setExpect(ex)  .setIllustrateTemplate(
                        DATA_INCONSISTENT,
                        RunTimeDataFactory.getPath(result.getId(), true) + DiffUtils.formatArrayIndex(i),
                        RunTimeDataFactory.getPath(result.getId(), false) + DiffUtils.formatArrayIndex(i)
                ));
                result.addStack(build);
            }catch (Exception e) {
                // 出现异常
                build.addDefects(new Defects().setActual(ac).setExpect(ex).setIllustrateTemplate(
                        FINDING_ANOMALY,
                        RunTimeDataFactory.getPath(result.getId(), true),
                        RunTimeDataFactory.getPath(result.getId(), false),
                        e.getMessage()
                ));
                result.addStack(build);
            }
        }
        return result;
    }


    /**
     * 忽略顺序
     * @param expects
     * @param actuals
     */
    @Override
    protected DiffProcessResultStack compareIgnoreOrder(Object[] expects, Object[] actuals) {

        DiffProcessResultStack result = DiffProcessResultStack.build();
        HashMap<Object, ObjectCount> objectCountHashMap = arrayConversion(expects, actuals);

        // 遍历期望数组
        for (Object ex: expects) {
            objectCountHashMap.get(ex).fallingActualCount(ex);
        }
        // 遍历实际数组
        for (Object ac: actuals) {
            objectCountHashMap.get(ac).fallingExpectCount(ac);
        }
        // 遍历没有消除的数据
        for (Map.Entry<Object, ObjectCount> entry: objectCountHashMap.entrySet()) {
            int expectCount = entry.getValue().getExpectCount();
            int actualCount = entry.getValue().getActualCount();
            if(expectCount == 0 && actualCount == 0) {
                continue;
            }
            Object expect = expectCount == 0 ? null : entry.getKey();
            Object actual = actualCount == 0 ? null : entry.getKey();
            DiffProcessResultStack build = DiffProcessResultStack.build(DiffUtils.formatArrayIndex(entry.getValue().getExpectIndex()), DiffUtils.formatArrayIndex(entry.getValue().getActualIndex()));
            // 发现差异
            build.addDefects(new Defects().setExpect(expect).setActual(actual)
                    .setIllustrateTemplate(
                            DATA_INCONSISTENT,
                            RunTimeDataFactory.getPath(result.getId(), true) + DiffUtils.formatArrayIndex(entry.getValue().getExpectIndex()),
                            RunTimeDataFactory.getPath(result.getId(), false) + DiffUtils.formatArrayIndex(entry.getValue().getActualIndex())
                    ));
            result.addStack(build);
        }
        return result;
    }


    @Override
    public boolean equalsItem(Object obj1, Object obj2){
        if (obj1 == null && obj2 == null) {
            return true;
        }
        if (obj1 != null && obj1.equals(obj2)) {
            return true;
        }
        return false;
    }


    /**
     * 统计元素出现的次数
     * @param expects 期望数组
     * @param actuals 实际数组
     * @return 返回统计结果
     */
    private HashMap<Object, ObjectCount> arrayConversion(Object[] expects, Object[] actuals) {
        HashMap<Object, ObjectCount> map = new HashMap<>();

        for (int i = 0; i < expects.length; i++) {
            Object ex = expects[i];
            if (map.containsKey(ex)) {
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
