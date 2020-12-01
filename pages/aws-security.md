# Security

- [Identity And Access Management (IAM)](#identity-and-access-management-iam)

# Identity And Access Management (IAM)

IAM is to manage, control and govern authentication, authorization and access control mechanisms of 
identities of your resources within your AWS Account. Identities are required to authenticate AWS accounts.
Access Management relates to authorization and access control. IAM is a global service.

Access control:
- username and password 
- multi-factor authentication (MFA) 
- federated access

IAM components:
- [Users](#users)
- [Groups](#groups)
- [Roles](#roles)
- Policy Permissions
- Access Control Mechanisms

### Users

A user can represent a real person who requires access to operate and maintain your AWS environment or
it can be an account used by an application that requires permissions to access your AWS resources
programmatically.

Access keys are required for programmatic access for authentication. These keys must be applied and
associated with your application. If you're using the AWS CLI to access a resource, you first have 
to instruct the AWS CLI to use these Access Keys. this association ensures that all API request
are signed with this digital signature.
- Access Key ID - Made up of 20 random uppercase alphanumeric characters.
- Secret Access Key ID - Made up of 40 random upper and lowercase characters. 
It's notpossible to retrieve lost Secret Access Key IDs!

**AWS CodeCommit** is a managed source control service. It allows you to host secure and scalable 
private Git repositories.

### Groups

IAM Groups are not used in authentication process. They are used to authorize access through AWS Policies.
IAM Groups contain IAM Users and have IAM policies associated. (AWS managed policies and/or Customer managed
policies)

### Roles

For example, you have an EC2 instance running an application that requires access to Amazon S3 to Put
and Get objects using the relevant API calls. You can assign an IAM Role to the EC2 instance. You should
always associate a role to an EC2 instance for accessing AWS resources over storing local credentials
on the instance itself.

Roles don't have any access keys or credentials associated with them. The credentials are dynamically 
assigned by AWS. You can alter the permissions assigned to the Role and all the EC2 instance associated 
will have the correct access.

There are currently 4 different types of Roles:
- AWS Service Role
- AWS Service-Linked Role - These roles do not allow you to modify permissions assigned.
- Role for Cross-Account Access. This role type offer two options:
  - Option 1: provide access between AWS accounts that you own.
  - Option 2: provide access between an account that you own and a third party AWS account.
  - The **trusting** account has the resources that need to be accessed. The **trusted** account contains 
  users that need to access the resources in the trusting account.
    1) A role is created in the trusting account.
    2) A trust is established with teh role by the AWS account number of the trusted account.
    3) Permissions are applied to the Role via **policies**.
    4) The users in the trusted account have a **policy** attached.
- Role for Identity Provider Access
  - Grant access to web identity providers - creates a trust for users using Amazon Cognito, amazon, Facebook,
  Google or other provider.
  - Grant Web Single Sign On to SAML Providers - allows access for users coming from a Security Assertion
  Markup Language (SAML) provider.
  - Grant API access to SAML Providers - Allows access from SAML provider via the AWS CLI, SDKs or API calls. 

### Policies

IAM Policies are used to assign permissions. They are formatted as a JSON document and have at least one 
statement with this structure:

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

There are two types of IAM Policies: Managed Policies and In-line Policies. Managed Policies are AWS Managed Policies
and Customer Managed Policies.
  

## Multi-Factor Authentication (MFA)



## Identity Federation

<br />

