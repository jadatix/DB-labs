docker exec -it postgres-db psql -U postgres -d mydb -c \
  "UPDATE games SET current_game_version = '3.33', updated_at = NOW() WHERE id = 2;"
