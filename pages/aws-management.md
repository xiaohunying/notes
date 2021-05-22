<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Management

# AWS CloudTrail

AWS CloudTrail **records and tracks all API requests in your AWS account**. These requests can be initiated 
from SDKs, AWS CLI, AWS management console or another AWS service. CloudTrail is a **global** service supporting
all regions.

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

### What Can AWS Config Do
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
- Config Rules - a great way to help you **enforce specific compliance controls**. Each rule is a Lambda function 
which notify you the non-compliant changes. 
- Resource Relationship - identifies relationships between resources.
- SNS Topic - is used as a Configuration Stream for notifications.
- S3 Bucket - is used to store all the Configuration History files and Snapshots.
- AWS Config Permissions - The IAM role is required to allow AWS Config to obtain the correct permissions
to interact with other services.

<br />

# CloudWatch

CloudWatch is a comprehensive monitoring tool that allows you to monitor your services and applications
in the cloud. It also allows you to react to events and even prevent service interruptions and outages.
CloudWatch will monitor and create trend reports for services and resources you're probably already using.
CloudWatch also provides a repository for logging.

- Basic Monitoring - Included with all resources in all regions. 3 dashboard / 50 metrics / 5 minute refresh rate
- Enhanced Monitoring - Priced per resource by region. 1 minute refresh rate 

<br />

# Logging in AWS

The Unified CloudWatch Agent allows the collection of logs from EC2 instances as well from on-premise server.

### CloudWatch Agent Installation
- Create a role and attach it to the instance with permissions to collect data from the instances in
addition to interacting with **AWS systems manager SSM**.
- Download and install the agent onto the EC2 instance.
- Configure and start the CloudWatch agent. (Configuration file is copied to **the SSM parameter store**.)


### Amazon CloudFront Access Logs

Log files capture data over a period of time and the amount of log files generated depend on 
the amount of requests received. CloudFront retains logs until they are ready to be delivered to S3.
This delivery can take between 1 and 24h.

<br />

# VPC Flow Logs

Within your VPC you could potentially have hundreds or even thousands of resources all communicating together.
VPC Flow Logs allows you to capture IP traffic information that flows between your network interfaces of
your resources within your VPC. this data is useful for a number of reasons, largely to help you resolve 
incidents with network communication and traffic flow in addition to beting used for security purposes to
help spot traffic reaching a destination that should be prohibited.

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

# Key Management Service (KMS)

KMS is a managed service used to store and generate encryption keys that are used by other AWS services
and applications. AWS KMS only uses **Symmetric cryptography** which uses a single key. KMS is for **encryption
at rest** only. KMS is a region specific service. 

### Key Components of KMS

- **Customer Master Keys (CMK)**
  - AWS managed CMKs
  - Customer managed CMKs
- **Data Encryption Keys (DEK)**
- **Key Policies** 
  - The key policies allow you to define who can use and access a key in KMS. These policies
are tied to the CMKs, they are resource based policies. These permissions are defined within a 
key policy JSON document.
  - Access control for most services can be completely controlled and governed by using IAM alone. For KMS 
    however, this is not the case. To manage access to your CMKs, you **must** use a key policy associated to your
    CMK.
- **Grants** - Grants are another method of controlling access and use of the CMKs held within KMS. They allow
you to delegate a subset of your own access to a CMK for principals. There is less risk of someone altering
the access control permissions for that CMK. Grants eliminates the possibility of anyone using the permission
_kms:PutKeyPolicy_.

### KMS Key Policies

A key policy is required for all CMKs. KMS creates a default key policy for you to allow principals 
to use the CMK in question. 
- KMS configures the root user of the AWS account full access to the CMK. If the full access user 
was deleted from IAM, you would need to contact AWS Support to regain the control.
- Without the root account having full access in the key policy, IAM can't be used.
- Key Administrators and users can also be configured within the key policy.
- Key Administrators can only administer the CMK, and not use it.
- Define which users should be allowed to perform any encryption using this CMK.
- Users are also able to use Grants.

### Using Key Policies with IAM

You must have the following entry within the Key policy allowing the root full KMS access to the CMK.

~~~json
{
  "Sid": "Enable IAM User Permissions",
  "Effect": "Allow",
  "Principal": {"AWS": "arn:aws:iam::123456789123:root"},
  "Action": "kms:*",
  "Resource": "*"
}
~~~

### Using key Policies with Grants

You can also assign permission using Grants alongside key policies.
- Grants allow you to delegate your permissions to another AWS principal within your AWS account.
- Grants need to be created using the AWS KMS APIs.
- Grants are attached to a CMK, much like key policies are.
- Permissions can then be adopted programmatically by the grantee.
- You need to specify the CMK identifier, the grantee principal and the required level of operations.
- To create a grant use the Create-Grant API.
- Grants generate a GrantToken and a GrantID.
- GrantTokens allow the grantee to perform the operations with immediate effect.

### Key Management

- **Rotation of CMKs** 
  - All existing backing keys are retained during and after rotation.
  - Automatic key rotation is not possible with imported key material.
  - CMKs in the state of disabled or pending deletion will not be rotated.
  - It's not possible to manage the key rotation for any **AWS managed CMKs**, these are rotated every **3 years**.
  - Manual key rotation is the process of replacing the current CMK with a new CMK.
- Import key material from an existing KMS outside of AWS
- Deletion of CMKs

> backing key: it is the cryptographic element that is used in the encryption process.
>
> key material: it is essentially the backing key.

<br />

# S3 Encryption Mechanisms

Server-Side Encryption
- with S3 Managed Keys (SSE-S3)
- with KMS Managed Keys (SSe-KMS)
- with Customer Provided Keys (SSE-C)

Client-Side Encryption
- with KMS Managed Keys (CSE-KMS)
- with Customer Provided Keys (CSE-C)

<br />

# AWS Organization

AWS Organizations uses the following components to help you manage your accounts.
- Organizations
- Root
- Organizational Units
- Accounts
- Service Control Policies (SCPs)

**Master account** is a standard AWS account that you have chosen to create the AWS organization. The best 
practice to use this account solely as a master account and not to use it to provision any other resources.
This allows you to restrict to the master account at a greater level. The fewer users who need access to it,
the better. The master account carries certain administrative level capabilities, such as being able to
create additional AWS accounts within your organization, invite other accounts to join your organization,
remove AWS accounts from your organization and apply security features via policies to different levels within 
your organization.

An **Security Control Policies (SCP)** does NOT grant access, they add a **guardrail** to define what is allowed. You will still need to configure
your identity-based or resource-based policies to identities granting permission to carry out actions
within your accounts.