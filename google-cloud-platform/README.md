# Cloud Shell

Google Cloud Shell provides you with gcloud command-line access to computing resources hosted on Google Cloud Platform and available through the Google Cloud Platform Console. Cloud Shell is a Debian-based virtual machine with a persistent 5GB home directory, which makes it easy for you to manage your Cloud Platform projects and resources. With Cloud Shell, the Cloud SDK gcloud and other utilities you need are pre-installed and always available when you need them.

~~~bash
gcloud auth list
gcloud config list project
gcloud config set project <PROJECT_ID>
~~~

~~~bash
gcloud -h
gcloud config --help
gcloud help config
~~~

~~~bash
cd $HOME

gcloud config list
gcloud config list --all
~~~

# Cloud Storage

~~~bash
gsutil mb gs://my-bucket-555
gsutil cp test.dat gs://my-bucket-555
~~~

# Cloud Launcher

Cloud Launcher provides a way to launch common software packages and stacks on Google Compute Engine with just a few clicks. Many common web frameworks, databases, CMSs, and CRMs are supported. This is one of the fastest ways to get up and running on Google Cloud Platform.

# Persistent disks

Google Compute Engine provides persistent disks for use as the primary storage for your virtual machine instances. Like physical hard drives, persistent disks exist independently of the rest of your machine – if a virtual machine instance is deleted, the attached persistent disk continues to retain its data and can be attached to another instance.

There are 2 types of persistent disks:

- Standard persistent disk
- SSD Persistent disk

Create vm instance:

~~~bash
gcloud compute instances create gcelab --zone us-central1-c
~~~

Create persistent disk:

~~~bash
gcloud compute disks create mydisk --size=200GB \
--zone us-central1-c
~~~

Attach the disk to vm instance:

~~~bash
gcloud compute instances attach-disk gcelab --disk mydisk --zone us-central1-c
~~~

Format and Mount the persistent disk:

~~~bash
sudo mkdir /mnt/mydisk

sudo mkfs.ext4 -F -E lazy_itable_init=0,lazy_journal_init=0,discard /dev/disk/by-id/scsi-0Google_PersistentDisk_persistent-disk-1

sudo mount -o discard,defaults /dev/disk/by-id/scsi-0Google_PersistentDisk_persistent-disk-1 /mnt/mydisk
~~~

Remount on vm instance restart:

~~~bash
/dev/disk/by-id/scsi-0Google_PersistentDisk_persistent-disk-1 /mnt/mydisk ext4 defaults 1 1
~~~

# Container Engine (Kubernetes)

![k8s](images/k8s.png)

# Stackdriver

Stackdriver Monitoring provides dashboards and alerts so you can review performance metrics for cloud services, virtual machines, and common open source servers such as MongoDB, Apache, Nginx, Elasticsearch, and more. You configure Stackdriver Monitoring using the Stackdriver Monitoring Console.

# Load Balancing

## Network load balancer

Network load balancing allows you to balance the load of your systems based on incoming IP protocol data, such as address, port, and protocol type. You also get some options that are not available with HTTP(S) load balancing. For example, you can load balance additional TCP/UDP-based protocols such as SMTP traffic. And if your application is interested in TCP-connection-related characteristics, network load balancing allows your app to inspect the packets, where HTTP(S) load balancing does not.

~~~bash
gcloud compute forwarding-rules create nginx-lb \
         --region europe-west1 \
         --ports=80 \
         --target-pool nginx-pool
~~~

## HTTP(S) load balancer

HTTP(S) load balancing provides global load balancing for HTTP(S) requests destined for your instances. You can configure URL rules that route some URLs to one set of instances and route other URLs to other instances. Requests are always routed to the instance group that is closest to the user, provided that group has enough capacity and is appropriate for the request. If the closest group does not have enough capacity, the request is sent to the closest group that does have capacity.

~~~bash
gcloud compute forwarding-rules create http-content-rule \
        --global \
        --target-http-proxy http-lb-proxy \
        --ports 80
~~~



