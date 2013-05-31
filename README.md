# Norad

Master-control for my personal Immutant MCP.

## Usage

Put this into `etc/sqs.clj`:

```clojure
{:aws-id "A..........."
 :aws-secret-key "...................."}
```

```
$ lein immutant deploy
$ lein immutant run
```

Norad will then attempt to consume messages from
[SQS](https://aws.amazon.com/sqs/) and send them to your notifier
(presumably screamy).

Heavy a work in progress.

## Modules

See:

- [screamy](https://github.com/dakrone/screamy)
- [metube](https://github.com/dakrone/metube)

## License

Copyright © 2013 Matthew Lee Hinman

Distributed under the Eclipse Public License, the same as Clojure.
