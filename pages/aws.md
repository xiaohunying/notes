# Amazon Web Services

- [AWS Storage](aws-storage.md)
  - [AWS Data Storage](./aws-storage.md#aws-data-storage)
  - [Backup and DR strategies](./aws-storage.md#backup-and-dr-strategies)
  - [Data Transfer in/out of AWS](./aws-storage.md#data-transfer-in-out-of-aws)
  - [Costs With AWS Storage Services](./aws-storage.md#costs-with-aws-storage-services)
- [AWS Compute](aws-compute.md)
  - [EC2 (Elastic Compute Cloud)](./aws-compute.md#ec2-elastic-compute-cloud)
  - [ELB (Elastic Load Balancer)](./aws-compute.md#elb-elastic-load-balancer)
  - [EC2 Auto Scaling](./aws-compute.md#ec2-auto-scaling)
- [AWS Network](aws-network.md)
  - [VPC (Virtual Private Cloud)](./aws-network.md#vpc-virtual-private-cloud)
    - [Subnet](./aws-network.md#subnet)
    - [Public Subnet](./aws-network.md#public-subnet)
    - [Route Table](./aws-network.md#route-table)
  - [VPC Security and Control](./aws-network.md#vpc-security-and-control)
    - [NACLs (Network Access Control Lists)](./aws-network.md#nacls-network-access-control-lists)
    - [Security Groups](./aws-network.md#security-groups)
    - [NAT Gateway](./aws-network.md#nat-gateway)
    - [Bastion Hosts](./aws-network.md#bastion-hosts)
  - [VPC Connectivity](./aws-network.md#vpc-connectivity)
    - [VPN (Virtual Private Networks)](./aws-network.md#vpn-virtual-private-networks)
    - [Direct Connect](./aws-network.md#direct-connect)
    - [VPC Peering](./aws-network.md#vpc-peering)
    - [Transit Gateway](./aws-network.md#transit-gateway)
  - [EIPs (Elastic IP Addresses)](./aws-network.md#eips-elastic-ip-addresses)
  - [ENIs (Elastic Network Interfaces)](./aws-network.md#enis-elastic-network-interfaces)
  - [ENA (Elastic Network Adaptor)](./aws-network.md#ena-elastic-network-adaptor)
  - [VPC Endpoints](./aws-network.md#vpc-endpoints)
  - [AWS Global Accelerator](./aws-network.md#aws-global-accelerator)
- [AWS Database](aws-database.md)
- [Design A Multi-Tier Solution](#design-a-multi-tier-solution)
- [DNS and Content Delivery](#dns-and-content-delivery)
  - [Amazon Route 53](#amazon-route-53)
  - [Amazon CloudFront](#amazon-cloudfront)

<br />

# Design A Multi-Tier Solution
  
### What is Multi-Tier Architecture

- A design doesn't have to have three tiers. They can be more or less.
   - Presentation tier
   - Logic tier
   - Data tier (Amazon Aurora, Amazon ElastiCache)
- It common to have web applications to have two tiers. 
- Multi-tier architecture provides a general framework to ensure decoupled and independently
scalable application components can be developed, managed and maintained separately. 
- In multi-tier architecture, each layer (tier) is independent of the other layers. Each layer can be scaled up
or down to meet specific demand.

### Common Multi-Tier Solution Design Patterns

- LAMP
  - LAMP is a common tool stack used for building web services
  - **L**inux operation system
  - **A**pache web server
  - **M**ySQL database
  - **P**HP programming languange
- MEAN
  - MEAN is an evolving stack pattern
  - **M**ongo DB (database layer)
  - **E**xpressJS (javascript framework)
  - **A**ngularJS (javascript framework)
  - **N**odeJS (javascript framework)
- SERVERLESS
  - Serverless is very beneficial in multi-tiered designs.
  - Generally use **Amazon API Gateway** and **AWS Lambda** to replace logic tier.
  - Generally one lambda function per API or one Lambda function per API method
- MICROSERVICES
  - Microservices is not so tied to the notion of tiers.
  - With microservice architecture patterns each of the application components is decoupled and independently
  deployed and operated.

<br />

# DNS and Content Delivery

## Amazon Route 53

## Amazon CloudFront











