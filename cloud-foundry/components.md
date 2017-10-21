# BOSH
BOSH is a project that unifies release engineering, deployment, and lifecycle management of small and large-scale cloud software. BOSH can provision and deploy software over hundreds of VMs. It also performs monitoring, failure recovery, and software updates with zero-to-minimal downtime.

While BOSH was developed to deploy Cloud Foundry PaaS, it can also be used to deploy almost any other software (Hadoop, for instance). BOSH is particularly well-suited for large distributed systems. In addition BOSH supports multiple Infrastructure as a Service (IaaS) providers like VMware vSphere, Google Cloud Platform, Amazon Web Services EC2, and OpenStack. There is a Cloud Provider Interface (CPI) that enables users to extend BOSH to support additional IaaS providers such as Apache CloudStack and VirtualBox.



# Router

The router routes incoming traffic to the appropriate component, either a Cloud Controller component or a hosted application running on a Diego Cell. The router periodically queries the Diego Bulletin Board System (BBS) to determine which cells and containers each application currently runs on. Using this information, the router recomputes new routing tables based on the IP addresses of each cell virtual machine (VM) and the host-side port numbers for the cell’s containers.

### Gorouter
The Gorouter routes traffic coming into Cloud Foundry to the appropriate component, whether it is an operator addressing the Cloud Controller or an application user accessing an app running on a Diego Cell. The router is implemented in Go. Implementing a custom router in Go gives the router full control over every connection, which simplifies support for WebSockets and other types of traffic (for example, through HTTP CONNECT). A single process contains all routing logic, removing unnecessary latency.


# Authentication

The OAuth2 server (the UAA) and Login Server work together to provide identity management. UAA stands for User Account and Authentication.

- The OAuth2 server (the UAA)
- Login Server


# Application Lifecycle

- Cloud Controller
- Diego Brain
- nsync
- Cell Reps

## Cloud Controller & Deigo Brain

The Cloud Controller (CC) directs the deployment of applications. To push an app to Cloud Foundry, you target the Cloud Controller. The Cloud Controller then directs the Diego Brain through the CC-Bridge components to coordinate individual Diego cells to stage and run applications.

### Blobstore

The Cloud Controller maintains a database (CC_DB) with tables for orgs, spaces, services, 
user roles, and more. To stage and run apps, Cloud Foundry manages and stores the following 
types of binary large object (blob) files:

- App Packages (/cc-packages): Full contents of app directories, including source code and resource files, zipped into single blob files.
- Buildpacks (/cc-buildpacks): Buildpack directories, which Diego cells download to compile and stage apps with.
- Resource Cache (/cc-resources): Large files from app packages that the Cloud Controller stores with a SHA for later re-use. To save bandwidth, the Cloud Foundry Command-Line Interface (cf CLI) only uploads large application files that the Cloud Controller has not already stored in the resource cache.
- Buildpack Cache (/cc-droplets/buildpack_cache): Large files that buildpacks generate during staging, stored for later re-use. This cache lets buildpacks run more quickly when staging apps that have been staged previously.
- Droplets (/cc-droplets): Staged apps packaged with everything needed to run in a container.


## nsync & BBS & Cell Reps

The nsync, BBS, and Cell Rep components work together along a chain to keep apps running. At one end is the user. At the other end are the instances of applications running on widely-distributed VMs, which may crash or become unavailable. Here is how the components work together:

- **nsync** receives a message from the Cloud Controller when the user scales an app. It writes the number of instances into a `DesiredLRP` structure in the Diego BBS database.
- **BBS** (Bulletin Board System) uses its convergence process to monitor the `DesiredLRP` and `ActualLRP` values. It launches or kills application instances as appropriate to ensure the `ActualLRP` count matches the `DesiredLRP` count.
- **Cell Rep** monitors the containers and provides the `ActualLRP` value.


# App Storage and Execution

## Blobstore

The blobstore is a repository for large binary files, which Github cannot easily manage because Github is designed for code. The blobstore contains the following:

- Application code packages
- Buildpacks
- Droplets

You can configure the blobstore as either an internal server or an external S3 or S3-compatible endpoint.

Blob Cleanup: orphan blobs

## Diego Cell
Application instances, application tasks, and staging tasks all run as Garden containers on the Diego Cell VMs. The Diego cell rep component manages the lifecycle of those containers and the processes running in them, reports their status to the Diego BBS, and emits their logs and metrics to Loggregator.

# Services

## Service Brokers

Applications typically depend on services such as databases or third-party SaaS providers. When a developer provisions and binds a service to an application, the **service broker** for that service is responsible for providing the service instance.


# Messaging

## Consul and BBS (Bulletin Board System)

Cloud Foundry component VMs communicate with each other internally through HTTP and HTTPS protocols, sharing temporary messages and data stored in two locations:

- A Consul server stores longer-lived control data, such as component IP addresses and distributed locks that prevent components from duplicating actions.
- Diego’s Bulletin Board System (BBS) stores more frequently updated and disposable data such as cell and application status, unallocated work, and heartbeat messages. The BBS stores data in MySQL, using the Go MySQL Driver.

## NATS Message Bus

The route-emitter component uses the NATS protocol to broadcast the latest routing tables to the routers.

> The NATS wire protocol is a simple, text-based publish/subscribe style protocol. Clients connect to and communicate with gnatsd (the NATS server) through a regular TCP/IP socket using a small set of protocol operations that are terminated by newline. Unlike traditional messaging systems that use a binary message format that require an API to consume, the text-based NATS protocol makes it easy to implement clients in a wide variety of programming and scripting languages.


# Metrics and Logging

## Loggregator
The Loggregator (log aggregator) system streams application logs to developers.

## Metrics Collector
The metrics collector gathers metrics and statistics from the components. Operators can use this information to monitor a Cloud Foundry deployment.




