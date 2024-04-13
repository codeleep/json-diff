package me.codeleep.jsondiff.common.model;

import me.codeleep.jsondiff.common.utils.PathUtil;

import static me.codeleep.jsondiff.common.model.Constant.PATH_ROOT;

/**
 * @author: codeleep
 * @createTime: 2023/03/05 01:33
 * @description: 遍历过程中的path数据
 */
public class TravelPath {

    /**
     * 期望对象遍历地址
     */
    private String expectTravelPath;

    /**
     * 实际对象遍历的地址
     */
    private String actualTravelPath;

    /**
     * 索引地址。一个抽象的地址
     */
    private String abstractTravelPath;


    public TravelPath(TravelPath parentPath, MappingKey mappingKey) {
        // 抽象的路径
        this.abstractTravelPath = PathUtil.getObjectPath(parentPath.getAbstractTravelPath()) + (mappingKey.getExpectKey() != null ? mappingKey.getExpectKey() : mappingKey.getActualKey());
        // 实际遍历的路径
        this.actualTravelPath =  PathUtil.getObjectPath(parentPath.getActualTravelPath()) + mappingKey.getActualKey();
        this.expectTravelPath =  PathUtil.getObjectPath(parentPath.getExpectTravelPath()) + mappingKey.getExpectKey();
    }

    public TravelPath(TravelPath parentPath,  int expectIndex, int actualIndex) {
        // 抽象的路径
        this.abstractTravelPath = parentPath.getAbstractTravelPath() + PathUtil.getAbstractIndexPath(null);
        // 实际遍历的路径
        this.actualTravelPath =  parentPath.getActualTravelPath() + PathUtil.getIndexPath(String.valueOf(actualIndex));
        this.expectTravelPath =  parentPath.getExpectTravelPath() + PathUtil.getIndexPath(String.valueOf(expectIndex));
    }

    public TravelPath(String abstractTravelPath) {
        this.abstractTravelPath = abstractTravelPath;
        this.actualTravelPath = abstractTravelPath;
        this.expectTravelPath = abstractTravelPath;
    }

    public TravelPath() {
        this.abstractTravelPath = PATH_ROOT;
        this.actualTravelPath = PATH_ROOT;
        this.expectTravelPath = PATH_ROOT;
    }


    public TravelPath(TravelPath travel) {
        this.abstractTravelPath = travel.getAbstractTravelPath();
    }


    public String getExpectTravelPath() {
        return expectTravelPath;
    }

    public void setExpectTravelPath(String expectTravelPath) {
        this.expectTravelPath = expectTravelPath;
    }

    public String getActualTravelPath() {
        return actualTravelPath;
    }

    public void setActualTravelPath(String actualTravelPath) {
        this.actualTravelPath = actualTravelPath;
    }

    public String getAbstractTravelPath() {
        return abstractTravelPath;
    }

    public void setAbstractTravelPath(String abstractTravelPath) {
        this.abstractTravelPath = abstractTravelPath;
    }

}
