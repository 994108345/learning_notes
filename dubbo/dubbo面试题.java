Dubbo是Alibaba开源的分布式服务框架，它最大的特点是按照分层的方式来架构，使用这种方式可以使各个层之间解耦合（或者最大限度地松耦合）。

从服务模型的角度来看，Dubbo采用的是一种非常简单的模型，要么是提供方提供服务，要么是消费方消费服务，

所以基于这一点可以抽象出服务提供方（Provider）和服务消费方（Consumer）两个角色。关于注册中心、协议支持、服务监控等内容，详见后面描述。

Dubbo面试题精集

默认使用的是什么通信框架，还有别的选择吗?

	默认也推荐使用netty框架，还有mina。

服务调用是阻塞的吗？

	默认是阻塞的，可以异步调用，没有返回值的可以这么做。

一般使用什么注册中心？还有别的选择吗？

	推荐使用zookeeper注册中心，还有redis等不推荐。

默认使用什么序列化框架，你知道的还有哪些？

	默认使用Hessian序列化，还有Duddo、FastJson、Java自带序列化。

服务提供者能实现失效踢出是什么原理？

	服务失效踢出基于zookeeper的临时节点原理。

服务上线怎么不影响旧版本？

	采用多版本开发，不影响旧版本。

如何解决服务调用链过长的问题？

可	以结合zipkin实现分布式服务追踪。

说说核心的配置有哪些？

核心配置有

dubbo:service/

dubbo:reference/

dubbo:protocol/

dubbo:registry/

dubbo:application/

dubbo:provider/

dubbo:consumer/

dubbo:method/

dubbo推荐用什么协议？

	默认使用dubbo协议。

同一个服务多个注册的情况下可以直连某一个服务吗？

	可以直连，修改配置即可，也可以通过telnet直接某个服务。

画一画服务注册与发现的流程图

	流程图见dubbo.io。

Dubbo集群容错怎么做？

	读操作建议使用Failover失败自动切换，默认重试两次其他服务器。写操作建议使用Failfast快速失败，发一次调用失败就立即报错。

在使用过程中都遇到了些什么问题？

	使用过程中的问题可以百度

dubbo和dubbox之间的区别？

	dubbox是当当网基于dubbo上做了一些扩展，如加了服务可restful调用，更新了开源组件等。

你还了解别的分布式框架吗？

	别的还有spring的spring cloud，facebook的thrift，twitter的finagle等