# 🎬 Movie REST API (Week 5)

> 웹 서비스 개발 01분반 · 5주차 과제 · 박찬 (22300330)


## ① 프로젝트 소개

### 주제와 관리하는 데이터
영화 정보를 등록·조회·수정·삭제하는 REST CRUD API입니다. Database 없이 Java Collection(`LinkedHashMap`)에 데이터를 저장합니다.

| 필드 | 타입 | 설명 |
|---|---|---|
| id | Long | 서버가 자동 생성하는 식별자 |
| title | String | 영화 제목 (필수) |
| director | String | 감독 |
| rating | int | 평점 (0 이상) |
| pubyear | int  | 개봉 연도 |
| category | String | 장르 (필수) |

### 프로젝트 구조

```
src/main/java/org/example/week5_webservice
├── Week5WebserviceApplication.java   # 시작 지점
├── controller
│   └── MovieController.java          # URL·HTTP Method 처리
├── service
│   └── MovieService.java             # CRUD 처리, 검증, DTO 변환
├── repository
│   ├── MovieRepository.java          # 저장소 인터페이스
│   └── MemoryMovieRepository.java    # LinkedHashMap 기반 구현체
├── domain
│   └── Movie.java                    # 저장소에서 관리하는 데이터
└── dto
    ├── MovieRequest.java             # 클라이언트 → 서버
    └── MovieResponse.java            # 서버 → 클라이언트
```

요청 처리 흐름: `MovieController → MovieService → MovieRepository(Interface) → MemoryMovieRepository → LinkedHashMap`

### 로컬 실행 방법
1. IntelliJ에서 프로젝트를 열고 `Week5WebserviceApplication`을 실행합니다.
2. 콘솔에 `Tomcat started on port 8080`이 나오면 `http://localhost:8080/api/movies`로 요청합니다.

Docker로 실행할 경우 프로젝트 최상위의 `Dockerfile`로 이미지를 빌드하고 포트를 `8080:8080`으로 연결해 실행합니다.

### API Endpoint

| Method | URL | 기능 | 성공 | 실패 |
|---|---|---|---|---|
| POST | `/api/movies` | 영화 등록 | 201 Created | 400 (잘못된 입력) |
| GET | `/api/movies` | 전체 조회 | 200 OK | - |
| GET | `/api/movies/{id}` | 단건 조회 | 200 OK | 404 Not Found |
| PUT | `/api/movies/{id}` | 영화 수정 | 200 OK | 400 / 404 |
| DELETE | `/api/movies/{id}` | 영화 삭제 | 204 No Content | 404 Not Found |
| GET | `/api/movies/category?category={장르}` | 장르로 필터링 (확장) | 200 OK | 400 (파라미터 누락) |

### 요청·응답 JSON 예시

**POST `/api/movies`** 요청
```json
{
  "title": "Inception",
  "director": "Nolan",
  "rating": 9,
  "pubyear": 2010,
  "category": "SF"
}
```

응답 `201 Created`
```json
{
  "id": 1,
  "title": "Inception",
  "director": "Nolan",
  "rating": 9,
  "pubyear": 2010,
  "category": "SF"
}
```

