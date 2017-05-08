package main.java.ExecutorExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2017-05-06.
 *
 * @version : ver 1.0.0
 * @desc : cho_jeong_ha
 *
 * 작업 처리 결과를 외부 객체에 저장 상황에 따라서 스레드가 작업한 결과를 외부 객체에 저장해야 할 경우도 있습니다. 예를 들어 스레드가 작업 처리를 완료하고 외부 Result
 * 객체에 작업 결과를 저장하면, 어플리케이션이 Result 객체를 사용해서 어떤 작업을 진행할 수 있을 것입니다. 대개 Result 객체는 공유 객체가 되어, 두 개 이상의
 * 스레드 작업을 취합할 목적으로 이용됩니다.
 *
 * 이런 작업을 위해서는 ExecutorService의 submit(Runnable task, V result) 메소드를 사용할 수 있는데, V가 바로 Result 타입이
 * 됩니다. 메소드를 호출하면 즉시 Future<V>가 리턴되는데, Future의 get() 메소드를 호출하면 스레드가 작업을 완료할 때까지 블로킹되었다가 작업을 완료하면 V
 * 타입 객체를 리턴합니다. 리턴된 객체는 submit() 의 두 번째 파라미터로 준 객체와 동일한데, 차이점은 스레드 처리 결과가 내부에 저장되어 있다는 것입니다.
 *
 * 다음 예제는 1부터 10까지의 합을 계산하는 두 개의 작업을 스레드풀에 처리 요청하고, 각각의 스레드가 작업 처리를 완료한 후 산출된 값을 외부 Result 객체에 누적하도록
 * 했습니다.
 **/
public class ExecutorServiceExample_5_ExportObjectResult {

  public static void main(String[] args) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    System.out.println("작업 처리 요청 !");

    class Task implements Runnable {

      Result result;

      Task(Result result) {
        this.result = result;
      }


      @Override
      public void run() {
        int sum = 0;

        for (int i = 1; i <= 10; i++) {
          sum += i;
        }

        result.addValue(sum);
      }
    };

    Result result = new Result();
    Runnable task1 = new Task(result);
    Runnable task2 = new Task(result);
    Future<Result> future1 = executorService.submit(task1, result);
    Future<Result> future2 = executorService.submit(task2, result);

    try {
      result = future1.get();
      result = future2.get();
      System.out.println("처리 결과: " + result.accumValue);
      System.out.println("처리 완료");
    } catch (Exception e) {
      e.printStackTrace();
    }

    executorService.shutdown();
  }
}

class Result {

  int accumValue;

  synchronized void addValue(int value) {
    accumValue += value;
  }
}
