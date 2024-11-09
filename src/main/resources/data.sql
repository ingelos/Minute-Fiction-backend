
INSERT INTO users (username, password, email, subscribed_to_mailing) VALUES ('author', '$2a$12$U5R7czHv7nscAY3bWk7ia.Dia4jN1I504ttY6JldJ4Mqmj9l51LeS', 'author@email.com', false),
                                                                            ('editor', '$2a$12$EwjsTV82flQyESYAE9xvYupRJ836UIxs/YVK3UU9I.OIv4kwevW3e', 'editor@email.com', false),
                                                                            ('reader', '$2a$12$sPp2nsBmhFSmitO21Kn6deBvqdChHOaPz9wd4uRMkcpSpmjZ0asFe', 'reader@email.com', true),
                                                                            ('author2', '$2a$12$SLgZ/.pM2/3jdgiu8iWAU.40aQdbXTx.STtJVfCc7wlcqtyQau08u', 'author2@email.com', true),
                                                                            ('author3', '$2a$12$D7Vj6SKMCOJqX1TezeasauaQFOpnS9k8w1EWKsOp3a.2fCxQEwoC6', 'author3@email.com', false);

INSERT INTO themes (id, name, description, open_date, closing_date) VALUES (1001, 'Sci-fi', 'Science Fiction, stories about a galaxy far far away, or on this planet.', '2023-05-01', '2023-05-30'),
                                                                           (1002, 'Fantasy', 'We all love ourselves a bit of fantasy.' , '2024-06-01', '2024-06-30'),
                                                                           (1003, 'Humor', 'As funny as you can make it!', '2024-07-01', '2024-12-30'),
                                                                            (1004, 'Fall', 'The leaves are falling...', '2024-10-01', '2024-12-30');

INSERT INTO mailings (id, subject, body, send_date) VALUES (1001, 'Fantastic fantasy!', 'Hi everyone! Last month the theme to write about was Fantasy...', '2024-06-05'),
                                                      (1002, 'Summer vacation!', 'Hi everyone, this month we have the summer holidays...', '2024-07-01');

INSERT INTO profiles (username, firstname, lastname, bio, dob) VALUES ('author', 'John', 'Doe', 'Been writing since the good old days', '1989-02-04'),
                                                                      ('author2', 'Jane', 'Smith', 'Loves writing', '1982-06-05'),
                                                                        ('author3', 'Michael', 'Smith', 'Love writing since I could write', '1999-03-12');

INSERT INTO stories (id, title, content, status, publish_date, author_id, theme_id) VALUES (1001, 'Sci-fi-fun', 'When the air runs out... Lorem ipsum odor amet, consectetuer adipiscing elit. ' ||
                                                                                                                'Himenaeos tristique mattis et ac quam pretium lectus at. Quis lobortis quis urna donec cras. ' ||
                                                                                                                'Habitasse lacus neque ultrices sagittis mollis eget, erat parturient turpis. Fermentum justo enim ' ||
                                                                                                                'netus placerat leo vestibulum, quis in. Eget tortor porta maecenas tellus taciti dis. Fusce gravida ' ||
                                                                                                                'sit fringilla curabitur facilisi natoque aliquam ridiculus. Primis cursus metus conubia libero aptent et ' ||
                                                                                                                'himenaeos euismod platea. Libero nunc cursus aliquet sollicitudin curae mus; maximus posuere.',
                                                                                                            'PUBLISHED', '2024-06-01', 'author', 1001),
                                                                                           (1002, 'What was there?', 'Before the stars were there...', 'DECLINED', NULL, 'author2', 1001),
                                                                                           (1003, 'A Fantasy adventure', 'There once was... Lorem ipsum odor amet, consectetuer adipiscing elit. ' ||
                                                                                                                'Himenaeos tristique mattis et ac quam pretium lectus at. Quis lobortis quis urna donec cras. ' ||
                                                                                                                'Habitasse lacus neque ultrices sagittis mollis eget, erat parturient turpis. Fermentum justo enim  '||
                                                                                                                'netus placerat leo vestibulum, quis in. Eget tortor porta maecenas tellus taciti dis. Fusce gravida ' ||
                                                                                                                'sit fringilla curabitur facilisi natoque aliquam ridiculus. Primis cursus metus conubia libero aptent et ' ||
                                                                                                                'himenaeos euismod platea. Libero nunc cursus aliquet sollicitudin curae mus; maximus posuere.',
                                                                                                            'PUBLISHED', '2024-07-21', 'author', 1002),
                                                                                           (1004, 'Fantastic fantasy!', 'A dog and a cat... Lorem ipsum odor amet, consectetuer adipiscing elit. ' ||
                                                                                                                'Himenaeos tristique mattis et ac quam pretium lectus at. Quis lobortis quis urna donec cras. ' ||
                                                                                                                'Habitasse lacus neque ultrices sagittis mollis eget, erat parturient turpis. Fermentum justo enim ' ||
                                                                                                                'netus placerat leo vestibulum, quis in. Eget tortor porta maecenas tellus taciti dis. Fusce gravida ' ||
                                                                                                                'sit fringilla curabitur facilisi natoque aliquam ridiculus. Primis cursus metus conubia libero aptent et ' ||
                                                                                                                'himenaeos euismod platea. Libero nunc cursus aliquet sollicitudin curae mus; maximus posuere.',
                                                                                                            'PUBLISHED', '2024-07-21', 'author3', 1002),
                                                                                            (1005, 'Having a laugh', 'Two guys walk into a bar...', 'SUBMITTED', NULL, 'author', 1003),
                                                                                           (1006, 'Another one', 'This is the story of...', 'DECLINED', NULL, 'author2', 1003),
                                                                                           (1007, 'Just some funny things', 'There was a group of...', 'ACCEPTED', NULL, 'author3', 1003),
                                                                                           (1008, 'Here we go again', 'Another story that is supposed to be funny', 'ACCEPTED', NULL, 'author2', 1003);


INSERT INTO comments (id, content, created, story_id, user_id) VALUES (1001, 'Lovely story!!', '2024-07-14 12:30:10', 1001, 'reader'),
                                                                      (1002, 'Great to read!', '2024-07-18 13:20:33', 1001, 'author2'),
                                                                        (1003, 'Absolutely wonderful!', '2024-08-29 12:03:22', 1003, 'reader');


INSERT INTO authorities (authority) VALUES ('READER'), ('AUTHOR'), ('EDITOR');

INSERT INTO users_authorities (username, authority) VALUES ('author', 'READER');
INSERT INTO users_authorities (username, authority) VALUES ('author', 'AUTHOR');
INSERT INTO users_authorities (username, authority) VALUES ('author2', 'READER');
INSERT INTO users_authorities (username, authority) VALUES ('author2', 'AUTHOR');
INSERT INTO users_authorities (username, authority) VALUES ('editor', 'READER');
INSERT INTO users_authorities (username, authority) VALUES ('editor', 'EDITOR');
INSERT INTO users_authorities (username, authority) VALUES ('reader', 'READER');
INSERT INTO users_authorities (username, authority) VALUES ('author3', 'AUTHOR');
INSERT INTO users_authorities (username, authority) VALUES ('author3', 'READER');




