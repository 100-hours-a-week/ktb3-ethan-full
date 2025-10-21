<details>
<summary><strong>1주차 2일차 수업 키워드</strong></summary>

| 키워드 | 정리 |
|:---|:---|
| JDK |  |
| JRE |  |
| JVM |  |
| 바이트코드 |  |
| 클래스로더 |  |
| 실행엔진(Execution Engine) |  |
| JIT 컴파일러 |  |
| JVM 메모리 영역(런타임 데이터 영역) |  |
| (선택)PermGen과 Metaspace |  |
| (선택)String pool |  |
| (선택)Garbage Collection |  |

</details>

<details>
<summary><strong>1주차 3일차 수업 키워드</strong></summary>

| 키워드 | 정리 |
|:---|:---|
| GC Root |  |
| Mark & Sweep & Compaction |  |
| Minor GC vs Major GC vs Full GC |  |
| Stop-The-World |  |
| GC 알고리즘 (Parallel GC, G1 GC) |  |
| primitive type |  |
| reference type |  |
| wrapper class |  |
| 오토박싱/언박싱 |  |
| String의 불변성 |  |
| String Constant Pool |  |
| 클래스 |  |
| 객체와 인스턴스 |  |
| 상속 |  |
| extends |  |
| super |  |
| (선택)다형성 |  |
| (선택)오버라이딩 |  |

</details>

<details>
<summary><strong>2주차 1일차 수업 키워드</strong></summary>

| 키워드 | 정리 |
|:---|:---|
| Primitive Type (기본 타입) |  |
| Reference Type (참조 타입) |  |
| Wrapper Class |  |
| 오토박싱/언박싱 |  |
| final 키워드 |  |
| static 키워드 |  |
| String의 불변성 |  |
| String Constant Pool |  |
| String vs StringBuilder vs StringBuffer |  |
| 컴파일 타임 다형성 vs 런타임 다형성 |  |
| 오버로딩(Overloading) |  |
| 오버라이딩(Overriding) |  |
| 업캐스팅(Upcasting) |  |
| 다운캐스팅(Downcasting) |  |
| instanceof |  |
| 동적 바인딩 |  |
| 접근 제어자 |  |
| getter/setter |  |
| 정보 은닉 |  |
| interface vs abstract class |  |
| default 메서드 |  |

</details>

<details>
<summary><strong>2주차 2일차 수업 키워드</strong></summary>

| 키워드 | 정리 |
|:---|:---|
| Thread | 하나의 프로세스 내에서, 동시에 실행될 수 있는 독립적인 실행 단위 |
| start()와 run()의 차이점 | start()는 스레드를 생성 후 실행하지만, run()은 현재 스레드에서 실행한다. |
| (선택) Java Thread의 생명주기 |  |
| Runnable | 스레드가 실행할 작업 코드를 정의하기 위한 표준 인터페이스 |
| ExecutorService | 스레드 풀 기반의 비동기 작업을 관리하는 "고수준 스레드 실행 서비스 인터페이스" |
| ThreadPoolExecutor |  |
| newFixedThreadPool / newCachedThreadPool / newSingleThreadExecutor | 지정된 갯수의 스레드를 사용하는 풀 생성 / 필요한 만큼 스레드를 무한정 생성하는 스레드 풀 / 하나의 스레드로 구성된 스레드 풀 |
| execute() vs submit() | 실행 결과나 예외 정보를 호출하지 않는 작업 시작 방식 vs 스레드 작업 결과 객체인 Future<T>를 반환하는 작업 시작 방식 |
| Future | Callable가 스레드 작업 후 반환하는 실행 결과 객체 |
| completableFuture |  |
| Callable vs Runnable | 결과를 반환하는 스레드 요청 형식 vs 결과를 반환하지 않는 요청 형식 |
| shutdown() vs shutdownNow() | 대기 큐의 남은 작업을 끝낸 후 정상 종료 vs 대기 큐의 작업을 취소하고, 스레드에 인터럽트를 요청하는 강제 종료 |
| Thread Pool | 스레드를 미리 생성해두고, 작업이 끝난 후 스레드를 재사용하기 위한 영역 |
| corePoolSize |  |
| maximumPoolSize |  |
| keepAliveTime |  |
| BlockingQueue | ExecutorService에 요청된 외부 작업이 스레드 수보다 많을 경우 FIFO 방식으로 저장하는 대기 큐 |
| ThreadPoolExecutor |  |
| RejectedExecutionHandler |  |
| awaitTermination() |  |

