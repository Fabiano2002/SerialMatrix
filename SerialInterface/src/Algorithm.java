
public class Algorithm {
	public static int[][][] Randomize() {
		int[][][] arr = new int[3][8][8];
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if(Math.random()> 0.5) {
						arr[0][x][y] =	(int)(255*Math.random());
						arr[1][x][y] = 	(int)(255*Math.random());
						arr[2][x][y] = 	(int)(255*Math.random());
					}
					else
					{
						arr[0][x][y] =	0;
						arr[1][x][y] = 	0;
						arr[2][x][y] = 	0;
					}
				}
			}
		return arr;
	}
	public static int cr = 255;
	public static int cg = 0;
	public static int cb = 0;
	public static int cs = 0;
	public static int[][][] idle() {
		int[][][] mtx = new int[3][8][8];
		if(cs == 0) {
			cr--;
			cg++;
			if(cg == 255) {
				cs++;
			}
		}
		if(cs==1) {
			cg--;
			cb++;
			if(cb == 255) {
				cs++;
			}
		}
		if(cs==2) {
			cb--;
			cr++;
			if(cr==255) {
				cs=0;
				Main.finishalgorithm();
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					if(i==0) {
						if(cr != 14) {
						mtx[0][x][y] = cr;
						}
						else
						{
						mtx[0][x][y] = 13;
						}
						}
					if(i==1) {
						if(cg != 14) {
							mtx[1][x][y] = cg;
							}
							else
							{
							mtx[1][x][y] = 13;
							}
						}
					if(i==2) {
						if(cb != 14) {
							mtx[2][x][y] = cb;
							}
							else
							{
							mtx[2][x][y] = 13;
							}
						}
				}
			}
		}
		return mtx;
	}
	public static int[][][] colorline(int id) {
		int[][][] mtx = new int[3][8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				int rid = ((j + (i*8)) * 4) + id;
				int[] col = getFullColor(rid);
				mtx[0][j][i] = col[0];
				mtx[1][j][i] = col[1];
				mtx[2][j][i] = col[2];
			}
		}
		return mtx;
	}
	public static int[] getFullColor(int id) {
		int cr = 255;
		int cb = 0;
		int cg = 0;
		int s = 0;
		for (int i = 0; i < id; i++) {
			switch(s) {
			case 0:
				cr--;
				cb++;
				if(cr == 0) {
					s = 1;
				}
				break;
			case 1:
				cb--;
				cg++;
				if(cb == 0) {
					s = 2;
				}
				break;
			case 2:
				cg--;
				cr++;
				if(cg == 0) {
					s = 0;
				}
				break;
			}
		}
		int[] ret = new int[3];
		ret[0] = cr;
		ret[1] = cb;
		ret[2] = cg;
		return ret;
	}
}
