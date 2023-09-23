# Hexagonal Architecture

## It is also known as Ports and Adapters
## divides a system into several loosely-coupled interchangeable components, such as the application core, the database, the user interface, test scripts and interfaces with other systems.
## Separate inside (Domain layer) and outsides(Infrastructure)
## Divides the software as inside and outside.and start with inside, domain layer.
## The principle of hexagonal architecture to isolate the domain from any dependency, such as UI, Data layer or even a framework like spring.
## It is responsible to create thoroughly testable application with isolated business logic from infrastructure and data source.
## Domain layer should be most independent and stable component of the system.
## Invented in 2005 by Alistair Cockburn as "Allow an application equally to be driven by users,programs automated test or batch scripts and to be developed and tested in an isolation form its eventual runtime device and database."
# Primary adaptors : Implements the input ports to execute use cases.
# Secondary Adaptors : Implements the output ports and called by business logic to complete external logic.


