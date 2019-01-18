# Kubernetes

## Kubernetes Architecture

- Masters
- Nodes
- Pods
- Services
- Deployments


### Masters

![master](images/k8s_master.png)

kube-apiserver

- Front-end to the control plane
- Exposes the API (Rest)
- Consumes JSON (via manifest files)
- Port 443

Cluster store

- Persistent storage
- Cluster state and config
- Uses etcd
- Distributed, consistent, watchable...
- The "source of truth" for the cluster
- Have a backup plan for it

kube-controller-manager

- Controller of controllers (node ontroller, endpoints controller, namespace controller, ...)
- Watches for changes
- Helps maintain desired state

kube-scheduler

- Watches apiserver for new pods
- Assigns work to nodes (affinity/anti-affinity, constraints, resources, ...)


