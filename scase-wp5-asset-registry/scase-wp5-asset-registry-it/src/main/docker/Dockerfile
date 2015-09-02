FROM mysql
ADD create-user.sql /tmp/create-user.sql
RUN MYSQL_ROOT_PASSWORD=bb bash -c "/entrypoint.sh mysqld &" && \
  sleep 15 && \
  mysql -u root -pbb < /tmp/create-user.sql
