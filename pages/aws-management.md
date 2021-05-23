<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Management

- [CloudWatch](#cloudwatch)
  - [CloudWatch Components](#cloudwatch-components)
  - [CloudWatch Logging Agent](#cloudwatch-logging-agent)
  - [CloudWatch Insights](#cloudwatch-insights)
- [AWS CloudTrail](#aws-cloudtrail)
  - [CloudTrail Events](#cloudtrail-events)
  - [CloudTrail Logs](#cloudtrail-logs)
  - [CloudTrail Use Cases](#use-cases)
  - [CloudTrail Core Features and Services](#core-features-and-services)
- [AWS Config](#aws-config)
  - [AWS Config Key Components](#aws-config-key-components)
- [AWS Organization](#aws-organization)
- [VPC Flow Logs](#vpc-flow-logs)

<br />

# CloudWatch

CloudWatch is a comprehensive monitoring tool that allows you to monitor your services and applications in the cloud. It also allows you to react to events and even prevent service interruptions and outages. CloudWatch will monitor and create trend reports for services and resources you're probably already using. CloudWatch also provides a repository for logging.

- Basic Monitoring - Included with all resources in all regions. 3 dashboard / 50 metrics / 5 minute refresh rate
- Enhanced Monitoring - Priced per resource by region. 1 minute refresh rate 

### CloudWatch Components
- CloudWatch Dashboards
- CloudWatch Metrics and Anomaly Detection
- CloudWatch Alarms
- CloudWatch EventBridge
  - Rules
    - A rule acts as filter for incoming streams of event traffic and then routes these events to the appropriate target defined within the rule.
    - The rule itself can route traffic to multiple targets. The targets must be in the same region.
  - Targets
    - Targes are where the events are sent by the rules. 
    - All events received by the target are in a JSON format.
  - Event Buses
    - An event bus is the component that actually receives the event from your applications and your rules are associated with a specific event bus.
    - CloudWatch EventBridge uses a default event bus that is used to receive events from AWS services.
- CloudWatch Logs
  - CloudWatch Log Events have a size limitation of 256KB on the events that they can process.
- CloudWatch Insights

### CloudWatch Logging Agent
CloudWatch Logs gives you a centralized location to house all of your logs from different AWS services that provide logs as an output, such as CloudTrail, EC2, VPC Flow logs, etc. in addition to your own applications. CloudWatch Logs acts as a central repository for real-time monitoring of log data. `Unified CloudWatch Agent` can collect logs and additional metric data from EC2 instances as well from on-premise services running either a Linux or Windows operating system. This metric data is in addition to the default EC2 metrics that CloudWatch automatically configures for you.

CloudWatch Agent Installation
- Create a role and attach it to the instance with permissions to collect data from instances in addition to interacting with AWS system manager SSM.
  - You will need to create two roles:
    - used to install the agent and also to send the additional metrics gathered to CloudWatch
    - used to communicate with the Parameter store within SSM, to store a configuration information file of the Agent
- Download and install the agent onto the EC2 instance.
- Configure and start the CloudWatch agent.

### CloudWatch Insights
Insights provide the ability to get more information from the data that CloudWatch is collecting. There are now 3 different types of insights within CloudWatch:
- Log Insights
  - This is a feature that can analyze your logs that are captured by CloudWatch Logs at scale in seconds using interactive queries delivering visualizations.
- Container Insights
  - Container insights allow you to collate and group different metric data from different container services and applications within AWS.
  - Container insights also allows you to capture and monitor diagnostic data giving you additional insights into how to resolve issues that arise within your container architecture.
  - This monitoring and insight data can be analysed at the cluster, node, pod and task level making it a valuable tool to help you understand your container applications and services.
- Lambda Insights
  - This feature provides you the opportunity to gain a deeper understanding of your applications using AWS Lambda.
  - It gathers and aggregates system and diagnostic metrics related to AWS Lambda to help you monitor and troubleshoot your serverless applications.

<br />

# AWS CloudTrail

AWS CloudTrail records and tracks **all API requests** in your AWS account. These requests can be initiated from SDKs, AWS CLI, AWS management console or another AWS service. CloudTrail is a **global** service supporting all regions.

### CloudTrail Events
- Every API request captured is recorded as an **event**.
- Multiple events are recorded with CloudTrail Logs.
- Events contain an array of associated metadata.

### CloudTrail Logs
- New log files are created every 5 minutes.
- Log files are delivered and stored within S3. It is in JSON format.
- Log files can be stored for as long as required allowing you to review the history of all API requests.
- CloudTrail Log files can also be delivered to CloudWatch Logs for metric monitoring and alerting via SNS.

### Use Cases
- effective for security analysis
  - monitor restricted API calls
  - notification of threshold breaches
- resolve day to day operational issues
  - filtering mechanisms for isolating data
  - quicker root cause identification
  - speedy resolution
- able to track changes to your AWS infrastructure
- CloudTrail logs can be used as evidence for various compliance and governance controls
  - ISO
  - PCI DSS
  - FedRamp
- Security at Scale

### Core Features and Services
- Trails
- Simple Storage Service (S3)
- Logs (created every 5 mins)
- Key Management Service (KMS)
- Simple Notification Service (SNS)
- CloudWatch Logs
- Event Selectors
- Tags
- Events
- API Activity Filters

<br />

# AWS Config

What Can AWS Config Do:
- Capture resource changes
- Act as a resource inventory
- Store configuration history
- Provide a snapshot of configurations
- Enable notifications about changes
- Provide AWS CloudTrail integration
- Use rules to check compliancy
- Perform security analysis
- Identify relationships between resources
- Config is **region** specific

### AWS Config Key Components
- AWS Resources
- Configuration Item (CI) - Configuration information, relationship information and other metadata in JSON.
- Configuration Stream
- Configuration History - delivered every 6 hours.
- Configuration Snapshot
- Configuration Recorder - is responsible for recording all the changes and generating the CIs
- Config Rules - a great way to help you **enforce specific compliance controls**. Each rule is a Lambda function which notify you the non-compliant changes. 
- Resource Relationship - identifies relationships between resources.
- SNS Topic - is used as a Configuration Stream for notifications.
- S3 Bucket - is used to store all the Configuration History files and Snapshots.
- AWS Config Permissions - The IAM role is required to allow AWS Config to obtain the correct permissions to interact with other services.

<br />

# AWS Organization

AWS Organizations uses the following components to help you manage your accounts.
- Organizations
  - An Organization is an element that serves to form a hierarchical structure of multiple AWS accounts.
- Root
  - The Root object is simply a container that resides at the top of your Organization. All of your AWS accounts and Organizational units will then sit underneath this Root. Within any Organization, there will only be one single Root object.
- Organizational Units
  - Organizational Units (OUs) provide a means of categorizing your AWS Accounts. Again, like the Root, these are simply containers that allow you to group together specific AWS accounts. An organizational unit (or OU) can connect directly below the Root or even below another OU (which can be nested up to 5 times).
- Accounts
  - Accounts. These are your AWS accounts that you use and create to be able to configure and provision AWS resources. Each of your AWS accounts has a 12 digit account number.
- Service Control Policies (SCPs)
  - Service control policies, or SCPs, allow you to control what services and features are accessible from within an AWS account. These SCPs can either be associated with the Root, Organizational Units, or individual accounts. When an SCP is applied to any of these objects, its associated controls are fed down to all child objects.
  - An Security Control Policies (SCP) does NOT grant access, they add a **guardrail** to define what is allowed. You will still need to configure your identity-based or resource-based policies to identities granting permission to carry out actions within your accounts.
  - The SCP would serve to prevent that service from being used within the AWS account and so have the overriding percedence and determine the maximum level of permissions allowed.
  - An SCP does NOT grant access, they add a guardrail to define what is allowed. You will still need to configure your identity-based or resource-based policies to identities granting permission to carry out actions within your accounts.

**Master account** is a standard AWS account that you have chosen to create the AWS organization. The best practice to use this account solely as a master account and not to use it to provision any other resources. This allows you to restrict to the master account at a greater level. The fewer users who need access to it, the better. The master account carries certain administrative level capabilities, such as being able to create additional AWS accounts within your organization, invite other accounts to join your organization, remove AWS accounts from your organization and apply security features via policies to different levels within your organization.

<br />

# VPC Flow Logs

Within your VPC you could potentially have hundreds or even thousands of resources all communicating together. VPC Flow Logs allows you to capture IP traffic information that flows between your network interfaces of your resources within your VPC. this data is useful for a number of reasons, largely to help you resolve incidents with network communication and traffic flow in addition to beting used for security purposes to help spot traffic reaching a destination that should be prohibited. The log data generated by VPC flow logs is sent to CloudWatch Logs.

You can set up and create a Flow Log against these resources:
- A Network interface on one of your instances
- A Subnet within your VPC
- Your VPC itself

### Limitations of VPC Flow Logs
- For VPC peered connections, you can only see flow logs of peered VPCs within the same account.
- You are not able to retrieve information from resources within the EC2-Classic environment.
- Once a VPC Flow Log has been cresated it can't be changed.
- The following traffic is not captured by the logs:
  - DHCP Traffic within the VPC.
  - Traffic from instances destined for Amazon DNS Servers.
  - Traffic destined to the IP addresses for the VPC default router.
  - Traffic to and from 169.254.169.254 (instance metadata) and 169.254.169.123 (Time Sync Service).
  - Traffic relating to an Amazon Windows activation license from a Window instance.
  - Traffic between a Network Load Balancer Network Interface and an Endpoint Network Interface.

<br />

# Logging in AWS

The Unified CloudWatch Agent allows the collection of logs from EC2 instances as well from on-premise server.

### CloudWatch Agent Installation
- Create a role and attach it to the instance with permissions to collect data from the instances in
addition to interacting with **AWS systems manager SSM**.
- Download and install the agent onto the EC2 instance.
- Configure and start the CloudWatch agent. (Configuration file is copied to **the SSM parameter store**.)

### Amazon CloudFront Access Logs
Log files capture data over a period of time and the amount of log files generated depend on the amount of requests received. CloudFront retains logs until they are ready to be delivered to S3. This delivery can take between 1 and 24h.

<br />
