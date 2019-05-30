INSERT INTO issues (title, description, date, votes) VALUES
('issue 1', 'bug', CURRENT_TIMESTAMP, 100),
('issue 2', 'another bug', CURRENT_TIMESTAMP, 95),
('issue 3', 'one more problem', CURRENT_TIMESTAMP, 170),
('issue 4', 'something strange', CURRENT_TIMESTAMP, 200),
('issue 5', 'what the hell is this?!', CURRENT_TIMESTAMP, 120);

INSERT INTO labels (title) VALUES
('spring'),
('it-park'),
('homework'),
('back-end'),
('front-end'),
('rest'),
('mvc');


INSERT INTO issues_labels (issue_id, label_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(2, 1),
(2, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(4, 2),
(4, 3),
(4, 5),
(4, 6),
(4, 7),
(5, 1),
(5, 6);