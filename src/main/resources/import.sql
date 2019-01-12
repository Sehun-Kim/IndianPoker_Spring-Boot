INSERT INTO human_player (id, player_name, password, win_cnt, file_id, extension, original_file_name, created_at) values (1, 'tester1', '1234', 19, 'example', 'jpeg', 'example.jpeg', CURRENT_TIMESTAMP());
INSERT INTO human_player (id, player_name, password, win_cnt, file_id, extension, original_file_name, created_at) values (2, 'tester2', '1234', 3, 'example', 'jpeg', 'example.jpeg', CURRENT_TIMESTAMP());

INSERT INTO indian_poker (id, first_player_id, player_chips_size, preemptive, game_status, created_at) values (1, 1, 20, true, 'WAITS_FOR_PLAYER', CURRENT_TIMESTAMP());
