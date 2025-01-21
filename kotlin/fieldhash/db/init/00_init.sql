create extension if not exists postgis;

create schema master;

create table master.partition_chain(
  id int8 generated always as identity primary key,
  chain jsonb not null
);

create table master.field_object(
  id int8 generated always as identity primary key,
  position geometry(point) not null,
  hash_chain_id int8 not null,
  field_hash text not null
);

create index field_objects$i_hash_chain_id__field_hash on master.field_object(hash_chain_id, field_hash);