### Repository와 배포 URL
- GitHub Organization Repository: https://github.com/2026-2-WebService/assign05-c01-22300330
- Personal GitHub Repository: https://github.com/underground-man/2026web_week5
- 배포 URL: https://two026web-week5.onrender.com 
- (API 경로:https://two026web-week5.onrender.com/api/movies)

---

## ② 개발환경 및 Dependency

| 항목 | 작성 내용 |
|---|---|
| IDE | IntelliJ IDEA 2026.1.1 |
| JDK | Eclipse Temurin 17.0.20.1 |
| Spring Boot | 4.1.1 |
| Build Tool | Gradle 9.7.1 (Gradle Wrapper) |
| 데이터 저장 | `LinkedHashMap<Long, Movie>` |
| 배포 환경 | Docker + Render Web Service (https://two026web-week5.onrender.com) |

### 사용한 Dependency

| Dependency | 필요한 이유 |
|---|---|
| `spring-boot-starter-webmvc` | `@RestController`, `@GetMapping` 등으로 REST API를 만들고, 내장 Tomcat으로 서버를 실행하며, 객체를 JSON으로 변환하기 위해 사용했습니다. |
| `spring-boot-starter-webmvc-test` | 프로젝트 생성 시 기본으로 포함된 테스트용 Dependency입니다. |

---

## ③ Solution 분석


**Q2. 새 책의 ID는 어느 메서드에서 생성하나요?**
A. `MemoryBookRepository.save()`에서 생성된다.

**Q3. `BookRequest`, `Book`, `BookResponse`는 각각 왜 필요한가요?**
A. BookRequest와 BookResonse는 DTO로써 프로그램의 캡슐화를 위해 BookRequest의 경우 controller와 service간의 객체 정보를, BookResponse는 service와 repository간 데이터를 주고받기 위해 쓰인다. Book은 repository 내에서 데이터를 사용하는데 쓰인다.


**Q6. `findAll()`은 `List<Book>`을 어떻게 `List<BookResponse>`로 바꾸나요?**
A. `BookService.findAll()`에서  BookSesponse를 가진 새로운 ArrayList를 만들어서 그안에 toResponse()함수로 값변환해 집어넣는다.


**Q7. `create()`와 `delete()`에서 `ResponseEntity`를 사용하는 이유는?**
A. 객체를 그냥 반환하면 항상 200 OK만 답변으로 제출된다. 다만 create, delete 각각 새로 만들어짐, 본문 없음이라는 상태를 표시하기위해 ResponseEntitiry 를 사용한다.

**Q8. 서버를 재시작하면 등록한 데이터는 어떻게 되나요?**
A. 없어진다.
---

## ④ 개발 과정 요약

**1단계. 프로젝트 생성과 Solution 분석**
- IntelliJ New Project → Spring Boot로 Gradle, JDK 17, Spring Web 프로젝트를 만들고 GitHub Repository와 연결했습니다.
- STUDY_GUIDE를 따라 Solution의 `BookController → BookService → BookRepository → MemoryBookRepository` 흐름을 읽었습니다.

**2단계. Domain·DTO 설계**
- 주제를 영화로 정하고 id 외 5개 필드로 `Movie`(Domain), `MovieRequest`, `MovieResponse`를 작성했습니다.

**3단계. Repository 구현**
- `MovieRepository` 인터페이스와 `MemoryMovieRepository`를 작성했습니다. `LinkedHashMap`을 써서 전체 조회 시 등록 순서가 유지되게 했고, `save()`에서 id를 증가시켜 부여합니다.
- 확인: 처음에 `import java.awt.print.Book`이 잘못 들어가 "추상 메서드를 구현해야 한다" 에러가 났고, import를 고쳐 해결했습니다.

**4단계. Service·Controller 구현**
- `MovieService`에 `create()`, `findAll()`, `findById()`, `update()`, `delete()`와 private 도우미 `findBook()`(404 처리), `toResponse()`(변환)를 작성했습니다.
- `MovieController`에서 각 메서드를 POST/GET/PUT/DELETE에 연결하고 `create()`는 201, `delete()`는 204를 반환하도록 했습니다.
- 확인: Postman으로 등록 → 전체 조회 → 단건 조회 → 수정 → 수정 결과 조회 → 삭제 → 삭제한 ID 조회(404)까지 확인했습니다.

**5단계. 기능 확장과 배포**
- 잘못된 입력 400 처리와 장르 필터링을 추가하고 Postman으로 확인했습니다.
- 멀티 스테이지 Dockerfile을 작성해 로컬 Docker에서 실행을 확인한 뒤 Render에 배포했습니다.

---

## ⑤ 기능 수정·확장

### A. 잘못된 입력 처리 (400 Bad Request)

**추가한 이유**: 제목이나 장르가 비어 있는 영화, 평점이 음수인 영화가 저장되면 조회·필터 결과가 의미 없어지기 때문입니다. 특히 장르가 비어 있으면 B의 장르 필터에서 찾을 수 없는 데이터가 생깁니다.

**수정한 클래스와 메서드**
- `MovieService.che()`: `title`이 `null`이거나 공백이면, `rating`이 음수면, `category`가 `null`이거나 공백이면 `ResponseStatusException(HttpStatus.BAD_REQUEST, ...)`를 던집니다.
- `MovieService.create()`: `repository.save()` 전에 검사를 호출해 잘못된 값이 저장되지 않게 했습니다.
- `MovieService.update()`: `findBook()` 전에 검사를 호출합니다. 그래서 없는 id에 잘못된 값을 보내면 404보다 400이 먼저 나옵니다.

검증 방식으로 `@Valid` 어노테이션 방식과 Service 직접 검사를 비교했고, 새 Dependency 없이 404 처리(`findBook()`)와 같은 구조로 만들 수 있는 Service 직접 검사를 선택했습니다. 이때 `title == ""`처럼 `==`로 문자열을 비교하면 내용이 아닌 객체를 비교하므로 `isBlank()`를 사용했고, `null.isBlank()`에서 500 에러가 나지 않도록 `null` 검사를 먼저 두었습니다.

**테스트**

| 요청 | 예상 결과 | 실제 결과     |
|---|---|-----------|
| 정상 JSON으로 POST | 201 Created | 201 Created |
| `title` 없이 POST | 400 | 400 Bad Request |
| `rating: -1`로 POST | 400 | 400 Bad Request |
| `category: ""`로 POST | 400 | 
| 없는 id(999)에 잘못된 값으로 PUT | 400 (검사가 먼저) | 

응답 본문에는 `timestamp`, `status`, `error`, `path`만 포함되고 직접 작성한 메시지는 나오지 않았습니다. Spring Boot 기본 설정이 예외 메시지를 응답에 포함하지 않기 때문으로 보이며, 상태 코드로 동작을 확인했습니다.

### B. 조회 기능 확장: 장르(category)로 필터링

**추가한 이유**: 영화가 많아지면 원하는 장르만 골라 보는 기능이 가장 자주 쓰일 것이라고 생각했습니다.

**수정한 클래스와 메서드**
- `MovieService.findCat(String category)` ✏: `repository.findAll()`을 `for`문으로 돌면서 `category.equals(movie.getCategory())`인 영화만 `toResponse()`로 변환해 담습니다. 비교 기준을 요청 값 쪽에 두어, 저장된 영화의 category가 `null`이어도 예외가 나지 않게 했습니다.
- `MovieController`: `@GetMapping("/category")`와 `@RequestParam`으로 `?category=` 값을 받습니다. 처음에는 `@GetMapping`만 써서 기존 전체 조회와 주소가 겹쳤고, 별도 경로 `/category`로 분리했습니다.
- 걸러내는 판단은 Service의 책임이라고 보고 Repository는 수정하지 않았습니다.

**테스트**


---

## ⑥ 배포 과정 요약

### 빌드 및 배포 순서
1. `bootJar`로 실행용 jar(`build/libs/week5_webservice-0.0.1-SNAPSHOT.jar`)가 만들어지는지 확인
2. 프로젝트 최상위에 `Dockerfile`, `.dockerignore` 작성
3. IntelliJ의 Run on Docker로 로컬 컨테이너 실행(포트 `8080:8080`) 후 Postman 테스트
4. GitHub에 push
5. Render에서 New → Web Service로 Repository 연결, 환경 변수 `PORT=8080` 설정 후 배포
6. 배포 로그에서 `BUILD SUCCESSFUL`, `Tomcat started on port 8080`, `Your service is live` 확인

### 추가·수정한 파일과 설정
- `Dockerfile` (멀티 스테이지 빌드)
    - 1단계: `eclipse-temurin:17-jdk`에서 소스를 복사하고 `./gradlew clean bootJar`로 jar 생성. Windows에서 만든 `gradlew`는 실행 권한이 없을 수 있어 `chmod +x gradlew`를 추가
    - 2단계: `eclipse-temurin:17-jre`에 jar만 복사해 `java -jar app.jar`로 실행, `EXPOSE 8080`
    - 빌드는 컴파일러가 필요해 JDK를, 실행은 jar 실행만 하면 되므로 가벼운 JRE를 써서 최종 이미지 크기를 줄였습니다.
    - `build/` 폴더는 `.gitignore`로 GitHub에 올라가지 않기 때문에, Render가 GitHub 코드만으로 빌드할 수 있도록 Dockerfile 안에서 빌드하는 방식을 택했습니다.
- `.dockerignore`: 빌드에 불필요한 폴더 제외
- Render 환경 변수 `PORT=8080`: Render의 기본 포트는 10000이라 앱 포트(8080)를 명시

### 배포 중 발생한 문제와 해결
- IntelliJ의 New → Dockerfile이 만든 기본 틀은 `FROM ubuntu:latest`, `ENTRYPOINT ["top", "-b"]`로 Java가 없고 서버를 실행하지 않는 내용이어서, Java 17 이미지와 `java -jar` 실행으로 바꿨습니다.
- Gradle의 `jar` 태스크는 라이브러리가 빠진 jar를 만들어 실행할 수 없어서 `bootJar`를 사용했습니다.
- 이전 실습의 Dockerfile은 `EXPOSE 8093`이었는데 이번 앱은 8080으로 실행되어 8080으로 수정했습니다.
- 로컬 Docker 실행 전에 IntelliJ에서 실행 중이던 서버를 종료해 8080 포트 충돌을 피했습니다.

### 배포 URL로 확인한 요청과 응답

---

## ⑦ Weekly Report


### Key Learning
1. DTO를 사용해 Controller <--> Service <--> Repositoy 간의 연결을 Response, Request로 연결하는 의미와 방법에 대해 알수 있었아.
2. Sptring boot의 어노테이션 @Service @controller @repository 의 사용목적과 이유를 알수 있었고, Mapping 의 종류와 사용법, 
3. **Optional과 예외를 이용한 상태 코드 처리**: `Optional.ofNullable()`로 "없을 수도 있는 값"을 표현하고, `orElseThrow()`나 `throw new ResponseStatusException(...)`으로 예외를 던지면 Spring이 404·400 응답으로 바꿔 준다는 것을 배웠습니다. 예외를 직접 `try-catch`로 잡으면 오히려 상태 코드가 전달되지 않습니다.

### Problem & Solution
**문제**: 서버 실행 시 `APPLICATION FAILED TO START`와 함께 "`MovieService`의 생성자가 `MovieRepository` 타입의 빈을 필요로 하는데 찾을 수 없다"는 에러가 났습니다.
**원인**: `MemoryMovieRepository`에 `@Repository`가 없어 Spring이 저장소 객체를 만들지 않았고, `MovieService`에 주입할 대상이 없었습니다.
**해결**: 로그의 `Description:` 부분을 읽고 원인을 찾았고, 인터페이스가 아닌 구현체 `MemoryMovieRepository`에 `@Repository`를 붙여 해결했습니다. 이후 `Started Week5WebserviceApplication` 로그를 확인했습니다.

### Code Review


```java
private void che(MovieRequest m){
    if((m.title() == null || m.title().isBlank()) || m.rating()<0){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"값이 제대로 입력되지 않았습니다 (제목 없음 혹은 점수가 음수)");
    }

    if(m.category()==null || m.category().isBlank()){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"category가 제대로 입력되지 않았습니다");
    }
}
```

- `create()`와 `update()`가 같은 규칙을 쓰므로 검사를 private 메서드 하나로 모아 중복을 없앴습니다.
- `||`는 앞 조건이 참이면 뒤 조건을 검사하지 않기 때문에, `null` 검사를 `isBlank()`보다 앞에 두어 `NullPointerException`(500)을 막았습니다.
- 조건을 만족하지 않으면 `throw`로 즉시 메서드를 빠져나가므로, `create()`에서는 `repository.save()`가 실행되지 않아 잘못된 값이 저장되지 않습니다.
- 개선할 점: 첫 번째 `if`는 `title`과 `rating`을 함께 검사해 어떤 값이 틀렸는지 메시지로 구분되지 않으므로, 필드별로 나누면 더 좋습니다.

### AI Usage
- **질문한 내용**: 과제 흐름과 의도, `HashMap`/`LinkedHashMap`을 쓰는 이유, `Optional`과 `ofNullable`, `ResponseEntity`, `@PathVariable`/`@RequestParam`/`@RequestBody`의 차이, `@Service`·`@Repository`의 역할, 에러 메시지(추상 메서드 미구현, `Optional` 타입 불일치, 빈 미등록) 읽는 법, Dockerfile 구조와 Render 포트 설정
- **참고한 답변**: AI는 코드를 대신 작성하지 않고 개념 설명과 힌트를 주었고, 저는 그 설명을 보고 직접 코드를 작성했습니다. 예를 들어 `for`문 변환 방법은 관련 없는 예시로 설명을 듣고 `findAll()`과 `findCat()`을 직접 구현했습니다.
- **직접 확인·수정한 부분**: `title == ""` 비교를 `isBlank()`로 바꾸고 `null` 검사를 추가했으며, 수정·삭제가 모두 `@GetMapping`으로 되어 있던 것을 `@PutMapping`, `@DeleteMapping`으로 고쳤습니다. 모든 기능은 Postman으로 직접 상태 코드를 확인했습니다..
- **Readme.md 작성 초안** ai와의 간의 대화를 바탕으로 Readme에 필요한 내용및 초안을 만들었다.

### Reflection
- 예외 처리를 한곳에 모으는 `@RestControllerAdvice`를 공부해서, 400 응답에 직접 작성한 메시지가 나오도록 만들어 보고 싶습니다.
- service에서 findAll()함수 결과를 걸러내는 방식으로 조회 기능을 구현 했는데 MovieRepository에 검색 메서드를 추가하는 방식으로도 써보고 싶다.
