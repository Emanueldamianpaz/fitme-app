
create table exercise
(
	id serial not null
		constraint exercise_un
			primary key,
	name varchar not null,
	type varchar,
	difficulty varchar not null,
	description varchar not null
);

create table goal
(
	id serial not null
		constraint goal_un
			primary key,
	type varchar not null,
	current_fat varchar not null,
	goal_fat varchar,
	frecuency_exercise varchar
);

create table nutrition
(
	id serial not null
		constraint nutrition_un
			primary key,
	name varchar not null,
	type varchar not null,
	calories double precision not null,
	image bytea
);

create table routine
(
	id serial not null
		constraint routine_un
			primary key,
	name varchar,
	description varchar
);

create table scoring
(
	id serial not null
		constraint scoring_un
			primary key,
	scoring varchar not null,
	tip varchar
);

create table routine_template
(
	id serial not null
		constraint routine_template_un
			primary key,
	id_routine bigint not null
		constraint routine_template_routine_fk
			references routine,
	id_exercise bigint not null
		constraint routine_template_exercise_fk
			references exercise,
	id_nutrition bigint not null
		constraint routine_template_nutrition_fk
			references nutrition
);

create table user_info
(
	id serial not null
		constraint user_info_un
			primary key,
	name varchar not null,
	email varchar not null,
	weight double precision not null,
	height double precision not null,
	genre varchar not null,
	id_goal bigint not null
		constraint user_info_goal_fk
			references goal
);

create table "user"
(
	id serial not null
		constraint user_pkey
			primary key,
	id_user_info bigint not null
		constraint user_user_info_fk
			references user_info,
	id_session varchar,
	constraint user_un
		unique (id, id_user_info)
);

create table user_routine
(
	id serial not null
		constraint user_routine_un
			primary key,
	id_routine bigint not null
		constraint user_routine_routine_fk
			references routine,
	id_user bigint not null
		constraint user_routine_user_fk
			references "user",
	id_scoring bigint not null
		constraint user_routine_scoring_fk
			references scoring
);

insert into exercise("name", "type", difficulty, description)
values
('Círculos de piernas hacia fuera', 'leg', 'easy', '1. Colócate de pie, estirado, y con los pies separados a la anchura de la cadera. \n2. Desplaza el peso a una pierna mientras elevas la rodilla de la pierna contraria a la altura de la cadera.\n 3. Con la rodilla, traza un circulo en el sentido de las agujas del reloj. \n4. Regresa a la posición inicial.\nConsejos:\n\tMantén una buena postura: cuello estirado, hombros hacia atrás y espalda recta.\n\tControla el movimiento durante todo el ejercicio.\n\tMantén los abdominales contraídos.'),
('Círculos de piernas hacia dentro', 'leg', 'easy', '1. Colócate de pie, estirado, y con los pies separados a la anchura de la cadera. \n2. Desplaza el peso a una pierna mientras elevas la rodilla de la pierna contraria a la altura de la cadera. \n3. Con la rodilla, traza un circulo en el sentido contrario de las agujas del reloj. \n4. Repite el movimiento durante el tiempo/repeticiones indicado. \n5. Regresa a la posición inicial. \nConsejos:\n\tMantén una buena postura: cuello estirado, hombros hacia atrás y espalda recta.\n\tControla el movimiento durante todo el ejercicio.\n\tMantén los abdominales contraídos.');
