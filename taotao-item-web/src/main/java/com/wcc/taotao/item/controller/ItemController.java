package com.wcc.taotao.item.controller;

import com.wcc.taotao.item.pojo.Item;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.pojo.TbItemDesc;
import com.wcc.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description: 商品详情Controller
 * @ClassName: ItemController
 * @Auther: changchun_wu
 * @Date: 2019/2/20 2:07
 * @Version: 1.0
 **/

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/{itemId}")
    public String getItemById(@PathVariable Long itemId, Model model){
        //引入服务
        //注入Service
        //获取商品信息
        TbItem tbItem = itemService.getItemById(itemId);
        //获取商品描述信息
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);
        //将TbItem对象转为Item对象
        Item item = new Item(tbItem);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",tbItemDesc);
        return "item";
    }
}
