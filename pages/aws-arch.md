<sub>[Amazon Web Services](../pages/aws.md)</sub>

# AWS Architecture

- [Simple Queue Service (SQS)](#simple-queue-service-sqs)
- [Simple Notification Service (SNS)](#simple-notification-service-sns)

<br />

# Simple Queue Service (SQS)

SQS is a fully managed service that works with serverless systems, microservices and distributed architectures. It has the capability of sending, storing and receiving messages at scale without dropping message data. It is possible to configure the service using the AWS Management Console, the AWS CLI or AWS SDKs.

### SQS Queue Types
- Standard Queues
  - Standard queues support at-least-once delivery of messages
  - They offer a best effort on trying to preserve the message ordering (don't use this if ordering is critical).
  - They provide almost unlimited number of transactions per second.
- FIFO Queues
  - The order of messages is maintained and there are no duplicates.
  - Limited number of transaction per second (defaulted to 300 TPS).
  - Batching allows you to perform actions against 10 messages at once with a single action.
- Dead-Letter Queues
  - The dead-letter queue sends messages that fail to be processed
  - this could be the result of code within your application, corruption within the message or simply missing information.
  - If the message can't be processed by a consumer after a maximum number of tries specified, the queue will send the message to a DLQ.
  - By viewing and analysing the content of the message it might be possible to identify the problem and ascertain the source of the issue. 

<br />

# Simple Notification Service (SNS)

SNS offers methods of controlling specific access to your topics through a topic policy. For example, you can restrict which protocol subscribers can use, such as SMS or HTTPS, or only allow access to this topic for a specific user. The topic policy follows the same format as IAM policies. To configure an SNS, start from creating a topic.

Use SNS as a producer for an SQS queue: SNS and SQS by working together, a solution can be designed to send messages to subscribers through a push method, while SQS handles incoming messages and waits for consumers to pull data.

<br />

# Amazon Kinesis

Amazon Kinesis does not store persistent data itself, unlike many of the other amazon big data services. As a result, Amazon Kinesis needs to be deployed as part of a larger event-driven solution. Amazon Kinesis provides three different solution capabilities:

### Amazon Kinesis Streams

Amazon Kinesis Streams enables you to build custom applications that process or analyze streaming data for specialized needs.

- Data streams: A data streaming service capable of elastically scaling to support thousands of data feeds.
- Video streams: Designed to securely elastically scale and ingest video streams on a massive scale.

An Amazon Kinesis stream is an ordered sequence of data records. A record is the unit of data in an Amazon Kinesis stream. Each record in the stream is composed of **a sequence number**, **a partition key** and **a data blob**.

A consumer receives records from Amazon Kinesis Streams and processes them in real-time. You need to build your applications using either the Amazon Kinesis API, or the Amazon Kinesis Client Library.

### Amazon Kinesis Data Firehose

Amazon Kinesis Firehose is a fully namaged service for delivering real-time streaming data to destinations such as Amazon S3, Amazon RedShift, and Amazon Elastic Search and Splunk.

With Kinesis Firehose, you do not need to write applications as your consumers. You can also configure Amazon Kinesis Firehose to transform your data before data delivery.

Firehose is limited to S3, Redshift, Elasticsearch and Splunk as the data destinations.

### Amazon Kinesis Analytics

Amazon Kinesis Analytics enables you to quickly author SQL code that continuously reads, processes and stores data. You can ingest in real-time billions of small data points. Each individual data point can then be aggregated to provide intelligent business insights, which in turn can be used to continually optimize and improve business processes.

- Create an input stream: Typically come from streaming data sources such as Kinesis streams
- Create SQL processing logic
- Create an output stream: to hold intermediate results that are used to feed into other queries or be used to stream out the final results.


