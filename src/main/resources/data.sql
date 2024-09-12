INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 1, 'INICIAL', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 2, 'INICIAL', 'TARDE');

INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 1, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 2, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 3, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 4, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 5, 'PRIMARIA', 'TARDE');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 6, 'PRIMARIA', 'TARDE');

INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 1, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 2, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 3, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 4, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 5, 'SECUNDARIA', 'MAÑANA');
INSERT INTO cursos (id_curso, orden, nivel, turno) VALUES (UUID_TO_BIN(UUID()), 6, 'SECUNDARIA', 'MAÑANA');


INSERT INTO talleres (id_taller, descripcion, nivel) VALUES (UUID_TO_BIN(UUID()), "INGLÉS - PRIMARIA", 'PRIMARIA');
INSERT INTO talleres (id_taller, descripcion, nivel) VALUES (UUID_TO_BIN(UUID()), "INGLÉS - SECUNDARIA", 'SECUNDARIA');
INSERT INTO talleres (id_taller, descripcion, nivel) VALUES (UUID_TO_BIN(UUID()), "MÚSICA - PRIMARIA", 'PRIMARIA');


INSERT INTO roles (id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, authority) VALUES (2, 'ROLE_DIRECTOR');
INSERT INTO roles (id, authority) VALUES (3, 'ROLE_PROFESOR');
INSERT INTO roles (id, authority) VALUES (4, 'ROLE_PRECEPTOR');
INSERT INTO roles (id, authority) VALUES (5, 'ROLE_TUTOR');
INSERT INTO roles (id, authority) VALUES (6, 'ROLE_ALUMNO');
INSERT INTO roles (id, authority) VALUES (7, 'ROLE_PADRE');

insert into personas (id_persona, nombre) values (0x8E4FFF536C6611EF9A0E503EAAE34948, 'Agus');
insert into users (enabled, id_persona, username, password) values (0x01, 0x8E4FFF536C6611EF9A0E503EAAE34948, '123', '123');
insert into user_roles (role_id, user_id) values (1, 0x8E4FFF536C6611EF9A0E503EAAE34948)
