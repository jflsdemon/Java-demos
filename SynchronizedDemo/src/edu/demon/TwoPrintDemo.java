package edu.demon;

class Consumer implements Runnable {
	 
    @Override
    public synchronized void run() {
           // TODO Auto-generated method stub
           int count = 10;
           while(count > 0) {
                synchronized (TwoPrintDemo.obj) {
                    
                    System. out.print( "B");
                    count --;
                    TwoPrintDemo.obj.notify(); // 主动释放对象锁
                    
                     try {
                    	 TwoPrintDemo.obj.wait();
                          
                    } catch (InterruptedException e) {
                           // TODO Auto-generated catch block
                          e.printStackTrace();
                    }
               }
               
          }
    }
}

class Produce implements Runnable {

    @Override
    public void run() {
           // TODO Auto-generated method stub
           int count = 10;
           while(count > 0) {
                synchronized (TwoPrintDemo.obj) {
                    
                     //System.out.print("count = " + count);
                    System. out.print( "A");
                    count --;
                    TwoPrintDemo.obj.notify();
                    
                     try {
                    	 TwoPrintDemo. obj.wait();
                    } catch (InterruptedException e) {
                           // TODO Auto-generated catch block
                          e.printStackTrace();
                    }
               }
               
          }

    }

}

public class TwoPrintDemo {
	public static final Object obj = new Object();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
	}

}
