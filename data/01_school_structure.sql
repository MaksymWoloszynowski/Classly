-- TEACHERS
INSERT INTO teachers (id, first_name, last_name) VALUES ('0fa59827-97ad-489f-90ee-76347c3d601a', 'Jakub', 'Informatyczny'); --informatyka
INSERT INTO teachers (id, first_name, last_name) VALUES ('c9877021-c858-470d-87c5-680cb68cc1f4', 'Mikolaj', 'Polski'); --polski I,III
INSERT INTO teachers (id, first_name, last_name) VALUES ('a2a47bfe-ddaa-435a-ba5d-21ea8216b83e', 'Olga', 'Polska'); --polski II,IV
INSERT INTO teachers (id, first_name, last_name) VALUES ('486f7c91-f074-4b10-8972-7cb9bbd33f03', 'Marcel', 'Biologiczny'); --biologia, chemia
INSERT INTO teachers (id, first_name, last_name) VALUES ('b370c6e3-4c26-4303-8346-022e48d56894', 'Franciszek', 'Fizyczny'); --fizyka
INSERT INTO teachers (id, first_name, last_name) VALUES ('ccf705fb-1971-4248-b6de-0fa58bda4d29', 'Hanna', 'Angielska'); --angielski
INSERT INTO teachers (id, first_name, last_name) VALUES ('64c1b23e-a7a5-48a8-a07a-3572d4689d8f', 'Zofia', 'Kaminski'); --historia
INSERT INTO teachers (id, first_name, last_name) VALUES ('327688e0-8d2d-4821-a889-7aaeb2a06fd1', 'Mikołaj', 'Ziemski'); --niemiecki
INSERT INTO teachers (id, first_name, last_name) VALUES ('b466c6fc-1510-4715-80ea-fd071ae57e8e', 'Janusz', 'Wolski'); --muzyka
INSERT INTO teachers (id, first_name, last_name) VALUES ('e7423a37-4ffa-423f-88dd-d24f3865c285', 'Marcin', 'Jaki'); --wf
INSERT INTO teachers (id, first_name, last_name) VALUES ('5580d5de-8d8c-4296-9ce5-9b3415557f80', 'Angieszka', 'Dobrowolska'); --geografia
INSERT INTO teachers (id, first_name, last_name) VALUES ('b6b90a92-e763-426d-a8b4-b49482d2162a', 'Maria', 'Serska'); --biznes
INSERT INTO teachers (id, first_name, last_name) VALUES ('c4ad6263-4571-4e22-bc15-2d1459b0606f', 'Michał', 'Żak'); --matematyka

-- SUBJECTS
INSERT INTO subjects (id, name) VALUES ('547ce05f-ad3a-4005-8a93-fa6bf6acf1eb', 'Math');
INSERT INTO subjects (id, name) VALUES ('94bc48a1-fb3c-4957-ad2b-d3550a97b48b', 'Polish');
INSERT INTO subjects (id, name) VALUES ('324bbd3d-7182-4966-9cf2-b7868bab6be7', 'English');
INSERT INTO subjects (id, name) VALUES ('6fee94d0-a59a-4ffe-91bb-d001fc1701bf', 'History');
INSERT INTO subjects (id, name) VALUES ('a76693df-f587-4f08-9757-b6475c6565a0', 'Physics');
INSERT INTO subjects (id, name) VALUES ('1d8f3c95-2482-4e2a-a8f9-02cd4fec3a06', 'Chemistry');
INSERT INTO subjects (id, name) VALUES ('b45b85c8-94a1-4298-9f12-da4317f2861d', 'Biology');
INSERT INTO subjects (id, name) VALUES ('fd334bcc-c248-4474-b440-66839e0870de', 'Sports');
INSERT INTO subjects (id, name) VALUES ('00ef24b4-0b1e-4f15-8c2b-5e970f4e0236', 'Computer science');
INSERT INTO subjects (id, name) VALUES ('9e9dad0e-da1f-4ae9-990a-265238f7309d', 'Geography');
INSERT INTO subjects (id, name) VALUES ('c2050a95-8b16-4918-b801-285fc257d795', 'German');
INSERT INTO subjects (id, name) VALUES ('58c3eb74-cff0-48de-af2a-582791bded3b', 'Music');
INSERT INTO subjects (id, name) VALUES ('7d6f02e3-477e-49c2-9585-2040f7a634db', 'Business');

-- SCHOOL GROUPS
INSERT INTO school_groups (id, name, homeroom_teacher_id) VALUES ('2b5d357c-f4dd-4010-aad1-8165ea228a87', '1', 'c9877021-c858-470d-87c5-680cb68cc1f4');
INSERT INTO school_groups (id, name, homeroom_teacher_id) VALUES ('5915a6bd-8f7f-4903-8e69-4cff5722082c', '2', 'c9877021-c858-470d-87c5-680cb68cc1f4');
INSERT INTO school_groups (id, name, homeroom_teacher_id) VALUES ('039d43ec-62fa-4a2f-92bd-0a0ffa733661', '3', 'ccf705fb-1971-4248-b6de-0fa58bda4d29');
INSERT INTO school_groups (id, name, homeroom_teacher_id) VALUES ('594027fa-c540-4509-8c4f-c4f9edfc678d', '4', 'c9877021-c858-470d-87c5-680cb68cc1f4');

