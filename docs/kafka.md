# Kafka
### Kafka brokers : servers run in cluster.
### Topics : Logical data unit that holds multiple partitions.
### Partition : Smallest data unit that holds subset of records.
### Producers : Write to end of the specific partition.
### Consumers : Read from partition suing an offset.
### Replication : resilience and fault tolerance.
### Scaling : Partition strategy. 
### Immutable append read only. 
### Zookeeper : Manage cluster and store metadata.
### Schema registry : Stores versioned history of all schema.
### producer sends schema to schema registry to get schema id. 
### docker compose -f common.yml -f zookeeper.yml up
### echo ruok | nc localhost 2181
### docker compose -f common.yml -f kafka_cluster.yml up
### docker compose -f common.yml -f init_kafka.yml up