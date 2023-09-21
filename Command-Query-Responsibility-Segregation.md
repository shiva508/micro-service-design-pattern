# Command Query Responsibility Segregation
## The command query responsibility segregation (CQRS) pattern separates the data mutation, or the command part of a system, from the query part. 
## You can use the CQRS pattern to separate updates and queries if they have different requirements for throughput, latency, or consistency.
## Separate read write operations.
## Better performance on read part using right technology for reading, and prevent conflicts with update command.
## Scale each part separately leads to consistency.

