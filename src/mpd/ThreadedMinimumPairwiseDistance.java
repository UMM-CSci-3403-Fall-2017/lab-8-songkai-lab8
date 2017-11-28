package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {
	
	
    @Override
    public int minimumPairwiseDistance(int[] values){
    	//Class Answer to get the result.
    	 Answer answer = new Answer();
    	 
    	 //Create 4 threads to find minimum distance separately.
         MyThreads[] myThreads = new MyThreads[4];
         
         for (int i = 0; i < 4; i++){
        	 myThreads[i] = new MyThreads(values,i,answer);
        	 myThreads[i].start();
         }
         
         //Run these threads
         for (int i = 0; i < 4;i++){
        	 try{
        		 myThreads[i].join();
        	 }catch(InterruptedException e){
        		 e.printStackTrace();
        	 }
         }
         
        //Get the minimum distance
         return answer.getAnswer();
    }

    
    
    private class MyThreads extends Thread{
    	//Get a shared answer to compare the results for each thread so that can get the minimum distance;
    	Answer sharedAnswer = new Answer();
    	
    	/*Here we define the 4 areas with different area code:
    	 * 0: Left bottom. 0 <= j < i < N/2.
    	 * 1: Right bottom. N/2 <= j+N/2 < i < N.
    	 * 2: Top right. N/2 <= j < i < N.
    	 * 3: Center. N/2 <= i < j+N/2 < N.
    	 */
    	private int[] array;
    	private int threadCode;
    		
    	public MyThreads(){}
    	
    	
    	public MyThreads(int[] a, int threadCode, Answer answer){
    		this.sharedAnswer = answer;
    		this.array = a;
    		this.threadCode = threadCode;
    	}
    	
    	
    	public void run(){
    		//find the value of N/2
    		int middle = array.length/2;
    		
    		if (threadCode == 0){ // Left bottom.
    			for(int i = 1; i < middle; i++){ //i: outer range
    				for (int j = 0; j < i; j++){//j: inner range
    					//compute the absolute value
    					int distance = Math.abs(array[i]-array[j]);
    					//if find the lower distance, update answer.
    					if (distance < sharedAnswer.getAnswer()){
    						sharedAnswer.setAnswer(distance);
    					}
    				}
    			}
    		}
    		
    		if(threadCode == 1){// Right bottom.
    			for(int i = middle+1; i < array.length; i++){ //i: outer range
    				for (int j = 0; j < i-middle; j++){//j: inner range
    					//compute the absolute value
    					int distance = Math.abs(array[i]-array[j]);
    					//if find the lower distance, update answer.
    					if (distance < sharedAnswer.getAnswer()){
    						sharedAnswer.setAnswer(distance);
    					}
    				}
    			}
    		}
    		
    		if(threadCode == 2){// Top right.
    			for(int i = middle+1; i < array.length; i++){ //i: outer range
    				for (int j = middle; j < i; j++){//j: inner range
    					//compute the absolute value
    					int distance = Math.abs(array[i]-array[j]);
    					//if find the lower distance, update answer.
    					if (distance < sharedAnswer.getAnswer()){
    						sharedAnswer.setAnswer(distance);
    					}
    				}
    			}
    		}
    		
    		if(threadCode == 3){// Center.
    			for(int j = 0; j < middle; j++){ //j: outer range
    				for (int i = middle; i < j+ middle; i++){//i: inner range
    					//compute the absolute value
    					int distance = Math.abs(array[i]-array[j]);
    					//if find the lower distance, update answer.
    					if (distance < sharedAnswer.getAnswer()){
    						sharedAnswer.setAnswer(distance);
    					}
    				}
    			}
    		}
    	}
    	
    }	

    
    
    
    private class Answer {
    	private int answer = Integer.MAX_VALUE;

    	public int getAnswer() {
    	  return this.answer;
    	}

    	// This has to be synchronized to ensure that no two threads modify
    	// this at the same time, possibly causing race conditions.
    	public synchronized void setAnswer(int newAnswer) {
    	  answer = newAnswer;
    	}
    }
    	
   

    
}
