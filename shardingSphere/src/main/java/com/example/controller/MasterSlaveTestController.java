package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Course;
import com.example.entity.Dict;
import com.example.mapper.CourseMapper;
import com.example.mapper.DictMapper;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * ClassName:ShardingSphereJdbcTest
 * Package:com.example.controller
 * Description: 测试读写分离 - application03.properties
 *
 * @Date:2022/4/7 10:49
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/ms")
public class MasterSlaveTestController {

    @Autowired
    private DictMapper dictMapper;

    // 写
    @RequestMapping("/addDict")
    public void addDict() {
        Dict dict = new Dict();
        dict.setUstatus("1");
        dict.setUvalue("正常");
        dictMapper.insert(dict);

        Dict dict2 = new Dict();
        dict2.setUstatus("2");
        dict2.setUvalue("异常");
        dictMapper.insert(dict2);
    }

    // 读
    @RequestMapping("/queryDict")
    public void queryDict() {
        QueryWrapper<Dict> wrapper = new QueryWrapper<Dict>();
        wrapper.eq("ustatus", "1");
        List<Dict> dicts = dictMapper.selectList(wrapper);
        dicts.forEach(dict -> System.out.println(dict));
    }
    @RequestMapping("/updateDict")
    public void updateDict() {
        Dict dict = new Dict();
        dict.setUstatus("1");
        dict.setUvalue("Normal");

        UpdateWrapper<Dict> wrapper = new UpdateWrapper<Dict>();
        wrapper.eq("ustatus", dict.getUstatus());
        dictMapper.update(dict, wrapper);
    }

}
