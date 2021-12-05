create table user_info(
                          user_id integer unsigned not null auto_increment,
                          user_name varchar(64) not null unique,
                          password varchar(64) not null,
                          primary key(user_id)
);

create table restaurant(
                           restaurant_id integer not null auto_increment,
                           mean_time_to_prepare_food_in_seconds integer not null,
                           primary key (restaurant_id)
);

create table delivery(
                         delivery_id integer unsigned not null auto_increment,
                         restaurant_id integer not null,
                         customer_type enum('VIP','LOYAL','NEW') not null,
                         delivery_status enum('RECEIVED', 'PREPARING', 'PICKED_UP', 'DELIVERED') not null,
                         current_distance_from_destination_in_metres integer not null,
                         expected_delivery_time timestamp not null,
                         time_to_reach_destination_in_seconds integer not null,
                         created_at timestamp not null,
                         foreign key (restaurant_id) references restaurant(restaurant_id),
                         primary key (delivery_id)
);

create table ticket(
                       ticket_id integer unsigned not null auto_increment,
                       reason_type varchar(100) not null,
                       delivery_id integer unsigned not null,
                       ticket_priority enum('HIGH', 'MEDIUM', 'LOW') not null,
                       foreign key (delivery_id) references delivery(delivery_id),
                       primary key (ticket_id),
                       unique key delivery_reason (delivery_id, reason_type)
);

insert into user_info (user_id, user_name, password) values (1, 'murtaza', '$2a$10$NObUlRIbtukiZqWzb94fXOgFRHQeJNeek1pe6eAm6sOYqHbtDSHw2');