</details>

<details>
<summary><strong>3주차 2일차 수업 키워드</strong></summary>

| 키워드 | 정리 |
|:---|:---|
| TCP | 안정적이고 신뢰할 수 있는 연결 지향형 통신 프로토콜입니다. 3-way handshake로 흐름/혼잡 제어, 데이터 순서 등 높은 신뢰성을 보장한다. |
| 3way-handshake | TCP 통신을 위해 SYN, SYN-ACK, ACK 패킷을 교환하며 두 호스트 간 연결을 초기화하는 절차 |
| 흐름제어, 혼잡제어 | TCP는 네트워크의 혼잡도나, 수신자의 처리 능력에 따라 데이터 전송 속도를 조절해 전송 효율을 최대화 한다. |
| (선택) Sliding | 수신측이 처리할 수 있는 데이터 양을 고려해 전송 속도를 조절하는 흐름 제어 메커니즘 |
| (선택) slow start | 네트워크 혼잡을 고려해 전송 속도를 늘려가는 혼잡 제어 알고리즘 |
| UDP | 신뢰성을 보장하지 않지만, 빠르고 단순한 비연결 지향형 통신 프로토콜 |
| Proxy | 클라이언트와 서버 사이의 통신을 대신 처리하는 중개 서버 |
| Foward Proxy | 클라이언트의 요청을 중계하는 프록시입니다. IP 변환을 통해 사용자의 익명성을 보장하거나, 특정 페이지 접근 제한, 캐싱을 통한 사용자 경험 개선 등이 가능하다. |
| ReverseProxy | 서버의 요청 처리 및 응답을 중계하는 프록시다. 로드밸런싱을 통한 부화 완화, 성능 향상이 가능하며, 요청 필터링이나 WAF를 통해 악성 트래픽을 차단할 수 있다. |
| (선택)XSS | 악의적 스크립트를 웹 페이지에 삽입해 사용자 정보를 탈취하는 해킹 기법이다. 사용자 입력을 검증하는 예방법이 있다. |
| (선택)CSRF | 인증된 사용자 권한을 도용해, 사용자와 무관하게 악의적인 요청을 시도하는 해킹 기법이다. CSRF 토큰 사용, SameSite 쿠키 설정 등으로 예방할 수 있다. |
| (선택) WAF | 웹 애플리케이션 보호를 위한 방화벽으로, 악성 IP를 필터링하는 용도로 사용한다. |
| HTTP Cache | 웹 리소스들을 사용자 브라우저나 서버에 임시로 저장하는 기술 |
| (선택) SWR 전략 | 캐시가 만료된 후에도, 우선 사용자에게 제공하고 백그라운드에서 캐시를 업데이트하는 캐시 관리 기법. 새로운 데이터가 있는지 재검증(Revalidate)후 캐시를 업데이트한다. 이후 사용자 웹페이지에 업데이트된 캐시 데이터 섹션만 동적으로 갱신한다. |
| 로드 밸런싱 | 트래픽을 여러 대의 서버로 분산하는 기술로, 서버의 부하를 낮추어 애플리케이션의 가용성을 높이기 위해 사용한다. |

</details>

<details>
<summary><strong>6주차 1일차 수업 키워드</strong></summary>

