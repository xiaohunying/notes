# AWS Network

# VPC (Virtual Private Cloud)

CIDR (Classless Inter-Domain Routing) Block range

<br />

## Subnet

Every time you create a subnet, it is a private subnet to begin with and that is until you attach an Internet 
Gateway (IGW) to your VPC and then add the additional route to the IGW. Private subnet: inaccessable by 
default from internet.

When you create a subnet, you need to define a CIDR block range that fits within the VPC CIDR block.

![subnet](../resources/images/aws-subnet-1.png)

## Public subnet: 

Public subnet is a subnet that has 
- accessable from internet
- public IP address
 
To make a subnet public, you need to 
- add an internet gateway (IGW)
  - IGW is attached to your VPC and acts as a gateway between your VPC and the outside world.
- add a route to the subnet's route table
  - all subnets within your VPC can communicate with each other due to the default configuration of route: local.

![subnet](../resources/images/aws-subnet-2.png)


## Route Table

- you can have the same route table associated to multiple subnets.
- you can NOT associate more than one route table to a single subnet.
- route table contains a destination field (destination) and a target field (route to the destination).

<br />

# VPC Security and Control

## NACLs (Network Access Control Lists)

NACLs are essentially virtual **network-level** firewalls that are associated to each and every subnet and 
they help to control both ingress and egress traffic moving in and out of your VPC and between your 
subnets.

- You can have the same NACL applied to a number of subnets.
- Only a single NACL can be associated to one subnet.

![nacl](../resources/images/aws-vpc-nacl.png)

## Security Groups

**Instance-level**






