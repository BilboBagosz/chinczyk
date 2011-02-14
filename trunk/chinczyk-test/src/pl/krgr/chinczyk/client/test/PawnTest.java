package pl.krgr.chinczyk.client.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import pl.krgr.chinczyk.client.presentation.Camp;
import pl.krgr.chinczyk.client.presentation.Cell;
import pl.krgr.chinczyk.client.presentation.IdMapping;
import pl.krgr.chinczyk.client.presentation.Pawn;

public class PawnTest {

	private Map<Integer, Cell> boardMap = new HashMap<Integer, Cell> ();
	private IdMapping mapping = new IdMapping();
	private Pawn redPawn = getPawn(Camp.RED);
	private Pawn yellowPawn = getPawn(Camp.YELLOW);
	private Pawn brownPawn = getPawn(Camp.BROWN);
	private Pawn greenPawn = getPawn(Camp.GREEN);		
	
	private int[] redExpectedCellIds = {
			31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			1,	2,  3,  4,  5,  6,  7,  8,  9,  10,
			11, 12, 13, 14,	15, 16, 17, 18, 19, 20,
			21, 22, 23, 24, 25, 26,	27, 28, 29, 30, 
			53, 54, 55, 56
	};
	private int[] yellowExpectedCellIds = {
			1,	2,  3,  4,  5,  6,  7,  8,  9,  10,
			11, 12, 13, 14,	15, 16, 17, 18, 19, 20,
			21, 22, 23, 24, 25, 26,	27, 28, 29, 30,
			31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			41, 42, 43, 44
	};
	private int[] brownExpectedCellIds = {
			11, 12, 13, 14,	15, 16, 17, 18, 19, 20,
			21, 22, 23, 24, 25, 26,	27, 28, 29, 30,
			31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			1,	2,  3,  4,  5,  6,  7,  8,  9,  10,
			45, 46, 47, 48
	};
	private int[] greenExpectedCellIds = {
			21, 22, 23, 24, 25, 26,	27, 28, 29, 30,
			31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
			1,	2,  3,  4,  5,  6,  7,  8,  9,  10,
			11, 12, 13, 14,	15, 16, 17, 18, 19, 20,
			49, 50, 51, 52
	};
	
	@Before
	public void setup() {		
		mapping.reset();
		prepareBoard();
	}
	
	@Test
	public void testRedMovement() {
		redPawn.move(6); // move from base
		assertTrue(redPawn.getOwner().getId() == redExpectedCellIds[0]);
		for (int i = 1; i < 44; i++) {
			redPawn.move(1);
			assertTrue(redPawn.getOwner().getId() == redExpectedCellIds[i]);
		}
	}
	
	@Test
	public void testGreenMovement() {
		greenPawn.move(6); // move from base
		assertTrue(greenPawn.getOwner().getId() == greenExpectedCellIds[0]);
		for (int i = 1; i < 44; i++) {
			greenPawn.move(1);
			assertTrue(greenPawn.getOwner().getId() == greenExpectedCellIds[i]);
		}
	}

	@Test
	public void testBrownMovement() {
		brownPawn.move(6); // move from base
		assertTrue(brownPawn.getOwner().getId() == brownExpectedCellIds[0]);
		for (int i = 1; i < 44; i++) {
			brownPawn.move(1);
			assertTrue(brownPawn.getOwner().getId() == brownExpectedCellIds[i]);
		}
	}

	@Test
	public void testYellowMovement() {
		yellowPawn.move(6); // move from base
		assertTrue(yellowPawn.getOwner().getId() == yellowExpectedCellIds[0]);
		for (int i = 1; i < 44; i++) {
			yellowPawn.move(1);
			assertTrue(yellowPawn.getOwner().getId() == yellowExpectedCellIds[i]);
		}
	}

	private void addCell(Pawn pawn) {
		Cell cell = new TestCell();
		int id = mapping.getActualValue();
		cell.setId(id);
		boardMap.put(id, cell);
		if (pawn != null) {
			cell.setPawn(pawn);
			pawn.setBaseCell(cell);
		}
	}
	
	private void addCells(int number) {
		for (int i = 0; i < number; i++) {
			addCell(null);
		}
	}
		
	private Pawn getPawn(Camp camp) {
		Pawn pawn = new Pawn(boardMap);
		pawn.setCamp(camp);
		return pawn;
	}
	
	private void prepareBoard() {
		addCell(redPawn);
		addCells(4);
		addCell(yellowPawn);
		addCells(52);	
		addCell(greenPawn);
		addCells(4);
		addCell(brownPawn);
		addCells(8);
	}	
}
