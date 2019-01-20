# Kubernetes

- Desired State (Declarative Model: manifest file)
- Actual State

## Kubernetes Architecture

- [Masters](#Masters)
- Nodes
- Pods
- Services
- Deployments


### Masters

![master](images/k8s_master.png)

**kube-apiserver**

- Front-end to the control plane
- Exposes the API (Rest)
- Consumes JSON (via manifest files)
- Port 443

**Cluster store**

- Persistent storage
- Cluster state and config
- Uses etcd
- Distributed, consistent, watchable...
- The "source of truth" for the cluster
- Have a backup plan for it

**kube-controller-manager**

- Controller of controllers (node ontroller, endpoints controller, namespace controller, ...)
- Watches for changes
- Helps maintain desired state

**kube-scheduler**

- Watches apiserver for new pods
- Assigns work to nodes (affinity/anti-affinity, constraints, resources, ...)

### Nodes

![node](images/k8s_node.png)

**kubelet**

- The main Kubernetes agent
- Registers node with cluster
- Watches **apiserver**
- Instantiates **pods**
- Reports back to **master**
- Exposes endpoint on :10255 (/spec /healthz /pods)

**Container Engine**

- Does container management: Pulling images; Starting/stopping containers; ...
- Pluggable: Usually **Docker**; Can be **rkt**.

**kube-proxy**

- Kubernetes networking: Pod IP addresses (All containers in a pod share a single IP)
- Load balances across all pods in a **service**

### Pods

Containers always run inside of pods. Pods can have multiple containers (advanced use-case).

All containers in pod share the pod environment.

![pod](images/k8s_pod.png)

**Pods are atomic**: the pod is never declared up and available until the whole lot is up. Pods exist on a single node. You can't have a single pod spread over multiple nodes.

Pod Lifecycle (mortal):

- Phase: pending
- Phase: running
- Phase: succeeded/failed

Deploying Pods: usually via higher level objects (Replication Controller)

### Services

![service](images/k8s_service.png)

The way that a pod belongs to a service is via **labels**.

![tag](images/k8s_tag.png)

### Deployments

Simple rolling updates and rollbacks.

- Multiple concurrent versions (blue-green deployments; Canary releases)
- Simple versioned rollbacks
























