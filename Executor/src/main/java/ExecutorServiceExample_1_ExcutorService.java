package main.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017-05-05.
 *
 * @version : ver 1.0.0
 * @desc : cho_jeong_ha
 *
 * -- 작업의 실행과 종료
 *
 * -- ExecutorService 객체를 생성할때 쓰레드 풀 관리 방식과 스레드 갯수를 정할수 있다.
 *
 * newFixedThreadPool       : 초기 스레드 수 = 0, 코어 스레드 수 = n, 최대 스레드 수 = n
 * newCachedThreadPool      : 초기 스레드 수 = 0, 코어 스레드 수 = 0, 최대 스레드 수 = Integer.MAX_VALUE
 * newSingleThreadExecutor  : 단일 스레드, 작업 중 Exception 발생시 새로운 스레드 생성
 * newScheduledThreadPool   : 일정 시간에 동작시킬 수 있는 스레드
 *
 * -- 쓰레드의 종료
 * void shutdown()          : 현재 처리 중인 작업뿐만아니라 작업 큐에 대기하고 있는 모든 작업을 처리한 뒤에 스레드 풀을 종료시킨다.
 * List<Runnable>           : 현재 작업 처리 중인 스레드를 interrupt해서 작업 중지를 시도하고 스레드 풀을 종료시킨다.
 *                            리턴값은 작업 큐에 있는 미처리된 작업의 목록이다.
 * boolean awaitTermination(long timeout, TimeUnit unit)
 *                          : shutdown 메소드 호출 이후, 모든 작업 처리를 timeout 시간내에 완료하면 true를 리턴하고,
 *                            완료하지 못하면 작업 처리 중인 스레드를 interrupt하고 false를 리턴한다.
 **/
public class ExecutorServiceExample_1_ExcutorService {
  // No Parameter
  static ExecutorService es1 = Executors.newCachedThreadPool();

  // CPU 코어 수의 최대 쓰레드 생성
  static ExecutorService es2 =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  public static void mian(String[] args){
    // 스레드의 종료
    es1.shutdown();
    es1.shutdownNow();
    try {
      es2.awaitTermination(5, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
