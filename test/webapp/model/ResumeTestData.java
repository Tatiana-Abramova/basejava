package webapp.model;

import java.time.LocalDate;
import java.util.List;

import static webapp.model.ContactType.*;
import static webapp.model.SectionType.*;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("uuid_1", "Григорий Кислин");
        resume.setContact(PHONE, "+7(921) 855-0482");
        resume.setContact(EMAIL, "gkislin@yandex.ru");
        resume.setContact(MESSENGERS, "skype:grigory.kislin");
        resume.setContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.setContact(GITHUB, "https://github.com/gkislin");
        resume.setContact(STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.setContact(HOME_PAGE, "http://gkislin.ru/");

        Section position = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(OBJECTIVE, position);

        Section personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(PERSONAL, personal);

        ListSection achievements = new ListSection();
        List<String> achievementsList = achievements.getList();
        achievementsList.add("Организация команды и успешная реализация Java проектов " +
                "для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, " +
                "система мониторинга показателей спортсменов на Spring Boot, " +
                "участие в проекте МЭШ на Play-2, " +
                "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\"," +
                "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). " +
                "Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                "Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        resume.setSection(ACHIEVEMENT, achievements);

        ListSection qualifications = new ListSection();
        List<String> qualificationList = qualifications.getList();
        qualificationList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.setSection(QUALIFICATIONS, qualifications);

        CompanySection experience = new CompanySection();
        List<Company> companies = experience.getCompanies();

        Company company1 = new Company("Java Online Projects", "http://javaops.ru/",
                new Period(
                        LocalDate.of(2013, 10, 1),
                        "Автор проекта",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        companies.add(company1);

        Company company2 = new Company("Wrike", "https://www.wrike.com/",
                new Period(
                        LocalDate.of(2014, 10, 1),
                        LocalDate.of(2016, 1, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами " +
                                "Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, " +
                                "Redis). Двухфакторная аутентификация, авторизация по OAuth1, " +
                                "OAuth2, JWT SSO."));
        companies.add(company2);
        resume.setSection(EXPERIENCE, experience);
        System.out.println(resume);
    }
}
