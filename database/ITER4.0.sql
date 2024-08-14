USE master;
GO

IF DB_ID('QPS4.0') IS NOT NULL
BEGIN
    ALTER DATABASE [QPS4.0] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE [QPS4.0];
END
GO

CREATE DATABASE [QPS4.0];
GO

USE [QPS4.0]
GO


CREATE TABLE [dbo].[Role] (
    [role_id] INT IDENTITY(1,1) NOT NULL,
    [role_name] NVARCHAR(25) NOT NULL,
    PRIMARY KEY CLUSTERED ([role_id] ASC)
);
GO

CREATE TABLE [dbo].[Category] (
    [category_id] INT IDENTITY(1,1) NOT NULL,
    [category_name] NVARCHAR(50) NOT NULL,
    PRIMARY KEY CLUSTERED ([category_id] ASC)
);
GO

CREATE TABLE [dbo].[RegistrationStatus] (
    [status_id] INT IDENTITY(1,1) NOT NULL,
    [status_name] NVARCHAR(50) NOT NULL,
    PRIMARY KEY CLUSTERED ([status_id] ASC)
);
GO

CREATE TABLE [dbo].[Subject] (
    [subject_id] INT IDENTITY(1,1) NOT NULL,
    [subject_name] NVARCHAR(50) NOT NULL,
	[thumbnail] NVARCHAR(255) NOT NULL,
    [category_id] INT NOT NULL,
    [is_featured] BIT NOT NULL,
    [status] NVARCHAR(50) NOT NULL,
    [tag_line] NVARCHAR(255) NULL,
    [brief_info] NVARCHAR(255) NULL,
    [description] NVARCHAR(MAX) NULL,
    [created_date] DATETIME DEFAULT GETDATE(),
    [expert_id] INT NULL,
    PRIMARY KEY CLUSTERED ([subject_id] ASC),
    FOREIGN KEY ([category_id]) REFERENCES [dbo].[Category]([category_id])
);
GO

CREATE TABLE [dbo].[Users] (
    [user_id] INT IDENTITY(1,1) NOT NULL,
    [email] NVARCHAR(100) NOT NULL UNIQUE,
    [password] NVARCHAR(255) NOT NULL,
    [full_name] NVARCHAR(100) NOT NULL,
    [gender] BIT NOT NULL,
    [role_id] INT NOT NULL,
    [status] BIT NOT NULL,
    [created_date] DATETIME DEFAULT GETDATE(),
	[avatar] NVARCHAR(MAX) NOT NULL,
	[reset_token] NVARCHAR(255),
	[reset_token_expiry] DATETIME,
	[mobile] NVARCHAR(15) NOT NULL,
    PRIMARY KEY CLUSTERED ([user_id] ASC),
    FOREIGN KEY ([role_id]) REFERENCES [dbo].[Role]([role_id])
);
GO

CREATE TABLE [dbo].[Blog] (
    [blog_id] INT IDENTITY(1,1) NOT NULL,
    [title] NVARCHAR(200) NOT NULL,
    [thumbnail] NVARCHAR(255) NOT NULL,
    [author_id] INT NOT NULL,
    [created_date] DATETIME DEFAULT GETDATE(),
    [update_date] DATETIME DEFAULT GETDATE(),
    [category_id] INT NOT NULL,
    [brief_info] NVARCHAR(255) NOT NULL,
    [view] INT DEFAULT 0,
    [status] BIT DEFAULT 1,
    [is_featured] BIT DEFAULT 0,
    PRIMARY KEY CLUSTERED ([blog_id] ASC),
    FOREIGN KEY ([category_id]) REFERENCES [dbo].[Category]([category_id]),
    FOREIGN KEY ([author_id]) REFERENCES [dbo].[Users]([user_id])
);
GO

CREATE TABLE [dbo].[BlogDetail] (
    [blog_detail_id] INT IDENTITY(1,1) NOT NULL,
    [blog_id] INT NOT NULL,
    [content] NVARCHAR(MAX) NOT NULL,
    PRIMARY KEY CLUSTERED ([blog_detail_id] ASC),
    FOREIGN KEY ([blog_id]) REFERENCES [dbo].[Blog]([blog_id])
);
GO

CREATE TABLE [dbo].[Package] (
    [package_id] INT IDENTITY(1,1) NOT NULL,
    [subject_id] INT NOT NULL,
    [package_name] NVARCHAR(100) NOT NULL,
    [duration] INT NOT NULL,
    [list_price] DECIMAL(10, 2) NOT NULL,
    [sell_price] DECIMAL(10, 2) NOT NULL,
    [status] NVARCHAR(50) NOT NULL,
    [description] NVARCHAR(MAX) NOT NULL,
    [created_date] DATETIME DEFAULT GETDATE(),
	[update_date] DATETIME DEFAULT GETDATE(),
	[updated_by] INT,
    PRIMARY KEY CLUSTERED ([package_id] ASC),
	FOREIGN KEY ([updated_by]) REFERENCES [dbo].[Users]([user_id]),
    FOREIGN KEY ([subject_id]) REFERENCES [dbo].[Subject]([subject_id])
);
GO

