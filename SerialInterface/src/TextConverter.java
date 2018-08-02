
public class TextConverter {
	public static int lastlength = 0;
	static int[][][] full = new int[3][4192][8];
	public static String fullstring = "";
	static int cf = 8;
	public static int[][][] convert(int pos) {
		// GRAB FRAME TO FN ARRAY
		int[][][] fn = new int[3][8][8];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				for (int j2 = 0; j2 < 8; j2++) {
					fn[i][j][j2] = full[i][j+pos][j2];
				}
			}
		}
		return fn;
	}
	public static void setfull(String s,int[][] cdata) {
		cf = 8;
		fullstring = s;
		for (int i = 0; i < s.length(); i++) {
			int[][] mtx = new int[8][8];
			// Get Length of Current Char
		char cs = s.charAt(i);
		cs = Character.toUpperCase(cs);
		int l = 0;
		boolean nx = false;
		switch(cs) {
		case 'A':
			mtx = SpriteData.letters[0];
			break;
		case 'B':
			mtx = SpriteData.letters[1];
			break;
		case 'C':
			mtx = SpriteData.letters[2];
			break;
		case 'D':
			mtx = SpriteData.letters[3];
			break;
		case 'E':
			mtx = SpriteData.letters[4];
			break;
		case 'F':
			mtx = SpriteData.letters[5];
			break;
		case 'G':
			mtx = SpriteData.letters[6];
			break;
		case 'H':
			mtx = SpriteData.letters[7];
			break;
		case 'I':
			mtx = SpriteData.letters[8];
			break;
		case 'J':
			mtx = SpriteData.letters[9];
			break;
		case 'K':
			mtx = SpriteData.letters[10];
			break;
		case 'L':
			mtx = SpriteData.letters[11];
			break;
		case 'M':
			mtx = SpriteData.letters[12];
			break;
		case 'N':
			mtx = SpriteData.letters[13];
			break;
		case 'O':
			mtx = SpriteData.letters[14];
			break;
		case 'P':
			mtx = SpriteData.letters[15];
			break;
		case 'Q':
			mtx = SpriteData.letters[16];
			break;
		case 'R':
			mtx = SpriteData.letters[17];
			break;
		case 'S':
			mtx = SpriteData.letters[18];
			break;
		case 'T':
			mtx = SpriteData.letters[19];
			break;
		case 'U':
			mtx = SpriteData.letters[20];
			break;
		case 'V':
			mtx = SpriteData.letters[21];
			break;
		case 'W':
			mtx = SpriteData.letters[22];
			break;
		case 'X':
			mtx = SpriteData.letters[23];
			break;
		case 'Y':
			mtx = SpriteData.letters[24];
			break;
		case 'Z':
			mtx = SpriteData.letters[25];
			break;
		case '1':
			mtx = SpriteData.numbers[0];
			break;
		case '2':
			mtx = SpriteData.numbers[1];
			break;
		case '3':
			mtx = SpriteData.numbers[2];
			break;
		case '4':
			mtx = SpriteData.numbers[3];
			break;
		case '5':
			mtx = SpriteData.numbers[4];
			break;
		case '6':
			mtx = SpriteData.numbers[5];
			break;
		case '7':
			mtx = SpriteData.numbers[6];
			break;
		case '8':
			mtx = SpriteData.numbers[7];
			break;
		case '9':
			mtx = SpriteData.numbers[8];
			break;
		case '0':
			mtx = SpriteData.numbers[9];
			break;
		case '.':
			mtx = SpriteData.special[0];
			break;
		case '!':
			mtx = SpriteData.special[1];
			break;
		case '"':
			mtx = SpriteData.special[2];
			break;
		case '(':
			mtx = SpriteData.special[3];
			break;
		case ')':
			mtx = SpriteData.special[4];
			break;
		case '{':
			mtx = SpriteData.special[5];
			break;
		case '}':
			mtx = SpriteData.special[6];
			break;
		case ',':
			mtx = SpriteData.special[7];
			break;
		case ':':
			mtx = SpriteData.special[8];
			break;
		case ';':
			mtx = SpriteData.special[9];
			break;
		case '-':
			mtx = SpriteData.special[10];
			break;
		case '+':
			mtx = SpriteData.special[11];
			break;
		case '/':
			mtx = SpriteData.special[12];
			break;
		//case '\': BACKSLASH DISABLED!
		//	mtx[0] = SpriteData.special[13];
		//	break;
		case '€':
			mtx = SpriteData.special[14];
			break;
		case '*':
			mtx = SpriteData.special[15];
			break;
		case '@':
			mtx = SpriteData.special[16];
			break;
		case '|':
			mtx = SpriteData.special[17];
			break;
		case '>':
			mtx = SpriteData.special[18];
			break;
		case '<':
			mtx = SpriteData.special[19];
			break;
		case ' ':
			cf+=5;
			nx=true;
			break;
		}
		if(nx == true) {
			continue;
		}
		for (int j = 0; j < mtx[0].length; j++) {
			if(mtx[j].length > l) {
				l = mtx[j].length;
			}
		}
		// PRINT LENGTH OF CURRENT CHAR
		//System.out.println(l);
		// SET CURRENT CHAR TO FULL ARRAY
		for (int u = 0; u < 3; u++) {
		for (int j = 0; j < l; j++) {
			for (int j2 = 0; j2 < 8; j2++) {
				full[u][j+cf][j2] = (int) (mtx[j2][j] * cdata[i][u]);
			}
		}
		}
		cf += (l+2);
		lastlength = cf;
		}
	}
}
