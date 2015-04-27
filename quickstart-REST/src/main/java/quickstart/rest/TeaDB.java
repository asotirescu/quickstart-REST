package quickstart.rest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TeaDB {
	
	private static Map<Integer, Tea> teaDB = new ConcurrentHashMap<Integer, Tea>();
	private static AtomicInteger id = new AtomicInteger();
	
	public TeaDB() {
		Tea defaultTea = new Tea();
		defaultTea.setName("Default TeaName");
		defaultTea.setCountry("Default Tea Country");
		defaultTea.setOrigin("Default Tea Origin");
		defaultTea.setYear(0);
		
		createTea(defaultTea);
	}
	
	public Map<Integer, Tea> getAll() {
		return teaDB;
	}
	
	public int createTea(Tea tea) {
		int newId = id.incrementAndGet();
		teaDB.put(newId, tea);
		return newId;
	}
	
	public void deleteTea(int id) {
		teaDB.remove(id);
	}

	@Override
	public String toString() {
		return "TeaDB " + teaDB;
	}

}
