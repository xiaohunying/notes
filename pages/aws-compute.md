<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Compute

- [EC2 (Elastic Compute Cloud)](#ec2-elastic-compute-cloud)
- [ECS (EC2 Container Service)](#ecs-ec2-container-service)
- [EKS (ECS for Kubernetes)](#eks-ecs-for-kubernetes)
- [AWS Batch](#aws-batch)
- [ELB (Elastic Load Balancer)](#elb-elastic-load-balancer)
- [EC2 Auto Scaling](#ec2-auto-scaling)
- [AWS Lambda](#aws-lambda)

<br />

# EC2 (Elastic Compute Cloud)

EC2 service can be broken down into the following components:

- Amazon Machine Images (AMIs)
- Instance types (vCPUs, Memory, Instance Storage, network Performance and etc.)
  - Micro instances: 
    - low traffic websites
  - General-purpose: 
    - small-median databases
    - tests and dev servers
    - back-end servers
  - Compute optimized: 
    - high-performance front end servers
    - web servers
    - high-performance science and engineering applications
    - video encoding
    - batch processing
  - GPU (Graphics Processing Unit):
    - graphic intensive applications
  - FPGA:
    - allows you to customize field programmable gateways to create application specific hardware accelerations when used with applications that use massively parallel processing power.
    - genomics and financial computing
  - Memory optimized
    - applications that are primarily used for large-scale enterprise class in-memory applications. Such as performing real time processing of unstructured data.
    - Microsoft SharePoint
  - Storage optimized
    - low latency and very high I/O performance, very high IOPS (input/output operations per second)
    - analytic workloads
    - no SQL databases
    - data file systems
    - log processing applications
- Instance Purchasing Options
  - _**On-demand instances**_: Pay, by the second, for the instances that you launch.
  - _**Reserved instances**_: Purchases for a set period of time for reduced cost including instance type and Region, for a term of 1 or 3 years. This reduce can be as much as 75%.
    - All upfront
    - Partial upfront
    - No upfront
  - _**Scheduled instances**_: You pay for the reservations on a recurring schedule. Note that even if you don't use the instance, you still will be charged.
  - _**Spot instances**_: Request unused EC2 instances, which can reduce your Amazon EC2 costs significantly. Only useful for processing data that can be suddenly interrupted, such as batch jobs and background processing of data.
  - _**On-Demand Capacity Reservations**_: Reserve capacity for your EC2 instances in a specific AZ for any duration.
- Tenancy
  - Shared tenancy
  - Dedicated instances: hosted on hardware that no other customer can access
  - Dedicated hosts: additional visibility and control on the physical host; allow to use the same host for a number of instances
- User Data
  - User data allows you to enter commands that will run during hte first boot cycle of that instance.
  - Useful to install software from a repository or perform OS updates.
- Metadata
  - used to gather and query instance data that is running, such as the host name, events, and security groups, etc.
  - Navigate to http://169.254.169.254/latest/meta-data/ to retrieve instance metadata.
- Storage options
  - Persistent storage: attaching EBS volumes
  - Ephemeral storage: instance store volume
    - Data is lost when the instance stops or terminates.
    - Data is NOT lost when the instance reboots.
- Security
  - ingress and egress traffic
  - security groups: instance associated with security group to set inbound and outbound rules
  - Key Pair (public key and private key):
    - For connecting to EC2 instances. Key pairs are used to encrypt the credentials to the instance.
    - To encrypt the login information for Linux and Windows EC2 instances, and then decrypt the same information allowing you to authenticate into the instance.

<br />

# ECS (EC2 Container Service)

ECS allows you to run Docker-enabled application packaged as containers across a cluster of EC2 instances without requiring you to manage a complex and administratively heavy cluster management system. The burden of managing your own cluster management system is abstracted with the ECS service by passing that responsibility over to AWS, specifically through the use of **AWS Fargate**.

**Launching an ECS cluster**: (1) Fargate Launch; (2) EC2 Launch.

<br />

# EKS (ECS for Kubernetes)

AWS provides a managed service allowing you to run Kubernetes across your AWS infrastructure without having to take care of provisioning and running the Kubernetes management infrastructure in what's referred to as **the control plane**. You ONLY need to provision and maintain the **worker nodes**.

Working with EKS
1. Configure and create an IAM service role that allows EKS to provision and configure specific resources. The role has to have the following permission policies: AmazonEKSClusterPolicy and AmazonEKSServicePolicy. (One time task)
2. Create an EKS Cluster VPC.
3. Install kubectl and the AWS-IAM-Authenticator.
4. Create EKS Cluster.
5. Configure kubectl for EKS.
6. Provision and configure Worker Nodes.
7. Configure the Worker Node to join the EKS Cluster.

<br />

# AWS Batch

AWS Batch is used to manage and run batch computing workloads within AWS.

There are 5 components:
- Jobs: A job is classed as the unit of work that is to be run by AWS Batch.
- Job Definitions
- Job Queues
- Job Scheduling
- Compute Environments

<br />

# ELB (Elastic Load Balancer)

The main function of an ELB is to help manage and control the flow of inbound requests destined to a group of targets
by distributing these requests evenly across the targeted resource group. 

### Load Balancer Types

- application load balanccer
  - operates at layer 7 of the OSI model: application
  - flexible feature set for your web applications running the HTTP or HTTPS protocols
  - operates at the request level
  - advanced routing
- network load balancer
  - operates at layer 4 of the OSI model: transport and enabling you to balance requests purely based on TCP and UDP protocols.
  - The listener supported by the NLB include TCP, TLS and UDP.
  - ultra-high performance while maintaining very low latencies
  - operates at the connection level, routing traffic to targets within your VPC
  - handles millions of requests.
  - If your application logic requires a static IP address, then the NLB will need be your choice of elastic load balancer.
- classic load balancer
  - used for applications that were built in the existing EC2 classic environment
  - operates at both the connection and request level

### ELB Components

- Target Groups: A target group is a group of your resources that you want your ELB to route requests to. You can configure your ELB with a number of different target groups, each associated with a different listener configuration and associated rules.
- Listeners: The listener defines how your inbound connections are routed to your target groups based on ports and protocols set as conditions. For every load balancer, you must configure at least one listeners.
- Rules: Rules are associated to each listener that you have configured within your ELB. They help to define how an incoming request gets routed to which target group.

![elbcomponent](../resources/images/elb-components.PNG)

Your ELB can contain 1 or more listeners, each listener can contain 1 or more rules and each rule can contain 1 or 
more conditions, and all conditions in the rule equal a single action.

- Health Checks
- Internet-Facing ELB
- Internal ELB
- ELB Nodes
- Cross-Zone Load Balancing

### Server Certificates (SSL/TLS)

The server certificate used by the ALB is an X.509 certificate, which is a digital ID provisioned 
by a Certificate Authority such as the AWS Certificate Manager (ACM)

ACM allows you to create and provisin SSL/TLS server certificates to be used within your AWS environment
acros different services.

IAM is used as your certificate manager when deploying your ELBs in regions that are not supported by ACM. (Third
party certificate)

<br />

# Auto Scaling

Auto scaling is a mechanism that automatically allows you to increase or decrease your EC2 resources to meet
the demand based off of custom defined metrics and thresholds.


There are two ways for auto scaling in AWS: 
- **EC2 Auto Scaling**: scale your EC2 fleet.
- **AWS Auto Scaling Service**: scale Amazon ECS tasks, DynamoDB tables and indexes and Amazon Aurora replicas.

### Auto Scaling Group

The auto scaling group defines:

- the desired capacity and other limitations of the group using scaling policies.
- where the group should scale resources, such as which availability zone.

Before you create your auto scaling group, you need to have your **launch configuration** defined using either
Launch Template (prefered) or Launch Configuration.

**Combining an ELB and Auto Scaling** helps you to manage and automatically scale your EC2 Compute resources both
in and out. When you attach an ELB to an auto scaling group, the ELB will automatically detect the instances 
and start to distribute all traffic to the resources in the auto scaling group. To associate an ALB or NLB, you
must **associate the auto scaling group with the ELB target group.**

<br />

# AWS Lambda

AWS Lambda is a serverless compute service that allows you to run your application code without having to manage EC2 instances.

You only ever have to pay for compute power when Lambda is in use via Lambda functions. AWS Lambda charges compute power per 100ms of use only when your code is running, in addition to the number of times your code runs.

### Components of AWS Lambda

- **Lambda function**:  A Lambda function is compiled of your own code that you want Lambda to invoke.
- **Events sources**: Events sources are AWS services that can be used to trigger your Lambda functions.
- **Downstream resources**: Downstream resources are resources that are required during the execution of your Lambda function.
- **Log streams**: Log streams help to identity issues and troubleshoot issues whith your Lambda function.

### Event Source Mapping

An event source mapping is the configuration that links your event source to your Lambda function.

For push-based services:
- The mapping is maintained within the event source.
- By using the appropriate API calls for the event source service, you are able to create and configure the relevant mappings.
- This will require specific access to allow your event source to invoke the function.
- The invocation type varies on the service. It could be Synchronous or Asynchronous.

For poll-based services:
- The configuration of the mappings are held within your lambda function.
- With the CreateEventSourceMapping API you can set up the relevant event source mapping for your poll-based service.
- The permission is required in the execution role policy.
- The invocation type is always synchronous.


<br />
