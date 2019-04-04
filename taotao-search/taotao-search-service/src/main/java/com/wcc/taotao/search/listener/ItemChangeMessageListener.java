package com.wcc.taotao.search.listener;


import com.wcc.taotao.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Description: 接收消息的监听器
 * @ClassName: ItemChangeMessageListener
 * @Auther: changchun_wu
 * @Date: 2019/2/18 22:53
 * @Version: 1.0
 **/
public class ItemChangeMessageListener implements MessageListener {
    @Autowired
    private SearchService searchService;

    @Override
    public void onMessage(Message message) {
        //判断消息的类型是否为textMessage
        if (message instanceof TextMessage){
            TextMessage textMessage = (TextMessage)message;
            try {
                String itemIdStr = textMessage.getText();
                long itemId = Long.parseLong(itemIdStr);
                searchService.getSearchItemById(itemId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
