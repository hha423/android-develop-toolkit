package com.example.aspectj;

public aspect PersonAj {
	
	pointcut speakerSpeak():call(void *Person.show());
	
    before() : speakerSpeak() {
    	System.out.println("[AspectObserver] speaker is about to speak!");
    }
    
    after() returning() : speakerSpeak() {
        System.out.println("[AspectObserver] speaker has completed his speech!");
    }
	
}