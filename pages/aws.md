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
- [Simple Queue Service (SQS)](#simple-queue-service-sqs)
- [Simple Notification Service (SNS)](#simple-notification-service-sns)
- [Amazon Kinesis](#amazon-kinesis)
  - [Amazon Kinesis Streams](#amazon-kinesis-streams)
  - [Amazon Kinesis Data Firehose](#amazon-kinesis-data-firehose)
  - [Amazon Kinesis Analytics](#amazon-kinesis-analytics)
- [AWS Lambda](#aws-lambda)

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

<br />

# Simple Queue Service (SQS)

- SQS is a fully managed service that works with serverless systems, microservices and distributed architectures.
- It has the capability of sending, storing and receiving messages at scale without dropping message data.
- It is possible to configure the service using the AWS Management Console, the AWS CLI or AWS SDKs.

SQS Queue Types:
- Standard Queues
  - Standard queues support at-least-once delivery of messages
  - They offer a best effort on trying to preserve the message ordering (don't use this if ordering is critical).
  - They provide almost unlimited number of transactions per second.
- FIFO Queues
  - The order of messages is maintained and there are no duplicates.
  - Limited number of transaction per second (defaulted to 300 TPS).
  - Batching allows you to perform actions against 10 messages at once with a single action.
- Dead-Letter Queues
  - The dead-letter queue sends messages that fail to be processed
  - this could be the result of code within your application, corruption within the message or simply missing 
  information.
  - If the message can't be processed by a consumer after a maximum number of tries specified, the queue will 
  send the message to a DLQ.
  - By viewing and analysing the content of the message it might be possible to identify the problem and ascertain
  the source of the issue. 

<br />

# Simple Notification Service (SNS)

SNS offers methods of controlling specific access to your topics through a topic policy. For example, you
can restrict which protocol subscribers can use, such as SMS or HTTPS, or only allow access to this topic
for a specific user. The topic policy follows the same format as IAM policies. To configure an SNS, start 
from creating a topic.

Use SNS as a producer for an SQS queue: SNS and SQS by working together, a solution can be designed to 
send messages to subscribers through a push method, while SQS handles incoming messages and waits for 
consumers to pull data.

<br />

# Amazon Kinesis

Amazon Kinesis does not store persistent data itself, unlike many of the other amazon big data services. As a 
result, Amazon Kinesis needs to be deployed as part of a larger event-driven solution. Amazon Kinesis provides
three different solution capabilities:

## Amazon Kinesis Streams

Amazon Kinesis Streams enables you to build custom applications that process or analyze streaming data 
for specialized needs.

- Data streams: A data streaming service capable of elastically scaling to support thousands of data feeds.
- Video streams: Designed to securely elastically scale and ingest video streams on a massive scale.

An Amazon Kinesis stream is an ordered sequence of data records. A record is the unit of data in an Amazon
Kinesis stream. Each record in the stream is composed of **a sequence number**, **a partition key** and 
**a data blob**.

A consumer receives records from Amazon Kinesis Streams and processes them in real-time. You need to build your
applications using either the Amazon Kinesis API, or the Amazon Kinesis Client Library.

## Amazon Kinesis Data Firehose

Amazon Kinesis Firehose is a fully namaged service for delivering real-time streaming data to destinations 
such as Amazon S3, Amazon RedShift, and Amazon Elastic Search and Splunk.

With Kinesis Firehose, you do not need to write applications as your consumers. You can also configure 
Amazon Kinesis Firehose to transform your data before data delivery.

Firehose is limited to S3, Redshift, Elasticsearch and Splunk as the data destinations.

## Amazon Kinesis Analytics

Amazon Kinesis Analytics enables you to quickly author SQL code that continuously reads, processes and 
stores data. You can ingest in real-time billions of small data points. Each individual data point can then 
be aggregated to provide intelligent business insights, which in turn can be used to continually optimize 
and improve business processes.

- Create an input stream: Typically come from streaming data sources such as Kinesis streams
- Create SQL processing logic
- Create an output stream: to hold intermediate results that are used to feed into other queries or be used to 
stream out the final results.

<br />

# AWS Lambda

You only ever have to pay for compute power when lambda is in use via Lambda Functions. AWS Lambda charges
compute power per 100ms of use only when your code is running, in addition to the number of times your code
runs.

There are essentially four steps to its operations:
- upload your code to lambda or write it within the code editors that Lambda 
provides. (Node.js, Java, C#, Python, Go, PowerShell and Ruby)
- Configure your Lambda functions to execute upon specific triggers from supported event sources.
- Once the specific trigger is initiated, Lambda will run your code (as per your lambda function) using
only the required compute power as defined.
- AWS records the compute time in Milliseconds and the quantity of Lambda functions run to ascertain the 
cost of the service.

The following elements form the key constructs of a Lambda Application:
- Lambda function
- Event resources
- Trigger
- Downsteam resources
- Log streams





