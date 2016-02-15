import java.io.*;
import java.util.*;

public class BPwc {

	public String name;       
	public int uStart;
	public int uEnd;       
	public int lStart;
	public int lEnd;
	public int designNum;
	public int uLen;
	public int lLen;
	public int uuE;
	public int ulE;
	public int luE;
	public int llE;
	public int pos; // possibility
	public BPdimer AB;
	public BPdimer BA;

	@SuppressWarnings("resource")
	public BPwc(File file, int designNum, int uLen, int lLen, int uuE, int ulE,
			int luE, int llE, int pos) throws FileNotFoundException {


		this.name = file.getName();

		this.uStart = -1;
		this.uEnd = -1;
		this.lStart = -1;
		this.lEnd = -1;
		this.designNum = designNum;
		this.uLen = uLen;
		this.lLen = lLen;
		this.uuE = uuE;
		this.ulE = ulE;
		this.luE = luE;
		this.llE = llE;
		this.pos = pos;

//		this.AB = new BPdimer("-18A19B", -1, uLen, uuE,ulE, designNum);
//		this.BA = new BPdimer("-18B19A", -1, uLen, uuE,ulE, designNum);

		getIndex(file);
		
	}

	
	public BPwc(File file) throws FileNotFoundException {

		this(file, -1, -1, -1, -1, -1, -1, -1, -1);

//		this.AB = new BPdimer("-18A19B", -1, uLen, uuE,ulE, designNum);
//		this.BA = new BPdimer("-18B19A", -1, uLen, uuE,ulE, designNum);

		getIndex(file);

	}

	
	private void getIndex(File file) throws FileNotFoundException {

		Scanner scanin = new Scanner(file);
		int indexCur = 0;
		while (scanin.hasNextLine()) {
			//			System.out.println("BP INSIDE!!!!!");
			if (this.uStart != -1 && this.uEnd != -1 && this.lStart != -1 && this.lEnd != -1) {
				break;
			}
			String next = scanin.nextLine().trim();
			indexCur++;
			if (this.uStart == -1) {
				if (next.startsWith("0")) {
					this.uStart = indexCur;
				}
			} else {
				if (this.uEnd == -1) {
					if (!next.startsWith("0")) {
						this.uEnd = indexCur;
					}
				} else {
					if (this.lStart == -1) {
						if (next.startsWith("0")) {
							this.uStart = indexCur;
						}
					}  else {
						if (this.lEnd == -1) {
							if (!next.startsWith("0")) {
								this.uEnd = indexCur;
							}
						} 
					}
				}
			}
		}
	}


}
