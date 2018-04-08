# Kubernetes Components

Refer to https://kubernetes.io/docs/concepts/overview/components/

## Master Components

Master components provide the cluster�s control plane. Master components make global decisions about the cluster (for example, scheduling), and detecting and responding to cluster events (starting up a new pod when a replication controller�s �replicas� field is unsatisfied).

### kube-apiserver

The Kubernetes API server validates and configures data for the api objects which include pods, services, replicationcontrollers, and others. The API Server services REST operations and provides the frontend to the cluster�s shared state through which all other components interact.

The kube-apiserver is the front-end for the Kubernetes control plane. It is designed to scale horizontally � that is, it scales by deploying more instances.

### etcd

The etcd is used as Kubernetes� backing store. All cluster data is stored here. Always have a backup plan for etcd�s data for your Kubernetes cluster.

### kube-controller-manager

The kube-controller-manager runs controllers, which are the background threads that handle routine tasks in the cluster. Logically, each controller is a separate process, but to reduce complexity, they are all compiled into a single binary and run in a single process. These controllers include:

- **Node Controller:** Responsible for noticing and responding when nodes go down.

- **Replication Controller:** Responsible for maintaining the correct number of pods for every replication controller object in the system.

- **Endpoints Controller:** Populates the Endpoints object (that is, joins Services & Pods).

- **Service Account & Token Controllers:** Create default accounts and API access tokens for new namespaces.

### cloud-controller-manager

The cloud-controller-manager runs controllers that interact with the underlying cloud providers. The cloud-controller-manager binary is an alpha feature introduced in Kubernetes release 1.6. cloud-controller-manager runs cloud-provider-specific controller loops only. You must disable these controller loops in the kube-controller-manager. You can disable the controller loops by setting the --cloud-provider flag to external when starting the kube-controller-manager.

cloud-controller-manager allows cloud vendors code and the Kubernetes core to evolve independent of each other. In prior releases, the core Kubernetes code was dependent upon cloud-provider-specific code for functionality. In future releases, code specific to cloud vendors should be maintained by the cloud vendor themselves, and linked to cloud-controller-manager while running Kubernetes.

The following controllers have cloud provider dependencies:

- **Node Controller:** For checking the cloud provider to determine if a node has been deleted in the cloud after it stops responding

- **Route Controller:** For setting up routes in the underlying cloud infrastructure

- **Service Controller:** For creating, updating and deleting cloud provider load balancers

- **Volume Controller:** For creating, attaching, and mounting volumes, and interacting with the cloud provider to orchestrate volumes

### kube-scheduler

kube-scheduler watches newly created pods that have no node assigned, and selects a node for them to run on.

### addons

Addons are pods and services that implement cluster features. The pods may be managed by Deployments, ReplicationControllers, and so on. Namespaced addon objects are created in the kube-system namespace.
Addon manager creates and maintains addon resources.

### DNS

While the other addons are not strictly required, all Kubernetes clusters should have cluster DNS, as many examples rely on it. Cluster DNS is a DNS server, in addition to the other DNS server(s) in your environment, which serves DNS records for Kubernetes services. Containers started by Kubernetes automatically include this DNS server in their DNS searches.

### Web UI (Dashboard)

Dashboard is a general purpose, web-based UI for Kubernetes clusters. It allows users to manage and troubleshoot applications running in the cluster, as well as the cluster itself.

### Container Resource Monitoring

Container Resource Monitoring records generic time-series metrics about containers in a central database, and provides a UI for browsing that data.

### Cluster-level Logging

A Cluster-level logging mechanism is responsible for saving container logs to a central log store with search/browsing interface.

## Node components

Node components run on every node, maintaining running pods and providing the Kubernetes runtime environment.

### kubelet

kubelet is the primary node agent. It watches for pods that have been assigned to its node (either by apiserver or via local configuration file) and:

- Mounts the pod�s required volumes.

- Downloads the pod�s secrets.

- Runs the pod�s containers via docker (or, experimentally, rkt).

- Periodically executes any requested container liveness probes.

- Reports the status of the pod back to the rest of the system, by creating a mirror pod if necessary.

- Reports the status of the node back to the rest of the system

### kube-proxy

kube-proxy enables the Kubernetes service abstraction by maintaining network rules on the host and performing connection forwarding.

### docker

docker is used for running containers.

### rkt

rkt is supported experimentally for running containers as an alternative to docker.

### supervisord

supervisord is a lightweight process monitor and control system that can be used to keep kubelet and docker running.

### fluentd

fluentd is a daemon which helps provide cluster-level logging.



