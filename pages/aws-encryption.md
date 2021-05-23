<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Encryption

- [KMS (Key Management Service)](#key-management-service-kms)

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
