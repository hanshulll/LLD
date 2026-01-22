# Module 1: Relational DB design fundamentals (PostgreSQL-first)

## 1) What a relational DB is (the interview definition)
A relational database stores data in **tables (relations)** made of **rows (tuples)** and **columns (attributes)**, and lets you:
- enforce **integrity** (via constraints),
- query via **SQL** (joins, aggregations),
- and keep changes correct under concurrency using **transactions** (ACID).

For SDE-2, you’re expected to translate requirements into:
- tables + keys,
- constraints,
- indexes,
- and transaction boundaries.

---

## 2) Keys: the backbone of schema design

### Primary Key (PK)
- Uniquely identifies a row.
- In Postgres, a PK is effectively: `NOT NULL + UNIQUE` plus an underlying index.

**Typical choices**
- `BIGINT` surrogate key (`GENERATED ... AS IDENTITY`) — common in OLTP.
- `UUID` — useful for distributed ID generation, public IDs, multi-region (but larger indexes).

**Interview trade-off**
- BIGINT: smaller index, faster joins, but centralized ID generation (usually fine).
- UUID: easier to generate outside DB, but bigger indexes → more IO.

### Foreign Key (FK)
- Enforces that a referencing row points to an existing parent row.
- Adds integrity but can add write overhead and require thought for deletes/updates.

**Deletion strategies**
- `ON DELETE RESTRICT` (default): prevents deleting parent if children exist (safest).
- `ON DELETE CASCADE`: convenient, but risky if you accidentally delete a parent.
- `ON DELETE SET NULL`: only if nullable FK makes sense.

---

## 3) Normalization: what you must know for interviews

Normalization prevents update anomalies and duplication. You don’t need to recite all forms, but you must *apply* them.

### 1NF (atomic values)
No arrays/CSV blobs in a single column if you need to query elements.

Bad:
- `orders.items = "sku1:2,sku2:1"`

Good:
- `order_items(order_id, sku, qty)`

### 2NF / 3NF (remove partial/transitive dependencies)
Rule of thumb for interviews:
- **Each table models one “entity” or relationship**
- Non-key columns should depend on **the key, the whole key, and nothing but the key**

Example: storing `user_city` in many tables is duplication; store it once in `users` (or `addresses`) and reference.

### When denormalization is acceptable (expected answer)
Denormalize when:
- you have proven read bottlenecks,
- you can tolerate some inconsistency or have a clear sync strategy,
- or you’re optimizing a read-heavy query path.

In Postgres OLTP interviews: default to **normalized**, then selectively denormalize.

---

## 4) Constraints: “the database should protect you”

Interviewers want you to use constraints to prevent bad data.

### Core constraints you should use comfortably
- `NOT NULL` for required fields
- `UNIQUE` for business uniqueness (email, username, etc.)
- `CHECK` for domain rules (qty > 0, price >= 0)
- `FK` for relationships

**Why constraints matter**
- They make the DB a correctness boundary. Application bugs shouldn’t corrupt data.

---

## 5) Indexing fundamentals (PostgreSQL focus)

### What an index is
A data structure (usually **B-tree**) that speeds up lookups by letting the DB avoid scanning the whole table.

### What interviewers expect you to know
- Indexes speed up reads but slow down writes (insert/update/delete).
- An index is used only if it helps enough; Postgres uses a cost-based optimizer.
- For OLTP, the most important indexes typically support:
  - lookups by foreign keys,
  - filtering by time (`created_at`),
  - business-unique lookups (`email`),
  - and pagination patterns.

### Common index types (SDE-2 level)
- **B-tree (default)**: equality + range (`=`, `<`, `>`, `BETWEEN`, `ORDER BY`)
- **GIN**: for `jsonb`, arrays, full-text search (advanced but valuable)
- (You don’t need deep BRIN/SP-GiST unless role-specific.)

### Composite index rule (very interview-relevant)
For composite index `(a, b)`, it can help with:
- `WHERE a = ...`
- `WHERE a = ... AND b = ...`
- `WHERE a BETWEEN ...` (range on first column often ends usefulness for later columns)

So column order matters: **put the most selective / most commonly filtered-first** (with nuance).

---

## 6) Transactions & ACID (what to say in interviews)

### ACID
- **Atomicity**: all-or-nothing
- **Consistency**: invariants remain true (constraints help)
- **Isolation**: concurrent transactions behave “as if” separated (depends on level)
- **Durability**: committed data persists

### Postgres isolation (what you should remember)
Postgres default is **READ COMMITTED**.
- Each statement sees a snapshot at statement start.
- Non-repeatable reads can happen across statements.
- For correctness under concurrency (e.g., balance updates), you often need:
  - `SELECT ... FOR UPDATE` row locks, or
  - constraints + retry on conflicts, or
  - SERIALIZABLE (heavier) in rare cases.

---

# Mini practice (Interview-style)

## A) Rapid concept checks
1. What’s the difference between a **primary key** and a **unique constraint** in Postgres?
2. When would you choose **UUID** over `BIGINT` IDs? Give 2 reasons.
3. Give an example where **3NF** prevents a real bug.
4. What are 2 downsides of adding “too many” indexes?

## B) Schema design warm-up (easy)
Design a schema for:
- Users can place Orders
- Each Order has multiple OrderItems
- Each OrderItem references a Product
Requirements:
- A user can have many orders
- An order must have at least 1 item
- Quantity must be > 0
- A product SKU is unique

Deliverables (tell me in plain text, no SQL yet):
- tables + columns
- PK/FK relationships
- constraints
- 3 indexes you’d add and why

## C) Concurrency scenario
Two requests try to decrement product inventory at the same time.
- How do you prevent overselling in Postgres?
- What isolation/locking approach would you use?  

---

## Your turn
Answer **B** (tables/columns/keys/indexes) first. I’ll challenge your choices like an interviewer, then we’ll refine and I’ll introduce the minimum SQL + Postgres-specific details for correctness and performance.