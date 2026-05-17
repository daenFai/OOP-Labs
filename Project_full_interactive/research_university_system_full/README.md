# Research-Oriented University Information System

Console-based Java OOP project for the discipline **OOP and Design**.

The project follows the UML and Use Case design discussed for a research-oriented university system. It contains models, services, custom exceptions, enums, serialization, comparators and design patterns.

## How to run

### macOS / Linux

```bash
chmod +x compile_run.sh
./compile_run.sh
```

### Windows CMD / PowerShell

```bat
compile_run.bat
```

### Git Bash on Windows

```bash
bash compile_run.sh
```

After launch, choose one of two modes:

```text
1. Run automatic demo
2. Open interactive console menu
0. Exit
```

Manual commands:

```bash
rm -rf out
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out Main
```

## How to generate HTML documentation

```bash
chmod +x generate_docs.sh
./generate_docs.sh
open docs/index.html
```

The repository already includes generated JavaDoc in the `docs/` folder.

## Main implemented requirements

- Required classes: `User`, `Employee`, `Teacher`, `Manager`, `Student`, `Admin`, `Course`, `Mark`, `Lesson`, `Researcher`, `ResearchPaper`, `ResearchProject`.
- `Researcher` is implemented as an interface.
- Professors are always researchers: `Professor extends Teacher implements Researcher`.
- Bachelor students can be researchers: `ResearchStudent extends Student implements Researcher`.
- Non-teacher employees can be researchers: `ResearchEmployee extends Employee implements Researcher`.
- 4th-year student supervisor validation is implemented.
- If supervisor h-index is lower than 3, `LowHIndexException` is thrown.
- If non-researcher tries to join a research project, `NotResearcherException` is thrown.
- A course can have more than one instructor.
- Students cannot register for more than 21 credits.
- Mark consists of first attestation, second attestation and final exam.
- Research papers can be sorted by citations, publication date and pages using comparators.
- Top cited researcher overall, by school and by year is implemented.
- Academic performance reports are generated.
- User action logs are stored.
- Users access the system through authentication.
- Serialization works through `DataStore.save()` and `DataStore.load()`.

## Design patterns used

1. **Singleton** — `DataStore`
2. **Factory Method** — `UserFactory`
3. **Strategy** — `Comparator<ResearchPaper>` implementations
4. **Observer** — `NewsService`
5. **Facade** — `UniversitySystem`

## Package structure

```text
src/
 ├── Main.java
 ├── academic/
 ├── communication/
 ├── comparators/
 ├── enums/
 ├── exceptions/
 ├── facade/
 ├── factories/
 ├── logs/
 ├── reports/
 ├── research/
 ├── services/
 ├── storage/
 └── users/
```

## Demo modes in Main.java

`Main.java` has two modes.

### 1. Automatic demo mode

This mode runs a complete prepared scenario without manual input. It is convenient for quick defense demonstration.

### 2. Interactive console menu

This mode asks for login and password, then opens a role-based menu for Student, Teacher, Manager, Admin, or Researcher. Demo accounts are printed in the console.

`Main.java` demonstrates:

1. User creation through factory.
2. Authentication success and failure.
3. Course creation and multiple instructors.
4. Lecture/practice lessons.
5. Course registration request and manager approval.
6. 21-credit limit exception.
7. Teacher putting marks.
8. Student transcript and GPA.
9. Teacher rating.
10. Academic report generation.
11. Employee messages.
12. News observer notifications.
13. Research papers and research projects.
14. `NotResearcherException`.
15. `LowHIndexException`.
16. Research paper sorting by citations, date and pages.
17. Top cited researchers.
18. Serialization save/load.
19. User action logs.

## Important architectural explanation

`Researcher` is an interface because researcher is a role, not a separate user type. A student, teacher, professor or employee can be a researcher. Java does not support multiple inheritance of classes, so using an interface keeps the inheritance hierarchy clean.

Research-related data is stored in `ResearchProfile`, which contains h-index, papers, projects and school.
