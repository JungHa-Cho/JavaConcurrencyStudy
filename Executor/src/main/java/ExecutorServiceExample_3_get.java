package main.java;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017-05-05.
 *
 * @version : ver 1.0.0
 * @desc : cho_jeong_ha
 *
 * -- 블로킹 방식의 작업 통보
 *
 * ExecutorService의 Submit() 메소드는 파라미터로 준 Ruannble 또는 Callable 작업을 스레드 풀의 작업 큐에 저장하고 즉시 Future 객체를
 * 리턴한다.
 *
 * Future<?> submit(Runnable task)            : Future<?> submit(Runnable task, V result)  :
 * Runnable 또는 Callable을 작업 큐에 저장 Future<?> submit(Callable<V> task)         : 리턴된 Future를 통해 작업 처리
 * 결과를 얻음
 *
 * Future 객체는 작업 결과가 아니라 작업이 완료될 때까지 기다렸다가 최종 결과를 얻는데 사용한다. 그래서 Future는 지연 완료 객체라고 한다. Future의 get()
 * 메소드를 호출하면 스레드가 작업을 완료할 때까지 블로킹 되었다가 작업을 완료하면 처리 결과를 리턴한다.
 *
 * V get() : 작업이 완료될 때까지 블로킹 되었다가 처리 결과 V를 리턴 V get(long timeout, TimeUnit unit) : Timeout 시간 전에 완료
 * 되면 결과 V 리턴 아니면 TimeoutException
 *
 * -- 리턴 타입 리턴 타입 V 는 Submit(Rannble task, V result)의 두번째 파라미터인 V 타입이거나 submit(Callable<V> task)의
 * Callable 타입 파라미터 V 타입이다. 다음은 세가지 submit() 메소드 별로 Future의 get() 메소드가 리턴하는 값을 표시한다.
 *
 * submit(Rannable task) -> 리턴 타입 future.get() = null submit(Rannable Integer result) -> 리턴 타입
 * future.get() = int 값 submit(Callable<String> task) -> future.get()
 *
 * 위 세가지 모두 작업 처리 도중 예외가 발생하면 future.get() -> 예외 발생한다.
 *
 * future는 블로킹 방식의 작업 완료 통보 방식이다. 여기서 주의할 점은 작업을 처리하는 스레드가 작업을 완료하기 전까지는 get() 메소드가 블로킹 되므로 다른 코드를
 * 실행할 수 없다. 다른 코드를 실행하는 스레드가 get() 메소드를 호출하면 작업을 완료하기 전까지 블로킹되기 때문에 get()을 호출하는 스레드는 다른 스레드가 되어야
 * 한다.
 *
 * future 객체의 다른 메소드 boolean cancel(Boolean mayInterruptRunning) : 작업 처리가 진행 중일 경우 취소시킴 boolean
 * isCancelled() : 작업이 취소되었는지 여부 boolean isDone() : 작업 처리가 완료되었는지 여부
 **/
public class ExecutorServiceExample_3_get {

  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(2);
    // 리턴 값이 없는 작업일 경우 Runnable 객체로 생성
    Runnable task1 = new Runnable() {
      @Override
      public void run() {
        // code
      }
    };
    // 리턴 값이 없음에도 future 객체를 리턴한다.
    // 스레드가 작업 처리를 정상적으로 완료했는지 아니면 작업 처리 도중 예외가 발생했는지 확인 하기 위함
    Future future = es.submit(task1);
    // null을 리턴하지만 작업 처리 도중 예외가 발생할수 있기 때문에 try catch 문이 필요하다.
    try {
      future.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    es.shutdown();

    /**
     * 이 예제는 리턴 값이 없고 단순히 1부터 10의 합을 출력하는 작업을 Raunnble 객체로 생성 후
     * 스레드 풀의 스레드가 처리하도록 요청한 것이다.
     */
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    System.out.println("[작업 처리 요청]");

    Runnable run = new Runnable() {
      @Override
      public void run() {
        int sum = 0;

        for (int i = 1; i <= 10; i++) {
          sum += i;
        }

        System.out.println("[처리 결과] : " + sum);
      }
    };

    Future future1 = executorService.submit(run);

    try {
      future1.get();
      System.out.println("[작업 처리 완료]");
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    executorService.shutdown();
  }
}
