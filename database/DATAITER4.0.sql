USE [QPS4.0]
GO


INSERT INTO [dbo].[Role]
           	([role_name])
    VALUES
           ('Admin'),
		   ('Customer'),
		   ('Expert'),
		   ('Marketing'),
		   ('Sale')
GO


INSERT INTO [dbo].[Category]
           	([category_name])
    VALUES
           ('Mathemactics'),
		   ('Linguistics'),
		   ('Software Engineering'),
		   ('Finance')
GO


INSERT INTO [dbo].[Level]
           ([level_name])
    VALUES
           ('Basic'),
		   ('Intermediate'),
		   ('Advanced')
GO


INSERT INTO [dbo].[RegistrationStatus]
			([status_name])
	VALUES
			('Submitted'),
			('Cancelled'),
			('Paid'),
			('Successful')
GO


INSERT INTO [dbo].[QuizType]
           	([quiz_type_name])
    VALUES
           	('Simulation Exam'),
		   	('Practice')
GO	


INSERT INTO [dbo].[LessonType] ([lesson_type_name])
VALUES 
    		('Subject Topic'), 
    		('Lesson'), 
    		('Quiz')
GO


INSERT INTO [dbo].[QuestionType]
           ([type_name])
    VALUES
           	('True/False'),
		   	('One Answer'),
		   	('Multiple Answers'),
		   	('Written')
GO


INSERT INTO [dbo].[Users]
           ([email],[password],[full_name],[gender],[role_id],[status],[created_date],[avatar],[reset_token],[reset_token_expiry],[mobile])
    VALUES 
           ('hungnbvhe186953@fpt.edu.vn', 'vhg1010101', 'Ngo Ba Vu Hung',1,1,1,GETDATE(),'resources/images/user/default.jpg','1234',GETDATE(),'012345678'),
           ('thaindhe186951@fpt.edu.vn','13745893','Nong Duc Thai',1,1,1,GETDATE(),'resources/images/user/default.jpg','1234',GETDATE(),'7583479857'),
           ('market@fpt.edu.vn','marketing404','marketing person',1,3,1,GETDATE(),'resources/images/user/default.jpg','1234',GETDATE(),'012345678'),
           ('admin@fpt.edu.vn','admin1234','Admin',1,1,1,GETDATE(),'resources/images/user/default.jpg','1234',GETDATE(),'012345678'),
           ('expert@fpt.edu.vn','expertpro','Expert',1,3,1,GETDATE(),'resources/images/user/default.jpg','1234',GETDATE(),'012345678'),
           ('john.doe@fpt.edu.vn', 'john1234', 'John Doe', 1, 4, 1, GETDATE(), 'resources/images/user/default.jpg', '5678', GETDATE(), '0987654321'),
           ('jane.doe@fpt.edu.vn', 'jane1234', 'Jane Doe', 0, 5, 1, GETDATE(), 'resources/images/user/default.jpg', '91011', GETDATE(), '0987654322'),
           ('alice.smith@fpt.edu.vn', 'alice1234', 'Alice Smith', 0, 3, 1, GETDATE(), 'resources/images/user/default.jpg', '121314', GETDATE(), '0987654323'),
           ('bob.johnson@fpt.edu.vn', 'bob1234', 'Bob Johnson', 1, 4, 1, GETDATE(), 'resources/images/user/default.jpg', '151617', GETDATE(), '0987654324'),
           ('charlie.brown@fpt.edu.vn', 'charlie1234', 'Charlie Brown', 1, 5, 1, GETDATE(), 'resources/images/user/default.jpg', '181920', GETDATE(), '0987654325'),
           ('david.wilson@fpt.edu.vn', 'david1234', 'David Wilson', 1, 2, 1, GETDATE(), 'resources/images/user/default.jpg', '212223', GETDATE(), '0987654326'),
           ('emily.davis@fpt.edu.vn', 'emily1234', 'Emily Davis', 0, 1, 1, GETDATE(), 'resources/images/user/default.jpg', '242526', GETDATE(), '0987654327');
GO


