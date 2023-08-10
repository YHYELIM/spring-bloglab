insert into user_tb(username, enc_password, email) values('ssar', '$2a$10$FGzKs0vX0Cie/SKIDidSBeMs3QFaEvXqUYjBSUtxD42x3cK8bvtuK', 'ssar@nate.com');
insert into user_tb(username, enc_password, email) values('cos',  '$2a$10$JzY5duSlgj/NN2Du1YNuTuS5gtzZgutrjgTE21mpjry3yDpU/oa3K','cos@nate.com');
insert into board_tb(title, content, user_id, created_at) values('제목2', '내용1', 1, now());
insert into board_tb(title, content, user_id, created_at) values('22', '내용2', 1, now());
insert into board_tb(title, content, user_id, created_at) values('222', '내용3', 1, now());
insert into board_tb(title, content, user_id, created_at) values('2222', '내용4', 2, now());
insert into board_tb(title, content, user_id, created_at) values('제목5', '내용5', 2, now());
insert into reply_tb(board_id, user_id, comment)values(1, 1, '댓글1');
insert into reply_tb(board_id, user_id, comment)values(2, 2, '댓글2');
