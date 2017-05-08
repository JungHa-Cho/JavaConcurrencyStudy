package main.java.ExecutorExample;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017-05-06.
 *
 * @version : ver 1.0.0
 * @desc : cho_jeong_ha
 *
 * % 콜백 방식의 작업 완료 통보 % 이번에는 콜백(callback) 방식을 이용해서 작업 완료 통보를 받는 방법에 대해서 알아봅시다. 콜백이란 어플리케이션이 스레드에게 작업
 * 처리를 요청한 후, 스레드가 작업을 완료하면 특정 메소드를 자동 실행하는 기법을 말합니다. 이때 자동 실행되는 메소드를 콜백 메소드(callback Method)라고 합니다.
 * 다음은 블로킹 방식과 콜백 방식을 비교한 그림입니다.
 *
 * 콜백 방식은 작업 처리를 요청한 후 결과를 기다릴 필요 없이 다른 기능을 수행할 수 있습니다. 그 이유는 작업 처리가 완료되면 자동적으로 콜백 메소드가 실행되어 결과를 알 수
 * 있기 때문입니다. 아래는 CompletionHandler를 이용해서 콜백 객체를 생성하는 코드입니다.
 *
 * CompletionHandler<V, A> callback = new CompletionHandler<V, A>() {
 * @Override public void completed(V result, A attachment) { }
 * @Override public void failed(Throwable exc, A attachment) { } }
 *
 * CompletionHandler는 completed()와 failed() 메소드가 있습니다. - completed(): 작업을 정상 처리 완료했을 때 호출되는 콜백
 * 메소드입니다. - failed(): 작업 처리 도중 예외가 발생했을 때 호출되는 콜백 메소드입니다.
 **/
public class ExecutorServiceExample_7_CallBack {

  private ExecutorService executorService;

  public ExecutorServiceExample_7_CallBack() {
    executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  }

  private CompletionHandler<Integer, Void> callback = new CompletionHandler<Integer, Void>() {
    @Override
    public void completed(Integer result, Void attachment) {
      System.out.println("completed() 실행: " + result);
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
      System.out.println("failed() 실행: " + exc.toString());
    }
  };

  public void doWork(final String x, final String y) {
    Runnable task = new Runnable() {
      public void run() {
        try {
          int intX = Integer.parseInt(x);
          int intY = Integer.parseInt(y);

          int result = intX + intY;
          callback.completed(result, null);

        } catch (NumberFormatException e) {
          callback.failed(e, null);
        }
      }
    };
    executorService.submit(task);
  }

  public void finish() {
    executorService.shutdown();
  }

  public static void main(String[] args) {
    ExecutorServiceExample_7_CallBack exam = new ExecutorServiceExample_7_CallBack();
    exam.doWork("4", "7");
    exam.doWork("4", "자바");
    exam.finish();
  }
}
