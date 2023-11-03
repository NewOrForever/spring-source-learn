package com.example.controller;

import com.example.config.DynamicDataSource;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * ClassName:DynamicDSTestController
 * Package:com.example.controller
 * Description:
 *
 * @Date:2022/4/9 10:58
 * @Author:qs@1.com
 */
@RestController
@RequestMapping("/ds")
public class DynamicDSTestController {
//    @Autowired
//    private CourseMapper courseMapper;
    @Autowired
    private DynamicDataSource dynamicDataSource;

    @RequestMapping("/getList")
    public void getList() {
        /**
         * 核心其实就是DynamicDataSource extends AbstractRoutingDatasource implements DataSource
         * 实际就是新建个DataSource包装两个DataSource数据源通过lookUpKey来决定最后使用哪个数据源
         * 由前端来决定使用哪个数据源，但是数据源数是固定的，要加得改代码
         *
         * threadlocal和jdbctemplate操作可以封装到DynamicDataSource中
         *  - jdbctemplate注入（配置类中先@Bean给个默认的datasource）
         *  - 搞个方法设置threadlocal和jdbctemplate的datasource
         *
         * @see AbstractRoutingDataSource#determineCurrentLookupKey()
         * @see DynamicDataSource#determineCurrentLookupKey()
         * @see org.springframework.jdbc.datasource.DataSourceUtils#getConnection(DataSource)#doGetConnection#fetchConnection
         * - 这里获取数据库连接的时候有个扩展determineCurrentLookupKey
         */

        // 读
        DynamicDataSource.name.set("R");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dynamicDataSource);
        List<Map<String, Object>> courses = jdbcTemplate.queryForList("select * from course", new Object[]{});

        for (Map<String, Object> map : courses) {
            System.out.println(map);
        }

        // 写
        DynamicDataSource.name.set("W");
        jdbcTemplate.update("insert into course(cname,user_id,cstatus) values ('dynamic',100,'1');");

    }
}
