INSERT INTO issues (title, description, date, votes) VALUES
('issue 1', 'bug', CURRENT_TIMESTAMP, 100),
('issue 2', 'another bug', CURRENT_TIMESTAMP, 95),
('issue 3', 'one more bug', CURRENT_TIMESTAMP, 170);

INSERT INTO labels (title) VALUES
('spring'),
('it-park'),
('homework'),
('backend'),
('mvc');

INSERT INTO issues_labels (issue_id, label_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5);