| 키워드 | 정리 |
|:---|:---|
| Database | 정보의 효율적 활용을 위해 구조화된 데이터 모음 |
| RDBMS | RDB(상호 연관된 데이터를 테이블 형태로 저장하는 데이터베이스)를 관리하는 시스템으로 트랜잭션, 무결성 제약 및 동시성 제어를 지원합니다. |
| 테이블 | RDB의 구성 단위 |
| 행 (Row), 열 (Column) | 테이블을 구성하는 단위로, 행은 관련된 데이터의 모음, 열은 데이터의 속성을 의미한다. |
| 기본 키 (Primary Key) | 행을 유일하게 식별할 수 있는 식별자 |
| 외래 키 (Foreign Key) | 참조하는 다른 테이블의 기본키 |
| 데이터 타입 (INT, VARCHAR, DATE, TIMESTAMP 등) |  |
| SQL | 원하는 자원을 명시하는 선언적 구조 질의 언어로 DDL, DML, DCL등으로 구성됩니다. 데이터베이스 조작을 위해 사용합니다. |
| DDL | 데이터베이스 구조를 만들거나 변경하는 명령어입니다. |
| DML | 데이터베이스에 데이터를 CRUD하기 위해 사용하는 명령어입니다. |
| DCL | 데이터베이스의 접근 권한을 관리하는 명령어입니다. |
| CREATE, ALTER, DROP | DDL에서 지원하는 명령어로 각각 테이블 생성, 테이블 구조 변경, 테이블 삭제를 수행합니다. |
| INSERT, SELECT, UPDATE, DELETE | DML에서 지원하는 명령어로 각각 데이터 삽입/검색/수정/삭제를 수행합니다. |
| WHERE 절 | 데이터 필터링(레코드 식별)을 위한 구문으로 여러 조건식과 연산자를 제공합니다. |
| 집계함수 (COUNT, SUM, AVG, MAX, MIN) | 열에 대한 집계기능을 제공하는 함수입니다. |
| ORDER BY | 결과를 정렬하기 위한 구문입니다. ASC/DESC와 LIMIT, OFFSET를 사용할 수 있습니다. |
| LIMIT, OFFSET | 데이터의 OFFSET번째부터, LIMIT개의 레코드를 가져옵니다. |
| GROUP BY | 결과 집합을 그룹화할 때 사용하는 구문입니다. HAVING으로 필터링이 가능하며, 집계함수와 함께 사용시, 그룹별 집계 결과를 구할 수 있습니다. |
| 정규화 | RDB 설계에 적합한 데이터 형태를 만들기 위해 데이터의 중복을 제거하는 과정입니다. |
| 제1정규형 (1NF) | 모든 컬럼은 하나의 값만 가져야 합니다. |
| 제2정규형 (2NF) | 부분 함수 종속을 제거해 기본키가 아닌 요소가 기본키 전체에 종속되도록 해야합니다. |
| 제3정규형 (3NF) | 이행적 종속을 제거해, 기본키 이외의 속성이 다른 일반 속성에 종속되지 않도록 해야합니다. |
| 반정규화 | 정규화에 따른 과도한 JOIN을 방지하기 위해, 갱신 빈도가 낮고, 빠른 조회가 필요한 데이터를 다시 합치는 과정입니다. |
| 이상 현상 | 데이터베이스 설계가 잘못 되었을 때 발생하는 문제로, 주로 데이터의 중복과 잘못된 종속 관계로 인해 발생합니다. 삽입 이상, 삭제 이상, 갱신 이상이 있습니다. |
| ERD | 개체와 개체 간 관계를 시각적으로 나타낸 도표 |
| 엔티티 | 현실 세계의 개체를 추상화한 개념으로 데이터베이스의 테이블과 매핑된다. |
| 카디널리티 (Cardinality) | 개체 간의 수적 관계를 표현한 것 |
| (선택) 함수적 종속성 | 한 속성의 값이, 다른 속성의 값을 유일하게 식별하는 관계 |
| (선택) 부분 함수 종속 | 복합키가 아닌 값이, 복합키의 일부에 의해 유일하게 식별되는 관계 |
| (선택) 이행적 종속 | 기본키가 아닌 다른 값에 의해 직접적으로 식별되는 관계 |
| (선택) 복합 키 | 2개 이상 속성을 결합해, 행을 유일하게 식별하는 기본키 |
| (선택) INNER JOIN | 두 테이블의 공통 열만 남도록 JOIN 연산 수행 |
| (선택) LEFT JOIN / RIGHT JOIN | 두 테이블의 공통 열과, 각 테이블의 공통되지 않은 열 중 한 테이블의 열을 남기는 JOIN 연산 |
| (선택) OUTER JOIN | 두 테이블의 공통열이 아닌 나머지 열도 남기는 JOIN연산 |
| (선택) VIEW | 쿼리 정의를 저장해둔 가상 테이블 |
| (선택) Materialized View | 쿼리 결과를 저장해둔 테이블, 원본 테이블이 변경되면 갱신해야 한다. |
| (선택) UNION | 두 결과를 합치고 중복을 제거하는 연산 |
| (선택) UNION ALL | 두 결과를 합치기만 하는 연산 |
| (선택) Full Table Scan | 테이블 전체를 확인하는 프로세스 |
| (선택) N+1 문제 | N개의 데이터 조회를 위해, N번의 쿼리를 요청하는 문제로 오버헤드가 발생한다. 주로 ORM환경에서 Lazy Loading때문에 발생하는 성능 문제 입니다. |

</details>