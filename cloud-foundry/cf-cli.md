

# Application SSH Commands

## Enable and Disable SSH Access.

~~~
cf enable-ssh MY-AWESOME-APP
cf disable-ssh MY-AWESOME-APP
cf allow-space-ssh SPACE-NAME
cf disallow-space-ssh SPACE-NAME
~~~

## Check SSH Access Permissions.

~~~
cf ssh-enabled MY-AWESOME-APP
cf space-ssh-allowed SPACE-NAME
~~~

## Securely log into an application container.

~~~
cf ssh MY-AWESOME-APP
cf ssh MY-AWESOME-APP -i 2
cf ssh MY-AWESOME-APP -L [LOCAL-NETWORK-INTERFACE:]LOCAL-PORT:REMOTE-HOST-NAME:REMOTE-HOST-PORT
~~~

> `-i` flag targets a specific instance of an application.

> The `-L` flag enables local port forwarding, binding an output port on your machine to an input port on the application VM. Pass in a local port, and your application VM port and port number, all colon delimited. You can prepend your local network interface, or use the default localhost.

> The `-N` flag skips returning a command prompt on the remote machine. This sets up local port forwarding if you do not need to execute commands on the host VM.

> The `--request-pseudo-tty` and `--force-pseudo-tty` flags let you run an SSH session in pseudo-tty mode rather than generate terminal line output.


## Enable secure log in to an application container using non-CF SSH tools like ssh, scp, and sftp.

Run `cf app MY-AWESOME-APP --guid` and record the GUID of your target app.

~~~
cf app MY-AWESOME-APP --guid
~~~

Query the `/v2/info` endpoint of the Cloud Controller in your deployment. Record the domain name and port of the `app_ssh_endpoint` field, and the `app_ssh_host_key_fingerprint` field. You will compare the `app_ssh_host_key_fingerprint` with the fingerprint returned by the SSH proxy on your target VM.

~~~
cf curl /v2/info
~~~

Run `cf ssh-code` to obtain a one-time authorization code that substitutes for an SSH password. You can run cf ssh-code | pbcopy to automatically copy the code to the clipboard.
~~~
cf ssh-code
~~~

Run your ssh or other command to connect to the application instance. Refer to https://docs.cloudfoundry.org/devguide/deploy-apps/ssh-apps.html

