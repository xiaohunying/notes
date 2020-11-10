# Amazon Web Services

- [AWS Storage](aws-storage.md)
- [AWS Compute](aws-compute.md)
- [AWS Database](aws-database.md)
- [AWS Network](aws-network.md)

# ELB Elastic Load Balancer

The main function of an ELB is to help manage and control the flow of inbound requests destined to a group of targets
by distributing these requests evenly across the targeted resource group. 

## Load Balancer Types

- application load balanccer
  - operates at layer 7 of the OSI model: application
  - flexible feature set for your web applications running the HTTP or HTTPS protocols
  - operates at the request level
  - advanced routing
- network load balancer
  - operates at layer 4 of the OSI model: transport
  - ultra-high performance while maintaining very low latencies
  - operates at the connection level, routing traffic to targets within your VPC
  - handles millions of requests
- classic load balancer
  - used for applications that were built in the existing EC2 classic environment
  - operates at both the connection and request level

## ELB Components

- Listeners
- Target Groups
- Rules

Your ELB can contain 1 or more listeners, each listener can contain 1 or more rules and each rule can contain 1 or 
more conditions, and all conditions in the rule equal a single action.

- Health Checks
- Internet-Facing ELB
- Internal ELB
- ELB Nodes
- Cross-Zone Load Balancing

## Server Certificates (SSL/TLS)

The server certificate used by the ALB is an X.509 certificate, which is a digital ID provisioned 
by a Certificate Authority such as the AWS Certificate Manager (ACM)

ACM allows you to create and provisin SSL/TLS server certificates to be used within your AWS environment
acros different services.

IAM is used as your certificate manager when deploying your ELBs in regions that are not supported by ACM. (Third
party certificate)

<br />

# EC2 Auto Scaling

Auto scaling is a mechanism that automatically allows you to increase or decrease your EC2 resources to meet
the demand based off of custom defined metrics and thresholds.

Amazon offers:
- Amazon EC2 Auto Scaling: scale your EC2 fleet.
- AWS Auto Scaling Service: scale Amazon ECS tasks, DynamoDB tables and indexes and Amazon Aurora replicas.

## Auto Scaling Group

The auto scaling group defines (1) the desired capacity and other limitations of the group using scaling policies;
(2) where the group should scale resources, such as which availability zone.

Before you create your auto scaling group, you need to have your **launch configuration** defined using either
Launch Template (prefered) or Launch Configuration.

**Combining an ELB and Auto Scaling** helps you to manage and automatically scale your EC2 Compute resources both
in and out. When you attach an ELB to an auto scaling group, the ELB will automatically detect the instances 
and start to distribute all traffic to the resources in the auto scaling group. To associate an ALB or NLB, you
must **associate the auto scaling group with the ELB target group.**



