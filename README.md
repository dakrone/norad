# Norad

Master-control for my personal Immutant MCP.

## Usage

Put this into `etc/sqs.clj`:

```clojure
{:aws-id "A..........."
 :aws-secret-key "...................."}
```

```
$ lein run
```

Or, if you have WildFly installed:

```
lein immutant war -o $JBOSS_HOME
$JBOSS_HOME/bin/standalone.sh -c standalone-full.xml
```

Norad will then attempt to consume messages from
[SQS](https://aws.amazon.com/sqs/) and send them to your notifier
(presumably screamy).

Messages should be a string that looks like:

```clojure
"{:queue \"queue.notifications\" :msg \"this is a message\"}"
```

Heavy work in progress.

## Modules

- notify - send notifications using growl, terminal-notifier, or notify-send

See also

- [metube](https://github.com/dakrone/metube)

## License

Copyright © 2013-2014 Matthew Lee Hinman

Distributed under the Eclipse Public License
