insert into restaurant (restaurant_id, mean_time_to_prepare_food_in_seconds) values (1, 100);

insert into delivery (delivery_id, restaurant_id, customer_type, delivery_status, current_distance_from_destination_in_metres,
                      expected_delivery_time, time_to_reach_destination_in_seconds, created_at)
values (1, 1, 'VIP', 'DELIVERED', 100, CURRENT_TIMESTAMP(), 100, CURRENT_TIMESTAMP);

insert into ticket (ticket_id, delivery_id, reason_type, ticket_priority)
values (1, 1, 'Dummy Reason 1', 'HIGH');

insert into ticket (ticket_id, delivery_id, reason_type, ticket_priority)
values (2, 1, 'Dummy Reason 2', 'MEDIUM');

insert into ticket (ticket_id, delivery_id, reason_type, ticket_priority)
values (3, 1, 'Dummy Reason 3', 'LOW');

insert into ticket (ticket_id, delivery_id, reason_type, ticket_priority)
values (4, 1, 'Dummy Reason 4', 'HIGH');