<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Storage

- [Backup and DR strategies](#backup-and-dr-strategies)
- [Data Transfer in/out of AWS](#data-transfer-in-out-of-aws)
- [Costs With AWS Storage Services](#costs-with-aws-storage-services)

<br />

On-Premise Options:
- SAN (Storage Area Network)
- NAS (Network Attached Storage)
- DAS (Directly Attached Storage)
- Tape backup

AWS Data Storage Options:
- Block Storage
  - [EBS: Elastic Block Store](#ebs-elastic-block-store)
  - [EC2 Instance Storage](#ec2-instance-storage)
- File Storage
  - [EFS: Elastic File System](#efs-elastic-file-system)
- Object Storage
  - [S3: Simple Storage Service](#s3-simple-storage-service)

<br />

# S3: Simple Storage Service

Amazon S3 is a **regional service**.

### S3 Storage Classes
- S3 Standard
- S3 INT (Intelligent): frequent/infrequent accesses
- S3 S-IA (Standard infrequent access)
- S3 Z-IA (Single zone, infrequent access)
- S3 Glacier: long term, backup and archive
  - Move data into S3 Glacier using APIs or SDKs. 
  - Retrieval data using APIs, SDKs or CLI. Retrieval data options: 
    - Expedited (5 minutes, 250MB)
      - **Provisioned Capacity Unit**: You can pay a fixed up-front fee for a given month to ensure the availability of retrieval capacity for expedited retrievals from Amazon S3 Glacier vaults. You can purchase multiple provisioned capacity units per month to increase the amount of data you can retrieve.
    - Standard (3-5 hours)
    - Bulk (PB of data, 5-12 hours)
- S3 G_DA: S3 Glacier deep archive
  - Retrieval data method: 
    - Standard
    - Bulk

### S3 Bucket Versioning
Versioning is not enabled by default, however, once you have enabled it, you can't disable it, instead, you can only suspend it on the bucket which will prevent any further versions from being created of your objects, but it will keep all existing versions of objects up to the point of suspension. 
Bucket versioning states:
- Unversioned (default)
- Versioning-enabled
- Versioning-suspended

### Static Website Hosting
Amazon S3 can be used for static website hosting. It does NOT support HTTPS. The bucket content needs to be marked public access. It does NOT support Requester pays.
- Host website: You need to add an index document the bucket. It is the home page of your static website. Add a bucket policy to allow the public to be able to reach the bucket.
- Redirect requests

### Server-Access Logging 
- When the target bucket is for logs, the source bucket and target bucket should be in the same region.
- The permissions of logs are controlled using **Log Delivery Group**. (1) If server-access logging is enabled through AWS console, the Log Delivery Group is automatically added to the ACL (Access Control List) of the target bucket; (2) If it is through AWS S3 API or AWS SDK, you need to manually configure this access. Permissions of the S3 access log group can only be assigned via ACL.
- If encryption is enabled on your target bucket, access logs will only be delivered if this is set to **SSE-S3** because encryption with KMS is not supported.

### Object Level Logging
AWS S3 bucket Object level logging is closely related to **AWS CloudTrail** service. S3 data events includes GetObject, DeleteObject and PutObject.

### Transfer Acceleration
Transfer Acceleration is to speed up data transfer. It uses Amazon **CloudFront** service, which is a Content Delivery Network (CDN) service that essentially provides a means of distributing traffic worldwide via edge locations.

### S3 Access Control

**Principle of least-priviledged**: By default, AWS states that access is denied to an object, even without an explicit Deny within any policy. To gain access, there has to be an Allow within a policy that the principal is associated to or defined by within a bucket policy or ACL.

- IAM Permissions Policies
  - This is identity-based Policies.
  - Attached to the IAM identity requiring access, using IAM permissions policies, either in-line or managed.
  - Associated to the user, to a group that the user belongs to, or via a role that the user has permission to assuem.
  - Defines the resource in the policy, ex. the bucket name.
- Bucket Policies
  - This is resource-based Policies. This policy is associated with the resource, rather than the identity.
- Access Control Lists (ACL)
  - You need to define who will be allowed or denied access.

### CORS (Cross Origin Resource Sharing)
At a high level, CORS allows specific resources on a webpage to be requested from a different domain than its own. And this allows you to build client-side web applications. And then if required, you can utilize CORS support to access resources stored in S3.

### S3 bucket
**Bucket name** needs to be unique globally. You can create a **folder** in a bucket, but S3 is not a file system.
- **Events**: You can monitor events occured to the bucket. Any events which are recorded can then be sent to an SNS topic or an SQS queue or a lambda function. 
- **Tags** in S3 bucket is known as S3 cost allocation tags. they are key-value pairs.
- **Encryption**: S3 bucket has two default encryption options: AES-256 (SSE-S3) and AWS-KMS (SSE-KMS).

### Object Lock
**Object Lock** on a bucket can only be achieved at the time of the creation of the bucket. Versioning needs to be enabled. Once object lock is enabled, it is permanent and cannot be disabled. There are two retention modes: Governance mode and Compliance mode. 
- **Governance mode**: Users can't overwrite or delete an object version or alter its lock settings unless they have special permissions. With governance mode, you protect objects against being deleted by most users.
- **Compliance mode**: No users (includes root users) can override the retention periods set or delete an object. 

**Legal hold**: Object Lock also enables you to place a legal hold on an object version. Like a retention period, a legal hold prevents an object version from being overwritten or deleted. However, a legal hold doesn't have an associated retention period and remains in effect until removed.

### Requester Pays
When this feature is enabled, any cost associated with requests and data transfer becomes the responsibilities of the requester instead of the bucket owner. The bucket owner still pays the storage of the objects in the bucket. Authenticating requests allow AWS trace back to the identity and to which AWS account that identity is originating from. And the cost is then transfered to that account.  

<br />

# EBS: Elastic Block Store

EBS operates as a separate service to EC2. **Each EBS volume can ONLY be attached to ONE EC2 instance.** Mulitple EBS volumns can be attached to a single EC2 instance. Data is retained if the EC2 instance is stopped, restarted or terminated. EBS volume can only be attached to EC2 instances that exist within the same AZ.

### EBS Snapshot
An EBS snapshot is a point-in-time copy of your EBS volume, which is lazily copied to Amazon S3. EBS snapshots are incremental copies of data. This means that only unique blocks of EBS volume data that have changed since the last EBS snapshot are stored in the next EBS snapshot.

### EBS Encryption
EBS offers **encryption at rest and in transit**. Encryption is managed by the EBS service itself. It can be enabled with a checkbox. The encryption is only available in selective instances.

### EBS Volume Types
- **SSD (Solid State Drive) backed storage**: It is suited for work with smaller blocks, databases using transactional workloads. It is often used for boot volumes on EC2 instances.
  - General Purpose SSD (GP2)
  - Provisioned IOPS (IO1)
- **HDD (Hard Disk Drive) backed storage**: It is designed for workloads requires a high rate of throughput (MB/s). It process larger blocks of data. For example, big data processing and logging information.
  - Cold HDD (SC1)
  - Throughput Optimized HDD (ST1)

### EBS Is Not For
- Temporary storage
- Multi-instance storage access
- Very high durability and availability

<br />

# EC2 Instance Storage

- EC2 instance storage volumes provide ephemeral storage (temporary). It is not recommended for critical or valuable data. **Your data is lost if the instance is stopped or terminated. Your data will remain in tact if your instance is rebooted**. Instance storage volumes are not available for all instances.
- It is ideal as a cache or buffer for rapidly changing data without the need for retention. It is often used within a load balancing group where data is replicated and pooled between the fleet.
- **No additional cost** for storage. It is included in the price of instance. The capacity of instance storage volumes increases with the size of the EC2 instance. It has the same security mechanisms provided by EC2.

<br />

# EFS: Elastic File System

EFS is a regional service. Apps across multiple AZ can all access the same file systems. It is 
not available within all regions. EFS supports both NFS version 4.1 and 4.0.

### Things to consider while choosing EFS:
- Storage classes:
  - Standard
  - Infrequent Access (IA)
  - EFS Lifestyle Management
    - The only exceptions to data not being moved to the IA storage class is for any files that are below 128K in size and any metadata of your files, which will all remain in the Standard storage class. 
- Performance modes:
  - General Purpose: EFS provide a **CloudWatch** metric for general purpose mode.
  - Max I/O
- Throughput modes: 
  - Bursting Throughput (default) 
  - Provisioned Throughput.

### EFS Mount Helper

EFS Mount Helper is a utility installed on your EC2 instances. 

### Importing Data

You can use **AWS DataSync** to import data into your EFS file system. AWS DataSync is designed to securely move and migrate and synchronize data from your existing on-premises site into AWS storage services. Data transfer can be completed over a Direct Connect link or over the internet. To sync files from your on-premises environment, you must download the DataSync agent. You then need to configure the agent with a source and destination target. DataSync can also transfer files between EFS file systems.

<br />

# Amazon FSx

### Amazon FSx for Windows File Server
- Provides a fully managed native Microsoft Windows file system on AWS.
- Easily move and migrate your windows-based workloads requiring file storage.
- The solution is built on Windows Server.
- Operates as shared file storage.
- Full support for SMB protocol, Windows NTFS, Active Directory (AD) integration and Distributed File System (DFS).
- Uses SSD storage for enhanced performance and throughput providing sub-millisecond latencies.
- Pricing:
  - Flexibility cost based on Capacity, Throughput and Backups.
  - Data Deduplication is recommended to reduce cost. No additional cost.
  - Cost is associated with throughput

### Amazon FSx for Lustre
- Afully managed file system designed for compute-intensive workloads, for example Machine Learning and HPC.
- Ability to process massive data sets.
- Performance can run up to hundreds of GB per second of throughput, millions of IOPS and sub-millisecond latencies.
- Integration with Amazon S3.
- Supports could-bursting workloads from on-premises over Direct Connect and VPN connections.
- Pricing:
  - Capacity
  - Data transfer between AZs will incur standard transfer costs for the associated regions.

<br />

# AWS Storage Gateway

AWS Storage Gateway allows you to provide a gateway between your own data center's storage systems such as your SAN, NAS or DAS and Amazon S3 and Glacier. The Storage Gateway itself can either be installed as software or physical hardware appliance that can be stored within your own data center which allows integration between your on-premise storage and that of AWS. This connectivity can allow you to scale your storage requirements both securely and cost-efficiently.

### Configurations
- File Gateway
  - Mount or map drives as if they were local shares.
  - On-premise cache is provisioned for to optimize latency.
  - This service is largely integrated with S3.
  - Pricing: 
    - Follows Amazon S3 pricing mechanism
    - A per GB request for data writes
- Volume Gateway
  - Stored Volume Gateway
    - Your data libary is still on-premise for low latency access
    - Volumes are backed by S3 and mounted as iSCSI devices
  - Cached Volume Gateway
    - Primary data storage is on Amazon S3
    - Local volumes act as buffer and cache for low latency
  - Pricing:
    - Uses a per-GB metric for volumes
    - Snapshots of the volumnes at EBS snapshot
- Tape Gateway
  - Backup data to S3 and leverage S3 Glacier storage classes
  - Lower cost than S3
  - Cloud based virtual tape backup solution
  - Backed by AWS S3/Glacier
  - Pricing:
    - Additional complexity due to range of storage classes. Understand the retrieval times for your data based on the S3 Glacier and Deep Archive classes, as you might be able to save a considerable amount.

<br />

# AWS Backup

- Centralized view of backups
- Lifecycle rules to help optimize costs
- Warm and cold storage options
- It is backed by Amazon S3 and Glacier
- Pricing points for Backup Storage and restoration

### Resource Type
- Amazon EFS File System Backup
- Amazon EFS Volume Snapshot
- Amazon RDS Database Snapshot
- Amazon DynamoDB Table Backup
- AWS Storage Gateway Volume Backup