CREATE TABLE [dbo].[Registration] (
    [registration_id] INT IDENTITY(1,1) NOT NULL,
	[user_id] INT NULL,
	[subject_id] INT NOT NULL,    
	[package_id] INT NOT NULL,
    [register_date] DATETIME DEFAULT GETDATE(),
    [valid_from] DATETIME DEFAULT GETDATE(),
    [valid_to] DATETIME NOT NULL,
    [total_cost] FLOAT NOT NULL,
    [notes] NVARCHAR(MAX) NOT NULL,
	[last_updated_by] INT NULL,
    [full_name] NVARCHAR(255) NULL,
    [email] NVARCHAR(255) NULL,
    [mobile] NVARCHAR(15) NULL,
    [gender] BIT NULL,
	[updated_date] DATETIME DEFAULT GETDATE(),
    [registration_status] INT NOT NULL,
    PRIMARY KEY CLUSTERED ([registration_id] ASC),
    FOREIGN KEY ([package_id]) REFERENCES [dbo].[Package]([package_id]),
    FOREIGN KEY ([subject_id]) REFERENCES [dbo].[Subject]([subject_id]),
    FOREIGN KEY ([user_id]) REFERENCES [dbo].[Users]([user_id]),
    FOREIGN KEY ([last_updated_by]) REFERENCES [dbo].[Users]([user_id]),
    FOREIGN KEY ([registration_status]) REFERENCES [dbo].[RegistrationStatus]([status_id])
);
GO

CREATE TABLE [dbo].[DimensionType] (
    [dimension_type_id] INT IDENTITY(1,1) NOT NULL,
    [dimension_type_name] NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(MAX),
	[create_date] DATETIME DEFAULT GETDATE(),
	[update_date] DATETIME DEFAULT GETDATE(),
	[updated_by] INT,
	FOREIGN KEY ([updated_by]) REFERENCES [dbo].[Users]([user_id]),
    PRIMARY KEY CLUSTERED ([dimension_type_id] ASC)
);
GO

CREATE TABLE [dbo].[SubjectDimension] (
    [dimension_id] INT IDENTITY(1,1) NOT NULL,
    [dimension_name] NVARCHAR(100) NOT NULL,
    [description] NVARCHAR(MAX),
	[create_date] DATETIME DEFAULT GETDATE(),
	[update_date] DATETIME DEFAULT GETDATE(),
	[updated_by] INT,
	FOREIGN KEY ([updated_by]) REFERENCES [dbo].[Users]([user_id]),
    PRIMARY KEY CLUSTERED ([dimension_id] ASC)
);
GO

CREATE TABLE [dbo].[Dimensions] (
	id INT IDENTITY(1,1),
    [dimension_id] INT NOT NULL,
	[dimension_type_id] INT NOT NULL,
    [subject_id] INT NOT NULL,
    PRIMARY KEY CLUSTERED ([id] ASC),
	FOREIGN KEY ([dimension_id]) REFERENCES [dbo].[SubjectDimension]([dimension_id]),
	FOREIGN KEY ([dimension_type_id]) REFERENCES [dbo].[DimensionType]([dimension_type_id]),
	FOREIGN KEY ([subject_id]) REFERENCES [dbo].[Subject]([subject_id])
);
GO

CREATE TABLE [dbo].[SubjectTopic] (
	[topic_id] INT IDENTITY(1,1) NOT NULL,
	[topic_name] NVARCHAR(100) NOT NULL,
	[description] NVARCHAR(255) NOT NULL,
	[subject_id] INT NOT NULL,
	PRIMARY KEY CLUSTERED ([topic_id] ASC),
	FOREIGN KEY ([subject_id]) REFERENCES [dbo].[Subject]([subject_id])
);
GO

CREATE TABLE [dbo].[Slider] (
    [slider_id] INT IDENTITY(1,1) NOT NULL,
    [slider_image] NVARCHAR(255) NOT NULL,
    [title] NVARCHAR(255) NOT NULL,
    [backlink] NVARCHAR(255) NOT NULL,
    [status] BIT NULL,
    [notes] NVARCHAR(255) NOT NULL,
    PRIMARY KEY CLUSTERED ([slider_id] ASC)
);
GO

CREATE TABLE [dbo].[Level] (
    [level_id] INT IDENTITY(1,1) NOT NULL,
    [level_name] NVARCHAR(255) NOT NULL,
    PRIMARY KEY CLUSTERED ([level_id] ASC)
);
GO

