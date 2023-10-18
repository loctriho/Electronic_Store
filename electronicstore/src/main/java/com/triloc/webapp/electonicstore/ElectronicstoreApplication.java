package com.triloc.webapp.electonicstore;

import com.triloc.webapp.electonicstore.model.Order;
import com.triloc.webapp.electonicstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@SpringBootApplication
public class ElectronicstoreApplication {

//	@Autowired
//	private OrderRepository orderRepository;

	public static void main(String[] args) {
		SpringApplication.run(ElectronicstoreApplication.class, args);
	}
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void fetchOrdersWithThreads() throws InterruptedException, ExecutionException {
//		ExecutorService executor = Executors.newFixedThreadPool(5);
//		List<Future<Long>> futures = new ArrayList<>();
//		long startTime = System.nanoTime();
//
//		for (int i = 0; i < 5; i++) {
//			futures.add(executor.submit(this::measureFetchingTime));
//		}
//
//		long totalDuration = 0;
//		for (int i = 0; i < 5; i++) {
//			long duration = futures.get(i).get();
//			System.out.println("Thread " + (i + 1) + " took " + duration + " ns");
//			totalDuration += duration;
//		}
//
//		long endTime = System.nanoTime();
//		System.out.println("Total time taken by all threads: " + totalDuration + " ns");
//		System.out.println("Time from start to finish: " + (endTime - startTime) + " ns");
//
//		executor.shutdown();
//	}
//
//	public long measureFetchingTime() {
//		long start = System.nanoTime();
//		List<Order> orders = getAllOrders();
//		return System.nanoTime() - start;
//	}
//
//	public List<Order> getAllOrders() {
//		return orderRepository.findAll();
//	}
}
