package com.wcc.taotao.controller;

import com.wcc.taotao.pojo.EasyUIDataGridResult;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.pojo.TbItemDesc;
import com.wcc.taotao.pojo.Vo;
import com.wcc.taotao.service.ItemService;
import com.wcc.taotao.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Description: 商品列表Controller
 * @ClassName: TbItemController
 * @Auther: changchun_wu
 * @Date: 2018/12/24 22:40
 * @Version: 1.0
 **/

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    /**
     * @Author: changchun_wu
     * @Date: 2018/12/24 23:11
     * @Description: 分页显示所有商品
     * URL: /item/list
     * method: GET
     * return: json
     **/
    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    @ResponseBody
    public EasyUIDataGridResult<TbItem> getItemList(Integer page,Integer rows){
        EasyUIDataGridResult<TbItem> itemList = itemService.getItemList(page, rows);
        return itemList;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/30 1:17
     * @Description: 保存商品
     **/
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem item,String desc){
        E3Result e3Result = itemService.addItem(item, desc);
        return e3Result;
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/30 1:18
     * @Description: 删除商品
     **/
    @RequestMapping(value = "/rest/item/delete",method = RequestMethod.POST)
    @ResponseBody
    public E3Result deleteItem(Vo vo){
        return itemService.deleteItem(vo.getIds());
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/30 17:03
     * @Description: 下架商品
     **/
    @RequestMapping(value = "/rest/item/instock",method = RequestMethod.POST)
    @ResponseBody
    public E3Result instockItem(Vo vo){
        return itemService.instockItem(vo.getIds());
    }

    /**
     * @Author: changchun_wu
     * @Date: 2018/12/30 17:03
     * @Description: 下架商品
     **/
    @RequestMapping(value = "/rest/item/reshelf",method = RequestMethod.POST)
    @ResponseBody
    public E3Result reshelfItem(Vo vo){
        return itemService.reshelfItem(vo.getIds());
    }

    /**
     * @Author: changchun_wu
     * @Date: 2019/1/1 20:22
     * @Description: 跳转到编辑界面
     **/
    @RequestMapping(value = "/rest/page/item-edit",method = RequestMethod.GET)
    public String editItemUi(){
        return "item-edit";
    }

    @RequestMapping(value = "/rest/item/query/item/desc",method = RequestMethod.GET)
    @ResponseBody
    public E3Result editItemDesc(Long id){
        try {
            TbItemDesc itemDesc = itemService.editItemDesc(id);
            E3Result e3Result = E3Result.build(200, "", itemDesc);
            return e3Result;
        } catch (Exception e) {
            e.printStackTrace();
            E3Result e3Result = E3Result.build(200,"","");
            return e3Result;
        }
    }
}
