# AWS Database

- [Amazon RDS](#amazon-rds)
- [Amazon DynamoDB](#amazon-dynamodb)
- [Amazon ElasticCache](#amazon-elasticcache)
- [Amazon Neptune](#amazon-neptune)
- [Amazon Redshift](#amazon-redshift)
- [Amazon QLDB](#amazon-quantum-ledger-database-qldb)
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

Amazon Neptune is a fast, reliable, secure, and fully-managed **graph database** service. It is used to help you 
both store and navigate relationships between highly connected data which could contain billions of separate 
relationships. Graph databases are ideal if their focus is on being able to identify these relationships of 
interconnected data, rather than the actual data itself.

Use Cases:
- Social Media
- Fraud Detection
- Recommendation Engines

Query Languages:
- Apache Tinkerpop Gremlin
- World Wide Web Consortium SPASQL

Connect to Neptune Database:
- Cluster endpoints
- Reader endpoints
- Instance endpoints

<br />

# Amazon Redshift

Amazon Redshift is a fast, fully-managed, petabyte-scale data warehouse. And it's designed for 
high performance and **analysis** of information capable of storing and processing petabytes of data and 
provide access to this data, using your existing **business intelligence tools**, using standard SQL. 
It operates as a relational database management system, and therefore is compatible with other 
RDBMS applications. Redshift itself is based upon PostgreSQL 8.0.2, but it contains a number of 
differences from PostgreSQL.

### Cluster, Compute Node, Leader Node, Node Slice

- A Cluster is a grouping of Compute Nodes.
- A Node Slice is a partition of a Compute Node where the node memory and disk space split.

### Performance Features

- Massively Parallel Processing (MPP)
- Columnar Data Storage
- Result Caching

<br />

# Amazon Quantum Ledger Database (QLDB)

<br />

# Amazon DocumentDB

<br />

# Amazon Keyspaces

<br />