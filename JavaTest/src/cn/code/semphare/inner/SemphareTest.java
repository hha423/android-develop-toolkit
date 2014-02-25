package cn.code.semphare.inner;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemphareTest {
	
	public static void main(String[] args) {
		Semaphore sp = SemaphoreFactory.createSemaphore();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		
		CommandInvoker.readRecord(pool, sp);
		CommandInvoker.execCommand(pool, sp);
		CommandInvoker.execCommand(pool, sp);
		CommandInvoker.execCommand(pool, sp);
		CommandInvoker.execCommand(pool, sp);
		CommandInvoker.execCommand(pool, sp);
		CommandInvoker.execCommand(pool, sp);
	}
}
