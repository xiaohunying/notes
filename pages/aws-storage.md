<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Storage

- [Backup and DR strategies](#backup-and-dr-strategies)
- [Data Transfer in/out of AWS](#data-transfer-in-out-of-aws)
- [Costs With AWS Storage Services](#costs-with-aws-storage-services)

<br />

### On-premise Options:
- SAN (Storage Area Network)
- NAS (Network Attached Storage)
- DAS (Directly Attached Storage)
- Tape backup

### AWS Data Storage Options:
- Block Storage
  - [EBS: Elastic Block Store](#ebs-elastic-block-store)
  - [EC2 Instance Storage](#ec2-instance-storage)
- File Storage
  - [EFS: Elastic File System](#efs-elastic-file-system)
- Object Storage
  - [S3: Simple Storage Service](#s3-simple-storage-service)

<br />

# S3: Simple Storage Service

- Amazon S3 is a **<span style="color:yellow">regional service</span>**.
- Amazon S3 can be used for **static website hosting**. You need to (1) The bucket content 
needs to be marked public access; (2) Index document is added to the bucket; (3) Add a 
bucket policy to allow the public to be able to reach the bucket. It also support redirect 
requests.
- **Server-Access Logging**: 
  - When the target bucket is for logs, the source bucket and target bucket should be in the 
  same region.
  - The permissions of logs are controlled using **Log Delivery Group**. (1) If server-access 
  logging is enabled through AWS console, the Log Delivery Group is automatically added to 
  the ACL (Access Control List) of the target bucket; (2) If it is through AWS S3 API or AWS 
  SDK, you need to manually configure this access. Permissions of the S3 access log group can 
  only be assigned via ACL.
  - If encryption is enabled on your target bucket, access logs will only be delivered if 
  this is set to **SSE-S3** because encryption with KMS is not supported.
- **S3 bucket**:
  - **Bucket name** needs to be unique globally. You can create a **folder** in a bucket, but S3 
  is not a file system.
  - **Versioning**: unversioned (default), versioning-enabled and versioning-suspended.
  - **Events**: You can monitor events occured to the bucket. Any events which are recorded can 
  then be sent to an SNS topic or an SQS queue or a lambda function. 
  - **Object Level Logging**: AWS S3 bucket Object level logging is closely related to 
  **AWS CloudTrail** service. S3 data events includes GetObject, DeleteObject and PutObject.
  - **Encryption**: S3 bucket has two default encryption options: AES-256 (SSE-S3) and AWS-KMS 
  (SSE-KMS).
  - **Object Lock** on a bucket can only be achieved at the time of the creation of the bucket.
  Versioning needs to be enabled. Once object lock is enabled, it is permanent and cannot be 
  disabled. There are two retention modes: Governance mode and Compliance mode. 
    - **Governance mode**: Users can't overwrite or delete an object version or alter its lock 
    settings unless they have special permissions. With governance mode, you protect objects 
    against being deleted by most users.
    - **Compliance mode**: No users (includes root users) can override the retention periods set 
    or delete an object. 
    - **Legal hold**: Object Lock also enables you to place a legal hold on an object version. 
    Like a retention period, a legal hold prevents an object version from being overwritten or 
    deleted. However, a legal hold doesn't have an associated retention period and remains in 
    effect until removed.
  - **Tags** in S3 bucket is known as S3 cost allocation tags. they are key-value pairs.
  - **Transfer Acceleration** is to speed up data transfer. It uses Amazon **CloudFront** service, 
  which is a Content Delivery Network (CDN) service that essentially provides a means of 
  distributing traffic worldwide via edge locations.
- **Requester Pays**: When this feature is enabled, any cost associated with requests and 
data transfer becomes the responsibilities of the requester instead of the bucket owner. The
bucket owner still pays the storage of the objects in the bucket. Authenticating requests allow
AWS trace back to the identity and to which AWS account that identity is originating from.
And the cost is then transfered to that account.
- **S3 Storage Classes**:
  - S3 Standard
  - S3 INT (Intelligent): frequent/infrequent accesses
  - S3 S-IA (Standard infrequent access)
  - S3 Z-IA (Single zone, infrequent access)
  - S3 Glacier: long term, backup and archive
  - S3 G_DA: S3 Glacier deep archive
- **S3 Glacier**: 
  - Move data into S3 Glacier using APIs or SDKs. 
  - Retrieval data using APIs, SDKs or CLI. Retrieval data options: Expedited (5 minutes, 250MB), 
  Standard (3-5 hours) and Bulk (PB of data, 5-12 hours).
  - **Provisioned Capacity Unit**: You can pay a fixed up-front fee for a given month to ensure the 
  availability of retrieval capacity for expedited retrievals from Amazon S3 Glacier vaults. You 
  can purchase multiple provisioned capacity units per month to increase the amount of data you 
  can retrieve.
- **S3 Glacier Deep Archive**:
  - Retrieval data method: Standard and Bulk

<br />

# EBS: Elastic Block Store

- EBS operates as a separate service to EC2. **Each EBS volume can ONLY be attached to ONE EC2 instance.**
Mulitple EBS volumns can be attached to a single EC2 instance. Data is retained if the EC2 instance
is stopped, restarted or terminated.
- **EBS Snapshot**: An EBS snapshot is a point-in-time copy of your EBS volume, which is lazily
copied to Amazon S3. EBS snapshots are incremental copies of data. This means that only unique blocks
of EBS volume data that have changed since the last EBS snapshot are stored in the next EBS snapshot.
- EBS offers **encryption at rest and in transit**. Encryption is managed by the EBS service itself.
It can be enabled with a checkbox. The encryption is only available in selective instances.
- **SSD (Solid State Drive) backed storage** (EBS volume type): It is suited for work with smaller blocks, 
databases using transactional workloads. It is often used for boot volumes on EC2 instances.
  - General Purpose SSD (GP2)
  - Provisioned IOPS (IO1)
- **HDD (Hard Disk Drive) backed storage** (EBS volume type): It is designed for workloads requires
a high rate of throughput (MB/s). It process larger blocks of data. For example, big data processing
and logging information.
  - Cold HDD (SC1)
  - Throughput Optimized HDD (ST1)

<br />

# EC2 Instance Storage

- EC2 instance storage volumes provide ephemeral storage (temporary). It is not recommended for 
critical or valuable data. **Your data is lost if the instance is stopped or terminated. 
Your data will remain in tact if your instance is rebooted**. Instance storage volumes are not
available for all instances.
- It is ideal as a cache or buffer for rapidly changing data without the need for retention.
It is often used within a load balancing group where data is replicated and pooled between 
the fleet.
- **No additional cost** for storage. It is included in the price of instance. The capacity of 
instance storage volumes increases with the size of the EC2 instance. It has the same security
mechanisms provided by EC2.

<br />

# EFS: Elastic File System

- EFS is a regional service. Apps across multiple AZ can all access the same file systems. It is 
not available within all regions.
- EFS supports both NFS version 4.1 and 4.0.
- Two things to consider while choosing EFS: Storage classes and performance options.
- Storage classes:
  - Standard
  - Infrequent Access (IA)
- Performance modes:
  - General Purpose: EFS provide a **CloudWatch** metric for general purpose mode.
  - Max I/O
  - Throughput modes: Bursting Throughput (default) and Provisioned Throughput.
- EFS Lifestyle Management

<br />

# Backup and DR strategies

When designing backup and DR strategies for business continuity plan, we need to
considering **RTO** (Recovery Time Objective) and **RPO** (Recovery Point Objective). 
RPO is the acceptable amount of data loss measured in time.

- **Backup and restore**
- **Pilot light**: The amount of DR resources are reserved as Prod.
- **Warm standby**: The minimum DR resources are reserved and can be scaled up when used. 
- **Multi-sites** 

<br />

# Data Transfer in/out of AWS

There are many ways to move data: direct connect, VPN connect and internet connect.
Amazon provides:
- [AWS Snowball](#aws-snowball) (direct connect)
- [AWS Snowmobile](#aws-snowmobile) (direct connect)
- [AWS Storage Gateway](#aws-storage-gateway) (internet connect)

### AWS Snowball

It is used to move data either from your on-premise data center to Amazon
S3 or from Amazon S3 back to your data center using a **physical appliance**, 
known as a snowball. The snowball appliance comes as either a **50 TB or 80 TB** 
device. It is dust, water and tamper resistant. It is built for high speed
data transfer: RJ45 (CAT6), SFP+ Copper and SFP+ Optical.

### AWS Snowmobile

**To migrate large datasets of 10PB or more in a single location**, you should
use Snowmobile. For datasets less than 10PB or distributed in multiple locations,
you should use Snowball. In addition, you should evaluate the amount of available 
bandwidth in your network backbone. If you have a high speed backbone with hundreds 
of Gb/s of spare throughput, then you can use Snowmobile to migrate the large datasets 
all at once. If you have limited bandwidth on your backbone, you should consider using 
multiple Snowballs to migrate the data incrementally. Each Snowmobile has **a total 
capacity of up to 100 petabytes** and multiple Snowmobiles can be used in parallel to 
transfer exabytes of data. 

### AWS Storage Gateway

Storage Gateway allows you to provide a gateway between your own data center's storage
systems such as your SAN, NAS or DAS and Amazon S3 and Glacier on AWS. It is a **software
appliance** that can be stored within your own data center which allows integration
between your on-premise storage and that of AWS. This connectivity can allow you scale
your storage requirements both securely and cost efficiently.

- File Gateway
- Volume Gateway
  - Stored volume gateway
  - Cached volume gateway
- Tape Gateway

<br />

# Costs With AWS Storage Services

## Cost of S3 and Glacier

S3 Storage Class | Cost
--- | ---
S3 Standard | <ul><li>Storage cost - Per month per GB (price is reduced as you add more data)</li><li>Request cost - per 1000 requests (Based on request type; DELETE and CANCEL requests are free)</li></ul>
S3 Intelligent | <ul><li>Storage cost:<ul><li>Frequent access tier - per month per GB (price is reduced as you add more data)</li><li>Infrequent access tier - per month per GB (a flat rate)</li></ul></li><li>Request cost - per 1000 requests (Based on request type; DELETE and CANCEL requests are free)</li><li>Monitoring and automation fee - per month</li></ul>
S3 Standard, Infrequent Access | <ul><li>Storage cost - Per month per GB (a flat rate)</li><li>Request cost - per 1000 requests (Based on request type; DELETE and CANCEL requests are free)</li><li>Data retrieval cost - Per GB (a flat rate)</li></ul>
S3 Single Zone, Infrequent Access | <ul><li>Storage cost - Per month per GB (a flat rate)</li><li>Request cost - per 1000 requests (Based on request type; DELETE and CANCEL requests are free)</li><li>Data retrieval cost - Per GB (a flat rate)</li></ul>
S3 Glacier | <ul><li>Storage cost - Per month per GB (a flat rate)</li><li>Request cost - per 1000 requests (Based on request type; DELETE and CANCEL requests are free)</li><li>Data retrieval cost - Per GB (based on retrieval method)</li></ul>
S3 Glacier deep archive | <ul><li>Storage cost - Per month per GB (a flat rate)</li><li>Request cost - per 1000 requests (Based on request type; DELETE and CANCEL requests are free)</li><li>Data retrieval cost - Per GB (based on retrieval method)</li></ul>
Provisioned Capacity Unit | <ul><li>Data retrieval cost - Per unit</li></ul>

**Cost of Data transfer**: 
- Data Transfer is charged Per GB. Data transfer is free when (1) Data is transferred into 
S3 from internet; (2) Data is transferred out to Amazon CloudFront; (3) Data is transferred out to EC2 instances 
in the same region.
- Transfer Acceleration:
  - When we look at Transfer acceleration, the pricing structure for transfer costs changes and this is largely 
  due to the fact that your data is routed through an optimized network path to Amazon S3 via CloudFront edge 
  locations.
  - Whereas normal data transfer into amazon S3 is free from the internet, with transfer acceleration, this is 
  a cost associated per gigabyte dependant on which edge location is used. Also, there is an increased cost for 
  any data transferred OUT of S3, either to the internet or to another Region, again due to the edge location 
  acceleration involved.

**Cost of Management and replication**
- Features that have associated cost:
  - S3 Inventory (Per million objects listed)
  - S3 Analytics Storage Class Analysis (Per million objects monitored per month)
  - S3 Object Tagging (Per 10,000 tags applied per month)
- S3 Batch Operations: Pricing for this feature has two price points, firstly on per batch job, and secondly 
per million object operations performed.
- S3 and Glacier Select: There are 2 price points related to Select: data scanned (per GB) and data returned 
(per GB). And much like when we looked at retrieval costs, Glacier is broken down into the 3 different 
retrieval modes: Expedited, Standard and Bulk.
- S3 Replication: 
  - There are no specific costs for the use of the S3 replication feature itself, instead, you are simply 
  charged for the cost for the storage class in your destination where your replicated objects will reside.
  - Two replication modes: (1) CRR - Cross-Region Replication between 2 different buckets and (2) SRR - 
  Same-Region Replication between 2 different buckets.
  - You will also incur costs for any COPY and PUT requests which will also be based upon the rates of 
  the destination region. 
  - When using Cross-Region replication, there will also be the addition of the inter-region data transfer fees, 
  which will be priced upon the source region.
  - S3 Replication Time Control: the cost to use S3-RTC is currently set at a flat rate across all regions.
- Versioning: cost is added to you as you are storing multiple versions of the same object and as we know, 
the Amazon S3 cost model is based on actual usage of storage.
- Lifecycle Policies

## Cost of EFS

- EFS charges based on storage classes: EFS Standard and EFS IA (Infrequent Access).
  - EFS Standard: charge storage by month.
  - EFS IA: EFS IA is cheaper than standard storage, but it charges for ready and write.
- EFS Lifecycle management: use this feature to reduce cost.
- EFS throughput modes: Bursting throughput (default) and Provisioned throughput. No addition charges.
- AWS DataSync: this is a managed service which helps you manage the transfer of data into and out of EFS. It 
is charged at a flat per GB rate.


## Cost of Amazon FSx

Amazon FSx comes in 2 forms: Amazon FSx for Windows File Server and Amazon FSx for Lustre. 

- Amazon FSx for Windows File Server:
  - Flexibility cost based on Capacity, Throughput and Backups.
  - Data Deduplication is recommended to reduce cost. No additional cost.
  - Cost is associated with throughput
- Amazon FSx for Lustre: 
  - Charged for Storage capacity.
  - Data transfer between AZ will incur standard transfer costs for associated region.
  - No cost is associated with throughput
  - No setup fee.

## Cost of AWS Storage Gateway

- File Gateway: Pricing follows Amazon S3 pricing metrics, but a per GB request for data writes.
- Volume Gateway: Simple pricing structure; But uses a per-GB metric for volumes in addition to any snapshots
of the volumes at EBS snapshot costings.
- Tape Gateway: Additional complexity due to range of storage classes. Understand the retrieval times for 
your data based on the S3 Glacier and Deep Archive classes, as you might be able to save a considerable amount.

## Cost of AWS Backup Service

AWS Backup is a great way to centralize the view of backups. It implements lifecycle rules to help optimize costs
through warm and cold storage. It is backed by Amazon S3 and Glacier. Pricing points are Backup Storage and
Restoration.