-- STUDENTS
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('9690ceb6-418a-46df-bfc3-aae31fb402e7', 'Julia', 'Marczak', '2010-09-04', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('cadb6bdc-91ce-4980-b53c-c1682e26a85c', 'Julia', 'Krawczyk', '2010-09-04', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('2116a816-3046-4e54-8668-b62bcaf67ab7', 'Maja', 'Nowakowski', '2010-05-27', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('7cd52a9f-2bad-4217-8743-25295272589f', 'Wiktoria', 'Lewandowski', '2010-12-03', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('82fb1787-8ab9-4c71-919f-edfd53ff195d', 'Mikolaj', 'Wozniak', '2010-02-28', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('34bc28c3-b82f-469c-950d-8be7c8fd3ac3', 'Alicja', 'Kozlowski', '2010-06-07', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('3f707528-75b0-4139-91e2-25c7b8e37581', 'Maja', 'Michalski', '2010-11-06', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('122fec30-f764-4958-a0e2-f0467e1133c1', 'Szymon', 'Krawczyk', '2010-07-09', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('49105e70-730f-4eea-bbb9-8b9df2eb5c93', 'Adam', 'Nowak', '2010-04-27', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('b27207ec-2dc8-4fe9-b377-22a8c3ac5cb5', 'Adam', 'Jankowski', '2010-05-03', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('8318be11-7b50-414f-8acd-aac8aa1615ba', 'Oskar', 'Dabrowski', '2010-04-21', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('f6824ebd-dd92-4d56-9603-55b526bd8376', 'Kinga', 'Krawczyk', '2010-03-09', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('c120113a-ed43-4e9a-bc62-928488b267c1', 'Mikolaj', 'Nowakowski', '2010-09-09', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('5c024f63-0e1a-4485-b861-878655292057', 'Wiktoria', 'Jankowski', '2010-06-08', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('fded35d6-4dab-4397-aa28-1beab629fed2', 'Maciej', 'Piotrowski', '2010-02-25', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('a90d2ef9-51d7-4d84-8557-e98ed537f1da', 'Filip', 'Kowalczyk', '2010-11-06', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('0c7671e2-b5d6-4da0-8deb-4a1ea903b9e6', 'Agata', 'Wisniewski', '2010-07-13', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('826e4d6a-50cb-4e8a-854a-1e23b57ff876', 'Oliwia', 'Szymanski', '2010-09-28', '2b5d357c-f4dd-4010-aad1-8165ea228a87');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('ffd8c83f-1b07-44fa-9bdd-26a87d4e16dc', 'Marcel', 'Szymanski', '2010-11-11', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('c045d2ef-7ba9-4e36-8771-1a8107295f31', 'Wojciech', 'Mazur', '2010-03-15', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('dbf0f0f7-ae36-4fcd-bd39-99a939b0d753', 'Aleksander', 'Grabowski', '2010-03-17', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('1fb5dbd7-2aa5-449e-b8dc-9b33c3703c18', 'Wojciech', 'Grabowski', '2010-10-07', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('1edb37e2-7a50-43f8-b899-b262878fe7d0', 'Bartosz', 'Kaminski', '2010-09-25', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('bfed7960-bae3-4739-8327-527e397aaa83', 'Patryk', 'Dabrowski', '2010-08-01', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('7baeba69-014e-4d3d-99a2-483857fd462e', 'Bartosz', 'Wozniak', '2010-04-02', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('728528bc-be19-4a5e-babd-b5305b9d59bf', 'Oskar', 'Wisniewski', '2010-02-24', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('f0c1c2bc-8d25-4ac6-afff-fdf2180fdd9f', 'Maja', 'Nowakowski', '2010-03-05', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('ef4005dd-f139-47fc-9ca4-a3d633b8c3d0', 'Weronika', 'Kaminski', '2010-05-17', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('f6a3019d-e714-4a70-af89-f59db77cdf9f', 'Amelia', 'Nowakowski', '2010-12-23', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('1be38ebb-8785-4fe8-a708-d1245f8fe6ec', 'Wojciech', 'Jankowski', '2010-11-21', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('6996421c-c9d9-41ef-9ee7-1c347de4215d', 'Marta', 'Grabowski', '2010-08-04', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('563e1909-27bf-4db1-8b4b-b0a4429aeaeb', 'Mikolaj', 'Wisniewski', '2010-06-01', '5915a6bd-8f7f-4903-8e69-4cff5722082c');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('571cf520-9833-47a7-961f-eeafd41d0d7e', 'Oskar', 'Zielinski', '2009-01-03', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('7179e4ac-ed0e-4832-9b76-b4ac3a082c96', 'Mikolaj', 'Wisniewski', '2009-01-28', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('1b7d1494-5af2-4a75-969b-94f56c496446', 'Maja', 'Grabowski', '2009-04-09', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('7291ed3c-a46b-44c9-a95c-86d39b3bea6a', 'Amelia', 'Nowakowski', '2009-03-24', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('a38f1f7e-13be-42b0-a47e-a215d9efda28', 'Hanna', 'Piotrowski', '2009-07-07', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('98a86e64-5475-4203-9d32-06d9a87cbf19', 'Filip', 'Mazur', '2009-06-14', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('5714119e-33fb-4b6f-b395-c1d744dcd7f7', 'Marta', 'Nowak', '2009-11-21', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('354e2484-b4c8-4955-94b6-95fdbd186f66', 'Kacper', 'Jankowski', '2009-12-11', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('cd553a96-7969-44d4-9fe9-3fcc3a38b6b9', 'Mikolaj', 'Lewandowski', '2009-04-18', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('ad721591-9b72-4ee8-9d4b-e368c07ed0a8', 'Lena', 'Mazur', '2009-03-09', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('3bc0ac30-a43b-4b41-9c56-f70e1ece35c4', 'Hanna', 'Wisniewski', '2009-08-26', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('975ed12c-8c6c-4121-8053-06bd77b75136', 'Kacper', 'Nowakowski', '2009-01-03', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('bfcacd78-c595-4a71-ab6d-3b54d9b7ba70', 'Szymon', 'Mazur', '2009-08-16', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('d345f7e6-be49-4b8b-958d-1f94072e9e30', 'Damian', 'Nowak', '2009-03-13', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('2b83fd14-2a67-4616-8ac1-ec5e470865ce', 'Damian', 'Szymanski', '2009-08-10', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('f3524ec3-ef53-491c-bcb7-fe4d7b24703a', 'Weronika', 'Piotrowski', '2009-03-07', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('7bb556ae-ec8b-4100-8209-92cd793d191f', 'Amelia', 'Nowak', '2009-10-24', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('ffc73b2c-d82b-4813-a254-477eb2a164ed', 'Adam', 'Nowak', '2009-01-19', '039d43ec-62fa-4a2f-92bd-0a0ffa733661');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('f34db32f-72d5-46e3-8a44-8c8f1ee1f928', 'Kacper', 'Grabowski', '2009-02-28', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('0091e235-0f0e-46a0-beed-dbabf0c0fedc', 'Antoni', 'Michalski', '2009-02-22', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('13fec3e7-6f73-4392-a5bc-e923c49d5dfb', 'Damian', 'Wojcik', '2009-10-08', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('9b3caaad-3f81-46da-ba7c-744247b9d0b6', 'Patryk', 'Wisniewski', '2009-07-22', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('f7d8492a-ad75-4531-8927-50fbfd2c3caf', 'Laura', 'Lewandowski', '2009-11-23', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('d4b6b596-32b2-4a38-894d-cd327f8965c6', 'Hanna', 'Szymanski', '2009-07-05', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('7973cd42-ae50-4397-8dd5-37e69a12912b', 'Marta', 'Dabrowski', '2009-02-01', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('4893e6e7-eb3b-43e5-aa4d-08f04f2129cd', 'Agata', 'Pawlowski', '2009-02-03', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('e5d8482e-88ad-445e-8aca-619e44cd8fe2', 'Maciej', 'Szymanski', '2009-03-12', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('2a8e440a-7980-481b-b082-f728fd9f1bdc', 'Mikolaj', 'Kozlowski', '2009-05-06', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('531d1a30-37b0-432b-951a-621ff28ee235', 'Weronika', 'Wozniak', '2009-10-26', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('f14ee2ad-c501-4c4d-a6c3-0fb434ac4178', 'Marcel', 'Wozniak', '2009-11-04', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('2eab5279-bebd-4a0f-b59b-48febe750429', 'Aleksander', 'Wojcik', '2009-02-24', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('6c1291b5-485d-49d8-9596-2cd1ca6a833f', 'Aleksander', 'Wozniak', '2009-10-07', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('35ee0fd5-6ea6-401f-9ab2-b7ed7d1659f9', 'Amelia', 'Szymanski', '2009-09-16', '594027fa-c540-4509-8c4f-c4f9edfc678d');
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES ('62eebe1e-1fdc-42c6-9279-a3d5fa446b00', 'Julia', 'Wisniewski', '2009-11-14', '594027fa-c540-4509-8c4f-c4f9edfc678d');
-- PARENTS
INSERT INTO parents (id, first_name, last_name) VALUES ('eda3d385-4223-4b99-a170-1b41c09e9d17', 'Kinga', 'Krawczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('3cbb59b2-ba8a-4d54-a249-bdab8f39202a', 'Jan', 'Krawczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('9a286c16-e6f2-4fef-b623-88f58732c1fb', 'Igor', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('31d2450b-6866-402e-9fea-374bcff78767', 'Maciej', 'Lewandowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('88aafe70-f87c-4714-9bcf-751595b263f4', 'Marcel', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('1543042a-ebaf-4884-b2c3-0c70c6b7f2da', 'Filip', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('4b794aca-b99d-44b8-8155-f35425e1b440', 'Konrad', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('84507da1-9e47-4f4d-94bc-cbf6decca382', 'Marta', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a4a5cad3-927e-45f2-8ab2-c2a81c38aaeb', 'Weronika', 'Kozlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('1b8fb8aa-dcd9-41db-b20b-dc371d633217', 'Laura', 'Michalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('aee97d07-0794-4354-8d07-9943e51af25e', 'Aleksander', 'Michalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('f5548246-c7b7-484f-951b-91170fc7b2b1', 'Hanna', 'Krawczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('fc28ad2a-39dc-4908-b613-c1026c8bf755', 'Marta', 'Krawczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('340a993c-b3ec-4cc7-b9dd-40a601a18ef1', 'Hanna', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('03237ece-0db3-4228-806b-57b4791b064d', 'Emilia', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('768ff0ce-8e47-4872-a55c-7ecd687939a8', 'Jan', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('58a93ec4-9cb5-4cd7-bd36-b5c1659b5425', 'Damian', 'Dabrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('74d70df5-4f13-4ceb-b7fa-1019644ebfa2', 'Antoni', 'Krawczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('4bbf9d60-6092-40e5-940d-8fdcb270eb1a', 'Emilia', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('2fcdaebd-95fd-4f7d-8059-5fc6ee1fe89a', 'Klara', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('8d7a05dd-4469-4fe8-8404-20074243b50c', 'Igor', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a98f86e2-4027-4f00-a4c2-d7288884705f', 'Oskar', 'Piotrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('fb170655-0fd2-479e-b6e8-9b02c1e1a16c', 'Nikola', 'Kowalczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('372a7573-ec42-4592-8606-edad96895657', 'Weronika', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('7f963e99-ec50-4c8e-a5b0-1e376a9b8e90', 'Leon', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('28431be4-af05-40a3-91cc-0df861edf64a', 'Klara', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('0ef337e3-28e0-46a1-a2be-6cf24db6780b', 'Damian', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('1d47032d-817e-4729-96b6-013449ba297f', 'Kinga', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('7c80bc6d-96f2-42f4-8503-2a9572927efc', 'Lena', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('9bd08107-5e22-46ba-94b4-30bf3e77b076', 'Damian', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('d0952394-aa69-4bd5-b611-9c255d74d138', 'Antoni', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('e408dd8e-403d-4f7a-ba00-7e80aed8744f', 'Konrad', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('c466e144-0099-4882-890f-7a307a333ee8', 'Kacper', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('34412781-667f-4061-9cdd-e961de2262f1', 'Emilia', 'Kaminski');
INSERT INTO parents (id, first_name, last_name) VALUES ('91788b41-7684-4882-847a-dae42dd26d75', 'Konrad', 'Kaminski');
INSERT INTO parents (id, first_name, last_name) VALUES ('d4128572-aedc-4cf2-b2ac-88d3261e39c8', 'Kinga', 'Dabrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a2fdcb27-c740-4fef-9c12-b6f92a04982e', 'Klara', 'Dabrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('e6cc2e9c-1e85-4113-b31d-5ed46c7e5bde', 'Leon', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('8c06e307-c844-48f5-b6b4-d49823d09d7c', 'Marcel', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('34881d8c-2ec2-497b-83b1-3b071b861430', 'Hanna', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('5fc82b5a-02ac-4557-b729-2f6aea1dab61', 'Jakub', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('821eeb94-2bee-4ab7-8483-fa6db7abf8da', 'Jakub', 'Kaminski');
INSERT INTO parents (id, first_name, last_name) VALUES ('85a4b5e9-3c0e-4b3a-a0a9-07e66b81ca5e', 'Mikolaj', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('e7ae1276-7a71-4b00-9541-9b7aa2ed8884', 'Leon', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('f24f6c64-70d9-4365-8fc9-4e9ff9972e9f', 'Konrad', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a19c495b-5de3-4aba-9178-bcbce19ab0e6', 'Alicja', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('fad83337-a70c-4748-9090-0f4208b8df3d', 'Szymon', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('fe056f07-ec53-421a-81d2-158437e78ce6', 'Oskar', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('b72dbcc2-5d42-44a9-aeb8-f5157487bf9e', 'Wojciech', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('48751fad-c428-4e77-8f27-086d3ad7775f', 'Kinga', 'Zielinski');
INSERT INTO parents (id, first_name, last_name) VALUES ('9f3273fc-0503-48f5-83f1-b8bc1947d650', 'Antoni', 'Zielinski');
INSERT INTO parents (id, first_name, last_name) VALUES ('e8f7deb7-b52f-4333-9e87-79d50b64ee81', 'Filip', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('f8a158e2-cde3-4054-baf6-0925f02079de', 'Agata', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('49382118-8ee8-4449-a5ed-95ad486889d5', 'Bartosz', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('eb7a3f24-4468-4e2c-a802-0f4ff99d1e91', 'Gabriela', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a2e1d884-9b42-4343-aaa1-9ad3131132fa', 'Maciej', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('f8263b0a-99db-4474-bab8-819cd82ccb6b', 'Igor', 'Piotrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('3a01d2da-1bd3-4c21-9872-d05d3c16368c', 'Zuzanna', 'Piotrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('816edd0a-0e5d-4261-bc11-14584f20833c', 'Marta', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('64c1d4ac-1069-4c53-ab3d-752d2c2e4256', 'Igor', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('ae2050a3-7696-4708-b1a1-62f80f97116c', 'Agata', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('7bba028d-a3c2-482e-bf97-9cc888fc75da', 'Marta', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('b6ffa2a9-d197-4f53-a500-a8a643c26c43', 'Wiktoria', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('121c7cca-ce7c-47c8-94dc-7b21473f2369', 'Hanna', 'Lewandowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('5505794e-4301-43da-a082-8b1f797e0fb7', 'Aleksander', 'Lewandowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('b2adb1b7-9dc9-42a0-a6f0-4d6bd413ece6', 'Konrad', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('e108d7a2-8314-4b53-bf72-5e7677d675df', 'Emilia', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('2e5cb077-a5a4-4377-9aff-d0f8863add0a', 'Emilia', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('77964280-266a-45d4-a76c-3bf87bef65a6', 'Amelia', 'Nowakowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('e507003a-dfdf-48e8-8c1a-b2070b18fc4b', 'Emilia', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('ce1ff3c2-168d-4270-9857-c9c7097574ac', 'Agata', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('7bf3cddc-3967-4854-9a33-095530bd0d2d', 'Maciej', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('c0efeefa-5342-4618-b2a9-b7cea0344a4e', 'Antoni', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('03bb3734-2b7a-45f3-b7dd-f3b4d927cbc7', 'Natalia', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('3a28623d-f679-406e-a437-c99b9ccfb483', 'Leon', 'Piotrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a6c60347-243c-4dbb-8601-bfba2775a8e4', 'Marta', 'Piotrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('0dfd575c-978e-4b06-ad37-613ccdc8c5f0', 'Wojciech', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('d0362e13-53d5-45d4-bbc6-dd6d56af0ea9', 'Hanna', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('5c2664cb-2665-4453-9e39-8c32d0b5399f', 'Natalia', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('aa0ef80f-8593-4e42-be64-bb4a413937c5', 'Klara', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('dce8e872-fcee-4eaf-abc3-580a4e29692b', 'Gabriela', 'Michalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('385105eb-6b59-4e46-bdc8-bfd6e75115d3', 'Laura', 'Michalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('ca036b2f-e0ca-4771-b305-a108a3adb387', 'Hanna', 'Wojcik');
INSERT INTO parents (id, first_name, last_name) VALUES ('b77d0399-2890-4fe1-bec4-26f7c6e7230e', 'Marcel', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('6838fe6a-d8ee-407f-8e22-98dfa0ad36ee', 'Franciszek', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('3289b74a-adfc-452f-b08a-c9b000b3812c', 'Laura', 'Lewandowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('b3d394ef-16f5-4460-96eb-9630a068cd73', 'Zuzanna', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('808e9584-e7d5-4537-b330-5f1f1785f8c3', 'Wojciech', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('454703fc-29bf-41d5-9bc7-f033fa589e0f', 'Alicja', 'Dabrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('fa1e69f0-ca7b-4abb-8172-9b92064d662f', 'Marcel', 'Pawlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a33d5d0e-784f-47d1-9a20-ae617ae1cc9f', 'Aleksander', 'Pawlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('8dca18fa-47f3-4ab5-b48c-f6847a07bc74', 'Marcel', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a08a8355-ff51-487e-86ab-c1cebef31c43', 'Leon', 'Kozlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('8aeec19e-8c04-451b-8b19-29d9c13cb48d', 'Jakub', 'Kozlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('8f7fa5e6-0569-4150-bde9-e15c171c5eb4', 'Natalia', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('533fbc40-e518-40a8-ac35-5e4736a3c4a2', 'Marta', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('a5447b06-35c9-4e90-8390-01bfa0d8e7f3', 'Natalia', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('e301ddc5-a360-494b-a88d-3471cbd84540', 'Damian', 'Wojcik');
INSERT INTO parents (id, first_name, last_name) VALUES ('c056dfe8-4027-4a81-821d-a7f82d812bd1', 'Oskar', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('8d7b853d-ee84-4320-ad30-b683f02d57bb', 'Jan', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('39542f09-e499-4539-bb2a-fe8c110c991b', 'Maja', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('2f223d83-ccf0-480d-a831-1a556e3a3add', 'Marcel', 'Wisniewski');
INSERT INTO parents (id, first_name, last_name) VALUES ('3ab714b0-9818-442b-9a52-ea20851adc7a', 'Marta', 'Kowalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('d49b68e8-aa1a-45c5-8d26-8a2f56c70786', 'Wiktoria', 'Krawczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('0cba594b-b106-454d-a822-31a174eab6d6', 'Nikola', 'Krawczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('a56c5f41-3f41-4b91-8352-d80f13698329', 'Patryk', 'Kowalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('3e3012e6-5bb0-4d92-9790-cd920b0e5007', 'Franciszek', 'Kowalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('dd693d8b-b4bf-4ef1-8cd7-7d988665194c', 'Maja', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('15e9430c-b614-48c3-b6dd-8a857a3a21aa', 'Szymon', 'Kowalczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('dfb89bbb-6e94-471c-a3df-fcdb59ff1693', 'Szymon', 'Kozlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('249e3e63-3d72-48d6-9582-35a0b552b5d6', 'Nikola', 'Kozlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('57140b6e-6112-489c-9b8d-8c0e62aa4303', 'Mikolaj', 'Kozlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('ec9a95b3-85ee-4c07-a573-c468d8b1a216', 'Marta', 'Kowalczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('10d408d7-0739-4de9-99a4-e064546efa66', 'Mikolaj', 'Kowalczyk');
INSERT INTO parents (id, first_name, last_name) VALUES ('8747da70-7a69-4289-bc88-1167f42327d1', 'Igor', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('6206ce59-6ec4-460e-8bf4-7c01fe28ed3a', 'Marcel', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('a6d7ca2d-b8bd-4abd-b659-1d9752dbbe2c', 'Aleksander', 'Zielinski');
INSERT INTO parents (id, first_name, last_name) VALUES ('fd40cfa1-f7a6-4629-95a2-f9020f90f114', 'Kacper', 'Nowak');
INSERT INTO parents (id, first_name, last_name) VALUES ('34da50c7-aea5-4257-b644-8f32403e1e54', 'Agata', 'Kozlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('06513e15-faf4-4ee8-8f27-e5e50669ce2a', 'Marta', 'Kowalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('cecd1a2c-aa8f-42e6-a223-d348c9535ff2', 'Konrad', 'Kowalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('285a0f08-44d5-4d3f-8767-f31f20562b15', 'Laura', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('7d92f782-143a-407f-a041-41fda1c2441c', 'Marta', 'Szymanski');
INSERT INTO parents (id, first_name, last_name) VALUES ('6d326e0c-0cc8-471d-ac51-7d6ee961c91e', 'Igor', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('3a599bf5-7ad4-413e-90ab-5d461e9e5a61', 'Zofia', 'Wojcik');
INSERT INTO parents (id, first_name, last_name) VALUES ('49c9016e-3c9c-40c0-b666-e2543680e074', 'Mikolaj', 'Wojcik');
INSERT INTO parents (id, first_name, last_name) VALUES ('3bf1da27-816f-4830-b561-8c7dec83633e', 'Aleksander', 'Wojcik');
INSERT INTO parents (id, first_name, last_name) VALUES ('1a96c83a-ed7a-4fe7-b9b5-c4f73d57c106', 'Szymon', 'Wojcik');
INSERT INTO parents (id, first_name, last_name) VALUES ('30a91381-b279-4d92-95db-d5423241c583', 'Laura', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('a2af1c6e-d10a-466d-960e-8ff47f774483', 'Oskar', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('05b7cf3d-09d9-47fd-8001-28fd4616d607', 'Maja', 'Pawlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('2aa97cca-9429-495f-bddc-ba8775ed9c43', 'Gabriela', 'Pawlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('b41e0a4d-87a0-4136-8287-77374e99264c', 'Emilia', 'Kowalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('0a9dd841-86c0-4635-9eac-7c9833ae7b3f', 'Szymon', 'Kowalski');
INSERT INTO parents (id, first_name, last_name) VALUES ('a829be6d-ca02-41f8-aed4-717c0ee9f66b', 'Natalia', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('32d47b19-5ca3-4d4f-ac0a-b7e520cf319c', 'Kinga', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('af3a87e7-19bc-4034-b780-da8ef9c8de9e', 'Konrad', 'Dabrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('2f97ea37-0266-4b96-91c7-c6cc385b2b52', 'Adam', 'Dabrowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('f63df3d8-d236-4555-8078-ea16c3835965', 'Zuzanna', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('dcb60c04-ac07-4875-a5ad-8b7417d2367a', 'Oliwia', 'Grabowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('029c43df-ba56-461e-91c2-e66e30291015', 'Klara', 'Jankowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('d7c9f2f4-1931-46fa-b064-3e3a76d7dcff', 'Maciej', 'Mazur');
INSERT INTO parents (id, first_name, last_name) VALUES ('6c56d0c6-3c93-42a5-99d3-e443251b6c62', 'Marta', 'Pawlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('7f18457f-ea18-4831-973a-65920048e4ec', 'Franciszek', 'Pawlowski');
INSERT INTO parents (id, first_name, last_name) VALUES ('dc8fb8a9-7c6f-40b3-b6ab-5483a883e402', 'Wojciech', 'Wozniak');
INSERT INTO parents (id, first_name, last_name) VALUES ('7cfbb2b0-08e3-4e5a-9481-3a5d2b05f959', 'Natalia', 'Wozniak');

-- PARENTS_STUDENTS
INSERT INTO parents_students(parent_id, student_id) VALUES ('eda3d385-4223-4b99-a170-1b41c09e9d17', 'cadb6bdc-91ce-4980-b53c-c1682e26a85c');

-- TEACHING ASSIGNMENTS
-- GROUP 1
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('bede62a5-01b8-4339-a05b-3a7c4c2b01a4', 'ccf705fb-1971-4248-b6de-0fa58bda4d29', '324bbd3d-7182-4966-9cf2-b7868bab6be7', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --angielski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('a67f0399-49b3-4de6-b9fa-95d588ada00b', '486f7c91-f074-4b10-8972-7cb9bbd33f03', 'b45b85c8-94a1-4298-9f12-da4317f2861d', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --biologia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('65ab47d3-ff0e-49f9-84de-e93d3e7954d5', '64c1b23e-a7a5-48a8-a07a-3572d4689d8f', '6fee94d0-a59a-4ffe-91bb-d001fc1701bf', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --historia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('cf2eaa5b-74cf-4937-a68e-4c10446871db', 'b370c6e3-4c26-4303-8346-022e48d56894', 'a76693df-f587-4f08-9757-b6475c6565a0', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --fizyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('6e0c106b-f45c-4674-8b05-f8cf45f82b6f', '0fa59827-97ad-489f-90ee-76347c3d601a', '00ef24b4-0b1e-4f15-8c2b-5e970f4e0236', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --informatyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('9bb27be1-c168-4696-b1ac-4e6cf4483a42', 'c9877021-c858-470d-87c5-680cb68cc1f4', '94bc48a1-fb3c-4957-ad2b-d3550a97b48b', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --polski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('1fb00dc7-b6f8-4f05-ab49-26a5fb6cdcaf', 'c4ad6263-4571-4e22-bc15-2d1459b0606f', '547ce05f-ad3a-4005-8a93-fa6bf6acf1eb', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --matematyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('6e129eb6-b0bf-462a-9b81-9f21bd8dd9c4', '486f7c91-f074-4b10-8972-7cb9bbd33f03', '1d8f3c95-2482-4e2a-a8f9-02cd4fec3a06', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --chemia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('0b76740d-43b7-4daf-b140-ddba2a4ef02f', '327688e0-8d2d-4821-a889-7aaeb2a06fd1', 'c2050a95-8b16-4918-b801-285fc257d795', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --niemiecki
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('85daac66-1dd4-4e5b-873d-355af09bcf62', 'b466c6fc-1510-4715-80ea-fd071ae57e8e', '58c3eb74-cff0-48de-af2a-582791bded3b', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --muzyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('91e6c25b-4851-472f-a8a6-4502a170c76a', 'e7423a37-4ffa-423f-88dd-d24f3865c285', 'fd334bcc-c248-4474-b440-66839e0870de', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --wf
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('89256fb9-f85b-4aaf-9cae-cdbf6682948f', '5580d5de-8d8c-4296-9ce5-9b3415557f80', '9e9dad0e-da1f-4ae9-990a-265238f7309d', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --geografia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('8b1ae4a8-de2b-4711-a12d-1837a8d61eed', 'b6b90a92-e763-426d-a8b4-b49482d2162a', '7d6f02e3-477e-49c2-9585-2040f7a634db', '2b5d357c-f4dd-4010-aad1-8165ea228a87'); --biznes

-- GROUP 2
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('8def220b-bdd1-4f43-addd-c241d61c7c88', 'a2a47bfe-ddaa-435a-ba5d-21ea8216b83e', '94bc48a1-fb3c-4957-ad2b-d3550a97b48b', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --polski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('e87e1565-110b-47b9-9bc6-cf29ccf1424e', 'c4ad6263-4571-4e22-bc15-2d1459b0606f', '547ce05f-ad3a-4005-8a93-fa6bf6acf1eb', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --matematyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('57bc5e9e-daf7-4bc7-bb11-68b3fb9094c6', 'ccf705fb-1971-4248-b6de-0fa58bda4d29', '324bbd3d-7182-4966-9cf2-b7868bab6be7', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --angielski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('5d5a1e58-d6e8-4146-b2ce-c91f79d1b83b', '327688e0-8d2d-4821-a889-7aaeb2a06fd1', 'c2050a95-8b16-4918-b801-285fc257d795', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --niemiecki
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('1f8e08a9-2c45-47f6-a13b-2a798e5c5c93', '64c1b23e-a7a5-48a8-a07a-3572d4689d8f', '6fee94d0-a59a-4ffe-91bb-d001fc1701bf', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --historia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('b9f1bc3a-ef52-4279-971e-ea3e4b3ce2d4', '5580d5de-8d8c-4296-9ce5-9b3415557f80', '9e9dad0e-da1f-4ae9-990a-265238f7309d', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --geografia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('86e3f23e-5706-48b1-925f-fdddcf4e9e5e', '486f7c91-f074-4b10-8972-7cb9bbd33f03', 'b45b85c8-94a1-4298-9f12-da4317f2861d', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --biologia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('5cdf0e7f-8baa-4c85-9f6e-0e939bdb9118', '486f7c91-f074-4b10-8972-7cb9bbd33f03', '1d8f3c95-2482-4e2a-a8f9-02cd4fec3a06', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --chemia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('eef1b6b1-3c4f-4002-a5ef-825436bd1868', 'b370c6e3-4c26-4303-8346-022e48d56894', 'a76693df-f587-4f08-9757-b6475c6565a0', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --fizyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('c84519ca-fe12-4693-90b4-147e72ed69c9', '0fa59827-97ad-489f-90ee-76347c3d601a', '00ef24b4-0b1e-4f15-8c2b-5e970f4e0236', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --informatyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('c06a363a-11f4-4807-aaf7-9f3b6a12b7da', 'b6b90a92-e763-426d-a8b4-b49482d2162a', '7d6f02e3-477e-49c2-9585-2040f7a634db', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --biznes
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('9256bd62-ebf5-45aa-b9c6-56eefa25ec07', 'e7423a37-4ffa-423f-88dd-d24f3865c285', 'fd334bcc-c248-4474-b440-66839e0870de', '5915a6bd-8f7f-4903-8e69-4cff5722082c'); --wf

-- GROUP 3
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('670bfacf-04c2-4523-8e0e-5a091edc588f','c9877021-c858-470d-87c5-680cb68cc1f4', '94bc48a1-fb3c-4957-ad2b-d3550a97b48b', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --polski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('adce8fdb-87ba-4914-a213-1ba0aad1cb8c','c4ad6263-4571-4e22-bc15-2d1459b0606f', '547ce05f-ad3a-4005-8a93-fa6bf6acf1eb', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --matematyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('372ec5fe-e32f-4d6d-923e-dbff5a507fd7','ccf705fb-1971-4248-b6de-0fa58bda4d29', '324bbd3d-7182-4966-9cf2-b7868bab6be7', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --angielski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('3b33ce7c-f180-401d-95b5-f4f82436d873','327688e0-8d2d-4821-a889-7aaeb2a06fd1', 'c2050a95-8b16-4918-b801-285fc257d795', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --niemiecki
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('8a15b315-d093-4587-9d0a-3fb3d8b2aaba','64c1b23e-a7a5-48a8-a07a-3572d4689d8f', '6fee94d0-a59a-4ffe-91bb-d001fc1701bf', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --historia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('b5e780c5-b7ac-4e48-b7ab-a03fe033f0e0','5580d5de-8d8c-4296-9ce5-9b3415557f80', '9e9dad0e-da1f-4ae9-990a-265238f7309d', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --geografia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('348a3742-7ee8-482b-b856-9348eb8efc1f','486f7c91-f074-4b10-8972-7cb9bbd33f03', 'b45b85c8-94a1-4298-9f12-da4317f2861d', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --biologia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('521110df-8818-4c15-9817-8526460cd336','486f7c91-f074-4b10-8972-7cb9bbd33f03', '1d8f3c95-2482-4e2a-a8f9-02cd4fec3a06', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --chemia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('99a2658c-1445-4803-8887-e0c8378757f2','b370c6e3-4c26-4303-8346-022e48d56894', 'a76693df-f587-4f08-9757-b6475c6565a0', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --fizyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('f95f213c-361a-437b-8ffb-57e4f3c29d6b','0fa59827-97ad-489f-90ee-76347c3d601a', '00ef24b4-0b1e-4f15-8c2b-5e970f4e0236', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --informatyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('2dd1a7bd-762c-4235-a8a7-a7d1d2fed70a','b6b90a92-e763-426d-a8b4-b49482d2162a', '7d6f02e3-477e-49c2-9585-2040f7a634db', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --biznes
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('757642b8-4a04-4bb4-bf7a-81d28f1f3b3b','e7423a37-4ffa-423f-88dd-d24f3865c285', 'fd334bcc-c248-4474-b440-66839e0870de', '039d43ec-62fa-4a2f-92bd-0a0ffa733661'); --wf

--GROUP 4
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('a2a47bfe-ddaa-435a-ba5d-21ea8216b83e','c9877021-c858-470d-87c5-680cb68cc1f4', '94bc48a1-fb3c-4957-ad2b-d3550a97b48b', '594027fa-c540-4509-8c4f-c4f9edfc678d'); --polski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('7019ee5d-5bf4-4ed6-8731-d812967d2441','c4ad6263-4571-4e22-bc15-2d1459b0606f', '547ce05f-ad3a-4005-8a93-fa6bf6acf1eb', '594027fa-c540-4509-8c4f-c4f9edfc678d'); --matematyka
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('70ab4a76-d2bf-4af2-bda2-747236719ce3','ccf705fb-1971-4248-b6de-0fa58bda4d29', '324bbd3d-7182-4966-9cf2-b7868bab6be7', '594027fa-c540-4509-8c4f-c4f9edfc678d'); --angielski
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('3c0162d7-7ad4-4b0a-aeea-626e3f15f2d5','327688e0-8d2d-4821-a889-7aaeb2a06fd1', 'c2050a95-8b16-4918-b801-285fc257d795', '594027fa-c540-4509-8c4f-c4f9edfc678d'); --niemiecki
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('c75a38e3-ffaa-4750-ad48-d2c89e294306','64c1b23e-a7a5-48a8-a07a-3572d4689d8f', '6fee94d0-a59a-4ffe-91bb-d001fc1701bf', '594027fa-c540-4509-8c4f-c4f9edfc678d'); --historia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('9374c962-51a4-4608-92b8-d3ce2e32eeb5','5580d5de-8d8c-4296-9ce5-9b3415557f80', '9e9dad0e-da1f-4ae9-990a-265238f7309d', '594027fa-c540-4509-8c4f-c4f9edfc678d'); --geografia
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES ('e3d6ec7b-d6b4-497c-972b-baf9a9fb42ed','e7423a37-4ffa-423f-88dd-d24f3865c285', 'fd334bcc-c248-4474-b440-66839e0870de', '594027fa-c540-4509-8c4f-c4f9edfc678d'); --wf

-- CLASSIFICATION PERIODS
INSERT INTO classification_periods (id, date_from, date_to, semester) VALUES ('46b68a03-99d7-4262-82db-b5099e92d9e0', '2025-09-01', '2026-01-25', 1);
INSERT INTO classification_periods (id, date_from, date_to, semester) VALUES ('11908013-f40d-4f8a-af98-e64d2e34b3df', '2026-01-26', '2026-06-26', 2);

