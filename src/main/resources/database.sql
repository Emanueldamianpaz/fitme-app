INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(1, 1000.0, '1 kiwi/café con leche desnatada/tostada de pan integral con aceite de oliva.', 'Desayuno');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(3, 2000.0, '1 Judías verde con patatas/Pechugas de pollo al horno con champiñones/1 yogur desnatado natural/1 manzanilla.', 'Comida');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(4, 200.0, '1 manzana.', 'Colación');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(2, 200.0, '1 zumo de naranja.', 'Colación');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(5, 800.0, 'Caldo de verduras. Revuelto de tortilla y ajetes. Un yogur desnatado con fruta.', 'Cena');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(6, 900.0, 'Un zumo de piña. Bol de leche desnatada con muesli y fresas troceadas.', 'Desayuno');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(7, 200.0, 'Un café con leche.', 'Colación');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(8, 800.0, 'Lentejas con verduras. Filetes de perca a la plancha. Dos mandarinas.', 'Comida');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(9, 400.0, 'Un puñado de nueces y un zumo de manzana.', 'Colación');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(10, 600.0, 'Filete de pavo con ensalada verde. Yogur griego ligero.', 'Cena');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(11, 900.0, 'Una rodaja de melón. Té con leche y una tostada de pan de molde integral con mermelada ligera de naranja.', 'Desayuno');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(12, 200.0, 'Un zumo de melocotón.', 'Colación');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(13, 2000.0, 'Brócoli con refrito de ajos. Filete de ternera con ensalada de canónigos. Yogur desnatado de sabor.', 'Comida');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(14, 150.0, 'Un bol de cerezas.', 'Colación');
INSERT INTO public.nutrition
(id, calories, "name", "type")
VALUES(15, 500.0, 'Ensalada de tomate y mozarella fresca con orégano. Un huevo cocido. Una cuajada.', 'Cena');


INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(1, 'Equipo necesario: calzado para caminar, ropa cómoda', 'Facil', 'Caminar', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(2, 'Equipo necesario: calzado para correr, pantalones cómodos, cortos o para correr, camiseta cómoda, sostén deportivo', 'Facil', 'Correr y trotar', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(3, 'Equipo necesario: bicicleta, ropa cómoda, casco', 'Intermedio', 'Andar en bicicleta', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(4, 'Equipo necesario: máquina elíptica o escalador, ropa cómoda', 'Intermedio', 'Máquina elíptica* o escalador', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(5, 'Equipo necesario: ropa cómoda; para algunas danzas (como el flamenco o el ballet) se necesitan zapatos especiales', 'Facil', 'Danza (zumba, danza del vientre, flamenco, tap, ballet, square dancing –baile de cuatro parejas que forman un cuadrado–, bailes de salón –en pareja–, etc.)', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(6, 'Equipo necesario: esquíes, bastones, botas, casco, gorra, gafas de esquí, chaquetas, pantalones o pecheras aislantes, guantes y calcetines', 'Dificil', 'Esquí', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(7, 'Equipo necesario: patines para hielo, con ruedas o rollers, calcetines, ropa cómoda, protectores de muñecas y codos, casco', 'Medio', 'Patinaje', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(8, 'Equipo necesario: raqueta, pelotas, ropa cómoda, calzado deportivo de tenis', 'Medio', 'Tenis*', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(9, 'Equipo necesario: traje de baño y toalla; en algunas piscinas se exige usar gorra y gafas para el agua', 'Medio', 'Natación', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(10, 'Equipo necesario: ropa cómoda, calzado deportivo', 'Facil', 'Ejercicios aeróbicos', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(11, 'Equipo necesario: ropa cómoda, bote y remos, si haces remo al aire libre; máquina de remar, si lo haces en un lugar bajo techo', 'Dificil', 'Remo*', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(12, 'Equipo necesario: botas o zapatos para senderismo, calcetines, ropa cómoda, adecuada al clima, botellas de agua; gorra, mochila, gafas para sol, repelente de insectos y un kit de primeros auxilios; los palos para senderismo son opcionales', 'Dificil', 'Senderismo', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(13, 'Equipo necesario: calzado deportivo de baloncesto, ropa cómoda, pelota de baloncesto, aro', 'Medio', 'Baloncesto', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(14, 'Equipo necesario: palos, bolsa, zapatos, pelotas, tees (soportes para pelotas), ropa cómoda (en algunos campos de golf se pueden alquilar los palos y la bolsa)', 'Medio', 'Golf', 'Aerobico');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(15, 'Equipo necesario: en realidad, nada; se puede usar una toalla o un cinturón para profundizar algunos estiramientos sosteniendo la toalla con ambas manos y esforzándote con cuidado para lograr el estiramiento)', 'Facil', 'Estiramiento', 'Flexibilidad');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(16, 'Equipo necesario: colchoneta de yoga, ropa cómoda', 'Facil', 'Yoga', 'Flexibilidad');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(17, 'Equipo necesario: ropa cómoda', 'Medio', 'Taichí (antiguo arte chino de movimientos suaves)', 'Flexibilidad');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(18, 'Equipo necesario: cilindro de goma espuma o bola de lacrosse', 'Facil', 'Hacer rodar un cilindro de goma espuma o una bola de lacrosse por los músculos', 'Flexibilidad');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(19, 'Equipo necesario: colchoneta, ropa cómoda; aparatos que se pueden usar en un centro de Pilates, pero los ejercicios sobre colchoneta sola ofrecen una buena ejercitación para lograr flexibilidad', 'Facil', 'Pilates', 'Flexibilidad');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(20, 'Equipo necesario: barras y discos, mancuernas, pesas rusas, máquinas de fuerza, ropa cómoda', 'Medio', 'Levantamiento de pesas', 'Fuerza');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(21, 'Equipo necesario: bandas elásticas de resistencia o para ejercicios, ropa cómoda', 'Medio', 'Ejercicios con bandas elásticas de resistencia', 'Fuerza');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(22, 'Equipo necesario: pesas rusas, ropa cómoda', 'Medio', 'Ejercicios con pesas rusas', 'Fuerza');
INSERT INTO public.exercise
(id, description, difficulty, "name", "type")
VALUES(23, 'Equipo necesario: ropa cómoda', 'Medio', 'Ejercicios con el peso corporal (lagartijas o push-ups, flexiones de brazos en barra horizontal o pull-ups, hacer el pino o la vertical, etc.)', 'Fuerza');
