package com.wcc.taotao.item.listener;

import com.wcc.taotao.item.pojo.Item;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.pojo.TbItemDesc;
import com.wcc.taotao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 接收商品修改消息
 * @ClassName: ItemChangeGenHTMLListener
 * @Auther: changchun_wu
 * @Date: 2019/2/24 16:11
 * @Version: 1.0
 **/

public class ItemChangeGenHTMLListener implements MessageListener {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer configurer;
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            try {
                String text = ((TextMessage) message).getText();
                Long itemId = Long.parseLong(text);
                TbItemDesc itemDesc = itemService.getItemDescById(itemId);
                TbItem tbItem = itemService.getItemById(itemId);
                Item item = new Item(tbItem);
                getHTMLFreemarker(item,itemDesc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 静态页面
     * @param item
     * @param itemDesc
     */
    private void getHTMLFreemarker(Item item, TbItemDesc itemDesc) throws Exception{
        //获取Configuration对象
        Configuration configuration = configurer.getConfiguration();
        //获取模板对象
        Template template = configuration.getTemplate("item.ftl");
        //设置数据
        Map map = new HashMap();
        map.put("item",item);
        map.put("itemDesc",itemDesc);
        //创建Writer对象
        Writer writer = new FileWriter(new File("D:\\Freemarker\\item\\"+item.getId()+".html"));
        //通过调用模板中的process方法输出
        System.out.println("D:\\Freemarker\\"+item.getId()+".html");
        template.process(map,writer);
        //关闭流
        writer.close();
    }
}
