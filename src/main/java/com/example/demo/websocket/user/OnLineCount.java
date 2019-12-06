package com.example.demo.websocket.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: wangchao
 * Time: 2019-07-03
 * Description: This is 线程安全计数器
 */
public class OnLineCount {
    private AtomicInteger atomicI = new AtomicInteger(0);
//    public int count = 0;

    /**
     * 增加用户量
     */
    public void safeAddCount(){
        for(;;){
            int temp = atomicI.get();
            if(atomicI.compareAndSet(temp,++temp))
                break;
        }
    }

    /**线程不安全的计数器*/

//    public void count(){
//        count++;
//    }

//    public void minute(){
//        count--;
//    }

    public void safeMinusCount(){
        for(;;){
            int temp = atomicI.get();
            if(atomicI.compareAndSet(temp,--temp))
                break;
        }
    }


    public  int getCount(){
        return atomicI.get();
    }



//    public static void main(String[] args){
//
//        final OnLineCount cas = new OnLineCount();
//        List<Thread> list = new ArrayList<Thread>();
//        long start = System.currentTimeMillis();
//
//        for(int j=0;j<100;j++){
//            Thread t = new Thread(new Runnable(){
//                @Override
//                public void run(){
//                    for(int i=0;i<1000;i++){
//                        cas.count();
//                        cas.safeAddCount();
//
////                        cas.minute();
////                        cas.safeMinusCount();
//                    }
//                }
//            });
//            list.add(t);
//        }
//        //启动线程
//        for(Thread t:list){
//            t.start();
//        }
//
//        //等待所有线程执行完毕
//        for(Thread t:list){
//            try{
//                t.join();
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//        System.out.println("线程不安全:"+cas.count);
//        System.out.println("线程安全:"+cas.atomicI.get());
//        System.out.println("耗时:"+(System.currentTimeMillis() - start));
//    }

}