CREATE TABLE [dbo].[QuizType] (
	[quiz_type_id] INT IDENTITY(1,1) NOT NULL,
	[quiz_type_name] NVARCHAR(20) NOT NULL
	PRIMARY KEY CLUSTERED ([quiz_type_id] ASC)
)

CREATE TABLE [dbo].[Quiz] (
    [quiz_id] INT IDENTITY(1,1) NOT NULL,
    [quiz_name] NVARCHAR(255) NOT NULL,
    [number_of_question] INT NOT NULL,
	[duration] INT NULL,
	[pass_rate] DECIMAL(4,2) NULL,
    [level_id] INT NOT NULL,
    [subject_id] INT NOT NULL,
	[quiz_type_id] INT NOT NULL,
	[status] BIT DEFAULT 1,
	[creator_id] INT NOT NULL,
	[last_updated_by] INT NOT NULL,
	[create_date] DATETIME DEFAULT GETDATE(),
	[update_date] DATETIME DEFAULT GETDATE(),
    PRIMARY KEY CLUSTERED ([quiz_id] ASC),
    FOREIGN KEY ([level_id]) REFERENCES [dbo].[Level]([level_id]),
    FOREIGN KEY ([subject_id]) REFERENCES [dbo].[Subject]([subject_id]),
	FOREIGN KEY ([quiz_type_id]) REFERENCES [dbo].[QuizType]([quiz_type_id]),
	FOREIGN KEY ([creator_id]) REFERENCES [dbo].[Users]([user_id]),
	FOREIGN KEY ([last_updated_by]) REFERENCES [dbo].[Users]([user_id])
);
GO

CREATE TABLE [dbo].[QuestionType] (
    [type_id] INT IDENTITY(1,1) NOT NULL,
    [type_name] NVARCHAR(255) NOT NULL,
    PRIMARY KEY CLUSTERED ([type_id] ASC)
);
GO

CREATE TABLE [dbo].[LessonType] (
    [lesson_type_id] INT IDENTITY(1,1) NOT NULL,
    [lesson_type_name] NVARCHAR(255) NOT NULL,
    PRIMARY KEY CLUSTERED ([lesson_type_id] ASC)
);
GO
  CREATE TABLE [dbo].[LessonTopic] (
    [lesson_topic_id] INT IDENTITY(1,1) NOT NULL,
    [lesson_topic_name] NVARCHAR(255) NOT NULL,
    PRIMARY KEY CLUSTERED ([lesson_topic_id] ASC)
);
GO
CREATE TABLE [dbo].[Lesson] (
    [lesson_id] INT IDENTITY(1,1) NOT NULL,
    [lesson_name] NVARCHAR(255) NOT NULL,
    [lesson_order] INT NOT NULL,
    [youtube_link] NVARCHAR(255) NULL,
    [html_content] NTEXT,
    [lesson_type_id] INT NOT NULL,
    [subject_id] INT NOT NULL,
    [topic_id] INT NOT NULL,
    [package_id] INT NOT NULL,
    [status] BIT NULL,
    [quiz_id] INT,
    [updated_by] INT,
    [created_date] DATETIME DEFAULT GETDATE(),
    [updated_date] DATETIME DEFAULT GETDATE(),
    PRIMARY KEY CLUSTERED ([lesson_id] ASC),
    FOREIGN KEY ([lesson_type_id]) REFERENCES [dbo].[LessonType]([lesson_type_id]),
    FOREIGN KEY ([subject_id]) REFERENCES [dbo].[Subject]([subject_id]),
    FOREIGN KEY ([topic_id]) REFERENCES [dbo].[LessonTopic]([lesson_topic_id]),
    FOREIGN KEY ([updated_by]) REFERENCES [dbo].[Users]([user_id]),
    FOREIGN KEY ([quiz_id]) REFERENCES [dbo].[Quiz]([quiz_id]),
    FOREIGN KEY ([package_id]) REFERENCES [dbo].[Package]([package_id])
);

CREATE TABLE [dbo].[Question] (
    [question_id] INT IDENTITY(1,1) NOT NULL,
    [question_detail] NVARCHAR(255) NOT NULL,
	[media] NVARCHAR(255) NULL,
	[create_date] DATETIME DEFAULT GETDATE(),
	[update_date] DATETIME DEFAULT GETDATE(),
    [status] BIT DEFAULT 0,
	[question_type_id] INT NOT NULL,
    [dimension_id] INT NOT NULL,
	[level_id] INT NOT NULL,
	[lesson_id] INT NOT NULL,
	[topic_id] INT NOT NULL,
	[creator_id] INT NOT NULL,
	[last_updated_by] INT NOT NULL,
    PRIMARY KEY CLUSTERED ([question_id] ASC),
    FOREIGN KEY ([question_type_id]) REFERENCES [dbo].[QuestionType]([type_id]),
    FOREIGN KEY ([dimension_id]) REFERENCES [dbo].[SubjectDimension]([dimension_id]),
	FOREIGN KEY ([level_id]) REFERENCES [dbo].[Level]([level_id]),
	FOREIGN KEY ([lesson_id]) REFERENCES [dbo].[Lesson]([lesson_id]),
	FOREIGN KEY ([topic_id]) REFERENCES [dbo].[SubjectTopic]([topic_id]),
);
GO

