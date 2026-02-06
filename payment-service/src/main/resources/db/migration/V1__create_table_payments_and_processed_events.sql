create table if not exists payments
(
    id           uuid primary key,
    order_id     uuid        not null,
    status       varchar(16) not null,
    provider_ref varchar(64),
    created_at   timestamptz not null
);

create unique index if not exists uk_payments_order_id on payments (order_id);
create index if not exists idx_payments_order_id on payments (order_id);

create table if not exists processed_events
(
    event_id     uuid primary key,
    processed_at timestamptz not null
);
