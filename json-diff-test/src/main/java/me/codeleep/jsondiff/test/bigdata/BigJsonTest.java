package me.codeleep.jsondiff.test.bigdata;


import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson2.JSON;
import me.codeleep.jsondiff.common.model.JsonCompareResult;
import me.codeleep.jsondiff.common.model.JsonComparedOption;
import me.codeleep.jsondiff.core.DefaultJsonDifference;
import me.codeleep.jsondiff.core.config.JsonDiffOption;
import me.codeleep.jsondiff.core.utils.JsonNeatFactory;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * @author: codeleep
 * @createTime: 2022/11/22 16:57
 * @description:
 */
public class BigJsonTest {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        while (scanner.nextInt() == 0) {
            test();
        }
    }


    public static void test() throws FileNotFoundException {
        FileReader expectFile = new FileReader("classpath:bigdata/expect.json");
        FileReader actualFile = new FileReader("classpath:bigdata/actual.json");
        String expectJson = expectFile.readString();
        String actualJson = actualFile.readString();
        JsonNeatFactory jsonNeatFactory = JsonDiffOption.getJsonNeatFactory();

        JsonComparedOption jsonComparedOption = new JsonComparedOption().setIgnoreOrder(true);
        JsonCompareResult jsonCompareResult = new DefaultJsonDifference()
                .option(jsonComparedOption)
                .detectDiff(JSON.parseObject(expectJson), JSON.parseObject(actualJson));
        System.out.println(JSON.toJSONString(jsonCompareResult));

    }
}
