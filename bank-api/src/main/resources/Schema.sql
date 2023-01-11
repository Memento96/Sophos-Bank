create table client (client_id bigint not null auto_increment, client_creator varchar(255), date_of_birth date, email_address varchar(255), id_number varchar(255), id_type varchar(255), last_names varchar(255), modification_date datetime, modification_user varchar(255), names varchar(255), primary key (client_id)) engine=InnoDB;
create table product (product_id bigint not null auto_increment, account_number varchar(255), account_status varchar(255), account_type varchar(255), available_balance double precision, balance double precision, creation_date datetime, gmf_exempt bit, modification_date datetime, modification_user varchar(255), account_creator bigint, primary key (product_id)) engine=InnoDB;
create table transaction (transaction_id bigint not null auto_increment, description varchar(255), modification_date datetime, movement_type varchar(255), transaction_amount double precision, transaction_type varchar(255), recipient_id bigint, sender_id bigint, primary key (transaction_id)) engine=InnoDB;
alter table product add constraint FKPRODUCT_X_CLIENT foreign key (account_creator) references client (client_id);
alter table transaction add constraint FKTRANSACTION_RECIPENT_ID foreign key (recipient_id) references product (product_id);
alter table transaction add constraint FKTRANSACTION_SENDER_ID foreign key (sender_id) references product (product_id);
alter table client add column creation_date date
alter table client add column is_deleted bool
