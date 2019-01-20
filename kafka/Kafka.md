# Kafka

## Kafka's existing delivery semantics

- At-least-once, in-order delivery per partition.
- Producer retries can introduce duplicates.

## solution

- Idempotent producer: exactly-once writes.
-- sequenceNumber, producerId

- Transactional producer: Atomic writes across multiple partitions.
-- atomic commit, read commit
-- Transactional Producer: 
--- Config parameter `transactional.id = "my-unique-tid"`
--- Transactional API (hard to use!)
-- Transactinal Comsumer: Config parameter `isolation.level="read_committed"`

- Exactly-once stream processing: read-process-write.

