CREATE TABLE public.users (
  id CHARACTER VARYING(255) PRIMARY KEY NOT NULL,
  username CHARACTER VARYING(255) NOT NULL,
  email CHARACTER VARYING(255) NOT NULL,
  password CHARACTER VARYING(60),
  phone CHARACTER VARYING(20),
  full_name CHARACTER VARYING(255)
);
CREATE UNIQUE INDEX users_username_uindex ON users USING BTREE (username);
CREATE UNIQUE INDEX users_email_uindex ON users USING BTREE (email);

CREATE SEQUENCE roles_id_seq;

CREATE TABLE public.roles (
  id INTEGER PRIMARY KEY NOT NULL DEFAULT nextval('roles_id_seq'::regclass),
  name CHARACTER VARYING(255) NOT NULL
);

CREATE TABLE public.role_user (
  role_id INTEGER NOT NULL,
  user_id CHARACTER VARYING(255) NOT NULL,
  PRIMARY KEY (role_id, user_id),
  FOREIGN KEY (role_id) REFERENCES public.roles (id)
  MATCH FULL ON UPDATE NO ACTION ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES public.users (id)
  MATCH FULL ON UPDATE NO ACTION ON DELETE CASCADE
);