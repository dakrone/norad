# Norad

Master-control for my personal Immutant MCP.

## Usage

```
$ lein immutant deploy
$ lein immutant run
```

```
$ curl localhost:8080/notify -d'this is a message'
```

Put this into `etc/sqs.clj`:

```clojure
{:aws-id "A..........."
 :aws-secret-key "...................."}
```

Norad will then attempt to consume messages from
[SQS](https://aws.amazon.com/sqs/) and send them to your notifier
(presumably screamy).

Heavy a work in progress.

## Modules

See:

- [screamy](https://github.com/dakrone/screamy)

## License

Copyright © 2013 Matthew Lee Hinman

Distributed under the Eclipse Public License, the same as Clojure.
