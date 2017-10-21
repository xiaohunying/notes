### BOSH

BOSH is an open source tool for release engineering, deployment, lifecycle management, and monitoring of distributed systems.

### Diego Architecture

### Diego Cell

### Diego BBS

BBS stands for Bulletin Board System

### Deigo Brain

### Cloud Controller (CC)

### Nsync

### Cell Reps

### Service Brokers

Applications typically depend on services such as databases or third-party SaaS providers. When a developer provisions and binds a service to an application, the service broker for that service is responsible for providing the service instance.

### Gorouter

The Gorouter uses the round-robin algorithm for load balancing incoming requests to application instances.

### Loggregator

The Loggregator (log aggregator) system streams application logs to developers.

### Metrics Collector

The metrics collector gathers metrics and statistics from the components. Operators can use this information to monitor a Cloud Foundry deployment.

### Blobstore

### UAA

The UAA is an OAuth2 server and stands for User Account and Authentication.

### Consul

Consul is a tool for discovering and configuring services in your infrastructure. It provides several key features: Service Discovery, Health Checking, Key/Value Store and Multi Datacenter.

### Zipkin

Zipkin is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in microservice architectures. It manages both the collection and lookup of this data. Zipkin’s design is based on the Google Dapper paper.

### WebSockets

WebSockets is a protocol providing bi-directional communication over a single, long-lived TCP connection, commonly implemented by web clients and servers. WebSockets are initiated through HTTP as an upgrade request. The Gorouter supports this upgrade handshake, and will hold the TCP connection open with the selected application instance.

### NATS protocol

The NATS wire protocol is a simple, text-based publish/subscribe style protocol. Clients connect to and communicate with gnatsd (the NATS server) through a regular TCP/IP socket using a small set of protocol operations that are terminated by newline. Unlike traditional messaging systems that use a binary message format that require an API to consume, the text-based NATS protocol makes it easy to implement clients in a wide variety of programming and scripting languages.
