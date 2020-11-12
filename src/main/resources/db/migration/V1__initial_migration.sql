Create table if not exists SIMULATION_SET (
    id serial not null,
    number_of_dice int not null,
    number_of_rolls int not null,
    number_of_sides int not null,
    primary key(id)
);

Create table if not exists SIMULATION_DETAILS (
    id serial not null,
    set_id int not null,
    total int not null,
    count int not null,
    primary key (id),
    foreign key (set_id) references simulation_set (id)

);

