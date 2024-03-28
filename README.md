# Address Service

The address service provides the bounded context `Address`. It is responsible for managing addresses, both for users and of the vendor itself.

## Documentation

Detailed information about the address service can be found in the [documentation](https://misarch.github.io/docs/docs/dev-manuals/services/address).


## Getting started

A development version of the address service can be started using docker compose:

```bash
docker-compose -f docker-compose.dev.yml up --build
```
A GraphiQL interface is available at http://localhost:8080/graphiql to interact with the service.

> [!NOTE]
> Running the service locally through the IDE is neither recommended nor supported.
