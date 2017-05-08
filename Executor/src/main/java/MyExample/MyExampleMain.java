package main.java.MyExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Administrator on 2017-05-08.
 *
 * @version : ver 1.0.0
 **/
public class MyExampleMain {
  public static void main(String[] args) {
    ExecutorService es = Executors.newFixedThreadPool(5);
    Send sms = new Send(es);
    for(int i = 0 ; i < 100; i++) {
      sms.Sending(i, 0);
    }
    sms.Sending(1, -123);
    sms.finish();
  }
}
