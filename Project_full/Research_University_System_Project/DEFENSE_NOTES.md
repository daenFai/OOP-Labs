# Defense Notes

## Why is Researcher an interface?

`Researcher` is a role/ability, not a fixed user type. A student can be a researcher, a teacher can be a researcher, and an employee can be a researcher. Java does not allow multiple inheritance of classes, therefore `Researcher` is implemented as an interface.

## Where are Researcher attributes stored?

The interface has operations. The data is stored in `ResearchProfile`:

- h-index
- school
- papers
- projects

Classes such as `Professor`, `ResearchStudent` and `ResearchEmployee` contain a `ResearchProfile` field and implement `getResearchProfile()`.

## Why services are used?

Models should not contain all business logic. For example, course registration depends on student, course, status, credit limit and manager approval. Therefore the process is handled by `CourseService`.

## Why DataStore is Singleton?

A console system needs one central storage object that contains users, courses, papers, projects, requests, news and logs. Singleton ensures that all services work with the same storage instance.

## Why comparators are separate classes?

Sorting research papers can be done in different ways: by citations, date or pages. This is the Strategy pattern. We can change sorting behavior without changing `ResearchPaper`.

## What does Main.java do?

`Main.java` is a demo/test class. It creates demo data and shows that the system works: login, registration, marks, transcript, reports, research papers, exceptions, serialization and logs.
