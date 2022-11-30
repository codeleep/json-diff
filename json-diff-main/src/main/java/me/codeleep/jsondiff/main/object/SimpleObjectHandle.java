package me.codeleep.jsondiff.main.object;

import me.codeleep.jsondiff.common.model.Defects;
import me.codeleep.jsondiff.common.model.DiffProcessResultStack;
import me.codeleep.jsondiff.common.utils.RunTimeDataFactory;
import me.codeleep.jsondiff.main.HandleExampleFactory;
import me.codeleep.jsondiff.main.utils.NodeTypeUtil;
import me.codeleep.jsondiff.spi.handle.object.AbstractObjectHandle;
import me.codeleep.jsondiff.spi.model.object.AbstractDiffJsonObject;

import java.util.*;

import static me.codeleep.jsondiff.common.model.Constant.*;

/**
 * @author: codeleep
 * @createTime: 2022/07/30 19:19
 * @description: 简单json对象对比
 */
public class SimpleObjectHandle extends AbstractObjectHandle {

    @Override
    protected DiffProcessResultStack doObjectHandle(AbstractDiffJsonObject expectObject, AbstractDiffJsonObject actualObject) {
        Set<String> expectKeys = expectObject.keySet();
        Set<String> actualKeys = actualObject.keySet();

        // 对比结果
        DiffProcessResultStack result = DiffProcessResultStack.build();
        ArrayList<ExtraKey> extraKeys = new ArrayList<>();

        // 将映射关系整理一下
        HashMap<String, String> relationship = relationshipKey(new HashSet<>(expectKeys), new HashSet<>(actualKeys), extraKeys);
        for (Map.Entry<String, String> entry: relationship.entrySet()) {
            Object ex = expectObject.get(entry.getValue());
            Object ac = actualObject.get(entry.getKey());
            // 简单元素
            if (NodeTypeUtil.isPrimitiveType(ex) && NodeTypeUtil.isPrimitiveType(ac)) {
                try {
                    if ((ex == null && ac == null) || ex.equals(ac)) {
                        continue;
                    }
                }catch (NullPointerException e) {
                    continue;
                }
                result.addDefects(new Defects().setActual(ac).setExpect(ex)  .setIllustrateTemplate(
                        DATA_INCONSISTENT,
                        RunTimeDataFactory.getPath(result.getId(), true) + SIGN + entry.getValue(),
                        RunTimeDataFactory.getPath(result.getId(), false) + SIGN + entry.getKey()
                ));
                continue;
            }
            // 复杂元素
            try {
                DiffProcessResultStack resultStack = HandleExampleFactory.handle(ex, ac);
                if (resultStack != null) {
                    // 设置当前的 nodeName
                    resultStack.setExpectNodeName(entry.getValue());
                    resultStack.setActualNodeName(entry.getKey());
                    result.addStack(resultStack);
                }
            }catch (Exception e) {
                // 抛出异常记录对比失败
                result.addDefects(new Defects().setActual(ac).setExpect(ex).setIllustrateTemplate(
                        FINDING_ANOMALY,
                        RunTimeDataFactory.getPath(result.getId(), true),
                        RunTimeDataFactory.getPath(result.getId(), false),
                        e.getMessage()
                ));
            }
        }

        // 记录只存在一个集合中的key
        for (ExtraKey extraKey: extraKeys) {
            Object ex = expectObject.get(extraKey.getKey());
            Object ac = actualObject.get(extraKey.getKey());
            result.addDefects(new Defects().setActual(ac).setExpect(ex).setIllustrateTemplate(
                    SEPARATE_KEY,
                    RunTimeDataFactory.getPath(result.getId(), true) + SIGN + (extraKey.isExpectExit() ? extraKey.getKey() : null),
                    RunTimeDataFactory.getPath(result.getId(), false) + SIGN + (extraKey.isActualExit() ? extraKey.getKey() : null)
            ));
        }
        return result;
    }


    /**
     * 整理key的映射关系。将通过配置处理得出期望key与真实key的对应比较
     * @param expectKeys 实际key集合
     * @param actualKeys 期望key集合
     * @return 返回一个key对应的集合。key是真实对象的key, value是期望对象的key
     */
    private HashMap<String, String> relationshipKey(Set<String> expectKeys, Set<String> actualKeys, List<ExtraKey> extraKeys) {
        List<String> ignoreKey = RunTimeDataFactory.getOptionInstance().getIgnoreKey();
        Map<String, String> mapping = RunTimeDataFactory.getOptionInstance().getMapping();
        // 剔除忽略的key
        ignoreKey.forEach(expectKeys::remove);
        ignoreKey.forEach(actualKeys::remove);

        HashSet<String> keys = new HashSet<>(expectKeys);
        keys.addAll(actualKeys);

        // 修改映射关系
        HashMap<String, String> relationshipKeys = new HashMap<>(mapping);
        for (String key: keys) {
            // 已经配置映射
            if (relationshipKeys.containsKey(key)) {
                continue;
            }
            // 都存在的key时
            if (expectKeys.contains(key) && actualKeys.contains(key)) {
                relationshipKeys.put(key, key);
                continue;
            }
            // 记录差异(存在一个只在某一个集合中存在的key)
            extraKeys.add(new ExtraKey(expectKeys.contains(key), actualKeys.contains(key), key));
        }
        return relationshipKeys;
    }

}
