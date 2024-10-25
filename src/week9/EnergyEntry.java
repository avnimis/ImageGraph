package week9;

public class EnergyEntry {
	
	public enum Direction { 
		HERE, ABOVE, ABOVELEFT, ABOVERIGHT
	}
	
	double value; 
	Direction direction;
	
	
	/**
	 * constructs an Energy Entry
	 * @param v - the value representing the energy up to till this entry
	 * @param d - points to one of three nodes above it with the lowest value. 
	 * 			if there are no nodes above, points to HERE
	 */
	public EnergyEntry(double v, Direction d){
		this.value = v; 
		this.direction = d;		
	}

	/**
	 * creates a string represenation of an Energy Entry 
	 */
	public String toString() { 
		return String.valueOf(value) + " " + direction.toString(); 
	}
		
	/**
	 * turns a array of doubles into a table of energy entry tracking 
	 * the path of lowest energy
	 * @param values - the array of doubles representing each pixel's energy
	 * @return - returns an array of Energy Entry with the cumulitive smallest values 
	 * 			and the direction of where they got them from
	 */
	public EnergyEntry[][] makeEnergyTable(double[][] values){
			
		int rows = values.length; 
		int cols = values[0].length;
			
		EnergyEntry[][] table = new EnergyEntry[rows][cols];
			
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				table[i][j] = new EnergyEntry(values[i][j], Direction.HERE);
						
				if(i > 0) {	
					double temp = table[i-1][j].value;
					table[i][j] = new EnergyEntry(
							values[i][j] + temp, 
							Direction.ABOVE);
					
					
					if(j > 0) {
						
						if(table[i-1][j-1].value < temp) {
							
							table[i][j] = new EnergyEntry(
									values[i][j] + table[i-1][j-1].value, 
									Direction.ABOVELEFT
									);
							temp = table[i-1][j-1].value;
						}
					} 
						
					if(j < cols - 1) {
						
						if (table[i-1][j+1].value < temp) {
							
							table[i][j] = new EnergyEntry(
									values[i][j] + table[i-1][j+1].value, 
									Direction.ABOVERIGHT
									);
						}
					} 	
				}		
			}
		}
			
		return table; 
			
	}
	
	
	
	public static void main (String[] args) {
		
		EnergyEntry e = new EnergyEntry(0, Direction.ABOVE);
		
		double[][] values = new double[4][4]; 
		
		values[0][0] = 5; 
		values[0][1] = 6;
		values[0][2] = 3;
		values[0][3] = 8;
		values[1][0] = 4;
		values[1][1] = 1;
		values[1][2] = 6;
		values[1][3] = 4;
		values[2][0] = 3;
		values[2][1] = 2;
		values[2][2] = 1;
		values[2][3] = 3;
		values[3][0] = 8;
		values[3][1] = 6;
		values[3][2] = 5;
		values[3][3] = 2;
		
		
		
		EnergyEntry[][] table = e.makeEnergyTable(values);
		
		StringBuilder sb = new StringBuilder(); 
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				sb.append(table[i][j].toString());
				sb.append("  ");
			}
			sb.append(System.lineSeparator());
		}
		
		
		System.out.print(sb);
		
		
	}
	
}
