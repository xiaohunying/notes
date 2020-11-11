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

The security groups are used at the **Instance layer**. With Security Group, if there is a rule in there 
then it's considered allowed, if there's no rule, then all traffic is dropped by default.

![sg](../resources/images/aws-vpc-sg.png)

## NAT Gateway

A NAT Gateway allows private instances to be able to access the internet while blocking connections 
initiated from the internet. For example, we are responsible to update and patch the operating 
systems running on each of our EC2 instances. Then we need to be able to download updates as and when 
we need to. Private subnet has no access to the internet gateway and therefore the internet.

- Because **NAT Gateway sits in a public subnet**, it has to have a public IP address in the form of an EIP
which is an Elastic IP address and this is assigned to the instance itself.
- The **route table** needs to be updated to provide a route to the NAT gateway.
- If you have multiple public subnets in different availability zones, you will need to set up another 
NAT gateway within that subnet as well.

![nat_gateway](../resources/images/aws-vpc-nat-gateway.png)


