package main.java.ExecutorExample;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017-05-06.
 *
 * @version : ver 1.0.0
 * @desc : cho_jeong_ha
 *
 * 스레드 풀의 스레드가 작업을 완료한 후에 어플리케이션이 처리 결과를 얻어야 된다면 작업
 * 객체를 Callable로 생성하면 됩니다. 다음은 Callable 객체를 생성하는 코드인데, 주의할 점은
 * 제네릭 타입 파라미터 T는 call() 메소드가 리턴하는 타입이 되도록 합니다.
 **/
public class ExecutorServiceExample_4_Callable {
  public static void main(String[] args) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    System.out.println("[작업 처리 요청]");
    Callable<Integer> task = new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        int sum = 0;

        for (int i = 1; i <= 10; i++) {
          sum += i;
        }

        return sum;
      }
    };

    Future<Integer> future = executorService.submit(task);

    try {
      int sum = future.get();
      System.out.println("[처리 결과] : " + sum);
      System.out.println("[작업 처리 완료]");
    } catch (Exception e) {
      e.printStackTrace();
    }

    executorService.shutdown();
  }
}
