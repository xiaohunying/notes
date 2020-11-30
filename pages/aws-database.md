# AWS Database

- [Amazon RDS](#amazon-rds)
- [Amazon DynamoDB](#amazon-dynamodb)
- [Amazon ElasticCache](#amazon-elasticcache)
- [Amazon Neptune](#amazon-neptune)
- [Amazon Redshift](#amazon-redshift)
- [Amazon QLDB](#amazon-quantum-ledger-database-qldb)
- [Amazon DocumentDB](#amazon-documentdb)
- [Amazon Keyspaces](#amazon-keyspaces-for-apache-cassandra)

<br />

# Amazon RDS

Available database engines for Amazon **Relational** Database Service:
- MySQL (EBS)
- MariaDB (EBS)
- PostgreSQL (EBS)
- Amazon Aurora (Shared Cluster Storage)
- Oracle (EBS)
- SQL Server (EBS)

## RDS Multi-AZ

- It is used to help with resilience and business continuity.
- Multi-AZ configures a secondary RDS instance (replica) within a different AZ in the same region as the primary
instance.
- The only purpose of Multi-AZ is to provide a failover option for a primary RDS instance. It's not to be used
as a secondary replica to offload read-only traffic to.
- The replication of data happens synchronously.
- RDS uses a Failover mechanism on Oracle, MySQL, MariaDB and PostgreSQL instances. The failover process
happens automatically and is managed by AWS. RDS updates the DNS record to point to the secondary instance
within 60-120 seconds.
- SQL Server Multi-AZ is achieved through the use of **SQL Server Mirroring**.
- Amazon Aurora DB clusters are fault tolerant by default and it is achieved within the cluster by replicating
the data across different instances in different AZs. The automatic provision launches a new primary instance
in the event of a failure which can take up to 10 minutes. This time can be reduced when you enable Multi-AZ on
Aurora cluster which allows RDS to provision a replica within a different AZ automatically. 

## Read Replicas

Read Replicas are available for MySQL, MariaDB, PostgreSQL, Amazon Aurora, Oracle, and SQL Server.

- Read Replicas are NOT used for resiliency or as secondary instance in the event of a failover.
- Read Replicas are used to serve read-only access to your database data via a separate instance.
- It is possible to deploy more than one read replica for a primary DB.
- Adding more replicas allows you to scale your read performance to a wider range of applications.
- You are able to deploy read replicas in different regions.
- It is also possible to promote an existing read replica to replace the primary DB in the event 
of an incident.
- During any maintenance of the primary instance, read traffic can be served via your read replicas.
- Amazon RDS for MySQL Read Replicas require a transactional storage engine and are only supported
for the InnoDB storage engine. (**InnoDB** is a storage engine for the database management system MySQL
and MariaDB. Since the release of MySQL 5.5.5 in 2010, it replaced MyISAM as MySQL's default table type.
InnoDB is transactional storage engine. MyISAM is non-transactional storage engine.)
- Read Replicas can be promoted to "standalone" DB instances.

<br />

# Amazon DynamoDB

Amazon DynamoDB is **NoSQL** DB (key-value stores). DynamoDB tables are schemaless.

Secondary Indexes:
- Global: lets you query across the entire table to find any record that matches a particular value.
- Local: can only help find data within a single partition key.

<br />

# Amazon ElasticCache

Amazon ElastiCache is a service that makes it easy to deploy, operate, and scale open-source, 
**in-memory** data stores in the cloud. This service improves the performance through caching, 
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

Amazon Redshift is a fast, fully-managed, petabyte-scale **data warehouse**. And it's designed for 
high performance and **analysis** of information capable of storing and processing petabytes of data and 
provide access to this data, using your existing **business intelligence tools**, using standard SQL. 
It operates as a relational database management system, and therefore is compatible with other 
RDBMS applications. Redshift itself is based upon PostgreSQL 8.0.2, but it contains a number of 
differences from PostgreSQL.

Relational Database Management System

### ETL

Extract, Transform and Load data.

### Cluster, Compute Node, Leader Node, Node Slice

- A Cluster is a grouping of Compute Nodes.
- A Node Slice is a partition of a Compute Node where the node memory and disk space split.

### Connection

ODBC and JDBC drivers

### Performance Features

- Massively Parallel Processing (MPP)
- Columnar Data Storage
- Result Caching
- Integrates with Amazon CloudWatch

<br />

# Amazon Quantum Ledger Database (QLDB)

Amazon QLDB is a fully managed and serverless database service, which has been designed as a **ledger** database.
One quick example of the use cases would be for recording financial data over a period of time. QLDB would 
allow to maintain a complete history of accounting and transactional data between multiple parties in an 
**immutable, transparent and cryptographic** way through the use of the cryptographic algorithm, SHA-256, making 
it highly secure. (Other use cases: audit, insurance, etc.) It acts as a central and trusted authority.

This may sound similar to **blockchain** technology where a ledger is also used. However, in blockchain, 
that ledger is distributed across multiple hosts in a decentralized environment, whereas QLDB is owned and 
managed by a central and trusted authority.

Amazon QLDB is maintaining an immutable ledger with cryptographic abilities to enable the verifiable tracking
of changes over time.

Data for your QLDB database is placed into tables of **Amazon Ion documents** (a superset of JSON) Tables 
are comprised of a group of Amazon Ion documents and their revisions and QLDB by design maintains an audit
history of all changes in addition to all previous revisions of the same Ion document. This creates a journal 
of transactional changes. And the journal acts as an append-only transactional log and maintain the source
of truth of that document an dthe entire history of changes.

Amazon QLDB uses Journal Storage and Indexed Storage
- Journal Storage is the storage that is used to hold the history of changes made within the ledger database.
- Indexed Storage is used to provision the tables and indexes within your ledger database.

Amazon QLDB integrated with Amazon Kinesis.

<br />

# Amazon DocumentDB

DocumentDB is a **document** database, which provides the ability to quickly and easily store any 
**JSON-like document data** which can then be queried and indexed. It is fully managed and runs in a VPC.
**Indexing** enhances the speed of retrieving data within a database thanks to an indexing data 
structure stored within the database.

DocumentDB has the ability to scale both its **compute and storage separately** from each other.

Amazon DocumentDB has full compatibility with **MongoDB**, which again is another document database, 
meaning that if required, you can easily migrate any existing MongoDB databases you might have into 
Amazon DocumentDB using the **Database Migration Service (DMS)**.

Components: 
- Cluster
- DB Instance (read only)
- Primary DB instance (read and write)
- Endpoints

An endpoint is a URL address with an identified port that points to your infrastructure. There are three 
types of endpoints:
- cluster endpoint: points directly to the current primary DB instance of a cluster. It should be used
by applications that require both read and write access to the database.
- Reader endpoint: used to connect to read replicas and allows applications to access your databaes for 
read requests. Only a single reader endpoint exists, even if you have multiple read replicas
- Instance endpoint: every instance within your cluster will have a unique instance endpoint. It allows
you to direct certain traffic to specific instances within the cluster. You might want to do this
for load balancing reasons across your applications reading from your replicas.

Automatic backup (Amazon S3)and retention policy.

<br />

# Amazon Keyspaces for Apache Cassandra

Keyspace is a grouping of tables Keyspaces offers two different throughput capacity modes when 
working with your read and writes to and from your tables:
- On-demand throughput (default)
- Provisioned throughput

CQL - Cassandra Query Language

<br />