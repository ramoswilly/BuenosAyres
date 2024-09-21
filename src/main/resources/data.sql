-- Ajustes para PostgreSQL:

-- Asumiendo que id_curso, id_taller e id_persona son de tipo UUID
-- y que user_id en user_roles también es de tipo UUID

CREATE EXTENSION IF NOT EXISTS "uuid-ossp"; -- Asegúrate de que la extensión uuid-ossp esté habilitada


INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 1, 'INICIAL', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 2, 'INICIAL', 'TARDE');

INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 1, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 2, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 3, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 4, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 5, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 6, 'PRIMARIA', 'TARDE');

INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 1, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 2, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 3, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 4, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 5, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (uuid_generate_v4(), 6, 'SECUNDARIA', 'MAÑANA');


INSERT INTO talleres (id_taller, descripcion, nivel) VALUES (uuid_generate_v4(), "INGLÉS - PRIMARIA", 'PRIMARIA');
INSERT INTO talleres (id_taller, descripcion, nivel) VALUES (uuid_generate_v4(), "INGLÉS - SECUNDARIA", 'SECUNDARIA');
INSERT INTO talleres (id_taller, descripcion, nivel) VALUES (uuid_generate_v4(), "MÚSICA - PRIMARIA", 'PRIMARIA');


INSERT INTO roles (id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, authority) VALUES (2, 'ROLE_DIRECTOR');
INSERT INTO roles (id, authority) VALUES (3, 'ROLE_PROFESOR');
INSERT INTO roles (id, authority) VALUES (4, 'ROLE_PRECEPTOR');
INSERT INTO roles (id, authority) VALUES (5, 'ROLE_TUTOR');
INSERT INTO roles (id, authority) VALUES (6, 'ROLE_ALUMNO');
INSERT INTO roles (id, authority) VALUES (7, 'ROLE_PADRE');

-- Convertir los valores hexadecimales a UUID
insert into personas (id_persona, nombre) values ('8e4fff53-6c66-11ef-9a0e-503eaae34948', 'Agus');
insert into users (enabled, id_persona, username, password) values (true, '8e4fff53-6c66-11ef-9a0e-503eaae34948', 'admin', 'guillermo31');
insert into user_roles (role_id, user_id) values (1, '8e4fff53-6c66-11ef-9a0e-503eaae34948');

