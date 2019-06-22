# Cloud Foundry Command Line Interface

Refer to https://docs.cloudfoundry.org/cf-cli/cf-help.html

~~~
cf config --locale YOUR_LANGUAGE
cf help
cf login [-a API_URL] [-u USERNAME] [-p PASSWORD] [-o ORG] [-s SPACE]
~~~

## Users and Roles

~~~
cf org-users ORG
cf space-users ORG SPACE
cf set-org-role USERNAME ORG ROLE
cf unset-org-role USERNAME ORG ROLE
cf set-space-role USERNAME ORG SPACE ROLE
cf unset-space-role USERNAME ORG SPACE ROLE
~~~

## Push

~~~
cf push APP_NAME [-b BUILDPACK_NAME] [-c COMMAND] [-f MANIFEST_PATH | --no-manifest] [--no-start] [-i NUM_INSTANCES] [-k DISK] [-m MEMORY] [-p PATH] [-s STACK] [-t HEALTH_TIMEOUT] [-u (process | port | http)] [--no-route | --random-route | --hostname HOST | --no-hostname] [-d DOMAIN] [--route-path ROUTE_PATH]
~~~

~~~
cf push APP_NAME --docker-image [REGISTRY_HOST:PORT/]IMAGE[:TAG] [--docker-username USERNAME] [-c COMMAND] [-f MANIFEST_PATH | --no-manifest] [--no-start] [-i NUM_INSTANCES] [-k DISK] [-m MEMORY] [-t HEALTH_TIMEOUT] [-u (process | port | http)] [--no-route | --random-route | --hostname HOST | --no-hostname] [-d DOMAIN] [--route-path ROUTE_PATH]
~~~

~~~
cf push -f MANIFEST_WITH_MULTIPLE_APPS_PATH [APP_NAME] [--no-start]
~~~

## User-Provided Service Instances

~~~
cf cups
cf uups
~~~

## Using the cf CLI with a Self-Signed Certificate

This topic describes how developers can use the cf CLI to communicate securely with a Cloud Foundry (CF) deployment without specifying --skip-ssl-validation under the following circumstances:

- The deployment uses a self-signed certificate.

- The deployment uses a certificate that is signed by a self-signed certificate authority (CA), or a certificate signed by a certificate that’s signed by a self-signed CA.

Before following the procedure below, the developer must obtain either the self-signed certificate or the intermediate and CA certificate(s) used to sign the deployment’s certificate. The developer can obtain these certificates from the CF operator or from the deployment manifest. 

The certificates that developers must insert into their local truststore vary depending on the configuration of the deployment.

- If the deployment uses a self-signed certificate, the developer must insert the self-signed certificate into their local truststore.

- If the deployment uses a certificate that is signed by a self-signed certificate authority (CA), or a certificate signed by a certificate that’s signed by a self-signed CA, the developer must insert the self-signed certificate and any intermediate certificates into their local truststore.

## Cf CLI Plugins

The Cloud Foundry Command Line Interface (cf CLI) includes plugin functionality. These plugins enable developers to add custom commands to the cf CLI. You can install and use plugins that Cloud Foundry developers and third-party developers create. The cf CLI identifies a plugin by its binary filename, its developer-defined plugin name, and the commands that the plugin provides. You use the binary filename only to install a plugin. You use the plugin name or a command for any other action.

> The cf CLI uses case-sensitive commands, but plugin management commands accept plugin and repository names irrespective of their casing.

Refer to https://docs.cloudfoundry.org/cf-cli/use-cli-plugins.html

## Application SSH Commands

Enable and Disable SSH Access.

~~~
cf enable-ssh MY-AWESOME-APP
cf disable-ssh MY-AWESOME-APP
cf allow-space-ssh SPACE-NAME
cf disallow-space-ssh SPACE-NAME
~~~

Check SSH Access Permissions.

~~~
cf ssh-enabled MY-AWESOME-APP
cf space-ssh-allowed SPACE-NAME
~~~

Securely log into an application container.

~~~
cf ssh MY-AWESOME-APP
cf ssh MY-AWESOME-APP -i 2
cf ssh MY-AWESOME-APP -L [LOCAL-NETWORK-INTERFACE:]LOCAL-PORT:REMOTE-HOST-NAME:REMOTE-HOST-PORT
~~~

### Enable secure log in to an application container using non-CF SSH tools like ssh, scp, and sftp.

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

## Cf CLI Return Codes

The cf CLI uses exit codes, which help with scripting and confirming that a command has run successfully. For example, after you run a cf CLI command, you can retrieve its return code by running `echo $?` (on Windows, `echo %ERRORLEVEL%`). If the return code is `0`, the command was successful.

