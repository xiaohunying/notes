<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Security

- [IAM (Identity And Access Management)](#identity-and-access-management-iam)
  - [Users, Groups & Roles](#users-groups--roles)
  - [IAM Policies](#iam-policies)
  - [MFA (Multi-Factor Authentication)](#mfa-multi-factor-authentication)
  - [Identity Federation](#identity-federation)
  - [Features of IAM](#features-of-iam)
- [WAF (AWS Web Application Firewall)](#aws-web-application-firewall-waf)
  - [AWS WAF Componnets](#aws-waf-componnets)
  - [When And Why WAF](#when-and-why-waf)
  - [AWS Firewall Manager](#aws-firewall-manager)
  - [AWS WAF Pricing](#aws-waf-pricing)
- [AWS Shield](#aws-shield)
- [AWS Cognito](#aws-cognito)

<br />

# Identity And Access Management (IAM)

IAM is to manage, control and govern authentication, authorization and access control mechanisms of identities of your resources within your AWS Account. Identities are required to authenticate AWS accounts. Access Management relates to **authorization** and **access control**. IAM is a **global service**.

### Users, Groups & Roles
- Users
  - A user can represent a real person who requires access to operate and maintain your AWS environment 
  - Or an account used by an application that requires permissions to access your AWS resources programmatically
    - Access Key ID
    - Secret Access Key ID
      - It's not possible to retrieve lost Secret Access Key IDs
    - These keys must be applied and associated with your application
- Groups
  - IAM Groups contain multiple users
  - IAM Groups are not used in authentication process. They are used to authorize access through AWS Policies.
  - IAM Groups contain IAM Users and have IAM policies associated. (AWS managed policies and/or Customer managed policies)
  - AWS account has a default maximum limit of 100 groups.
  - A user can only be associated to 10 groups.
- Roles
  - IAM Roles allow you to adopt a set of temporary IAM permissions to access AWS resources.
    - Use case example, You have an EC2 instance running an application that requires access to S3 to Put and Get objects using the relevant API calls. You can assign an IAM role to the EC2 instance. You should always associate a role to an EC2 instance for accessing AWS resources over storing local credentials on the instance itself.
  - Roles don't have any access keys or credentials associated with them. The credentials are dynamically assigned by AWS.
  - You can alter the permissions assigned to the Role and all the EC2 instance associated will have the correct access.
  - There are 4 types of Roles:
    - AWS Service Role
    - AWS Service-Linked Role
      - These are roles associated to certain AWS services. They are pre-defined by AWS and the permissions can't be altered in any way as they are set to perform a specific function.
      - Example: Amazon Lex-Bots, Amazon Lex-Channels.
    - Roles for Cross-Account Access
      - This role type offer two options:
        - provide access between AWS accounts that you own
        - provide access between an account that you own and a third party AWS account
      - The **trusting** account has the resources that need to be accessed.
      - The **trusted** account contains users that need to access the resources in the trusting account.
      - A role is created in the trusting account.
      - A trust is established with the role by the AWS account number of the trusted account.
      - Permissions are applied to the Role via **policies**.
      - The users in the trusted account have a **policy** attached.
    - Role for Identity Provider Access 
      - Grant access to web identity providers - creates a trust for users using Amazon Cognito, amazon, Facebook, Google or other provider.
      - Grant Web Single Sign On to SAML Providers - allows access for users coming from a Security Assertion Markup Language (SAML) provider.
      - Grant API access to SAML Providers - Allows access from SAML provider via the AWS CLI, SDKs or API calls.
- Policy Permissions
  - These are JSON policies that define what resources can or can't be accessed
- Access Control Mechanisms
  - username and password 
  - multi-factor authentication (MFA)
  - federated access

### IAM Policies

IAM Policies are used to assign permissions. They are formatted as a JSON document and have at least one `statement` with this structure: (By default, `effect` is set to `Deny`.

~~~json
{
    "Version": "2020-12-25",
    "Statement": [
      {
        "Sid": "Stmt123456789012",
        "Action": "cloudtrail:*",
        "Effect": "Allow",
        "Resource": "*",
        "Condition": {
          "IpAddress": {
            "aws:SourceIp": "10.10.0.0/16"
          }
        }
      }
    ]
}
~~~

There are two types of IAM Policies: 
- Managed Policies
  - AWS Managed Policies
  - Customer Managed Policies
- In-line Policies
  - They are directly embedded into a specific user, group or role.
  - If there are conflicting permissions assigned to the same user, follow:
    - By default, all access is denied.
    - Access will only be allowed if an explicit "Allow" has been specified.
    - A single "Deny" will overrule any "Allow".

### MFA (Multi-Factor Authentication)
MFA utilizes a random 6 digit number generated by an MFA device. There is no additional charge for this. You need 
to own a MFA device which can be a physical token or a virtual device. The MFA device must be configured and 
associated to the user. This configuration can be done from within IAM.

### Identity Federation
Identity federation allows you to access and manage AWS resources even if you don't have a user account
within IAM. Identity providers (IdP) allow users to access AWS resources securely. There must be a trust 
relationship between the IdP and your AWS account. AWS supports two types of IdP:
- **OpenID** - such as Facebook, Google, Amazon. Amazon Cognito
- **SAML2 (Security Assertion Markup Language)** - allows your existing MS-AD users to authenticate to your AWS
resources on a SSO approach.

An example:

1. The user initiates a request to authenticate against the **ADFS server** via a web browser using a **SSO URL**.
2. If the authentication is successful by the AD credentials, SAML will then issues a **Successful Authentication Assertion** back to the users' client requesting federated access.
3. The **SAML Assertion** is sent to the `AWS STS` to assume a role within IAM using the **AssumeRoleWithSAML API**.
4. **STS** responds to the user requesting federated access with **temporary security credentials** with an assumed role and associated permissions.
5. The user then has **federated access** to the necessary AWS services as per the role permissions.

> **Security Token Service (STS)** allows you to gain temporary security credentials for federated users via IAM.

### Features of IAM
- Account settings
  - Account Settings contains information related to your IAM Password policy and Security Token Service Regions. 
- Credential Report
  - Credential report is a list of all your IAM users and credentials. The report will only be generated once in 4 hours.
- Key Management Service (KMS)
  - The KMS enables you to easily manage encryption keys to secure your data.

<br />

# AWS Web Application Firewall (WAF)

AWS WAF helps to prevent web sites and web applications from being maliciously attacked by common web attack patterns such as SQL injection and cross-site scripting. It is also used to identify how **Amazon CloudFront** distributions and **application load balancers** respond to web requests based upon specific conditions. It filters both HTTP and HTTPS request distinguishing between legitimate and harmful inbound requests.

AWS WAF integrated with **CloudWatch** allowing you to monitor set metrics for the service. WAF CloudWatch metrics are reported in one minute intervals by default and are kept for a 2 week period. The metrics monitored are: _AllowedRequests_, _BlockedRequests_, _CountedRequests_, _PassedRequests_.

AWS WAF relies heavily on AWS CloudFront distributions. It also supports custom origins allowing you to apply the same level of security to web infrastructure managed outside of AWS. The association between the Web ACL and a CloudFront distribution can take approximately 15 minutes for the Web ACL and associated rules to be propagated. When a request is blocked by WAF, CloudFront is notified that the request was forbidden and returns a **403 error** to their browser. You can create your own custom 403 error to guide the user to other useful links and provide a polite reason as to why they may have experienced the error. 

### AWS WAF Componnets
- **Conditions**
  - conditions allow you to specify what elements of the incoming HTTP or HTTPS request you want WAF to be monitoring for.
    - Cross-site scripting
    - Geo match
    - IP addresses
    - Size constraints
    - SQL injection attacks
    - String and regex matching
- **Rules**
  - a WAF rule allows you to compile one or more of these conditions into a list which acts as a rule where each condition is ANDed to form the complete rule.
  - You must specify if the rule is associated to CloudFront or an ALB, and in which region.
  - Rule types:
    - Regular Rule
      - count the number of requests that are being received from a particular IP address over a time period of five minutes.
    - Rate-based Rule
      - You will be asked the maximum number of requests from a single IP within a five minute time frame. When the count is reached, all the following requests from that same IP address is then blocked. If the request rate falls back below the rate limit specified the traffic is then allowed to pass through and is no longer blocked. When you set the limit, it must be above 2000. Any request under this limit is considered a **regular rule**.
- **Web ACLs** 
  - Rules can be added to Web ACLs. Within the web ACL, an action is applied for each rule. These actions can either be Allow, Block or Count. 
  - **Rules are executed in the order** that they are listed within a Web ACL. As soon as the request matches all the conditions within a rule it will be associated with that rule regardless of if there is another rule further down that would also be a match.
    - WhiteListedIP (Allow)
    - BlackListedIP (Block)
    - BadSignatures (Block)

### When And Why WAF
If you are delivering web content via a CloudFront distribution or through an application load balancer, then you should implement the AWS Web Application Firewall service as an additional layer of security.

OWASP Top 10:
- Injections
- Broken Authentication and Session Management
- Cross-Site Scripting (XSS)
- Insecure Direct Object references
- Security Misconfiguration
- Sensitive Data Exposure
- Missing Function level access control
- Cross-Site request forgery (CSRF)
- Using known vulnerable components
- Unvalidated redirects and forwards

<br />

### AWS Firewall Manager
AWS Firewall Manager has been designed to help you manage WAF in a multi-account environment with simplicity and control. It allows you to protect your vulnerable resources across all of your AWS accounts within your AWS Organization. It can group and protect specific resources together, e.g. all resources with a particular tag or all of your CloudFront distributions. It automatically protects certain resources that are added to your account as they become active. 

- Prerequisites of Using Firewall Manager
  - ensure your AWS account is a part of an AWS Organization which much have been configured with all features.
  - define which AWS account will act as the **Firewall Manager Admin**.
  - ensure you have **AWS Config** enabled.
- Components of AWS Firewall Manager
  - WAF Rules
  - Rule Groups
    - allow you to group together one or more WAF rules that will have the same action applied. You can create your own rule group and add your own WAF rules, or purchase existing rule groups via the AWS Marketplace. Rule Groups can only contain 1 of 2 actions, these being either Block or Count. You can only have 10 rules per group which can not be increased.
  - Firewall Manager Policies
    - contain the rule groups that you want to assign to your AWS resources. You can only have 2 rule groups per policy: one customer created rule group, one AWS Marketplace rule group.

### AWS WAF Pricing 
There are three chargeable elements of AWS WAF. You will not be charged extra for assigning the 
same Web ACL to multiple distributions.
- the number of incoming requests
- the number of web ACLs that you have
- the number of Rules within each of the Web ACL

<br />

# AWS Shield

AWS Shield has been designed to protect your infrastructure against DDoS attacks (Distributed Denial of Service attacks). AWS Shield itself is available at two different levels of features: 
- AWS Shield Standard
  - (free) for network and transport layers.
- **AWS Shield Advanced** 
  - (additional cost) for network, transport and application layers.

DDoS attack target a web host and it receives a huge number of requests simultaneously from multiple distributed sources. It prevent legitmate requests getting through whilst at the same time severely hindering the performance of the application or website. Types of DDoS attacks:
- SYN flood
- DNS query flood
- HTTP flood/Cache-busting

### Activate AWS Shield Advanced
- you need to manually select the resources needing protection. You can select the resources using ARNs. You must associate an EIP address to your EC2 instance for it to be protected.
- you must add rate-based rules. These rate-based rules are only associated with CloudFront distribution and application load balancers. For each supported resource in the list, it is recommended you associate a Web ACL with a rate-based rule.
- you may or may not authorize the AWS DRT (the AWS DDoS Response Team) to review, update and modify your Web ACLs and Shield configurations during an attack. If you want the assistance of the DRT team, you must be subscribed to either the business or enterprise support plans.

<br />

# AWS Cognito

<br />


