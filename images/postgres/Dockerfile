FROM postgres:9.6

MAINTAINER David Bohn

RUN localedef -i de_DE -c -f UTF-8 -A /usr/share/locale/locale.alias de_DE.UTF-8
ENV LANG de_DE.utf8

ADD migrations/*.sql /docker-entrypoint-initdb.d/