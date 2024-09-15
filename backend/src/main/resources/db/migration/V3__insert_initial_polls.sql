INSERT INTO polls (title)
VALUES ('What is your favorite drink?'),
       ('Is this a cool question?');

INSERT INTO polls_options (poll_id, title, votes)
VALUES
    (1, 'Water', 0),
    (1, 'Tea', 0),
    (1, 'Coffee', 0);

INSERT INTO polls_options (poll_id, title, votes)
VALUES
    (2, 'Yes', 0),
    (2, 'No', 0),
    (2, 'Cool, another option', 0);
