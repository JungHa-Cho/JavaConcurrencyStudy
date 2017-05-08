package main.java.MyExample;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;

/**
 * Created by Administrator on 2017-05-08.
 *
 * @version : ver 1.0.0
 **/
public class Send {

  private ExecutorService executorService;

  public Send(ExecutorService es) {
    //executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    executorService = es;
  }

  private CompletionHandler<Integer, Void> callback = new CompletionHandler<Integer, Void>() {
    @Override
    public void completed(Integer result, Void attachment) {
      System.out.println("completed() 실행: " + result);
    }

    @Override
    public void failed(Throwable e, Void attachment) {
      System.out.println("failed() 실행: " + e.toString());
    }
  };

  public void Sending(final int x, final int y) {
    Runnable task = new Runnable() {
      public void run() {
        try {
          //int intX = Integer.parseInt(x);
          //int intY = Integer.parseInt(y);

          int result = x + y;
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
}
