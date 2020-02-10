package com.leyou.search.mq;

import com.leyou.search.service.SearchService;
import com.rabbitmq.http.client.domain.ExchangeType;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemListener {
    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.insert.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchang",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void listenInsertOrupdate(Long spuId){
        if (spuId ==null){
            return;
        }
        //处理消息,对索引库进行新增或者修改
        searchService.createOrUpdateIndex(spuId);
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.delete.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchang",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void listendelete(Long spuId){
        if (spuId ==null){
            return;
        }
        //处理消息,对索引库进行新增或者修改
        searchService.deleteIndex(spuId);
    }
}
