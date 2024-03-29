package org.example.xml.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.example.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.SmartView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * ClassName:RequestMappingController
 * Package:org.example.xml.controller
 * Description:
 *
 * @Date:2022/2/25 10:41
 * @Author:qs@1.com
 */

@Controller
@RequestMapping("/request")
public class RequestMappingController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @RequestMapping("/mapping")
    public ModelAndView mapping() {
        System.out.println("this is requestMappingController");

        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setView(new SmartView() {
//            @Override
//            public boolean isRedirectView() {
//                return false;
//            }
//
//            @Override
//            public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//            }
//        });
        modelAndView.setViewName("a");
        modelAndView.addObject("source", "requestMappingController");
//        modelAndView.setStatus(HttpStatus.OK);
        
        return modelAndView;
    }

    @RequestMapping(value="/mappin*")
    public String mapping02(){
        System.out.println("通配符——*");
        return "a";
    }
    @RequestMapping(value="/mappin?")
    public String mapping03(){
        System.out.println("通配符——？");
        return "a";
    }
    @RequestMapping(value="/**")
    public String mapping04(){
        System.out.println("**");
        return "a";
    }

    /**
     * 参数进来绑定到model以及响应出去model->json，需要引入json的相关包，不然会报错的
     */

    @RequestMapping("/updateUser")
    @ResponseBody
    public User updateUser(User user) {
        //返回修改后的 那么可能会把数据库中的年龄更新为空
        return user;
    }

    @ResponseBody
    @RequestMapping("/updateUser2")
    public User updateUser2(Integer id, String lastName) {
        User user=new User(id,lastName);

        return user;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
