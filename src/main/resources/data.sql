
INSERT INTO users (username, password, email, subscribed_to_mailing) VALUES ('author1', 'password1', 'author1@email.com', false),
                                                                            ('author2', 'password2', 'author2@email.com', true),
                                                                            ('author3', 'password3', 'author3@email.com', false),
                                                                            ('editor1', 'password4', 'editor1@email.com', false),
                                                                            ('reader1', 'password5', 'reader1@email.com', false);

INSERT INTO themes (id, name, description, open_date, closing_date) VALUES (1001, 'Sci-fi', '(CLOSED) Science Fiction stories', '2023-05-01', '2023-05-30'),
                                                                           (1002, 'Fantasy', '(CLOSED) Fantasy stories', '2024-06-01', '2024-06-30'),
                                                                           (1003, 'Humor', '(OPEN) Humor stories', '2024-07-01', '2024-12-30');

INSERT INTO mailings (id, subject, body, date) VALUES (1001, 'Fantastic fantasy!', 'Hi everyone! Last month the theme to write about was Fantasy...', '2024-06-05'),
                                                      (1002, 'Summer vacation!', 'Hi everyone, this month we have the summer holidays...', '2024-07-01');

INSERT INTO profiles (username, firstname, lastname, bio, dob) VALUES ('author1', 'John', 'Doe', 'Been writing since the good old days', '1989-02-04'),
                                                                      ('author2', 'Jane', 'Smith', 'Loves writing', '1982-06-05');

INSERT INTO stories (id, title, content, status, publish_date, author_id, theme_id) VALUES (1001, 'A Fantasy adventure', 'There once was...', 'PUBLISHED', '2024-07-21', 'author1', 1002),
                                                                                            (1002, 'Having a laugh', 'Two guys walk into a bar...', 'SUBMITTED', NULL, 'author2', 1003),
                                                                                           (1003, 'Sci-fi-fun', 'When the air runs out...', 'PUBLISHED', '2024-06-01', 'author1', 1001);

INSERT INTO comments (id, content, created, story_id, user_id) VALUES (1001, 'Lovely story!!', '2024-07-14 12:30:10', 1001, 'reader1'),
                                                                      (1002, 'Great to read!', '2024-07-18 13:20:33', 1001, 'author2');