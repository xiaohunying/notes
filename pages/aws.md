# AWS Data Storage

On-premise Options:

- SAN: Storage Area Network
- NAS: Network Attached Storage 
- DAS: Directly Attached Storage
- Tape backup 

AWS Options:

- Block Storage (comparable to DAS)
  - **EBS**: Elastic Block Store
  - **EC2 Instance Storage**
- File Storage (comparable to NAS)
  - **EFS**: Elastic File System
- Object Storage
  - **S3**: Simple Storage Service
  
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
- **AWS Snowball** (direct connect)
- **AWS Snowmobile** (direct connect)
- **AWS Storage Gateway** (internet connect)
