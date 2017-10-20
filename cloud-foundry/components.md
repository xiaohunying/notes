# BOSH
BOSH is a project that unifies release engineering, deployment, and lifecycle management of small and large-scale cloud software. BOSH can provision and deploy software over hundreds of VMs. It also performs monitoring, failure recovery, and software updates with zero-to-minimal downtime.

While BOSH was developed to deploy Cloud Foundry PaaS, it can also be used to deploy almost any other software (Hadoop, for instance). BOSH is particularly well-suited for large distributed systems. In addition BOSH supports multiple Infrastructure as a Service (IaaS) providers like VMware vSphere, Google Cloud Platform, Amazon Web Services EC2, and OpenStack. There is a Cloud Provider Interface (CPI) that enables users to extend BOSH to support additional IaaS providers such as Apache CloudStack and VirtualBox.


# Cloud Controller

The Cloud Controller provides REST API endpoints for clients to access the system. 
The Cloud Controller maintains a database (CC_DB) with tables for orgs, spaces, services, 
user roles, and more.

## Blobstore

To stage and run apps, Cloud Foundry manages and stores the following 
types of binary large object (blob) files:

- App Packages (/cc-packages): Full contents of app directories, including source code and resource files, zipped into single blob files.
- Buildpacks (/cc-buildpacks): Buildpack directories, which Diego cells download to compile and stage apps with.
- Resource Cache (/cc-resources): Large files from app packages that the Cloud Controller stores with a SHA for later re-use. To save bandwidth, the Cloud Foundry Command-Line Interface (cf CLI) only uploads large application files that the Cloud Controller has not already stored in the resource cache.
- Buildpack Cache (/cc-droplets/buildpack_cache): Large files that buildpacks generate during staging, stored for later re-use. This cache lets buildpacks run more quickly when staging apps that have been staged previously.
- Droplets (/cc-droplets): Staged apps packaged with everything needed to run in a container.

### Blob Cleanup

orphan blobs


# Gorouter

The Gorouter routes traffic coming into Cloud Foundry to the appropriate component, whether it is an operator addressing the Cloud Controller or an application user accessing an app running on a Diego Cell. The router is implemented in Go. Implementing a custom router in Go gives the router full control over every connection, which simplifies support for WebSockets and other types of traffic (for example, through HTTP CONNECT). A single process contains all routing logic, removing unnecessary latency.