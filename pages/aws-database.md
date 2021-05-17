# AWS Database

- [Amazon RDS (Relational Database Service)](#amazon-rds-relational-database-service)
- [Amazon DynamoDB](#amazon-dynamodb)
- [Amazon ElasticCache](#amazon-elasticcache)
- [Amazon Neptune](#amazon-neptune)
- [Amazon Redshift](#amazon-redshift)
- [Amazon QLDB](#amazon-quantum-ledger-database-qldb)
- [Amazon DocumentDB](#amazon-documentdb)
- [Amazon Keyspaces](#amazon-keyspaces-for-apache-cassandra)

<br />

# Amazon RDS (Relational Database Service)

Available database engines for Amazon **Relational** Database Service:
- MySQL (EBS)
- MariaDB (EBS)
- PostgreSQL (EBS)
- Amazon Aurora (Shared Cluster Storage)
- Oracle (EBS)
- SQL Server (EBS)

### RDS Multi-AZ

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


<br />

# Amazon DynamoDB

Amazon DynamoDB is **NoSQL** DB (key-value stores). DynamoDB tables are schemaless. It is designed internally to automatically partition data and incoming traffic across **multiple partitions**. Partitions are stored on **numerous backend servers** distributed across **three AZs** within a single region. A DynamoDB partition is a dedicated area of SSD storage allocated to a table and for which is automatically replicated synchronously across 3 AZs within a particular region. 

### Global Tables
DynamoDB provides a secondary layer of availability in the form of cross region (Global Tables). A Global table gives you the capability to replicate a single table across one or many alternate regions. A Global table elevates the availability of your data and enables applications to take advantage of data locality. Users can be served data directly from the closest geographically located table replica. Existing DynamoDB tables can be converted into global tables either by using the **relevant configuration options** exposed within the AWS DynamoDB console or by using the AWS CLI and executing this command:
`aws dynamodb update-table`.

### Secondary Indexes
- Global: lets you query across the entire table to find any record that matches a particular value.
- Local: can only help find data within a single partition key.

### On Demand Backups
On demand backups allow you to request a full backup of a table, as it is at the very moment of the backup request is made. On demand backups are manually requested and can be performed either through the AWS DynamoDB console or by using the AWS CLI. Scheduling on demand backups provides you with the ability to restore table data back to a point in time. On demand backups remain in the account until they are explicitly requested to be deleted by an administrator. Backups typically finish within seconds and have zero impact on the table performance and availability.

### Point In Time Recovery (PITR)
PITR operates at the table level and provides you with the ability to perform a point in time recovery for anytime between the current time and the last 35 days. This feature needs to be enabled as it is disabled by default. The restoration will always be performed into a new table. Table restoration can be performed in the same region as the original table or into a different region all together.

### Scan And Query
DynamoDB provides two commands for searching data on the table: scan and query. A scan operation examines every item on the table and returns all the data attributes for each one of them. When you initially navigate to the Items tab for a table, a scan is performed by default.


### Downside of DynamoDB:
- Your data is automatically replicated to different AZs and that replication usually happens quickly in milliseconds. But sometimes it can take longer. This is known as **eventual consistency**.
- There are certain kinds of queries and table scans that may return older versions of data before the most recent copy.
- You might have a requirement where you need microsecond response times in read-heavy workloads and this is where DAX comes in to play.

### DynamoDB Accelerator (DAX)
DAX is an **in-memory cache** delivering a significant performance enhancement up to 10 times as fast as the default DynamoDB settings allowing response times to decrease from milliseconds to microseconds. It is a fully managed feature offered by AWS and as a result is also highly available. DAX is also highly scalable making it capable of handling millions of requests per second without any requirement for you to modify any logic to your applications or solutions.

Your DAX deployment can start with a multi-node cluster containing a minimum of 3 nodes which you can quickly and easily modify and expand reaching a maximum of 10 nodes with 1 primary and 9 read replicas. It can also enable you to reduce your provisioned read capacity within DynamoDB. Reducing the provisioned requirements on your DynamoDB will also reduce your overall costs.

From a security perspective, DAX also support encryption at rest. This ensures that any cached data is encrypted using the 256-bit Advanced Encryption Standard algorithm with the integration of the Key Management Service (KMS) to manage the encryption keys.

DAX is a separate entity to DynamoDB and so architecturally it sits outside of DynamoDB and is placed within your VPC where as DynamoDB sits outside of your VPC and is accessed via an endpoint. DAX will deploy a node in each of the subnets of the subnet group with one of those nodes being the primary and the remaining nodes will act as read replicas.

To allow your EC3 instances to interact with DAX you will need to install a DAX client on those EC2 instances. This client then intercepts with and directs all DynamoDB API calls made from your client to your new DAX cluster endpoint, where the incoming request is then load balanced across all the nodes in teh cluster. You must ensure that the security group associated with your DAX cluster is open to TCP port 8111 on the inbound rule set.

DAX does not process any requests relating to table operations and management, for example, create, update or delete tables.

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
