
INSERT INTO users (username, password, email, subscribed_to_mailing) VALUES ('author1', 'password1', 'author1@email.com', true),
                                                                            ('author2', 'password2', 'author2@email.com', true),
                                                                            ('editor1', 'password3', 'editor1@email.com', true),
                                                                            ('reader1', 'password4', 'reader1@email.com', true);

INSERT INTO themes (id, name, description, open_date, closing_date) VALUES (1001, 'Sci-fi', '(CLOSED) Science Fiction stories', '2023-05-01', '2023-05-30'),
                                                                           (1002, 'Fantasy', '(CLOSED) Fantasy stories', '2024-06-01', '2024-06-30'),
                                                                           (1003, 'Humor', '(OPEN) Humor stories', '2023-07-01', '2023-08-30');

INSERT INTO mailings (id, subject, body, date) VALUES (1001, 'Sci-fi', 'This month the theme to submit to was Fantasy,\n\n You''ve all been very enthusiastic in submitting your stories.\n\nThe following story was regarded very highly, by John Doe: ''There once was...''', '2024-07-20');


INSERT INTO profiles (id, firstname, lastname, bio, dob, username) VALUES ('1001', 'John', 'Doe', 'Been writing since the good old days', '1989-02-04', 'author1'),
                                                                      ('1002', 'Jane', 'Smith', 'Loves writing', '1982-06-05', 'author2');

INSERT INTO stories (id, title, content, status, publish_date, author_profile_username, theme_id) VALUES (1001, 'A Fantasy adventure', 'There once was...', 'PUBLISHED', '2024-07-21', 'author1', 1002),
                                                                                                                (1002, 'Having a laugh', 'Two guys walk into a bar...', 'SUBMITTED', NULL, 'author2', 1003);




INSERT INTO comments (id, content, created, story_id, user_id) VALUES (1001, 'Lovely story!!', '2024-07-14 12:30:10', 1001, 'reader1'),
                                                                      (1002, 'Great to read!', '2024-07-18 13:20:33', 1001, 'author2');