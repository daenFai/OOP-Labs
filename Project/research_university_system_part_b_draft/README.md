# Research University System — Part B Models Draft

This is a Java console-based draft implementation for **Part B: Models / Classes**.
It follows the provided UML and Use Case diagrams with a few architecture improvements:

1. `Researcher` is an interface because it is a role/ability. A `Student`, `Teacher`, or `Employee` can be a researcher.
2. `Professor` extends `Teacher` and implements `Researcher`, because professors are always researchers.
3. `ResearchProfile` stores shared research data: `hIndex`, `papers`, `projects`, and `school`.
4. `Course` supports multiple instructors through `List<Teacher>`.
5. `RegistrationRequest` models the manager approval process.
6. `Mark` is connected to `Course`, so transcript entries are meaningful.

## Main packages

- `users` — User, Student, Employee, Teacher, Professor, Manager, Admin, ResearchStudent, ResearchEmployee
- `academic` — Course, Lesson, Mark, Transcript, RegistrationRequest
- `research` — Researcher, ResearchProfile, ResearchPaper, ResearchProject
- `communication` — Message, News
- `reports` — Report
- `logs` — UserActionLog
- `enums` — RegistrationStatus, LessonType, TeacherTitle, ManagerType, CourseStatus, School, UserRole
- `exceptions` — custom exceptions
- `comparators` — paper sorting strategies
- `services` — AuthService, CourseService, ResearchService, ReportService, UserService, NewsService, LogService
- `storage` — DataStore Singleton with serialization methods
- `facade` — UniversitySystem facade
- `factories` — UserFactory

## Design patterns used

- Singleton — `DataStore`
- Factory Method style — `UserFactory`
- Strategy — `Comparator<ResearchPaper>` implementations
- Observer style — `NewsService`
- Facade — `UniversitySystem`

## Compile and run

From this folder:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out Main
```
or
```bash
cd research_university_system_part_b_draft
chmod +x compile_run.sh
./compile_run.sh
```

## What the demo shows

- creating users
- login/authentication
- creating a course
- assigning multiple teachers to a course
- student course registration request
- manager approval
- teacher putting marks
- transcript and GPA calculation
- adding research papers
- sorting papers by citations/date/pages
- rejecting non-researcher from research project
- throwing exception for supervisor with h-index < 3
- generating academic report
- printing user action logs
