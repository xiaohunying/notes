<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Database

- [Amazon RDS (Relational Database Service)](#amazon-rds-relational-database-service)
  - [RDS Multi-AZ](#rds-multi-az)
  - [Read Replicas](#read-replicas)
  - [Amazon Aurora](#amazon-aurora)
- [Amazon DynamoDB (NoSQL Database)](#amazon-dynamodb)
  - [DynamoDB Accelerator (DAX)](#dynamodb-accelerator-dax)
- [Amazon ElasticCache (In-Memory Data Store)](#amazon-elasticcache)
  - [Cache Engines](#cache-engines)
  - [Components of ElastiCache](#components-of-elasticache)
  - [ElastiCache Common Use Cases](#elastiCache-common-use-cases)
- [Amazon Neptune (Graph Database)](#amazon-neptune)
- [Amazon Redshift](#amazon-redshift)
- [Amazon RDS Costs](#amazon-rds-costs)

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
It is used to help with resilience and business continuity. **The only purpose of Multi-AZ is to provide a failover option for a primary RDS instance.** It's NOT to be used as a secondary replica to offload read-only traffic to. Multi-AZ configures a secondary RDS instance (replica) within a different AZ in the same region as the primary instance. The replication of data happens synchronously.

- RDS uses a Failover mechanism on **Oracle**, **MySQL**, **MariaDB** and **PostgreSQL** instances. The failover process happens automatically and is managed by AWS. RDS updates the DNS record to point to the secondary instance within 60-120 seconds. The failover process happens in the following scnarios on the primary instance:
  - Patching maintenance
  - Host failure
  - Availability zone failure
  - Instance rebooted with failover
  - DB instance class is modified
- **SQL Server** Multi-AZ is achieved through the use of **SQL Server Mirroring**. You need to ensure you have your environment configured correctly:
  - A DB subnet group must be configured with a minimum of 2 different AZ's within it.
  - You can specify which availability zone the standby mirrored instance will reside in.
- **Amazon Aurora** DB clusters are fault tolerant by default
  - This is achieved within the cluster by replicating the data across different instances in different AZs. 
  - Aurora can automatically provision and launch a new primary instance in the event of a failure which can take up to 10 minutes.
  - This time can be reduced when you **enable Multi-AZ** on Aurora cluster which allows RDS to provision a replica within a different AZ automatically.
    - Should a failure occur, the replica instance is promoted to the new primary instance without having to wait 10 minutes
    - This creates a highly available and resilient database solution
    - It is possible to create up to 15 replicas if required, each with a different priority

### Read Replicas
Read Replicas are NOT used for resiliency or as secondary instance in the event of a failover. They are used to serve read-only access to your database data via a separate instance.
- Read Replicas are available for MySQL, MariaDB, PostgreSQL, Amazon Aurora, Oracle, and SQL Server.
- It is possible to deploy more than one read replica for a primary DB.
- Adding more replicas allows you to scale your read performance to a wider range of applications.
- You are able to deploy read replicas in different regions.
- It is also possible to promote an existing read replica to replace the primary DB in the event of an incident.
- During any maintenance of the primary instance, read traffic can be served via your read replicas.

Read Replicas for MySQL
- Amazon RDS for MySQL Read Replicas require a transactional storage engine and are only supported for the InnoDB storage engine. (**InnoDB** is a storage engine for the database management system MySQL and MariaDB. Since the release of MySQL 5.5.5 in 2010, it replaced MyISAM as MySQL's default table type. InnoDB is transactional storage engine. MyISAM is non-transactional storage engine.)
- It is also possible to have nested read replica chains
  - A read replica replicates from your source DB and can then act as a source DB for another read replica
  - This chain can only be maximum of 4 layers deep
  - The same prerequisites must also apply to the source read replica
  - You can have up to a maximum of 5 read replicas per each source DB
- If an outage occurs with the primary instance, RDS automatically redirects the read replica source to the secondary DB. Amazon Cloudwatch can monitor the synchronisation between the source DB and the read replica through a metric known as ReplicaLag.

Read Replicas for MariaDB
- You still need to have the backup retention period greater than 0, and you can only have 5 read replicas per source DB.
- The same read replicas nesting rules apply and you also have the same monitoring metric for CloudWatch.
- You can be running ANY version of MariaDB for read replicas.

Read Replicas for PostgreSQL
- The automatic backup retention value needs to be greater than 0 and the limitation of read replicas is 5 per source DB.
- When using PostgreSQL, you need to run version 9.3.5 or later for read replicas.
- The native PostgreSQL streaming replication is used to handle the replication and creation of the read replica.
- The connection between the master and the read replica instance replicates data asynchronously between the 2 instances.
- A role is used to manage replication when using PostgreSQL.
- PostgreSQL allows you to create a Multi-AZ read replica instance.
- PostgreSQL does not allow nested read replicas.

### Amazon Aurora
Aurora separate the compute layer (EC2) and storage layer from each other. It uses a **quorum and gossip protocol** baked within the storage layer to ensure that the data remains consistent. Aurora in general, regardless of the compute layer setup, always provides **6 way replicated storage across 3 AZs**. Aurora is only supported in regions that have 3 or more AZs.
- Aurora provides both automatic and manual failover of the master either of which takes approximately 30 seconds to complete.
- **Connection endpoint** load balancing is implemented internally using Route 53 DNS. Be careful in the client layer **not to cache** the connection endpoint lookups longer than their specified TTLs. There are 4 different connection endpoint types:
  - Cluster endpoint
    - The cluster endpoint points to the current master database instance. using the Cluster endpoint allows your application to perform read and writes against the master instance.
  - Reader endpoint
    - The reader endpoint load balancers connections across the read replica fleet within the cluster.
  - Custom endpoint
    - Cutom endpoint can be used to group instances based on instance size or maybe group them on a particular DB parameter group. You can then dedicate the custom endpoint for a specific role or task within your organisation.
  - Instance endpoint
    - An instance endpoint maps directly to a cluster instance. Each and every cluster instance has its own instance endpoint. You can use an instance endpoint when you want fine-grained control over which instance you need to service your requests.
- Single master - Multiple Read Replicas
  - Single master instance can be configured with up to 15 read replicas when using Aurora.
  - This type of cluster supports being stopped and started manually in its entirety. When you stop and start a cluster, all underlying compute instances are either stopped or started. When stopped the cluster remains stopped for up to 7 days, after which it will automatically restart.
  - Daily bakups are automatically performed with a default retention period of 1 day and for which can be adjusted up to a maximum retention period of 35 days.
  - On-demand manual snapshots can be performed on the database at any time. The manual snapshots are stored indefinitely until you explicitly choose to delete them. Restores are performed into a new databse.
- Multi-Master cluster:
  - An Aurora multi master setup leverages 2 compute instances configured in active-active read write configuration.
  - If an instance outage occurs in one AZ, all database writes can be redirected to the remaining active instance (all without the need to perform a failover). 
  - A maximum of 2 compute instances can be configured as masters in a multi-master cluster. 
  - You can not add read replicas to a multi master cluster.
  - Incoming database connections to an Aurora multi master cluster are not load balanced by the service. Load balancing connection logic must be implemented and performed within the client.
- Aurora Serverless
  - An Aurora Serverless database is configured with a **single connection endpoint** which makes sense (given that it it designed to be serverless) this endpoint is obviously used for all read and writes.
  - An option to consider is the **Web Service Data API feature** which is available only on Aurora Serverless databases. It makes implementing Lambda functions which need to perform data lookups and/or mutations within an Aurora serverless database a breeze. The AWS CLI has been updated to allow you to execute queries through it from the command line.
  - Aurora Serverless performs a **continuous automatic backup** of the database with a default retention period of 1 day - which can be manually increased to a maximum retention period of 35 days. This style of backup gives you the capability of **restoring to a point in time** within the currently configured backup retention period. Restores are performed to a **new serverless database cluster**.

<br />

# Amazon DynamoDB

Amazon DynamoDB is **NoSQL** DB (key-value stores). DynamoDB tables are schemaless. It is designed internally to automatically partition data and incoming traffic across **multiple partitions**. Partitions are stored on **numerous backend servers** distributed across **three AZs** within a single region. A DynamoDB partition is a dedicated area of SSD storage allocated to a table and for which is automatically replicated synchronously across 3 AZs within a particular region. 

- **Global Tables**: DynamoDB provides a secondary layer of availability in the form of cross region (Global Tables). A Global table gives you the capability to replicate a single table across one or many alternate regions. A Global table elevates the availability of your data and enables applications to take advantage of data locality. Users can be served data directly from the closest geographically located table replica. Existing DynamoDB tables can be converted into global tables either by using the **relevant configuration options** exposed within the AWS DynamoDB console or by using the AWS CLI and executing this command: `aws dynamodb update-table`.
- Secondary Indexes
  - Global: lets you query across the entire table to find any record that matches a particular value.
  - Local: can only help find data within a single partition key.
- **On Demand Backups** allow you to request a full backup of a table, as it is at the very moment of the backup request is made. On demand backups are manually requested and can be performed either through the AWS DynamoDB console or by using the AWS CLI. Scheduling on demand backups provides you with the ability to restore table data back to a point in time. On demand backups remain in the account until they are explicitly requested to be deleted by an administrator. Backups typically finish within seconds and have zero impact on the table performance and availability.
- **Point In Time Recovery (PITR)** operates at the table level and provides you with the ability to perform a point in time recovery for anytime between the current time and the last 35 days. This feature needs to be enabled as it is disabled by default. The restoration will always be performed into a new table. Table restoration can be performed in the same region as the original table or into a different region all together.
- **Scan And Query**: DynamoDB provides two commands for searching data on the table: scan and query. A scan operation examines every item on the table and returns all the data attributes for each one of them. When you initially navigate to the Items tab for a table, a scan is performed by default.

### DynamoDB Accelerator (DAX)
Downside of DynamoDB:
- Your data is automatically replicated to different AZs and that replication usually happens quickly in milliseconds. But sometimes it can take longer. This is known as **eventual consistency**.
- There are certain kinds of queries and table scans that may return older versions of data before the most recent copy.
- You might have a requirement where you need microsecond response times in read-heavy workloads and this is where DAX comes in to play.

DynamoDB Accelerator (DAX)
- DAX is an **in-memory cache** delivering a significant performance enhancement up to 10 times as fast as the default DynamoDB settings allowing response times to decrease from milliseconds to microseconds. It is a fully managed feature offered by AWS and as a result is also highly available. DAX is also highly scalable making it capable of handling millions of requests per second without any requirement for you to modify any logic to your applications or solutions.
- Your DAX deployment can start with a multi-node cluster containing a minimum of 3 nodes which you can quickly and easily modify and expand reaching a maximum of 10 nodes with 1 primary and 9 read replicas. It can also enable you to reduce your provisioned read capacity within DynamoDB. Reducing the provisioned requirements on your DynamoDB will also reduce your overall costs.
- From a security perspective, DAX also support encryption at rest. This ensures that any cached data is encrypted using the 256-bit Advanced Encryption Standard algorithm with the integration of the Key Management Service (KMS) to manage the encryption keys.
- DAX is a separate entity to DynamoDB and so architecturally it sits outside of DynamoDB and is placed within your VPC where as DynamoDB sits outside of your VPC and is accessed via an endpoint. DAX will deploy a node in each of the subnets of the subnet group with one of those nodes being the primary and the remaining nodes will act as read replicas.
- To allow your EC2 instances to interact with DAX you will need to install a DAX client on those EC2 instances. This client then intercepts with and directs all DynamoDB API calls made from your client to your new DAX cluster endpoint, where the incoming request is then load balanced across all the nodes in the cluster. You must ensure that the security group associated with your DAX cluster is open to TCP port 8111 on the inbound rule set.
- DAX does not process any requests relating to table operations and management, for example, create, update or delete tables.

<br />

# Amazon ElasticCache

Amazon ElastiCache is a service that makes it easy to deploy, operate, and scale open-source, **in-memory** data stores in the cloud. This service improves the performance through caching, where web applications allow you to retrieve information from fast managed in-memory data stores instead of relying entirely on slower disk-based solutions.

### Cache Engines
- Amazon ElastiCache for **Memcached**: 
  - A high performance, submillisecond latency Memcached-compatible, in-memory, key-value store service that can either be used as a cache, in addition to a data store. 
  - Use cases:
    - Caching
    - Session Store
- Amazon ElastiCache for **Redis**: 
  - An in-memory data store designed for high performance and again providing sub-millisecond latency on a huge scale to real-time applications. 
  - Cluster Mode:
    - Disabled: each cluster will have 1 shard
    - Enabled: each cluster can have up to 90 shards
  - Use cases:
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
- **Node**: a fixed sized chunk of secure, network-attached RAM.
- **Shard**: Redis Shard (also known as node group). a group of up to 6 ElasticCache nodes.
- **Redis Cluster**: a group of 1-90 Redis shards. Data is partitioned all crossed the shards.
- **Memcached Cluster**: a collection of one or more cache nodes.

### ElastiCache Common Use Cases
- Online gaming
- Social networking sites
- Real-time analytics

<br />

# Amazon Neptune

Amazon Neptune is a fast, reliable, secure, and fully-managed **graph database** service. It is used to help you both store and navigate relationships between highly connected data which could contain billions of separate relationships. Graph databases are ideal if their focus is on being able to identify these relationships of interconnected data, rather than the actual data itself.

### Use Cases:
- Social Networking
- Fraud Detection
- Recommendation Engines

### Query Languages:
- Apache Tinkerpop Gremlin
- World Wide Web Consortium SPASQL

### Connect to Neptune Database:
- Cluster endpoints:
  - Points directly to the current primary DB instance of a cluster.
  - Should be used by applications that require both read and write access to the database.
  - When Primary DB instance fails, will point to the new primary instance without any changes required by your applications accessing the database.
- Reader endpoints
  - Used to connect to read replicas.
  - Allows applications to access your database on a read only basis for queries.
  - A single reader endpoint exists, even if you have multiple read replicas.
  - Connections served by the read replicas will be performed on a round-robin basis.
  - The endpoint does not load balance your traffic in any way across the available replicas in your cluster.
- Instance endpoints
  - Every instance within your cluster will have a unique instance endpoint.
  - Allows you to direct certian traffic to specific instances within the cluster.
  - You might want to do this for load balancing reasons across your applications reading from your replicas.

<br />

# Amazon Redshift

Amazon Redshift is a fast, fully-managed, petabyte-scale **data warehouse**. And it's designed for high performance and **analysis** of information capable of storing and processing petabytes of data and provide access to this data, using your existing **business intelligence tools**, using standard SQL. It operates as a relational database management system, and therefore is compatible with other RDBMS applications. Redshift itself is based upon PostgreSQL 8.0.2, but it contains a number of differences from PostgreSQL.

A data warehouse is used to consolidate data from multiple sources to allow you to run business intelligent tools, across your data, to help you identify actionable business information, which can then be used to direct and drive your organization to make effective data-driven decisions to the benefit of your company. As a result, using a data warehouse is a very effective way to manage your reporting and data analysis at scale.

### Redshift Componnets
- **Cluster**: A Cluster is a grouping of Compute Nodes.
- **Compute Node**
- **Leader Node**: The leader node of the cluster has the role of coordinating communication between your compute nodes in your cluster and your external applications accessing your Redshift data warehouse. So the leader node is essentially gateway into your cluster from your applications. When external applications are querying the data in your warehouse, the leader node will create execution plans, containing code to return the required results from the database.
- **Node Slice**: A Node Slice is a partition of a Compute Node where the node memory and disk space split.

### Connection
Communication between your BI applications and Redshift, will use industry standard open database connectivity, **ODBC**. And Java database conductivity, **JDBC** drivers for PostgreSQL.

### Performance Features
Amazon Redshift integrates with Amazon CloudWatch which generate query/load performance data
- Massively Parallel Processing (MPP)
- Columnar Data Storage
- Result Caching

<br />

# Amazon RDS Costs

Currently, only the Oracle database engine uses the BYOL (Bring Your Own License) purchase options, all other DB engines only use on-demand instances and reserved instances, with the added exception of Aurora also using serverless as an additional purchasing option. 

### RDS Instance Purchasing Options
| DB          | On-demand Instances | On-demand Instances (BYOL) | Reserved Instances | Reserved Instances (BYOL) | Serverless |
|-------------|:-------------------:|---------------------------:|-------------------:|--------------------------:|-----------:|
| MySQL       | Y                   |                            | Y                  |                           |            |
| PostgresSQL | Y                   |                            | Y                  |                           |            |
| MariaDB     | Y                   |                            | Y                  |                           |            |
| Aurora      | Y                   |                            | Y                  |                           | Y          |
| Oracle      | Y                   | Y                          | Y                  | Y                         |            |
| SQL Server  | Y                   |                            | Y                  |                           |            |

- **On-Demand Instance**:
  - Any partial DB instance hours used are cost on per second increments.
  - For any database changes that alter the running costs, such as modifying the instance or creating the DB instance, then a minimum of a 10-minute charge will be applied even if the DB is terminated or altered again before 10 minutes has passed.
- **Reserved Instances**:
  - allow you to purchase a discount for an instance type with set criteria for a set period of time in return for a reduced cost compared to on-demand instances. Further reductions with reserved instances is depending on which **Payment Method** you choose. 
  - Payment method: 
    - All Upfront
    - Partial Upfront
    - No Upfront
- **Amazon Aurora Serverless**:
  - No instances to manage
  - On-demand pricing not applicable; Serverless pricing is measured in **Aurora Capacity Units (ACU)**
  - Each ACU consists of 2GB of memory & any associated CPU and networking requirements

### Database Storage and I/O Pricing
- General Purpose SSD storage
  - When using SSD, you are charged for the amount of storage provisioned and not for the number of I/Os processed.
- Provisioned IOPS (SSD) storage
  - The charges for this option are based upon the amount of storage provisioned in addition to the IOPS throughput selected, again, you are charged not for the total number of I/Os processed.
- Magnetic storage
- Amazon Aurora
  - The pricing metric used is **GB-month**, in addition to **the actual number of I/Os processed**, which are billed per million requests. 

### Backup Storage Pricing
- Amazon RDS does not charge any backup storage costs that equate to the total sum of provisioned storage used with your databases within a specific region.
- Any backup storage used over this 'free' tier is charged

What is Backup Storage:
- Any automated backups taken use backup storage.
- Any manual snapshots that are taken of your database will also use backup storage.
- By extending your backup retention periods (how long you'd like to keep your backups for) will increase the amount of storage required for backups.
- Backup storage is calculated based upon the total amount of backup storage consumed within a specific region across all your RDS databases.
- If you copy your backups to another region, this will also increase the amount of backup storage used within that new region.

### Backtrack Storage Pricing
Backtrack is a feature that is only currently available for a MySQL-compatible Aurora database, which allows you to go back in time on the database to recover from an error or incident.

### Snapshot Export Pricing
Snapshots in RDS are your backups of your database tables and instances, and these snapshots can then be exported out of Amazon RDS, to Amazon S3. The cost associated with performing snapshot exports are based on region by region basis.

### Data Transfer Pricing
You are charged, when
- Data transferred IN to your RDS database from the internet
- Data transferred OUT from your RDS database to the internet
- Data transferred OUT to Amazon CloudFront
- Data transferred OUT to AWS Regions
- Data transferred OUT to EC2 instances in the same availability zone
- Data transferred between availability zones for multi-az replication
- Data transferred between an EC2 instance and an RDS instance in different availability zones of the same region
- Data transferred when a snapshot copy is transferred to a different region

You will NOT be charged, when
- Any data that is transferred IN to your RDS database from the internet
- Any Data that is transferred OUT to Amazon CloudFront
- Any data that is transferred OUT to EC2 instances in the same availability zone
- Data transferred between availability zones for multi-az replication

<br />

