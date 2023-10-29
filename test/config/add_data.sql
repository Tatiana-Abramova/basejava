truncate resume cascade;
insert into resume (uuid, full_name)
values('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'Иванов Иван'),
('057b95a9-3cdd-415b-b675-c4b2a2d09212', 'Мельников Георгий Макарович'),
('9c013952-2db0-4db0-a226-81c6164f486c', 'Лукьянов Даниил Игоревич'),
('dcfb5424-b5a3-4343-9a54-7c940029c76f', 'Нестерова Вера Дмитриевна'),
('f9b0dd17-cac5-4843-84fe-84100b514f1f', 'Крючкова Есения Михайловна'),
('f17481dd-3979-4fb9-af52-5527cc785aa5', 'Осипов Иван Всеволодович'),
('fdf846a0-24c5-4599-ae16-a56f2c4fe011', 'Савельева Алиса Матвеевна');

insert into contact (resume_uuid, "type", value)
values('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'PHONE', '41234123421'),
('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'MAIL', 'dfvefv@dcfdc.efe'),
('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'LINKEDIN', 'dfsf'),
('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'GITHUB', 'salksjdl');

insert into contact (resume_uuid, "type", value)
values('9c013952-2db0-4db0-a226-81c6164f486c', 'PHONE', '6555555'),
('9c013952-2db0-4db0-a226-81c6164f486c', 'MAIL', 'rrrr@dcfdc.efe'),
('9c013952-2db0-4db0-a226-81c6164f486c', 'HOME_PAGE', 'ttttttt'),
('9c013952-2db0-4db0-a226-81c6164f486c', 'STACKOVERFLOW', 'bbbbbb');

insert into contact (resume_uuid, "type", value)
values('fdf846a0-24c5-4599-ae16-a56f2c4fe011', 'PHONE', '3333333'),
('fdf846a0-24c5-4599-ae16-a56f2c4fe011', 'MAIL', 'gggg@dcfdc.efe'),
('fdf846a0-24c5-4599-ae16-a56f2c4fe011', 'HOME_PAGE', 'vvvvvvvv'),
('fdf846a0-24c5-4599-ae16-a56f2c4fe011', 'STACKOVERFLOW', 'uyyy');

insert into "section" (resume_uuid, "type", value)
values('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'PERSONAL', 'Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям'),
('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'OBJECTIVE', 'Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.'),
('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'ACHIEVEMENT', 'Организация команды и успешная реализация Java проектов
для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы система мониторинга показателей
 спортсменов на Spring Boot участие в проекте МЭШ на Play-2 многомодульный Spring Boot
+ Vaadin проект для комплексных DIY смет'),
('3acdea7e-d3b9-4b73-9931-725cdeefb1cd', 'QUALIFICATIONS', 'JEE AS: GlassFish (v2.1, v3), OC4J,
JBoss, Tomcat, Jetty, WebLogic, WSO2');

insert into "section" (resume_uuid, "type", value)
values('f9b0dd17-cac5-4843-84fe-84100b514f1f', 'PERSONAL', 'Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям'),
('f9b0dd17-cac5-4843-84fe-84100b514f1f', 'OBJECTIVE', 'Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.'),
('f9b0dd17-cac5-4843-84fe-84100b514f1f', 'ACHIEVEMENT', 'Организация команды и успешная реализация Java проектов
для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы система мониторинга показателей
 спортсменов на Spring Boot участие в проекте МЭШ на Play-2 многомодульный Spring Boot
+ Vaadin проект для комплексных DIY смет'),
('f9b0dd17-cac5-4843-84fe-84100b514f1f', 'QUALIFICATIONS', 'JEE AS: GlassFish (v2.1, v3), OC4J,
JBoss, Tomcat, Jetty, WebLogic, WSO2');
