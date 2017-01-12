CREATE TABLE public.projects (
    id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(255) NOT NULL,
    description TEXT
);

CREATE TABLE public.project_user (
    project_id INTEGER NOT NULL,
    user_id CHARACTER VARYING(255) NOT NULL,
    PRIMARY KEY (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES public.projects (id)
    MATCH FULL ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES public.users (id)
    MATCH FULL ON UPDATE NO ACTION ON DELETE CASCADE
);