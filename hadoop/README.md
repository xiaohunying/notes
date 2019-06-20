# Apache Hadoop

the core of Apache Hadoop consiss of 

- a story part: Hadoop Distributed File System **HDFS**
- a processing part: a **MapReduce** programming model

The base Apache Hadoop framework is composed of the following modules:

- Hadoop Common: contains libraries and utilities needed by other Hadoop modules.
- Hadoop Distributed File System (HDFS): a distributed file system that stores data on commodity machines, providing very high aggregate bandwidth across the cluster.
- Hadoop YARN: introduced in 2012 is a platform responsible for managing computing resources in clusters and using them for scheduling users' applications.
- Hadoop MapReduce: an implementation of the MapReduce programming model for large-scale data processing.

The Hadoop framework itself is mostly written in the **Java** programming language, with some native code in C and command line utilities written as shell scripts.

### MapReduce

- map
- shuffle
- reduce


# Apache Hive

Apache Hive is a data warehouse software project built on top of Apache Hadoop for providing **data query and analysis**. Hive gives a SQL-like interface to query data stored in various databases and file systems that integrate with Hadoop.

- Supports analysis of large datasets stored in Hadoop HDFS and compatible file systems such as Amazon S3 filesystem and Alluxio.
- Provides HiveQL with schema on read and transparently converts queries to MapReduce, Apache Tez and Spark jobs. All three execution engines can run in Hadoop's resource negotiator, YARN (yet another resource negotiator).
- Provides indexes, including bitmap indexes to accelerate queries.
