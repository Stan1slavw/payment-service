-- V1__create_orders.sql (PostgreSQL)

create table if not exists orders (
                                      id uuid primary key,
                                      user_id uuid not null,
                                      amount numeric(10,2) not null,
    currency varchar(3) not null,
    status varchar(32) not null,
    created_at timestamptz not null,
    updated_at timestamptz not null,
    version integer not null
    );

create index if not exists idx_orders_user_id on orders(user_id);
create index if not exists idx_orders_status on orders(status);
create index if not exists idx_orders_created_at on orders(created_at);