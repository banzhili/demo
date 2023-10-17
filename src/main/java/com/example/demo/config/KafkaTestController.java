//package com.example.demo.config;
//
//
//import cn.hutool.json.JSONUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//
///**
// * @author banzhili
// * @date 2022/9/1 15:42
// */
//@Slf4j
//@RestController
//@RequestMapping("/kafka")
//public class KafkaTestController {
//    @Autowired
//    private KafkaTemplate kafkaTemplateTag;
//
//
//
//
//    /**
//     * sendTest2
//     */
//    @PostMapping(value = "/sendTest2")
//    public String list1(@RequestParam String kafukaReq,@RequestParam String topic) {
//        try {
//            boolean send = sendTest1(kafukaReq,topic);
//            return "true";
//        } catch (Exception e) {
//            log.error("sendTest2异常", e);
//            return "sendTest2异常";
//        }
//    }
//
//    /**
//     * 自测发到这个topic消息。进行监听
//     *
//     * @param obj
//     * @return
//     */
//    public boolean sendTest1(String obj,String topic) {
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplateTag.send(topic, "551ced3d9fce471e8d11cb7965da2f10_123", JSONUtil.toJsonStr(obj));
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//                log.info("kafka: The message failed to be sent: {}", throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
//                log.info("kafaka: The message was send successfully");
//                log.info("kafaka: result:{}", stringObjectSendResult.toString());
//            }
//        });
//        return true;
//    }
//
//    public static void main(String[] args) {
//new ArrayList<>();
//        String a="ab";
//        Boolean s=true;
//        Boolean equals = a.equals(s);
//        if(equals){
//            System.out.printf("true");
//        }else {
//            System.out.printf("false");
//
//        }
//
//    }
//}
