//package com.example.demo.config;
//
//import org.apache.kafka.clients.admin.AdminClient;
//import org.apache.kafka.clients.admin.AdminClientConfig;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaAdmin;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.listener.ContainerProperties;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * kafka手动装配数据到springboot
// *
// * @author banzhili
// * @date 2022/8/26 下午4:02
// */
//
//@Configuration
//@EnableConfigurationProperties(KafkaProperties.class)
//class KafkaPropertiesConfig {
//
//    private final KafkaProperties properties;
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    public KafkaPropertiesConfig(KafkaProperties properties) {
//        this.properties = properties;
//    }
//
//    @Bean
//    public ConsumerFactory<?, ?> kafkaConsumerFactory() {
//        Map<String, Object> props = properties.buildConsumerProperties();
//        /**
//         * 使用 Kafka 的组管理设施时，与消费者协调器之间的心跳之间的预期时间。
//         * 心跳用于确保消费者的会话保持活跃，并在新消费者加入或离开组时促进重新平衡。
//         * 该值必须设置为低于 <code>session.timeout.ms<code>，但通常应设置为不高于该值的 13。它可以调整得更低，
//         * 以控制正常重新平衡的预期时间。
//         */
//        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 1000);
//        //服务端broker多久感知不到一个consumer心跳就认为他故障了，默认是10秒
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 60000);
//        /**
//         * 在一次 poll() 调用中返回的最大记录数。
//         */
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "5000");
//
//        /**
//         * 如果两次poll操作间隔超过了这个时间，broker就会认为这个consumer处理能力太弱，
//         * 会将其踢出消费组，将分区分配给别的consumer消费
//         */
//        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30 * 1000);
//        /**
//         * 手动提交
//         */
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//        /**
//         * 订阅或分配主题时，允许在代理上自动创建主题。
//         * 仅当代理允许使用 `auto.create.topics.enable` 代理配置时，才会自动创建订阅的主题。
//         */
//        props.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG, "false");
//
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        /**
//         * kafka服务器地址
//         */
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        /*这个参数用来配置元数据的过期时间，默认值为300000 ( ms ），即5 分钟。如果元数据在
//         *此参数所限定的时间范围内没有进行更新，则会被强制更新，即使没有任何分区变化或有新的
//         * broker 加入。。
//         */
//        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, 5000);
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public DefaultKafkaProducerFactory<?, ?> kafkaProducerFactory() {
//        Map<String, Object> stringObjectMap = properties.buildProducerProperties();
//        stringObjectMap.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 5000);
//        return new DefaultKafkaProducerFactory<>(stringObjectMap);
//    }
//
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(AdminClientConfig.METADATA_MAX_AGE_CONFIG, 5000);
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        return new KafkaAdmin(props);
//    }
//
//    @Bean
//    public AdminClient adminClient() {
//        return AdminClient.create(kafkaAdmin().getConfig());
//    }
//
//    @Bean
//    public KafkaConsumer<String, String> kafkaConsumer(ConsumerFactory consumerFactory) {
//        return new KafkaConsumer(consumerFactory.getConfigurationProperties());
//    }
//
//    @Bean("batchFactory")
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> batchFactory(ConsumerFactory consumerFactory) {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory);
////        //并发数量
////        factory.setConcurrency(concurrency);
////        //开启批量监听
////        factory.setBatchListener(type);
//        // 被过滤的消息将被丢弃
//        factory.setAckDiscarded(true);
//        // 设置记录筛选策略
////        factory.setRecordFilterStrategy(new RecordFilterStrategy() {
////            @Override
////            public boolean filter(ConsumerRecord consumerRecord) {
////                String msg = consumerRecord.value().toString();
////                if (Integer.parseInt(msg.substring(msg.length() - 1)) % 2 == 0) {
////                    return false;
////                }
////                // 返回true消息将会被丢弃
////                return true;
////            }
////        });
//        // ack模式
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
//        return factory;
//    }
//
//}
