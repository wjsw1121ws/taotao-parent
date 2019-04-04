package com.wcc.taotao.portal.controller;

import com.wcc.taotao.content.service.ContentService;
import com.wcc.taotao.pojo.TbContent;
import com.wcc.taotao.portal.pojo.Ad1Node;
import com.wcc.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 首页展示
 * @ClassName: PageController
 * @Auther: changchun_wu
 * @Date: 2019/1/3 22:05
 * @Version: 1.0
 **/

@Controller
public class PageController {
    @Autowired
    private ContentService contentService;

    @Value("${AD1_CATEGORYID}")
    private Long AD1_CATEGORYID;

    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;

    @Value("${AD1_HEIGHTB}")
    private String AD1_HEIGHTB;

    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;

    @Value("${AD1_WIDTHB}")
    private String AD1_WIDTHB;


    /**
     * @Author: changchun_wu
     * @Date: 2019/1/3 22:06
     * @Description: 访问首页
     **/
    @RequestMapping(value = "/index")
    public String index(Model model) {
        List<TbContent> tbContents = contentService.getContentByCategoryId(AD1_CATEGORYID);
        List<Ad1Node> nodes = new ArrayList<>();
        for (TbContent tbContent : tbContents) {
            Ad1Node node = new Ad1Node();
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setAlt(tbContent.getSubTitle());
            node.setHref(tbContent.getUrl());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHTB);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTHB);
            nodes.add(node);
        }
        model.addAttribute("ad1",JsonUtils.objectToJson(nodes));
        return "index";
    }
}
