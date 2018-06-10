create table if not exists  public.exercise (
	id serial primary key,
	"name" varchar not null,
	"type" varchar null,
	difficulty varchar not null,
	description varchar not null,
    constraint exercise_un unique (id)
)

create table if not exists public.goal (
	id serial primary key,
	"type" varchar not null,
	current_fat varchar not null,
	goal_fat varchar null,
	frecuency_exercise varchar null,
	constraint goal_un unique (id)
)

create table if not exists public.nutrition (
	id serial primary key,
	"name" varchar not null,
	"type" varchar not null,
	calories float8 not null,
	constraint nutrition_un unique (id)
)

create table if not exists public."routine" (
	id serial primary key,
	"name" varchar null,
	description varchar null,
	constraint routine_un unique (id)
)

create table if not exists public.scoring (
	id serial primary key,
	scoring varchar not null,
	tip varchar null,
	constraint scoring_un unique (id)
)

create table if not exists public.routine_template (
	id serial primary key,
	id_routine int8 not null,
	id_exercise int8 not null,
	id_nutrition int8 not null,
	constraint routine_template_un unique (id),
	constraint routine_template_nutrition_fk foreign key (id_nutrition) references public.nutrition(id),
	constraint routine_template_routine_fk foreign key (id_routine) references public."routine"(id),
	constraint routine_template_exercise_fk foreign key (id_exercise) references public.exercise(id)
)

create table if not exists public.user_info (
	id serial primary key,
	"name" varchar not null,
	email varchar not null,
	weight float8 not null,
	height float8 not null,
	genre varchar not null,
	id_goal int8 not null,
	constraint user_info_un unique (id),
	constraint user_info_goal_fk foreign key (id_goal) references public.goal(id)
)

create table if not exists public."user" (
	id serial primary key,
	id_user_info int8 not null,
	id_session varchar null,
	constraint user_un unique (id, id_user_info),
	constraint user_user_info_fk foreign key (id_user_info) references public.user_info(id)
)

create table if not exists  public.user_routine (
	id serial primary key,
	id_routine int8 not null,
	id_user int8 not null,
	id_scoring int8 not null,
	constraint user_routine_un unique (id),
	constraint user_routine_routine_fk foreign key (id) references public."routine"(id),
	constraint user_routine_scoring_fk foreign key (id_scoring) references public.scoring(id),
	constraint user_routine_user_fk foreign key (id) references public."user"(id)
)

insert into public.exercise("name", "type", difficulty, description)
values
('Círculos de piernas hacia fuera', 'leg', 'easy', '1. Colócate de pie, estirado, y con los pies separados a la anchura de la cadera. \n2. Desplaza el peso a una pierna mientras elevas la rodilla de la pierna contraria a la altura de la cadera.\n 3. Con la rodilla, traza un circulo en el sentido de las agujas del reloj. \n4. Regresa a la posición inicial.\nConsejos:\n\tMantén una buena postura: cuello estirado, hombros hacia atrás y espalda recta.\n\tControla el movimiento durante todo el ejercicio.\n\tMantén los abdominales contraídos.'),
('Círculos de piernas hacia dentro', 'leg', 'easy', '1. Colócate de pie, estirado, y con los pies separados a la anchura de la cadera. \n2. Desplaza el peso a una pierna mientras elevas la rodilla de la pierna contraria a la altura de la cadera. \n3. Con la rodilla, traza un circulo en el sentido contrario de las agujas del reloj. \n4. Repite el movimiento durante el tiempo/repeticiones indicado. \n5. Regresa a la posición inicial. \nConsejos:\n\tMantén una buena postura: cuello estirado, hombros hacia atrás y espalda recta.\n\tControla el movimiento durante todo el ejercicio.\n\tMantén los abdominales contraídos.');
