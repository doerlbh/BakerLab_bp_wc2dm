import java.io.*;     // for File, FileNotFoundException
import java.util.*;   // for Scanner, List, Set, Collections

public class bp_wc2dm {

	private static final String TEMPBPAB = "18A19B-template.bp";
	private static final String TEMPBPBA = "18B19A-template.bp";
	private static final String OFILEAB = "18A19B-wc2dm-test1";
	private static final String OUTPAB = "18A19B-wc2dm-test1";
	private static final String OFILEBA = "18B19A-wc2dm-test1";
	private static final String OUTPBA = "18B19A-wc2dm-test1";
	//	private static final String PARAMETER = "test1-fParameter.out";
	private static final String PATH = "/Users/DoerLBH/Dropbox/Eclipse/workspace/bp_wc2dm/";
	private static final String PDB = ".pdb";
	private static final String BP = ".bp";
	private static final String PATHPDB = "/Users/DoerLBH/Dropbox/Eclipse/"
			+ "workspace/bp_wc2dm/wc2dm/finished_models_test1_256";
	private static List<String> ABtemplateList = new ArrayList<String>();
	private static List<String> BAtemplateList = new ArrayList<String>();
	//	private static List<Integer> parameterList = new ArrayList<Integer>();
	private static TreeMap<String, BPdimer> Map18A19B = new TreeMap<String, BPdimer>();
	private static TreeMap<String, BPdimer> Map18B19A = new TreeMap<String, BPdimer>();
	//	private static Map<String, BPwc> bpList = new TreeMap<String, BPwc>();
	//	private static Map<String, PDBinfo> pdbList = new TreeMap<String, PDBinfo>();


	public static void main(String[] args) throws IOException {	

		File path = new File(PATH);
		String dir = path.getPath();
		String oDirAB = createDir(OUTPAB);
		String oDirBA = createDir(OUTPBA);

		tempOut(dir, ABtemplateList, TEMPBPAB);
		tempOut(dir, BAtemplateList, TEMPBPBA);

		File pathPDB = new File(PATHPDB);
		File allFiles[] = pathPDB.listFiles();
		for (File file : allFiles) {
			extractAA(file); 
		}

		//		@SuppressWarnings("resource")
		//		Scanner parameterin = new Scanner(new File(dir, PARAMETER));
		//		while (parameterin.hasNextLine()) {
		//			parameterList.add(Integer.parseInt(parameterin.nextLine().trim()));
		//		}
		//		sortOut(parameterList);

		printList(Map18A19B, oDirAB, OFILEAB);
		printList(Map18B19A, oDirBA, OFILEBA);

		debugCombo(Map18A19B, Map18B19A);
		debugCombo(Map18B19A, Map18A19B);

	}

	private static void debugCombo(TreeMap<String, BPdimer> map1, TreeMap<String, BPdimer> map2) {
		for (String e : map1.keySet()) {
			System.out.println("[" + e + "] " + map1.get(e).name);
//			System.out.println(map1.get(e).tmatch);
			for (String id : map1.get(e).tmatch) {
				System.out.println(map2.get(id).name);
			}
		}
	}

	private static void extractAA(File file) throws FileNotFoundException {
		if (file.getName().endsWith(PDB)) {
			PDBinfo pdb = new PDBinfo(file);
			String[] parts = file.getName().split("-");
			List<Integer> pList = new ArrayList<Integer>();
			for (int i = 0; i < parts.length; i++) {
				String l = parts[i].replaceAll("[^0-9]", "");
				//				System.out.println(l);
				if (!l.equals("")) {
					pList.add(Integer.parseInt(l));
				}
			}
			int ulen = pList.get(4) + pList.get(6) + pList.get(7);
			int llen = pList.get(5) + pList.get(8) + pList.get(9);
			String AB = pdb.getSeq(76, 76 + ulen);
			String BA = pdb.getSeq(228 + ulen, 228 + ulen + llen);

			BPdimer abbp = new BPdimer(ABtemplateList, OFILEAB, pList.get(4), 
					pList.get(6), pList.get(7), pList.get(10));
			BPdimer babp = new BPdimer(BAtemplateList, OFILEBA, pList.get(5), 
					pList.get(8), pList.get(9), pList.get(10));
			if (!Map18A19B.containsKey(AB)) {
				abbp.id = AB;
				Map18A19B.put(AB, abbp);
				//				System.out.println("insideKey");
			}
			if (!Map18B19A.containsKey(BA)) {
				babp.id = BA;
				Map18B19A.put(BA, babp);
				//				System.out.println("insideKey" + BA);
			}
			Map18A19B.get(AB).shuffle();
			Map18B19A.get(BA).shuffle();
			//			System.out.println(Map18B19A.get(BA));
			//			Map18A19B.get(AB).putPDB(pdb);
			//			Map18B19A.get(BA).putPDB(pdb);	
			//			Map18A19B.get(AB).putBP(Map18B19A.get(BA));
			//			Map18B19A.get(BA).putBP(Map18A19B.get(AB));	
			Map18A19B.get(AB).tputPDB(pdb.name);
			Map18B19A.get(BA).tputPDB(pdb.name);	
			Map18A19B.get(AB).tputBP(BA);
			Map18B19A.get(BA).tputBP(AB);	
		}
	}

	private static String createDir(String outp) {
		File oPath = new File(PATH + outp);
		if (!oPath.exists()) {
			if (oPath.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		return oPath.getPath();
	}

	private static void tempOut(String dir, List<String> list, String tempName) 
			throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner templateIn = new Scanner(new File(dir, tempName));
		while (templateIn.hasNextLine()) {
			list.add(templateIn.nextLine());
		}
	}

	//	private static void sortOut(List<Integer> pList) {
	//		for (int i = 0; i < pList.size()/13; i++) {
	//			List18A19B.add(new BPdimer(ABtemplateList, OFILEAB, pList.get(i+6), 
	//					pList.get(i+8), pList.get(i+9), pList.get(i+12)));
	//			List18B19A.add(new BPdimer(BAtemplateList, OFILEBA, pList.get(i+7), 
	//					pList.get(i+10), pList.get(i+11), pList.get(i+12)));			
	//		}
	//	}

	private static void printList(TreeMap<String, BPdimer> map, String oDir, String ofile) throws IOException {

		int count = 0;
		for (String id : map.keySet()) {
			BPdimer bpfile = map.get(id);
			count++;
			String fout = ofile + "-D" + String.format("%03d", count) 
			+ bpfile.name.substring(ofile.length(), bpfile.name.length() - 3) + "-" + bpfile.id + BP;
			//			String fout = bpfile.name;
			File result = new File(oDir, fout);
			result.createNewFile(); 
			PrintStream output = new PrintStream(result);
			for (int r = 0; r < bpfile.bpPrint.size(); r++) {
				output.print(bpfile.bpPrint.get(r));
				output.println();
			}

			output.close();
			System.out.println("Finished! " + fout);
		}

	}

}


