## Scale
There are rwo types of scaling:

### Vertical Scaling 
In this case with the help of improving hardware such as CPU and RAM the performance of application will be improved.
For the legacy systems it could be recommended.

### Horizontal Scaling
In this case with the help of increasing application's instance on different machines or same machine with different 
virtual machines the performance will be increased. By Kubernetes and with the help of Pods and Nodes, clustering could
be available which is recommended for applications. In our design, because each game should register the SseEmitter in the memory
the scaling should be implemented differently. The load balancer should send the request based on gameId to the same instance.
For achieving horizontally scaling, the ***Map*** in the ***GameEmitterRepository*** class must change with the ***Redis***.

Apart from scaling , in case of huge number of requests per day, with the help of non-blocking, and asynchronous coding
the performance of application will be improved.


