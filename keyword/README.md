

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

