docker exec -it postgres-db psql -U postgres -d mydb -c \
  "INSERT INTO games (name, release_date, current_game_version, updated_at) \
   VALUES ('Minecraft', '2011-11-18', '1.26', NOW());"
