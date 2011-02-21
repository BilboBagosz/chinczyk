package pl.krgr.chinczyk.model;

public class IdMapping {
	
	public static final IdMapping INSTANCE = new IdMapping();
	
	private int index = 0;
	
	private final int[] map = {
	57, 58, 		39, 40, 1, 		61, 62,
	59, 60, 		38, 41, 2, 		63, 64,
					37, 42, 3,
					36, 43, 4,
	31, 32, 33, 34, 35, 44, 5, 6, 7, 8, 9,
	30, 53, 54, 55, 56,     48, 47, 46, 45, 10,
	29, 28, 27, 26, 25, 52, 15, 14, 13, 12, 11,
					24, 51, 16,
					23, 50, 17,
	69, 70, 		22, 49, 18,		65, 66,
	71, 72,			21, 20, 19,		67, 68				
	};
	
	private IdMapping() {}
	
	public int getActualValue() {
		if (index > map.length - 1) 
			return -1;
		return map[index++];
	}
	
	public void reset() {
		index = 0;
	}
	
	public int[] getMapping() {
		return map;
	}
}
