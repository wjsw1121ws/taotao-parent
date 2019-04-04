package com.wcc.taotao.controller;

import com.wcc.taotao.pojo.EasyUiTreeNodeResult;
import com.wcc.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description: 商品分类Controller
 * @ClassName: ItemCatController
 * @Auther: changchun_wu
 * @Date: 2018/12/26 0:44
 * @Version: 1.0
 **/

@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;
    @RequestMapping(value = "/item/cat/list",method = RequestMethod.POST)
    @ResponseBody
    public List<EasyUiTreeNodeResult> getItemCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        return itemCatService.getItemCatList(parentId);
    }

}
