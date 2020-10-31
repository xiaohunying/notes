# AWS Data Storage

On-premise Options:

- SAN: Storage Area Network
- NAS: Network Attached Storage 
- DAS: Directly Attached Storage
- Tape backup 

AWS Options:

- Block Storage
  - [EBS: Elastic Block Store](#ebs-elastic-block-store)
  - [EC2 Instance Storage](#ec2-instance-storage)
- File Storage
  - [EFS: Elastic File System](#efs-elastic-file-system)
- Object Storage
  - [S3: Simple Storage Service](#s3-simple-storage-service)

<br />

### EBS: Elastic Block Store

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

### EC2 Instance Storage

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

### EFS: Elastic File System

### S3: Simple Storage Service


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

<br />

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
