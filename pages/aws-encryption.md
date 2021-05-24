<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Encryption

- [KMS (Key Management Service)](#key-management-service-kms)
  - [Key Components of KMS](#key-components-of-kms)
  - [Using Key Policies with IAM](#using-key-policies-with-iam)
  - [Using Key Policies with Grants](#using-key-policies-with-grants)
  - [Key Management](#key-management)
- [AWS CloudHSM](#aws-cloudhsm)
  - [Using CloudHSM as a Custom Key Store in KMS](#using-cloudhsm-as-a-custom-key-store-in-kms)
- [S3 Encryption Mechanisms](#s3-encryption-mechanisms)

<br />

# Key Management Service (KMS)

KMS is a managed service used to store and generate encryption keys that are used by other AWS services and applications. AWS KMS only uses **Symmetric cryptography** which uses a single key. KMS is for **encryption at rest ONLY**. KMS is a region specific service. 

### Key Components of KMS
- CMK: Customer Master Keys
  - There are two types of CMKs:
    - AWS managed CMKs
    - Customer managed CMKs
- DEK: Data Encryption Keys
  - Data keys are used to encrypt your data of any size
- Key Policies
  - Access control for most services can be completely controlled and governed by using IAM alone. For KMS however, this is not the case. To manage access to your CMKs, you MUST use a key policy associated to your CMK.
  - The key policies allow you to define who can use and access a key in KMS. These policies are resource based which are tied to the CMKs. The permissions are defined within a key policy JSON document much like IAM policies.
  - A key policy is required for all CMKs. KMS creates a default key policy for you to allow principals to use the CMK in question. 
  - KMS configures the root user of the AWS account full access to the CMK. If the full access user was deleted from IAM, you would need to contact AWS Support to regain the control.
  - When the root account has full access to the key policy, access to the CMK can be given by normal IAM policies for users and roles.
    - Without the root account having full access in the key policy, IAM can't be used to manage access for other users.
  - You can define Key Administrators in the key policy who can only administer the CMK, but not use it.
  - You can define CMK users in the key policy are allowed to perform any encryption using this CMK.
- Grants 
  - Grants are another method of controlling access and use of the CMKs held within KMS. They allow you to delegate a subset of your own access to a CMK for principals. There is less risk of someone altering the access control permissions for that CMK. Grants eliminates the possibility of anyone using the permission _kms:PutKeyPolicy_.

### Using Key Policies with IAM
When the root account has full access to the key policy, access to the CMK can be given by normal IAM policies for users and roles. Without the root account having full access in the key policy, IAM can't be used to manage access for other users. You must have the following entry within the Key policy allowing the root full KMS access to the CMK.
~~~json
{
  "Sid": "Enable IAM User Permissions",
  "Effect": "Allow",
  "Principal": {"AWS": "arn:aws:iam::123456789123:root"},
  "Action": "kms:*",
  "Resource": "*"
}
~~~

### Using Key Policies with Grants

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
- Rotation of CMKs
  - Automatic key rotation happens every 365 days and there is no way to alter that time frame. But you can perform a manual key rotation.
  - Key rotation simply creates a new backing key and retains the older backing keys to allow you to continue to decrypt existing data encrypted by the original backing key.
  - Automatic key rotation is not possible with imported key material. But you can perform a manual key rotation.
  - CMKs in the state of disabled or pending deletion will not be rotated.
  - It's not possible to manage the key rotation for any **AWS managed CMKs**, these are rotated every **3 years**.
  - Manual key rotation is the process of replacing the current CMK with a new CMK.
- Import key material from an existing KMS outside of AWS
  - key material is essentially the backing key.
  - backing key is the cryptographic element that is used in the encryption process.
- Deletion of CMKs
  - KMS enforces a scheduled deletion process, which can range from 7-30 days.

<br />

# AWS CloudHSM

HSM (Hardware Security Module) is a physical tamper-resistant hardware applicance that is used to protect and safeguard cryptographic material and encryption keys. CloudHSM is used for secure encryption key management and storage which can be used as a root of trust for an enterprise when it comes to data protection allowing you to deploy secure and compliant workloads within AWS. CloudHSM must be provisioned inside a VPC. There are a number of different operations that CloudHSM can help you provide:
- Creation, storage and management of cryptographic keys.
- The ability to use cryptographic hash functions.
- The ability to generate cryptographic secure random data.

### Using CloudHSM as a Custom Key Store in KMS

When working with AWS KMS, you are able to create custom key stores.  A key store is effectively a storage location which can store and protect your cryptographic keys used to encrypt and decrypt your data in AWS.  When working with AWS KMS, the default key stores are managed by KMS and are stored on HSMs managed by AWS, and so as a user of KMS you have no control over these HSMs which underpin the cryptographic storage of KMS.

However, if you have specific compliance controls that you need to adhere to, where you might require a greater level of control of your key stores.  By creating a custom key store you can leverage the power of your CloudHSM cluster which you have full management of

<br />

# S3 Encryption Mechanisms

- Server-Side Encryption
  - with S3 Managed Keys (SSE-S3)
  - with KMS Managed Keys (SSE-KMS)
  - with Customer Provided Keys (SSE-C)
- Client-Side Encryption
  - with KMS Managed Keys (CSE-KMS)
  - with Customer Provided Keys (CSE-C)

<br />