CREATE TABLE [dbo].[Answer] (
    [answer_id] INT IDENTITY(1,1) NOT NULL,
    [question_id] INT NOT NULL,
    [answer_detail] NVARCHAR(255) NOT NULL,
    [is_correct] BIT NOT NULL,
    PRIMARY KEY CLUSTERED ([answer_id] ASC),
    FOREIGN KEY ([question_id]) REFERENCES [dbo].[Question]([question_id])
);
GO

CREATE TABLE [dbo].[Explanation] (
    [explanation_id] INT IDENTITY(1,1) NOT NULL,
	[explanation_detail] NVARCHAR(255) NOT NULL,
    [source] NVARCHAR(255) NOT NULL,
    [page] NVARCHAR(255) NOT NULL,
	[question_id] INT NOT NULL,
    PRIMARY KEY CLUSTERED ([explanation_id] ASC),
    FOREIGN KEY ([question_id]) REFERENCES [dbo].[Question]([question_id])
);
GO


CREATE TABLE [dbo].[UserTemp] (
    [temp_id] INT IDENTITY(1,1) NOT NULL,
    [email] NVARCHAR(100) NOT NULL,
    [password] NVARCHAR(255) NOT NULL,
    [full_name] NVARCHAR(255) NOT NULL,
    [gender] BIT NOT NULL,
    [mobile] NVARCHAR(15) NOT NULL,
    [token] NVARCHAR(255) NOT NULL,
    [token_expiry] DATETIME NOT NULL,
    PRIMARY KEY CLUSTERED ([temp_id] ASC)
);
GO

CREATE TABLE [dbo].[QuizQuestion](
	[quiz_id] INT NOT NULL,
	[question_id] INT NOT NULL,
	FOREIGN KEY ([quiz_id]) REFERENCES [dbo].[Quiz]([quiz_id]),
	FOREIGN KEY ([question_id]) REFERENCES [dbo].[Question]([question_id])
);
GO

CREATE TABLE [dbo].[QuizRecord](
	[quiz_record_id] INT IDENTITY(1,1) NOT NULL,
	[create_date] DATETIME DEFAULT GETDATE(),
    [finish_date] DATETIME DEFAULT GETDATE(),
	[time_spent] INT,
	[score] DECIMAL(4,2) NULL,
	[user_id] INT NOT NULL,
	[quiz_id] INT NOT NULL,
    PRIMARY KEY CLUSTERED ([quiz_record_id] ASC),
	FOREIGN KEY ([user_id]) REFERENCES [dbo].[Users]([user_id]),
	FOREIGN KEY ([quiz_id]) REFERENCES [dbo].[Quiz]([quiz_id]),
);
GO

CREATE TABLE [dbo].[AnswerRecord](
	[answer_record_id] INT IDENTITY(1,1) NOT NULL,
	[is_marked] BIT DEFAULT 0,
	[answer_content] NVARCHAR(MAX) NULL,
	[selected_answer_id] INT NULL,
	[quiz_record_id] INT NULL,
	[question_id] INT NOT NULL,
	PRIMARY KEY CLUSTERED ([answer_record_id] ASC),
	FOREIGN KEY ([selected_answer_id]) REFERENCES [dbo].[Answer]([answer_id]),
    FOREIGN KEY ([quiz_record_id]) REFERENCES [dbo].[QuizRecord]([quiz_record_id]),
    FOREIGN KEY ([question_id]) REFERENCES [dbo].[Question]([question_id])
);
GO

CREATE TABLE [dbo].[SettingType](
	[setting_type_id] INT IDENTITY(1,1),
	[setting_type_name] NVARCHAR(200) NOT NULL,
	PRIMARY KEY CLUSTERED ([setting_type_id] ASC)
);

GO

CREATE TABLE [dbo].[Settings](
	[setting_id] INT IDENTITY(1,1),
	[value] NVARCHAR(200) NOT NULL,
	[order] INT NOT NULL,
	[status] BIT NOT NULL,
	[description] NVARCHAR(MAX),
	[setting_type_id] INT NOT NULL,
	PRIMARY KEY CLUSTERED ([setting_id] ASC),
	FOREIGN KEY ([setting_type_id]) REFERENCES [dbo].[SettingType]([setting_type_id]),
);

GO
