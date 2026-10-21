docker exec -it kind_shamir /opt/kafka/bin/kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic games \
--property print.key=true \
--property key.separator=" : "
