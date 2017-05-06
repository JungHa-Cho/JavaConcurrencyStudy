package main.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2017-05-05.
 *
 * @version : ver 1.0.0
 * @desc : cho_jeong_ha
 *
 * -- 작업의 처리 요청
 *
 * ExecutorService는 일종의 생성자 소비자 패턴으로 ExecutorService 작업큐에 Runnable이나 Callable
 * 객체를 넣는 행위이다. 이를 작업의 처리 요청이라 한다.
 *
 * ExecutorService의 작업 처리 요청 메소드
 * void execute(Runnable command)             : Runnable을 작업 큐에 저장, 작업 처리 결과를 받지 못한다.
 * Future<?> submit(Runnable task)            : Runnable 또는 callable 작업을 큐에 저장
 * Future<V> submit(Runnable task, V result)  : 리턴된 Future를 통해 자업 처리 결과를 얻을 수 있다.
 * Future<V> submit(Callable<V> task)         :
 *
 * -- execute()와 submit() 메소드의 차이점
 * - execute는 작업 처리 결과를 받지 못하지만 submit은 작업 처리 결과를 future 타입으로 리턴한다.
 * - execute는 작업 처리 중 예외가 발생하면 스레드가 종료되고 해당 스레드는 스레드 풀에서 제거되고
 * 새로운 스레드를 생성한다.
 * - submit은 작업 처리 중 예외가 발생하면 스레드는 종료되지 않고 다음 작업을 위해 재사용 된다.
 **/
public class ExecutorServiceExample_2_ExcuteAndSubmit {
  public static void main(String[] args) throws InterruptedException {
    final ExecutorService es = Executors.newFixedThreadPool(2);

    for(int i = 0; i < 10; i++) {

      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          ThreadPoolExecutor tpe = (ThreadPoolExecutor) es;

          int poolSize = tpe.getPoolSize();
          String threadName = Thread.currentThread().getName();
          System.out.println("[총 스레드 개수: " + poolSize + "] 작업 스레드 이름: " + threadName);

          // excute의 예외 발생 시켜서 쓰레드 풀 제거되고 새로운 스레드 생성을 위해 일부러 ex 발생
          int value = Integer.parseInt("숫자");
        }
      };

      // Excute와 submit의 차이점을 보기위해서.
      // excute와 submit의 주석을 번갈아가면서 제거하고 실행해보기
      // 결과가 다르다.

      //es.execute(runnable);
      es.submit(runnable);
      Thread.sleep(10);
    }

    es.shutdown();;
  }
}
