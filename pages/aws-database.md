# AWS Database

- [Amazon RDS](#amazon-rds)
- [Amazon DynamoDB](#amazon-dynamodb)
- [Amazon ElasticCache](#amazon-elasticcache)
- [Amazon Neptune](#amazon-neptune)
- [Amazon Redshift](#amazon-redshift)
- [Amazon QLDB](#amazon-qldb)
- [Amazon DocumentDB](#amazon-documentdb)
- [Amazon Keyspaces](#amazon-keyspaces)

<br />

# Amazon RDS

Available database engines for Amazon Relational Database Service:
- MySQL (EBS)
- MariaDB (EBS)
- PostgreSQL (EBS)
- Amazon Aurora (Shared Cluster Storage)
- Oracle (EBS)
- SQL Server (EBS)

<br />

# Amazon DynamoDB

Amazon DynamoDB is NoSQL DB (key-value stores). DynamoDB tables are schemaless.

Secondary Indexes:
- Global: lets you query across the entire table to find any record that matches a particular value.
- Local: can only help find data within a single partition key.

<br />

# Amazon ElasticCache

Amazon ElastiCache is a service that makes it easy to deploy, operate, and scale open-source, 
in-memory data stores in the cloud. This service improves the performance through caching, 
where web applications allow you to retrieve information from fast managed in-memory data stores 
instead of relying entirely on slower disk-based solutions.

### Cache Engines

**Memcached** use cases:
- Caching
- Session Store

**Redis** use cases:
- Caching
- Session Store
- Chat and Messaging
- Gaming Leaderboards
- Geospatial
- Machine Learning
- Media Streaming
- Queues
- Real-Time Analytics

### Components of ElastiCache

Node: a fixed sized chunk of secure, network-attached RAM.
Shard: Redis Shard (node group). a group of up to 6 ElasticCache nodes.
Redis Cluster: a group of 1-90 Redis shards.
Memcached Cluster: a collection of one or more cache nodes.

<br />

# Amazon Neptune

<br />

# Amazon Redshift

<br />

# Amazon QLDB

<br />

# Amazon DocumentDB

<br />

# Amazon Keyspaces

<br />