package com.example.demo.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaPlan {

//    @Autowired
//    AdminClient adminClient;
//    @Autowired
//    KafkaConsumer kafkaConsumer;
//
//    @KafkaListener(topicPattern = "event_topic", containerFactory = "batchFactory")
//    public void operationPlanListen(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
//        Object value = consumerRecord.value();
//        String topic = consumerRecord.topic();
//        String key = consumerRecord.key();
//        log.info("神策kafka消息打印:key:{},offset{}, value {},topic {}", consumerRecord.key(), consumerRecord.offset(), value, topic);
//        if (null == value) {
//            log.info("神策kafka消息打印消息为空，不做任何处理。");
//            return;
//        }
//        log.info("神策kafka消息打印结束。。。。");
//
//
//    }

}
