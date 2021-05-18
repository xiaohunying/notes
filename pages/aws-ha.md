<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS High Availability


# AWS DR Storage Solution

- RTO (Recovery Time Objective): Defined as the maximum amount of time in which a service can remain unavailable before it's classed as damaging to the business.
- RPO (Recovery Point Objective): Defined as the maximum amount of time for which data could be lost for a service.

### Data Backups

- AWS Snowball
  - Physical appliance sent to customer site
  - 50 TB or 80 TB in size
  - Use multiple Snowball appliances to scale to petabyte scale
- Snowmobile
  - Exabyte-scale transfer service
  - Transfer up to 100 PB per snowmobile (45-foot shipping container) pulled by a semi-trailer truck
- AWS Storage Gateway
  - File Gateway
  - Stored Volume Gateway
  - Cached Volume Gateway
  - Tape Gateway (Virtual Tape Library)


### High Availability In RDS
- RDS Multi-AZ


