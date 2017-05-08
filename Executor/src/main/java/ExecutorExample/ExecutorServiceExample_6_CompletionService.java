package main.java.ExecutorExample;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017-05-06.
 *
 * @version : ver 1.0.0
 * @desc : cho_jeong_ha
 *
 * % 작업 완료 순으로 통보 % 작업 요청 순서대로 작업 처리가 완료되는 것은 아닙니다. 작업의 양과 스케줄링에 따라서 먼저 요청한 작업이 나중에 완료되는 경우도 발생합니다.
 * 스레드 풀에서 작업 처리가 완료된 것만 통보받는 방법이 잇는데, CompletionService를 이용하는 것 입니다. CompletionService는 처리 완료된 작업을
 * 가져오는 poll()과 take() 메소드를 제공합니다.
 *
 * Future<V> poll()                             : 완료된 작업의 future를 가져옴, 완료된 작업이 없다면 즉시 null을 리턴
 * Future<V> poll(long timeout, TimeUnit unit)  : 완료된 작업의 future를 가져옴, 완료된 작업이 없다면 timeout까지 블로킹
 * Future<V> take()                             : 완료된 작업의 future를 가져옴, 완료된 작업이 없다면 있을때까지 블로킹
 * Future<V> submit(Callable<V> task)           : 스레드 풀에 Callable 작업 처리 요청 Future<V> submit(Runnable
 * task, V result)    : 스레드 풀에 Runnable 작업 처리 요청
 *
 * CompletionService 구현 클래스는 ExecutorCompletionService<V> 입니다. 객체를 생성할 때 생성자 파라미터로 ExecutorService를
 * 제공하면 됩니다. ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
 * CompletionService<V> cs = new ExecutorServiceCompletion<V>(es); poll()과 take() 메소드를 이용해서 처리 완료된
 * 작업의 Future를 얻으려면 CompletionService 의 submit() 메소드로 작업 처리 요청을 해야 합니다. cs.submit(Callable<V> task);
 * cs.submit(Runnable task, V result);
 *
 * 다음은 take() 메소드를 호출하여 완료된 Callable 작업이 있을 때까지 블로킹 되었다가 완료된 작업의 Future를 얻고, get() 메소드로 결과값을 얻어내는
 * 코드입니다. while문은 어플리케이션이 종료될 때까지 반복 실행해야 하므로 스레드 풀의 스레드에서 실행하는 것이 좋습니다.
 *
 * executorService.submit(new Runnable() { public void run() { while (true) { try { Future<Integer>
 * future = completionService.take(); int value = future.get(); } catch (Exception e) { break; } } }
 * });
 *
 * take() 메소드가 리턴하는 완료된 작업은 submit() 으로 처리 요청한 작업의 순서가 아님을 명심해야 합니다. 작업의 내용에 따라서 먼저 요청한 작업이 나중에 완료될
 * 수 있기 때문입니다. 더 이상 완료된 작업을 가져올 필요가 없다면 take() 블로킹에서 빠져나와 while 문을 종료해야 합니다. ExecutorService의
 * shutdownNow()를 호출하면 take() 에서 InterruptedException이 발생하고 catch 절에서 break 가 되어 while문을 종료하게 됩니다.
 */
public class ExecutorServiceExample_6_CompletionService {

  public static void main(String[] args) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // CompletionService 생성
    final CompletionService<Integer> completionService =
        new ExecutorCompletionService<>(executorService);

    System.out.println("처리 요청");

    for (int i = 0; i < 3; i++) {
      completionService.submit(new Callable<Integer>() {
        public Integer call() throws Exception {
          int sum = 0;

          for (int i = 1; i <= 10; i++) {
            sum += i;
          }

          return sum;
        }
      });
    }

    System.out.println("처리 완료된 작업 확인");
    executorService.submit(new Runnable() {
      public void run() {
        while (true) {
          try {
            Future<Integer> future = completionService.take();
            int value = future.get();
            System.out.println("처리 결과: " + value);
          } catch (Exception e) {
            break;
          }
        }
      }
    });

    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
    }
    executorService.shutdownNow();
  }
}
