# AWS Data Storage

On-premise Options: SAN (Storage Area Network), NAS (Network Attached Storage), DAS (Directly
Attached Storage) and Tape backup. 

**AWS Data Storage Options:**

- Block Storage
  - [EBS: Elastic Block Store](#ebs-elastic-block-store)
  - [EC2 Instance Storage](#ec2-instance-storage)
- File Storage
  - [EFS: Elastic File System](#efs-elastic-file-system)
- Object Storage
  - [S3: Simple Storage Service](#s3-simple-storage-service)

<br />

## EBS: Elastic Block Store

- EBS operates as a separate service to EC2. **Each EBS volume can ONLY be attached to ONE EC2 instance.**
Mulitple EBS volumns can be attached to a single EC2 instance. Data is retained if the EC2 instance
is stopped, restarted or terminated.
- **EBS Snapshot**: An EBS snapshot is a point-in-time copy of your EBS volume, which is lazily
copied to Amazon S3. EBS snapshots are incremental copies of data. This means that only unique blocks
of EBS volume data that have changed since the last EBS snapshot are stored in the next EBS snapshot.
- EBS offers **encryption at rest and in transit**. Encryption is managed by the EBS service it self.
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

## EC2 Instance Storage

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

## EFS: Elastic File System

## S3: Simple Storage Service

- Amazon S3 is a **regional** service.
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
  this is set to SSE-S3 because encryption with KMS is not supported.
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
  disabled. There are two retention modes: Governance mode and Compliance mode. No users (includes
  root users) can override the retention periods set or delete an object for compliance mode. 
  Legal hold on an object has no retention period.
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
  - Retrieval data using APIs, SDKs or CLI. Retrieval data options: Expedited (5 minutes, 250MB), Standard (3-5 hours) and Bulk (PB 
  of data, 5-12 hours)

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
