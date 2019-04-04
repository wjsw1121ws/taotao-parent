package com.wcc.taotao.item.pojo;

import com.wcc.taotao.pojo.TbItem;
import org.springframework.beans.BeanUtils;

/**
 * @Description:
 * @ClassName: Item
 * @Auther: changchun_wu
 * @Date: 2019/2/20 2:03
 * @Version: 1.0
 **/
public class Item extends TbItem {

    public Item(TbItem tbItem){
        //复制tbItem中的属性到Item中
        BeanUtils.copyProperties(tbItem,this);
    }

    public String[] getImages(){
        String image = super.getImage();
        return image.split(",");
    }
}