INSERT INTO [dbo].[Subject]
           ([subject_name], [thumbnail], [category_id], [is_featured], [status], [tag_line], [brief_info], [description], [created_date], [expert_id])
     VALUES
           ('MAD','resources/images/subjects/mad.jpg',1,0,1,'Discrete Mathematics',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'e combinatorics of partially ordered sets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('MAE','resources/images/subjects/mae.jpg',1,0,1,'Mathematics Engineering',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'Tlly ordered sets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('JPD1','resources/images/subjects/jpd1.jpg',2,0,1,'Elementary Japanese 1.1',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'Thely ordered sets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('JPD2','resources/images/subjects/jpd2.jpg',2,0,1,'Elementary Japanese 1.2',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'Therdered sets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('PRF','resources/images/subjects/prf.jpg',3,0,1,'Programming Fundamentals',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'The rets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('PRJ','resources/images/subjects/prj.jpg',3,0,1,'Java Web Application',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'The sets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('SWP','resources/images/subjects/swp.jpg',3,1,1,'Software Project Application',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'Theets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('SWT','resources/images/subjects/swt.jpg',3,1,1,'Software Testing',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'Tsets, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('SWR','resources/images/subjects/swr.jpg',3,1,1,'Software Requirements',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('CEA','resources/images/subjects/cea.jpg',3,0,1,'Computer Organization and Architecture',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('CSD','resources/images/subjects/csd.jpg',3,0,1,'Data Sctructure and Algorythms',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('CSI','resources/images/subjects/csi.jpg',3,0,1,'Introduction to Computer Science',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('DBI','resources/images/subjects/dbi.jpg',3,0,1,'Introduction to Databases',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('IOT','resources/images/subjects/iot.jpg',3,0,1,'Internets of Things',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('ITE','resources/images/subjects/ite.jpg',3,1,1,'Ethics in IT',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('MAS','resources/images/subjects/mas.jpg',1,0,0,'Statistics and Probability',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('NWC','resources/images/subjects/nwc.jpg',3,0,1,'Computer Nerworking',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('OSG','resources/images/subjects/osg.jpg',3,0,0,'Operating Systems',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('PRO','resources/images/subjects/pro.jpg',3,0,1,'Object-Oriented Programming',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5),
		   ('SWE','resources/images/subjects/swe.jpg',3,0,0,'Introduction to Software Engineering',
		   'Discrete Mathematics provides a common forum for significant research in many areas of discrete mathematics and combinatorics.',
		   'T, extremal set theory, matroid theory, algebraic combinatorics, discrete geometry, matrices, discrete probability, and parts of cryptography.'
		   ,GETDATE(),5)
GO


-- Inserting Basic packages for subjects from 32 to 51
INSERT INTO [dbo].[Package] ([subject_id], [package_name], [duration], [list_price], [sell_price], [status], [description], [created_date],[updated_by])
VALUES
(1, 'Basic MAD', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(2, 'Basic MAE', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(3, 'Basic JPD1', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(4, 'Basic JPD2', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(5, 'Basic PRF', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(6, 'Basic PRJ', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(7, 'Basic SWP', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(8, 'Basic SWT', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(9, 'Basic SWR', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(10, 'Basic CEA', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(11, 'Basic CSD', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 11:44:31.907',2),
(12, 'Basic CSI', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(13, 'Basic DBI', 3, 34.00, 40.00, 0, '1 month access', '2024-05-30 12:44:31.907',2),
(14, 'Basic IOT', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(15, 'Basic ITE', 3, 34.00, 40.00, 1, '1 month access', '2024-05-30 12:44:31.907',2),
(16, 'Basic MAS', 3, 34.00, 40.00, 1, '1 month access', '2024-06-05 12:44:31.907',2),
(17, 'Basic NWC', 3, 34.00, 40.00, 0, '1 month access', '2024-06-05 12:44:31.907',2),
(18, 'Basic OSG', 3, 34.00, 40.00, 1, '1 month access', '2024-06-05 12:44:31.907',2),
(19, 'Basic PRO', 3, 34.00, 40.00, 0, '1 month access', '2024-05-30 12:44:31.907',2),
(20, 'Basic SWE', 3, 34.00, 40.00, 1, '1 month access', '2024-07-30 12:44:31.907',2)
GO


-- Inserting Standard packages for subjects from 32 to 51
INSERT INTO [dbo].[Package] ([subject_id], [package_name], [duration], [list_price], [sell_price], [status], [description], [created_date],[updated_by])
VALUES 
(1, 'Standard MAD', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(2, 'Standard MAE', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(3, 'Standard JPD1', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(4, 'Standard JPD2', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(5, 'Standard PRF', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(6, 'Standard PRJ', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(7, 'Standard SWP', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(8, 'Standard SWT', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(9, 'Standard SWR', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(10, 'Standard CEA', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(11, 'Standard CSD', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(12, 'Standard CSI', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(13, 'Standard DBI', 6, 50.00, 55.00, 0, '6 months access', '2024-05-30 12:44:31.907',2),
(14, 'Standard IOT', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(15, 'Standard ITE', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(16, 'Standard MAS', 6, 50.00, 55.00, 0, '6 months access', '2024-05-30 12:44:31.907',2),
(17, 'Standard NWC', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(18, 'Standard OSG', 6, 50.00, 55.00, 1, '6 months access', '2024-05-30 12:44:31.907',2),
(19, 'Standard PRO', 6, 50.00, 55.00, 0, '6 months access', '2024-05-30 12:44:31.907',2),
(20, 'Standard SWE', 6, 50.00, 55.00, 0, '6 months access', '2024-05-30 12:44:31.907',2)
GO


-- Inserting Premium packages for subjects from 32 to 51
INSERT INTO [dbo].[Package] ([subject_id], [package_name], [duration], [list_price], [sell_price], [status], [description], [created_date],[updated_by])
VALUES
(1, 'Premium MAD', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(2, 'Premium MAE', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(3, 'Premium JPD1', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(4, 'Premium JPD2', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(5, 'Premium PRF', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(6, 'Premium PRJ', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(7, 'Premium SWP', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(8, 'Premium SWT', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(9, 'Premium SWR', 9, 80.00, 85.00, 0, '9 months access', '2024-05-30 12:44:31.907',2),
(10, 'Premium CEA', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(11, 'Premium CSD', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(12, 'Premium CSI', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(13, 'Premium DBI', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2),
(14, 'Premium IOT', 9, 80.00, 85.00, 0, '9 months access', '2024-05-30 12:44:31.907',2),
(15, 'Premium ITE', 9, 80.00, 85.00, 1, '9 months access', '2024-05-30 12:44:31.907',2)
GO


insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Secrets of Effective Time Management', 'https://picsum.photos/300/200?id=42', 11, '2023-06-04', '2023-11-28', 4, 'Productivity tools and apps', 771);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Art of Interior Design', 'https://picsum.photos/300/200?id=48', 9, '2023-08-23', '2024-05-29', 2, 'Pet care and adoption tips', 486);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Delicious Smoothie Recipes', 'https://picsum.photos/300/200?id=11', 5, '2023-11-22', '2024-02-17', 4, 'Travel tips and destination recommendations', 315);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Regular Exercise', 'https://picsum.photos/300/200?id=13', 8, '2024-01-13', '2023-11-11', 3, 'Self-help books and personal development resources', 792);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Ways to Reduce Stress', 'https://picsum.photos/300/200?id=90', 8, '2023-10-08', '2023-07-20', 4, 'Fashion trends and style tips', 976);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('How to Overcome Procrastination', 'https://picsum.photos/300/200?id=35', 9, '2023-08-08', '2023-09-01', 4, 'Art therapy and creative expression', 449);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Rise of Sustainable Fashion', 'https://picsum.photos/300/200?id=70', 7, '2023-06-10', '2023-11-22', 4, 'Relationship advice and dating tips', 766);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The History of Cinema', 'https://picsum.photos/300/200?id=71', 7, '2023-06-26', '2023-11-27', 1, 'Movie re[view]s and recommendations', 175);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Ways to Boost Your Productivity', 'https://picsum.photos/300/200?id=57', 10, '2023-10-09', '2024-02-24', 2, 'Movie marathons and watch parties', 937);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Ultimate Guide to Personal Development', 'https://picsum.photos/300/200?id=91', 8, '2023-11-21', '2023-07-26', 3, 'Science fiction book recommendations', 517);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The History of Photography', 'https://picsum.photos/300/200?id=10', 1, '2023-06-19', '2024-01-12', 2, 'Self-love and body positivity tips', 395);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('5 Ways to Boost Your Creativity', 'https://picsum.photos/300/200?id=56', 5, '2024-02-09', '2023-12-10', 3, 'Photography challenges and creative prompts', 148);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Art of Creative Writing', 'https://picsum.photos/300/200?id=32', 9, '2023-06-18', '2024-03-22', 3, 'Personal finance podcasts and resources', 647);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Must-Visit Destinations for Foodies', 'https://picsum.photos/300/200?id=29', 9, '2023-08-21', '2023-12-11', 1, 'Productivity tools and apps', 761);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('5 Ways to Stay Motivated', 'https://picsum.photos/300/200?id=20', 5, '2023-06-01', '2023-12-28', 2, 'Gaming tips and strategies', 362);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Must-Read Books for Summer', 'https://picsum.photos/300/200?id=14', 3, '2023-11-02', '2023-08-02', 2, 'Crafting challenges and community projects', 301);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Mindfulness', 'https://picsum.photos/300/200?id=7', 11, '2023-06-12', '2024-04-16', 1, 'Science experiments and hands-on activities', 482);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Easy Dinner Recipes', 'https://picsum.photos/300/200?id=60', 8, '2024-03-17', '2024-04-22', 1, 'History documentaries and films', 590);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The History of Art', 'https://picsum.photos/300/200?id=2', 7, '2023-10-13', '2024-01-19', 4, 'Automotive re[view]s and recommendations', 372);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The History of Chocolate', 'https://picsum.photos/300/200?id=21', 11, '2024-02-22', '2023-12-26', 4, 'Fitness challenges and community events', 407);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('Navigating the World of Freelancing', 'https://picsum.photos/300/200?id=46', 9, '2023-08-29', '2023-12-28', 1, 'Financial advice and money-saving tips', 812);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Ultimate Guide to Personal Development', 'https://picsum.photos/300/200?id=96', 8, '2023-06-19', '2024-02-01', 3, 'Music history and iconic albums', 298);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('5 Ways to Boost Your Creativity', 'https://picsum.photos/300/200?id=30', 1, '2023-10-24', '2023-10-02', 4, 'Financial success stories and testimonials', 834);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Must-See Art Exhibitions', 'https://picsum.photos/300/200?id=19', 12, '2023-07-19', '2024-04-18', 2, 'Local travel guides and recommendations', 274);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('How to Overcome Fear', 'https://picsum.photos/300/200?id=16', 2, '2023-12-06', '2023-07-09', 4, 'Relationship humor and funny anecdotes', 638);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Must-Read Books for Summer', 'https://picsum.photos/300/200?id=54', 5, '2023-12-17', '2023-11-27', 1, 'Financial success stories and testimonials', 622);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('5 Ways to Stay Motivated', 'https://picsum.photos/300/200?id=80', 9, '2023-06-04', '2023-07-09', 2, 'Personal finance success stories and testimonials', 820);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The History of Art', 'https://picsum.photos/300/200?id=6', 9, '2023-11-18', '2023-07-02', 2, 'Food photography and styling tips', 847);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Science of Positive Thinking', 'https://picsum.photos/300/200?id=27', 5, '2024-03-14', '2023-12-31', 1, 'Photography contests and competitions', 536);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('5 Ways to Improve Your Relationships', 'https://picsum.photos/300/200?id=17', 6, '2023-08-22', '2023-08-31', 2, 'Food trends and viral recipes', 115);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Reading', 'https://picsum.photos/300/200?id=15', 12, '2024-02-13', '2024-02-12', 3, 'Crafting tutorials and project ideas', 733);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Ultimate Guide to Skincare', 'https://picsum.photos/300/200?id=45', 3, '2023-12-21', '2024-05-10', 3, 'Wedding planning tools and resources', 647);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The History of Hip-Hop Music', 'https://picsum.photos/300/200?id=12', 4, '2023-09-20', '2024-04-20', 4, 'Home organization and decluttering tips', 127);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Exercise', 'https://picsum.photos/300/200?id=52', 8, '2023-10-30', '2024-03-02', 3, 'Financial planning workshops and seminars', 260);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Importance of Mental Health', 'https://picsum.photos/300/200?id=69', 4, '2023-12-18', '2024-05-22', 3, 'Sports news and updates', 498);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Tips for a Happy Life', 'https://picsum.photos/300/200?id=64', 4, '2024-03-09', '2024-05-06', 1, 'Pet care and adoption tips', 440);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Meditation', 'https://picsum.photos/300/200?id=31', 3, '2024-01-13', '2023-10-16', 3, 'Crafting challenges and community projects', 550);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Exercise', 'https://picsum.photos/300/200?id=79', 1, '2024-02-15', '2023-10-30', 2, 'Personal finance podcasts and resources', 204);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Art of Photography', 'https://picsum.photos/300/200?id=22', 8, '2023-06-25', '2024-02-17', 1, 'Career advice and job search tips', 593);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Art of Feng Shui', 'https://picsum.photos/300/200?id=73', 5, '2024-01-20', '2023-07-06', 3, 'Self-love and body positivity tips', 565);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Easy DIY Home Decor Projects', 'https://picsum.photos/300/200?id=3', 10, '2023-11-25', '2024-01-15', 3, 'Gaming re[view]s and recommendations', 570);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The History of Cinema', 'https://picsum.photos/300/200?id=18', 9, '2023-06-18', '2024-04-19', 3, 'Photography inspiration and tutorials', 611);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('10 Must-See Art Exhibitions', 'https://picsum.photos/300/200?id=85', 11, '2023-12-30', '2024-02-09', 2, 'Personal finance podcasts and resources', 443);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Importance of Mental Health', 'https://picsum.photos/300/200?id=53', 8, '2024-01-05', '2023-12-30', 2, 'A [Blog] about cooking delicious meals', 500);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Reading', 'https://picsum.photos/300/200?id=76', 4, '2023-08-03', '2024-05-22', 4, 'Crafting and DIY projects', 411);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Rise of Influencer Marketing', 'https://picsum.photos/300/200?id=23', 11, '2023-12-01', '2024-01-28', 3, 'Music re[view]s and recommendations', 797);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Importance of Self-Care', 'https://picsum.photos/300/200?id=59', 10, '2023-12-05', '2024-01-20', 2, 'History and culture exploration', 132);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Rise of Digital Nomads', 'https://picsum.photos/300/200?id=39', 12, '2023-08-06', '2024-05-14', 2, 'Crafting and DIY projects', 442);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Benefits of Regular Exercise', 'https://picsum.photos/300/200?id=38', 1, '2023-06-04', '2023-09-12', 1, 'Movie trivia and behind-the-scenes facts', 773);
insert into [Blog] ([title], [thumbnail], [author_id], [created_date], [update_date], [category_id], [brief_info], [view]) values ('The Ultimate Guide to Skincare', 'https://picsum.photos/300/200?id=28', 1, '2023-10-29', '2023-11-29', 1, 'Pet care and adoption tips', 993);
GO


insert into [BlogDetail] ([blog_id], [content]) values (42, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (48, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (20, 'Duis ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (49, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (24, 'Nulla ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (8, 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Proin nec purus.');
insert into [BlogDetail] ([blog_id], [content]) values (32, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (4, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (21, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (18, 'Etiam auctor');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'Suspendisse potenti. Nullam nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (43, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (24, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (17, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (2, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (22, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (39, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (5, 'Fusce ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (37, 'Duis ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (27, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (37, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (47, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (15, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (14, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (14, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (12, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (4, 'Lorem ipsum dolor sit amet');
insert into [BlogDetail] ([blog_id], [content]) values (44, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (9, 'Vivamus ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (47, 'Fusce ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (33, 'Quisque auctor');
insert into [BlogDetail] ([blog_id], [content]) values (38, 'Vivamus ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (3, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (49, 'Quisque auctor');
insert into [BlogDetail] ([blog_id], [content]) values (48, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (41, 'Nulla ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (13, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (27, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (40, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (14, 'consectetur adipiscing elit. Sed ut ultricies mauris.');
insert into [BlogDetail] ([blog_id], [content]) values (48, 'Fusce ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (35, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (40, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (29, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (35, 'Mauris ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (21, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (32, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (30, 'Quisque auctor');
insert into [BlogDetail] ([blog_id], [content]) values (4, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (12, 'Fusce vel libero nec nunc lacinia ultricies. Nulla facilisi.');
insert into [BlogDetail] ([blog_id], [content]) values (16, 'Vivamus ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (30, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (47, 'Lorem ipsum dolor sit amet');
insert into [BlogDetail] ([blog_id], [content]) values (30, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (16, 'Mauris ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (12, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (7, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (45, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (29, 'Nulla ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (26, 'Etiam auctor');
insert into [BlogDetail] ([blog_id], [content]) values (8, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (3, 'Vivamus ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (22, 'Aenean ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (29, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (28, 'Quisque auctor');
insert into [BlogDetail] ([blog_id], [content]) values (42, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (32, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (4, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (26, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (47, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (22, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (5, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (20, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (37, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (45, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (21, 'Duis ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (25, 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Proin nec purus.');
insert into [BlogDetail] ([blog_id], [content]) values (4, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (24, 'Donec auctor');
insert into [BlogDetail] ([blog_id], [content]) values (40, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (15, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (45, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (39, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (8, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (10, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (47, 'Nam auctor');
insert into [BlogDetail] ([blog_id], [content]) values (30, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (31, 'Vivamus ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (15, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (28, 'Fusce ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (45, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (6, 'Mauris ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (40, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (11, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (45, 'Curabitur auctor');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'Fusce vel libero nec nunc lacinia ultricies. Nulla facilisi.');
insert into [BlogDetail] ([blog_id], [content]) values (19, 'Fusce vel libero nec nunc lacinia ultricies. Nulla facilisi.');
insert into [BlogDetail] ([blog_id], [content]) values (48, 'Praesent auctor');
insert into [BlogDetail] ([blog_id], [content]) values (49, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (13, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (26, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (39, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (50, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (22, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (48, 'consectetur adipiscing elit. Sed ut ultricies mauris.');
insert into [BlogDetail] ([blog_id], [content]) values (14, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (25, 'Curabitur auctor');
insert into [BlogDetail] ([blog_id], [content]) values (31, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (11, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (18, 'Mauris ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (13, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (18, 'Suspendisse potenti. Nullam nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (46, 'Mauris ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (50, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (7, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (16, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (47, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (18, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (13, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (26, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (6, 'Nam auctor');
insert into [BlogDetail] ([blog_id], [content]) values (1, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (46, 'Cras ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (35, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (15, 'Duis ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (10, 'Curabitur auctor');
insert into [BlogDetail] ([blog_id], [content]) values (22, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (6, 'consectetur adipiscing elit. Sed ut ultricies mauris.');
insert into [BlogDetail] ([blog_id], [content]) values (31, 'consectetur adipiscing elit. Sed ut ultricies mauris.');
insert into [BlogDetail] ([blog_id], [content]) values (33, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (35, 'Cras ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (9, 'Quisque auctor');
insert into [BlogDetail] ([blog_id], [content]) values (27, 'Nulla ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (12, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (19, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (39, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (36, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (13, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (43, 'Mauris ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (40, 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (20, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (34, 'Etiam auctor');
insert into [BlogDetail] ([blog_id], [content]) values (6, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (15, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (44, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (36, 'Maecenas ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (35, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (50, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (45, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (2, 'Donec auctor');
insert into [BlogDetail] ([blog_id], [content]) values (49, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (34, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (45, 'Curabitur auctor');
insert into [BlogDetail] ([blog_id], [content]) values (27, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (25, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (11, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (41, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (37, 'Fusce vel libero nec nunc lacinia ultricies. Nulla facilisi.');
insert into [BlogDetail] ([blog_id], [content]) values (14, 'Aenean ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (9, 'Aenean ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (32, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (40, 'Mauris ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (1, 'Fusce vel libero nec nunc lacinia ultricies. Nulla facilisi.');
insert into [BlogDetail] ([blog_id], [content]) values (50, 'Fusce ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (9, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (20, 'Vivamus ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (22, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (21, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (32, 'Cras ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (33, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (12, 'Etiam auctor');
insert into [BlogDetail] ([blog_id], [content]) values (10, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (36, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (24, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (22, 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.');
insert into [BlogDetail] ([blog_id], [content]) values (32, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (38, 'nec ultricies nunc justo sit amet purus.');
insert into [BlogDetail] ([blog_id], [content]) values (20, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (50, 'Aenean ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (5, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (33, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (46, 'Maecenas ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (3, 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.');
insert into [BlogDetail] ([blog_id], [content]) values (33, 'Fusce ac nunc nec nunc ultricies ultricies. Integer nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (10, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (29, 'Curabitur auctor');
insert into [BlogDetail] ([blog_id], [content]) values (34, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (13, 'Etiam auctor');
insert into [BlogDetail] ([blog_id], [content]) values (4, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (3, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (17, 'Sed auctor');
insert into [BlogDetail] ([blog_id], [content]) values (34, 'libero sed ultricies ultricies');
insert into [BlogDetail] ([blog_id], [content]) values (21, 'mi eros ultricies justo');
insert into [BlogDetail] ([blog_id], [content]) values (16, 'Suspendisse potenti. Nullam nec purus nec eros fermentum scelerisque.');
insert into [BlogDetail] ([blog_id], [content]) values (23, 'Sed auctor');
GO

insert into [Slider] (slider_image, [title], [backlink], [status], [notes]) values ('resources/images/slider/blog.png', 'Blog with QUYSY', 'http://localhost:9999/group6/blog-list', 1, 'See the wonder of blogs.');
insert into [Slider] (slider_image, [title], [backlink], [status], [notes]) values ('resources/images/slider/home1.png', 'WElCOME TO QUYSY', 'http://localhost:9999/group6/home', 0, 'Learn everthing you want');
insert into [Slider] (slider_image, [title], [backlink], [status], [notes]) values ('resources/images/slider/home2.png', 'Visit QUYSY', 'http://localhost:9999/group6/home', 1, 'Do test and do fun');
insert into [Slider] (slider_image, [title], [backlink], [status], [notes]) values ('resources/images/slider/jap.png', 'Japanese with us QUYSY', 'http://localhost:9999/group6/subject?search=&service=listAllSubjects&category=2', 0, 'So you can as good as native');
insert into [Slider] (slider_image, [title], [backlink], [status], [notes]) values ('resources/images/slider/math.png', 'See that MATH', 'http://localhost:9999/group6/subject', 0, 'That is pradise of maths lover');
insert into [Slider] (slider_image, [title], [backlink], [status], [notes]) values ('resources/images/slider/se.png', 'Software enginering', 'http://localhost:9999/group6/subject?search=&service=listAllSubjects&category=3', 1, 'Fun and fabulous');
GO



INSERT INTO [dbo].[DimensionType]([dimension_type_name],[description],[create_date],[update_date],[updated_by])
VALUES
('Academic', 'Academic subjects', '2024-06-30 10:44:31.907', '2024-07-25 12:44:31.907', 2),
('Language', 'Foreign languages', '2024-06-30 10:44:31.907', '2024-07-27 12:44:31.907', 2),
('Economics', 'Economic principles and applications', '2024-06-30 10:44:31.907', '2024-07-25 12:44:31.907', 2),
('Engineering', 'Engineering disciplines and practices', '2024-07-30 10:44:31.907', '2024-07-25 12:44:31.907', 2),
('Business', 'Business and management studies', '2024-06-30 10:44:31.907', '2024-07-04 12:44:31.907', 2),
('Technology', 'Information Technology', '2024-07-30 10:44:31.907', '2024-07-27 12:44:31.907', 2);

INSERT INTO [dbo].[SubjectDimension] ([dimension_name],[description],[create_date],[update_date],[updated_by])
VALUES 
('Mathematics','Study of numbers, quantities, and shapes.','2024-06-30 10:44:31.907','2024-07-27 12:44:31.907',2),
('Japanese','Study of the Japanese language, its structure, and literature.','2024-06-30 10:44:31.907','2024-06-30 12:44:31.907',2),
('Computer Science','Study of computers and computational systems.','2024-06-30 11:44:31.907','2024-06-30 12:44:31.907',2),
('Programming Fundamentals','Study of systems for storing, retrieving, and sending information.','2024-07-25 10:44:31.907','2024-07-26 12:44:31.907',2),
('Software Engineer','Study of systems for problem solving, gathering requirements, and working in group.','2024-07-26 10:44:31.907','2024-07-26 15:44:31.907',2),
('IOT','Study of systems for creating electronic devices','2024-06-30 10:44:31.907','2024-06-30 12:44:31.907',2),
('Networking','Study of computer networks, including the design, implementation, and management of network systems.','2024-06-30 10:44:31.907','2024-06-30 12:44:31.907',2);

INSERT INTO [dbo].[Dimensions] ([dimension_id],[dimension_type_id],[subject_id])
VALUES 
    (1,1,1),
	(1,1,2),
	(2,2,3),
	(2,2,4),
	(4,3,5),
	(4,3,6),
	(5,3,7),
	(5,3,8),
	(5,3,9),
	(3,3,10),
	(4,3,11),
	(3,3,12),
	(3,3,13),
	(6,3,14),
	(5,3,15),
	(1,1,16),
	(7,3,17),
	(3,3,18),
	(4,3,19),
	(5,3,20),
	(4,3,13);

insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Physical Education', 'Nunc nec neque.', 1);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Biology', 'Aenean fermentum', 1);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Mathematics', 'nunc augue blandit nunc', 1);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Meteorology', 'Excepteur sint occaecat cupidatat non proident', 1);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Computer Science', 'Sed adipiscing.', 14);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Psychology', 'Vestibulum erat wisi', 6);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Engineering', 'elit eget tincidunt condimentum', 17);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Sociology', 'wisi.', 19);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Political Science', 'auctor et', 10);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Economics', 'Nullam sagittis.', 7);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Anthropology', 'Donec posuere vulputate arcu.', 15);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Art', 'Donec non enim in turpis pulvinar facilisis.', 3);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Philosophy', 'Pellentesque posuere.', 17);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Agriculture', 'Sed adipiscing.', 5);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Geography', 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Sed aliquam', 18);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Science', 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.', 1);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Zoology', 'nisi.', 5);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('History', 'sagittis tempus lacus enim ac dui.', 20);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Physics', 'posuere ac', 11);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Literature', 'quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', 8);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Music', 'Nullam sagittis.', 12);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Chemistry', 'gravida non', 2);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Botany', 'Nam dui mi', 11);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Oceanography', 'ac placerat dolor lectus quis orci.', 5);
insert into [SubjectTopic] ([topic_name], [description], [subject_id]) values ('Languages', 'tincidunt quis', 2);
GO


   INSERT INTO [dbo].[LessonTopic] ([lesson_topic_name])
VALUES 
    ('Introduction'),
    ('Main Content'),
	('Chapter'),
	('Grading');
 GO


INSERT INTO [dbo].[Registration] (
    [user_id], [subject_id], [package_id], [valid_to], [total_cost], [notes], [last_updated_by],
    [full_name], [email], [mobile], [gender], [registration_status]
) VALUES
(1, 1, 1, '2024-12-30', 40.00, 'Note 1', 1, 'Ngo Ba Vu Hung', 'hungnbvhe186953@fpt.edu.vn', '012345678', 1, 1),

           (2, 2, 2, '2024-12-30', 45.00, 'Note 2', 2, 'Nong Duc Thai', 'thaindhe186951@fpt.edu.vn', '7583479857', 1, 2),

           (3, 3, 3, '2024-12-30', 50.00, 'Note 3', 3, 'Marketing Person', 'market@fpt.edu.vn', '012345678', 1, 3),

           (4, 4, 4, '2024-12-30', 55.00, 'Note 4', 4, 'Admin', 'admin@fpt.edu.vn', '012345678', 1, 4),

           (5, 5, 5, '2024-12-30', 60.00, 'Note 5', 5, 'Expert', 'expert@fpt.edu.vn', '012345678', 1, 1),

           (6, 6, 6, '2024-12-30', 65.00, 'Note 6', 6, 'John Doe', 'john.doe@fpt.edu.vn', '0987654321', 1, 2),

           (7, 7, 7, '2024-12-30', 70.00, 'Note 7', 7, 'Jane Doe', 'jane.doe@fpt.edu.vn', '0987654322', 0, 3),

           (8, 8, 8, '2024-12-30', 75.00, 'Note 8', 8, 'Alice Smith', 'alice.smith@fpt.edu.vn', '0987654323', 0, 4),

           (9, 9, 9, '2024-12-30', 80.00, 'Note 9', 9, 'Bob Johnson', 'bob.johnson@fpt.edu.vn', '0987654324', 1, 1),

           (10, 10, 10, '2024-12-30', 85.00, 'Note 10', 10, 'Charlie Brown', 'charlie.brown@fpt.edu.vn', '0987654325', 1, 2),

           (11, 11, 11, '2024-12-30', 90.00, 'Note 11', 11, 'David Wilson', 'david.wilson@fpt.edu.vn', '0987654326', 1, 3),

           (12, 12, 12, '2024-12-30', 95.00, 'Note 12', 12, 'Emily Davis', 'emily.davis@fpt.edu.vn', '0987654327', 0, 4)
GO

INSERT INTO [dbo].[Quiz]
           ([quiz_name]
           ,[number_of_question]
           ,[duration]
		   ,[pass_rate]
           ,[level_id]
           ,[subject_id]
           ,[quiz_type_id]
		   ,[status]
		   ,[creator_id]
		   ,[last_updated_by])
     VALUES
           ('MAD_SP24',6,15,70,2,1,1,1,1,1),
		   ('MAD_SP24_Retake',5,20,50,1,1,1,1,1, 2),
		   ('MAD_FA23',10,15,80,3,1,1,1,1, 2),
		   ('MAD_Practice_01',15,90,20,1,1,1,2,1, 2),
		   ('PRF_SP24', 10, 30, 0.7, 1, 5, 1,0, 1, 2),
		   ('MAE_SP24', 20, 45, 0.75, 2, 2, 1,0,1, 2),
		   ('JPD_SP24', 15, 40, 0.8, 3, 3, 1,0,1, 2),
		   ('JPD2_SP24', 12, 35, 0.65, 1, 4, 1,0,1, 2),
		   ('PRF_SU24', 18, 50, 0.7, 2, 5, 1,0,1, 2),
		   ('PRJ_SP24', 22, 60, 0.8, 3, 6, 1,0,1, 2),
	       ('SWP_SP24', 14, 33, 0.7, 1, 7, 1,0,1, 2),
		   ('SWT_SP24', 25, 55, 0.75, 2, 8, 1,0,1, 2),
		   ('SWR_SP24', 17, 45, 0.8, 3, 9, 1,0,1, 2),
           ('CEA_SP24', 20, 40, 0.65, 1, 10, 1,0,1, 2),
           ('CSD_SP24', 12, 50, 0.7, 2, 11, 1,0,1, 2),
           ('CSI_SP24', 18, 35, 0.75, 3, 12, 1,0,1, 2),
           ('DBI_SP24', 22, 60, 0.8, 1, 13, 1,0,1, 2),
           ('IOT_SP24', 15, 33, 0.7, 2, 14, 1,0,1, 2),
           ('ITE_SP24', 25, 55, 0.75, 3, 15, 1,0,1, 2),
           ('MAS_SP24', 17, 45, 0.8, 1, 16, 1,0,1, 2),
           ('NWC_SP24', 20, 40, 0.65, 2, 17, 1,0,1, 2),
           ('OSG_SP24', 12, 50, 0.7, 3, 18, 1,0,1, 2),
           ('PRO_SP24', 18, 35, 0.75, 1, 19, 1,0,1, 2),
           ('SWE_SP24', 22, 60, 0.8, 2, 20, 1,0,1, 2),
           ('MAD_SP20', 15, 33, 0.7, 3, 1, 1,0,1, 2),
           ('MAE_SU24', 25, 55, 0.75, 1, 2, 1,0,1, 2),
           ('JPD_SU24', 17, 45, 0.8, 2, 3, 1,0,1, 2),
           ('JPD2_SP24', 20, 40, 0.65, 3, 4, 1,0,1, 2),
           ('PRF_FA24', 12, 50, 0.7, 1, 5, 1, 0, 1, 2),
           ('PRJ_SU24', 18, 35, 0.75, 2, 6, 1, 0, 1, 2),
           ('SWP_SU24', 22, 60, 0.8, 3, 7, 1, 0, 1, 2),
           ('SWT_FA24', 15, 33, 0.7, 1, 8, 1, 0, 1, 2),
           ('SWR_FA24', 25, 55, 0.75, 2, 9, 1, 0, 1, 2),
           ('CEA_SP22', 17, 45, 0.8, 3, 10, 1, 0, 2, 2);
GO
INSERT INTO [dbo].[Lesson] 
    ([lesson_name], [lesson_order], [youtube_link], [html_content], [lesson_type_id], [subject_id], [topic_id], [package_id], [status], [created_date], [updated_date], [quiz_id], [updated_by])
VALUES 
    ('Lesson1', 1, '#', '<html><body>Content</body></html>', 1, 1, 1, 1, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson2', 2, '#', '<html><body>Content</body></html>', 2, 1, 1, 1, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson3', 3, '#', '<html><body>Content</body></html>', 3, 1, 1, 1, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson4', 2, '#', '<html><body>Content</body></html>', 2, 1, 1, 1, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson5', 1, '#', '<html><body>Content</body></html>', 2, 1, 1, 1, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson6', 3, '#', '<html><body>Content</body></html>', 3, 1, 1, 1, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson7', 3, '#', '<html><body>Content</body></html>', 1, 1, 1, 1, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson8', 2, '#', '<html><body>Content</body></html>', 2, 1, 1, 1, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson9', 1, '#', '<html><body>Content</body></html>', 3, 1, 1, 1, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson10', 1, '#', '<html><body>Content</body></html>', 3, 1, 1, 1, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson11', 1, '#', '<html><body>Content</body></html>', 2, 1, 1, 1, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson12', 2, '#', '<html><body>Content</body></html>', 3, 1, 1, 1, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson13', 2, '#', '<html><body>Content</body></html>', 2, 1, 1, 1, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson14', 3, '#', '<html><body>Content</body></html>', 2, 1, 1, 1, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson15', 2, '#', '<html><body>Content</body></html>', 1, 1, 1, 1, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson16', 1, '#', '<html><body>Content</body></html>', 1, 2, 2, 2, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson17', 2, '#', '<html><body>Content</body></html>', 2, 2, 2, 2, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson18', 3, '#', '<html><body>Content</body></html>', 3, 2, 2, 2, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson19', 4, '#', '<html><body>Content</body></html>', 1, 2, 2, 2, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson20', 5, '#', '<html><body>Content</body></html>', 2, 2, 2, 2, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson21', 6, '#', '<html><body>Content</body></html>', 3, 2, 2, 2, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson22', 7, '#', '<html><body>Content</body></html>', 1, 2, 2, 2, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson23', 8, '#', '<html><body>Content</body></html>', 2, 2, 2, 2, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson24', 9, '#', '<html><body>Content</body></html>', 3, 2, 2, 2, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson25', 10, '#', '<html><body>Content</body></html>', 1, 2, 2, 2, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson26', 11, '#', '<html><body>Content</body></html>', 2, 2, 2, 2, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson27', 12, '#', '<html><body>Content</body></html>', 3, 2, 2, 2, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson28', 13, '#', '<html><body>Content</body></html>', 1, 2, 2, 2, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson29', 14, '#', '<html><body>Content</body></html>', 2, 2, 2, 2, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson30', 15, '#', '<html><body>Content</body></html>', 3, 2, 2, 2, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson31', 1, '#', '<html><body>Content</body></html>', 1, 3, 3, 3, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson32', 2, '#', '<html><body>Content</body></html>', 2, 3, 3, 3, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson33', 3, '#', '<html><body>Content</body></html>', 3, 3, 3, 3, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson34', 4, '#', '<html><body>Content</body></html>', 1, 3, 3, 3, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson35', 5, '#', '<html><body>Content</body></html>', 2, 3, 3, 3, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson36', 6, '#', '<html><body>Content</body></html>', 3, 3, 3, 3, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson37', 7, '#', '<html><body>Content</body></html>', 1, 3, 3, 3, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson38', 8, '#', '<html><body>Content</body></html>', 2, 3, 3, 3, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson39', 9, '#', '<html><body>Content</body></html>', 3, 3, 3, 3, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson40', 10, '#', '<html><body>Content</body></html>', 1, 3, 3, 3, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson41', 11, '#', '<html><body>Content</body></html>', 2, 3, 3, 3, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson42', 12, '#', '<html><body>Content</body></html>', 3, 3, 3, 3, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson43', 13, '#', '<html><body>Content</body></html>', 1, 3, 3, 3, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson44', 14, '#', '<html><body>Content</body></html>', 2, 3, 3, 3, 1, GETDATE(), GETDATE(), 3, 1),
    ('Lesson45', 15, '#', '<html><body>Content</body></html>', 3, 3, 3, 3, 1, GETDATE(), GETDATE(), 4, 1),
    ('Lesson46', 16, '#', '<html><body>Content</body></html>', 1, 3, 3, 3, 1, GETDATE(), GETDATE(), 5, 1),
    ('Lesson47', 17, '#', '<html><body>Content</body></html>', 2, 3, 3, 3, 1, GETDATE(), GETDATE(), 8, 1),
    ('Lesson48', 18, '#', '<html><body>Content</body></html>', 3, 3, 3, 3, 1, GETDATE(), GETDATE(), 12, 1),
    ('Lesson49', 19, '#', '<html><body>Content</body></html>', 1, 3, 3, 3, 1, GETDATE(), GETDATE(), 2, 1),
    ('Lesson50', 20, '#', '<html><body>Content</body></html>', 2, 3, 3, 3, 1, GETDATE(), GETDATE(), 3, 1);

INSERT INTO [dbo].[Question]
           ([question_detail]
           ,[status]
           ,[question_type_id]
           ,[dimension_id]
           ,[level_id]
           ,[lesson_id]
           ,[topic_id]
           ,[creator_id]
           ,[last_updated_by])
     VALUES
           ('What is the value of 1 + 1 ?', 1, 2, 1, 1, 1, 1, 2, 2),
           ('What is the value of 2 + 2 ?', 1, 2, 1, 2, 2, 1, 2, 2),
           ('What is the value of 5 - 3 ?', 1, 2, 1, 2, 3, 1, 2, 2),
           ('What is 10% of 50?',1,3, 1, 3, 1, 2, 2, 2),
           ('What is the value of 3^2?',1, 3, 1, 1, 2, 2, 2, 2),
           ('2 + 3 = 5?',1, 1, 1, 2, 3, 2, 2, 2),
           ('What is the square root of 16?',1, 3, 1, 2, 1, 3, 2, 2),
           ('Is 7 a prime number?',1, 1, 1, 3, 2, 3, 2, 2),
           ('What is the value of 8 / 2?',1, 2, 1, 2, 3, 3, 2, 2),
           ('What is the value of 7 * 3?',1, 2, 1, 2, 2, 1, 2, 2),
           ('What is the result of 12 - 3?',1, 2, 1, 2, 3, 1, 2, 2),
           ('What is the value of 5 + 7?',1, 2, 1, 3, 3, 1, 2, 2),
           ('Is 9 divisible by 3?',1, 1, 1, 3, 2, 1, 2, 2),
           ('What is 25% of 200?',1, 3, 1, 1, 1, 1, 2, 2),
           ('What is the cube of 3?',1, 3, 1, 1, 2, 1, 2, 2),
           ('What is the value of 6 * 6?',1, 2, 1, 1, 2, 2, 2, 2),
           ('What is 15% of 60?',1, 3, 1, 1, 1, 2, 2, 2),
           ('What is the result of 13 - 9?',1, 2, 1, 1, 3, 2, 2, 2),
           ('Is 12 an even number?',1, 1, 1, 1, 1, 3, 2, 2),
           ('What is the value of 9 + 6?',1, 2, 1, 1, 3, 3, 2, 2),
		   ('What is the value of 3 + 5?',1, 2, 1, 3, 2, 3, 2, 2),
		   ('What is the value of 7 - 3?',1, 2, 1, 2, 1, 1, 2, 2),
		   ('What is 20% of 80?',1, 3, 1, 2, 1, 1, 2, 2),
		   ('What is the value of 3^2?',1, 3, 1, 3, 2, 1, 2, 2),
		   ('5 + 5 = 10?',1, 1, 1, 1, 2, 2, 2, 2),
		   ('What is the square root of 81?',1, 3, 1, 2, 2, 2, 2, 2),
			('Is 11 a prime number?',1, 1, 1, 1, 1, 2, 2, 2),
			('What is the value of 9 / 3?',1, 2, 1, 2, 3, 3, 2, 2),
			('What is the value of 6 * 3?',1, 2, 1, 2, 2, 3, 2, 2),
			('What is the result of 20 - 5?',1, 2, 1, 1, 3, 3, 2, 2),
			('What is the value of 8 + 7?',1, 2, 1, 3, 3, 1, 2, 2),
			('Is 15 divisible by 5?',1, 1, 1, 3, 2, 1, 2, 2),
			('What is 50% of 300?',1, 3, 1, 1, 1, 1, 2, 2),
			('What is the cube of 3?',1, 3, 1, 3, 2, 2, 2, 2),
			('What is the value of 7 * 7?',1, 2, 1, 2, 2, 2, 2, 2),
			('What is 10% of 100?',1, 3, 1, 3, 1, 2, 2, 2),
			('What is the result of 18 - 8?',1, 2, 1, 1, 3, 3, 2, 2),
			('Is 20 an even number?',1, 1, 1, 1, 1, 3, 2, 2),
			('What is the value of 10 + 12?',1, 2, 1, 2, 3, 3, 2, 2),
			('What is the value of 6 - 3?',1, 2, 1, 1, 1, 1, 2, 2)

GO



INSERT INTO [dbo].[Answer]
           ([question_id]
           ,[answer_detail]
           ,[is_correct])
     VALUES
           (1,'2',1),
		   (1,'1',0),
		   (1,'7',0),
		   (1,'3',0),
		   (2,'1',0),
		   (2,'4',1),
		   (2,'3',0),
		   (2,'5',0),
		   (3,'2',1),
		   (3,'1',0),
		   (4,'5',1),
		   (5,'9',1),
		   (6,'True',1),
		   (6,'False',0)
GO

INSERT INTO [dbo].[Explanation]
           ([explanation_detail]
           ,[source]
           ,[page]
           ,[question_id])
     VALUES
           ('The value of 1 + 1 is 2 because adding 1 and 1 gives us 2.','Basic Math Textbook','Page 15', 1),
           ('The value of 2 + 2 is 4 because adding 2 and 2 gives us 4.','Basic Math Textbook','Page 16', 2),
           ('The value of 5 - 3 is 2 because subtracting 3 from 5 gives us 2.','Basic Math Textbook','Page 17', 3),
           ('10% of 50 is calculated as 0.10 * 50, which equals 5.','Basic Math Textbook','Page 18', 4),
           ('3^2 means 3 raised to the power of 2, which is 3 * 3 and equals 9.','Basic Math Textbook','Page 19', 5),
           ('2 + 3 equals 5 as adding 2 and 3 gives us 5, hence the statement is True.','Basic Math Textbook','Page 20', 6)

GO


INSERT INTO [dbo].[QuizQuestion]
           ([quiz_id]
           ,[question_id])
     VALUES
           (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),
           (2,1),(2,2),(2,3),(2,4),(2,5),(2,6),
		   (2,7),(2,8),(2,9),(2,10),(2,11),(2,12),
		   (2,13),(2,14),(2,15),(2,16),(2,17),(2,18),
		   (2,19),(2,20)
GO


INSERT INTO [dbo].[QuizRecord]
           ([time_spent]
           ,[score]
           ,[user_id]
           ,[quiz_id])
     VALUES
           (1600,8,1,1),
		   (1600,8.5,2,1),
		   (1600,4.5,3,1),
		   (1600,2,4,1),
		   (1600,9,5,1)
GO


INSERT INTO [dbo].[AnswerRecord]
           ([is_marked]
           ,[answer_content]
           ,[selected_answer_id]
           ,[quiz_record_id]
           ,[question_id])
     VALUES
           (1,NULL,2,1,2),
		   (0,'3',2,2,3)
GO

INSERT INTO [dbo].[SettingType]([setting_type_name])
VALUES('Subject Dimension'),
('Roles'),
('Categories'),
('Test Types')
           
GO

INSERT INTO [dbo].[Settings]([value],[order],[status],[description],[setting_type_id])
VALUES('Computer Science',1,1,'Settings for Computer Science',1),
('Programming Fundamentals',2,1,'Settings for Programming Fundamentals',1),
('Japanese',3,1,'Settings for Japanese',1),
('Admins',4,1,'Settings for Admin',2),
('Customers',5,1,'Settings for Customers',2),
('Experts',6,1,'Settings for Experts',2),
('Linguistics',7,1,'Settings for Linguistics',3),
('Mathematics',8,1,'Settings for Math',3),
('Simulation Exam',9,1,'Settings for Simulation Exam',4),
('Quiz',10,1,'Settings for Quizzes',4),
('Finance',11,0,'Settings for Finance',3)

GO