
import java.io.*;
import java.util.*;

public class BPdimer {

	private static final String STNL = "BLOCK";
	private static final String ADD = "0 x L PIKAA ";

	public String name;
	public int len;
	public int uE;
	public int lE;
	public int WCdesignPos; // possibility
	public TreeSet<BPdimer> match = new TreeSet<BPdimer>(); 
	public TreeSet<PDBinfo> parents = new TreeSet<PDBinfo>();
	public TreeSet<String> tmatch = new TreeSet<String>(); 
	public TreeSet<String> tparents = new TreeSet<String>();
	public List<String> bpPrint;
	public String id; //check for uniqueness

	public BPdimer(List<String> template, String prefix, int len, int uE, int lE, int WCdesignPos) {

		this.name = prefix + "-len" + len + "-uE" + uE + "-lE" + lE + ".bp";

		this.len = len;
		this.uE = uE;
		this.lE = lE;
		this.WCdesignPos = WCdesignPos;
		this.bpPrint = new ArrayList<String>();
		this.bpPrint.addAll(template);
		this.id = "";
	}

	public void tputBP(String bpd) {
		tmatch.add(bpd);
	}

	public void tputPDB(String pdb) {
		tparents.add(pdb);
	}
	
	public void shuffle() {
		for (int t = 0; t < this.bpPrint.size(); t++) {
			if (this.bpPrint.get(t).endsWith(STNL)) {
				this.bpPrint.remove(t);
				char[] chars = this.id.toCharArray();
				for (int i = chars.length - 1; i >= 0; i--) {
					this.bpPrint.add(t, ADD + chars[i]);
				}
			} 
		}
	}

}
