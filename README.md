# Nanosai Ops Examples & Experiments
Nanosai Ops Examples contains examples showing how to use the various Nanosai Ops toolkits.

Nanosai Ops Examples is also used to experiment with different types of solutions (designs) for various problems.
Be aware, that everything under the package "com.nanosai.examples" serve as currently valid ways of doing XYZ.
Contrarily, everything under the "com.nanosai.experiments" is still experimental. Experiments may not be the best way to do XYZ,
but may just be an exploration of a certain design that turned out not to work well. Experiments may also change etc.

Nanosai Ops is a collection of toolkits (for Java) designed for implementing fast, scalable, robust distributed systems using
advanced techniques such as asynchronous communication, non-blocking IO, samethreaded separate state concurrency model,
minimal garbage collection etc.


## Nanosai Ops Toolkits
Currently, the suite of toolkits in Nanosai Ops consists of:

 - [Mem Ops](https://github.com/nanosai/mem-ops-java)
 - [Thread Ops](https://github.com/nanosai/thread-ops-java)
 - [RION Ops](https://github.com/nanosai/rion-ops-java)
 - [Stream Ops](https://github.com/nanosai/stream-ops-java)
 - [Net Ops](https://github.com/nanosai/net-ops-java)

The toolkits are designed to be usable on their own, or together as a coherent ecosystem. Some of the toolkits
depend on other toolkits in the Nanosai Ops collection, but we aim at keeping dependencies
to a minimum.


## Nanosai Ops Design Goals
The Nanosai Ops toolkit design goals are:

 - High performance
 - High scalability
 - Robustness
 - Architectural flexibility
 - Use case versatility
 - High configurability
 - Coherent ecosystem
 - Easy to use

Achieving all of these goals simultaneously is not easy. We have had to design and implement all the toolkits
from scratch to align with these goals.

Obviously we have had to make compromises between some of these design goals. For instance, sometimes ease of use
conflicts with configurablity, architectural flexibility etc. Sometimes performance conflicts with flexibility too.
And sometimes write performance conflicts with read performance. In these cases we have tried to make sensible
compromises between the goals - e.g. by providing sensible default behaviours and configurations.


## Nanosai Ops Use Cases
Some of the use cases you could implement with Nanosai Ops are:

- RPC clients and servers
- Relay server / routing server
- Reverse server
- Data streaming and event driven architecture
- P2P networks
- etc.