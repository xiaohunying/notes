HTTP Routing

Refer to https://docs.cloudfoundry.org/concepts/http-routing.html

# Sticky Sessions

When multiple instances of an app are running on CF, requests from a
particular client always reach the same app instance.

- **JSESSIONID** (app returns a JSESSIONID cookie)
- **VCAP_ID** (the CF routing tier generates a unique VCAP_ID cookie for the app instance)

# HTTP Headers

- **X-Forwarded-Proto** gives the scheme of the HTTP request from the client.
- **X-Forwarded-For** gives the IP address of the client originating the request.


# Zipkin Tracing in HTTP Headers

- **X-B3-TraceId** HTTP header
- **X-B3-SpanId** HTTP header
- **X-B3-ParentSpan** HTTP header

# App Instance Routing in HTTP Headers

HTTP header **X-CF-APP-INSTANCE**

~~~bat
$ curl app.example.com -H "X-CF-APP-INSTANCE":"YOUR-APP-GUID:YOUR-INSTANCE-INDEX"
~~~

# Forward Client Certificate to Applications

- Always Forward Headers: **router.forwarded_client_cert: always_forward**
- Forward XFCC Headers for mTLS Connections: **router.forwarded_client_cert: forward**
- Remove XFCC Headers: **router.forwarded_client_cert: sanitize_set**

# SSL/TLS Termination

Terminate SSL/TLS

- at the Gorouter
- at the Gorouter and the load balancer
- at the load balancer only

# Transparent Retries

30 seconds

# Round-Robin Load Balancing

The Gorouter uses the round-robin algorithm for load balancing incoming 
requests to application instances.

# WebSockets

WebSockets is a protocol providing bi-directional communication over a single, 
long-lived TCP connection, commonly implemented by web clients and servers. WebSockets 
are initiated through HTTP as an upgrade request. The Gorouter supports this upgrade 
handshake, and will hold the TCP connection open with the selected application instance. 

# Keepalive Connections


