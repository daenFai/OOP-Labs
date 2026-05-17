# Requirements Checklist

| Requirement | Implementation |
|---|---|
| User | `users.User` abstract class |
| Employee | `users.Employee` abstract class |
| Teacher | `users.Teacher` |
| Manager | `users.Manager` |
| Student | `users.Student` |
| Admin | `users.Admin` |
| Course | `academic.Course` |
| Mark | `academic.Mark` |
| Lesson | `academic.Lesson` |
| Researcher | `research.Researcher` interface |
| ResearchPaper | `research.ResearchPaper` |
| ResearchProject | `research.ResearchProject` |
| Lesson type lecture/practice | `enums.LessonType` |
| More than one instructor per course | `Course.instructors: List<Teacher>` |
| 4th year student supervisor | `ResearchService.assignSupervisor()` |
| h-index < 3 custom exception | `LowHIndexException` |
| Teachers and students can be researchers | `Professor`, `ResearchStudent` |
| Non-teacher employee can be researcher | `ResearchEmployee` |
| Researcher papers and projects | `ResearchProfile` |
| Print papers sorted by comparator | `Researcher.printPapers(Comparator)` |
| Print all university papers sorted | `ResearchService.printAllPapers()` |
| Top cited researcher | `ResearchService.getTopCitedResearcher()` |
| Top cited researcher by school | `ResearchService.getTopCitedResearcherBySchool()` |
| Top cited researcher of year | `ResearchService.getTopCitedResearcherOfYear()` |
| Non-researcher cannot join project | `NotResearcherException` |
| Report generation | `ReportService`, `Report` |
| Comparable | `User`, `Course`, `ResearchPaper` |
| Comparators | `PaperCitationsComparator`, `PaperDateComparator`, `PaperPagesComparator` |
| equals/hashCode/toString | Implemented in major models |
| Serialization | `DataStore.save()`, `DataStore.load()` |
| Authentication | `AuthService` |
| Enums | `UserRole`, `LessonType`, `TeacherTitle`, `ManagerType`, `School`, etc. |
| Collections | Lists for users, courses, papers, projects, marks, logs |
| Java API usage | `LocalDate`, `LocalDateTime`, `UUID`, `ObjectOutputStream`, `Comparator`, collections |
| Low coupling/high cohesion | Models store data; services contain business logic; facade hides complexity |
| Design pattern 1 | Singleton: `DataStore` |
| Design pattern 2 | Factory Method: `UserFactory` |
| Design pattern 3 | Strategy: paper comparators |
| Design pattern 4 | Observer: `NewsService` |
| Design pattern 5 | Facade: `UniversitySystem` |
