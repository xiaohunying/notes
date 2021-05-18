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
  - Exactly-once processing
  - Limited number of transaction per second (defaulted to 300 TPS).
  - Batching allows you to perform actions against 10 messages at once with a single action.
- Dead-Letter Queues
  - The dead-letter queue sends messages that fail to be processed
  - this could be the result of code within your application, corruption within the message or simply missing information.
  - If the message can't be processed by a consumer after a maximum number of tries specified, the queue will send the message to a DLQ.
  - By viewing and analysing the content of the message it might be possible to identify the problem and ascertain the source of the issue.
  - The DLQ must have the same queue type (standard or FIFO) as the source its used against

<br />

# Simple Notification Service (SNS)

SNS is used as a publish/subscribe messaging service. It is centred around topics. Users or endpoints can then subscribe to this topic, where messages or events are published. When a message is published, ALL subscribers to that topic receive a notification of that message. SNS is a managed service and highly scalable, allowing you to distribute messages automatically to all subscribers across your environment, including mobile devices. It can be configured with the AWS management console the CLI or with the AWS SDK.

### SNS Topics
- Publisher --> SNS Topic --> Subscribers
  - Subscribers:
    - HTTPS
    - EMAIL
    - EMAIL-JSON
    - SQS
    - APPLICATION
    - LAMBDA
    - SMS
- SNS offers methods of controlling specific access to your topics through a topic policy. 
  - For example, you can restrict which protocol subscribers can use, such as SMS or HTTPS, or only allow access to this topic for a specific user.
  - The topic policy follows the same format as IAM policies. 
  - To configure an SNS, start from creating a topic.

### SNS and SQS
SNS and SQS by working together, a solution can be designed to send messages to subscribers through a push method, while SQS handles incoming messages and waits for consumers to pull data. - Use SNS as a producer for an SQS queue.

<br />

# Fundamentals of Stream Processing

Some data is valuable only when it is being accessed and processed. Time-critical data is used for preventative maintenance or to react to one or more events in real time. **Stream Processing** is created to address issues of latency, session boundaries, and inconsistent load. The term streaming is used to describe information as it flows continuously without a beginning or an end.

### Stream Application
- Producer
  - Collects events and transactions and put into the Data Stream
- Data Stream
  - Stores the data itself
- Consumer
  - Access the Data Stream, read the data and then act on it

<br />

# Amazon Kinesis

Amazon Kinesis was designed to address the complexity and costs of streaming data into the AWS Cloud. The data includes (1) Event logs (2) Social media feeds (3) Clickstream data (4) Application data (5) IoT sensor data. Amazon Kinesis is composed of four services:
- Amazon Kinesis Video Streams
  - is used to do stream processing on binary-encoded data. ex. audio, video.
  - supports the open-source project WebRTC, which allows you for two-way real-time media streaming between web browsers, mobile applications, and connected devices.
  - highly-customizable: all parts involved with stream processing (data ingestion, monitoring, scaling, elasticity and consumption) are done programmatically when creating a stream.
  - AWS will provision resources only when requested.
  - does not have the ability to do Auto Scaling.
  - To facilitate the development, management and usage of Kinesis Data Streams, AWS provides APIs, the AWS SDKs, the AWS CLI, the Kinesis Agent for Linux, and the Kinesis Agent for windows.
- Amazon Kinesis Data Streams
  - Kinesis Data Streams is used to do stream processing on base64 text-encoded data
  - A Kinesis Data Stream is a set of Shards. A shard contains a sequence of Data Records. Data Records are composed of a Sequence Number, a Partition Key and a Data Blob.
  - Two types of consumers
    - classic: pulls data from stream (polling mechanism)
    - Enhanced Fan Out: data being pushed automatically from the shard into a consumer application
- Amazon Kinesis Data Firehose
  - Kinesis Data Firehose is used to do stream processing on base64 text-encoded data
  - Kinesis Data Firehose is fully managed and a streaming delivery service for data. Ingested data can be dynamically transformed, scaled automatically, and is automatically delivered to a data store.
  - Kinesis Data Firehose is NOT a streaming storage layer in the way that Kinesis Data Streams is.
  - Kinesis Data Firehose uses producers to load data into streams in batches. Once inside the stream, the data is delivered to a data store.
  - Kinesis Data Firehose buffers incoming streaming data before delivering it to its destination. It is considered a near real-time streaming solution.
  - Kinesis Data Firehose will automatically scale as needed
  - Kinesis Data Firehose can convert the format of your input data from JSON to Apache Parquet or Apache ORC before storing data in Amazon S3.
  - Kinesis Data Firehose can also invoke Lambda functions to transform incoming source-data and deliver the transformed data to its destination.
  - There is no free tier for using Kinesis Data Firehose. However, costs are incurred when data is inside a Firehose stream. There is no bill for provisioned capacity, only used capacity.
- Amazon Kinesis Data Analytics
  - Kinesis Data Analytics is used to do stream processing on base64 text-encoded data
  - Kinesis Data Analytics has the ability to read from the stream in real time and do aggregation and analysis on data while it is in motion.
  - Kinesis Data Analytics charges an hourly rate based on the number of Amazon Kinesis Processing Units (KPUs) used to run a streaming application.

### Layers of Streaming

- Source
- Stream Ingestion
  - The producer
  - Amazon Kinesis Agent, Amazon Kinesis Producer Library, Amazon SDK
- Stream Storage
  - **Amazon Kinesis data streams** stores data for 24 hours (default) to 365 days
  - Data records are immutable
  - Data can not be removed from the stream. It can only expire
- Stream Processing
  - The consumer
  - **Amazon Kinesis Data Analytics**, **Amazon Kinesis Data Firehose**, Amazon Kinesis Consumer Library
- Destination
  - Amazon S3, Amazon Redshift, Amazon Elasticsearch Service, Splunk



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


