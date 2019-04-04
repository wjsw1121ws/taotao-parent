package com.wcc.taotao.controller;

import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @ClassName: TestController
 * @Auther: changchun_wu
 * @Date: 2018/12/20 1:40
 * @Version: 1.0
 **/
@Controller
public class TestController {
    @Resource
    private TestService testService;
    @RequestMapping(value = "/selectNow")
    @ResponseBody
    public String selectNow(){
        return testService.selectNow();
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/23 17:14
     * @Description: 测试分页
     **/
    @RequestMapping(value = "/testPageHelp")
    @ResponseBody
    public String testPage(){
        List<TbItem> items = testService.testPageHelp();
        for (TbItem item : items) {
            System.out.println(item.getId()+"\t\t"+item.getTitle());
        }
        return items.toString();
    }

}
