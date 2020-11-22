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

DNS (Domain Name System) is a hierarchical distributed naming system for computers, services or any resource
connected to the internet or a private network. It is responsible of the translation of domain 
names (ex. amazon.com) to IP addresses.

## Amazon Route 53

Amazon Route 53 is Amazon's highly available and scalable domain name system (DNS). It provides secure and 
reliable routing of requests, both for services within AWS and infrastructure that is outside of AWS. It provides
this service through its global network of authoritative DNS servers that reduce latency and can be managed via 
the management console and API.

### Hosted Zones

A hosted zone is a container that holds information about how you want to route traffic for a domain such as
amazon.com. Route 53 supports the following type of zones:

- Public Hosted Zone: This zone determines how traffic is routed on the internet and can be created when you
register your domain with Route 53.
- Private Hosted Zone: For Amazon VPC, this zone determines how traffic is routed within the Amazon VPC. If your
resources are not accessible outside of the VPC you can use any domain name you wish.

### Domains

There are different domains that are supported by Route 53:

- Generic Top-Level Domains (TLDs): .watch .clothing
- Geographic Domains: .com.au .uk

### Resource Record Types

Route 53 supports the most common Resource Record types: A, AAAA, CAA, CNAME, MX, NAPTR, NS, PTR, SOA, SPF, SRV, TXT.

In addition to these record types, Route 53 also uses **alias records**, which are a Route 53 specific extension to DNS.
These alias records which act like a CNAME record allow you to route your traffic to other AWS resources, such as
Elastic load balancers, Elastic Beanstalk environments, CloudFront distributions, VPC interface endpoints, or S3
buckets configured as static websites.

### Routing Policies

When you create a resource record set, you must choose a routing policy that will be applied to it, and this
then determines how Route 53 will respond to these queries. 

- Simple Routing Policy (default)
- Failover Routing Policy
- Geo-Location Routing Policy (based on the users)
- Geoproximity Routing Policy (based on both the users and the resources)
- Latency Routing Policy
- Multivalue Answer Routing Policy
- Weighted Routing Policy

## Amazon CloudFront

Amazon CloudFront is AWS's fault-tolerant and globally scalable **content delivery network (CDN)service**. It 
provides seamless integration with other Amazon Web Services to provide an easy way to distribute content. When using 
CloudFront, content requests are **routed to the closest edge** to the user's location which provides the lowest 
latency to deliver the best performance **through cached data**.

When configuring your **distribtions**, you will be required to enter your origin information which is essentially
where the distribution is going to get the data to distribute across edge locations and it will be the DNS names
of the S3 bucket or the HTTP server. 

- Web distribution
- RTMP distribution: Distribute streaming media with the Adobe Flash media service RTMP protocol. Source can only be 
located in S3 bucket and not in a EC2 web server.

If using an S3 bucket as your origin, then for additional security you can create a CloudFront user 
called an **origin access identity (OAI)**. This ensures that only this OAI can access and serve content 
from your bucket and therefore preventing anyone circumventing your CloudFront distribution by accessing
the files directly in the bucket using the object URL.

**Caching behavior options** defines how you want the data at the edge location to be cached via
various methods and policies.

You can also define if you want your distribution to be **associated to a web application firewall access
control list** for additional security and web application protection.

In addition to using a web application firewall access control list, you can also **implement additional 
encryption security by specifying an SSL certificate** that must be used with a distribution.










