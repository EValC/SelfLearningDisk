package svmlearn;

import java.io.*;
import java.util.*;
public class Problem {
	public int l;
	public int n;
	public int[] y;
	public CategoryMap<Integer> catmap;
	public FeatureNode[][] x;
	public Problem() {
		l = 0;
		n = 0;
		catmap = new CategoryMap<Integer>();
	}
	public void loadBinaryProblem(String filename) {
		String row;
		ArrayList<Integer> classes = new ArrayList<Integer>();
		ArrayList<FeatureNode []> examples = new ArrayList<FeatureNode []>();
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			while ((row = r.readLine()) != null) {
				String [] elems = row.split(" ");
				if (elems[0].charAt(0) == '+') 
                                {
					elems[0] = elems[0].substring(1);
				}
				Integer cat = Integer.parseInt(elems[0]);
				catmap.addCategory(cat);
				if (catmap.size() > 2) {
					throw new IllegalArgumentException("only 2 classes allowed!");
				}
				classes.add(catmap.getNewCategoryOf(cat));
				examples.add(parseRow(elems));
			}
			x = new FeatureNode[examples.size()][];
			y = new int[examples.size()];
			for (int i=0; i<examples.size(); i++) {
				x[i] = examples.get(i);
				y[i] = 2*classes.get(i)-1;
			}
			l = examples.size();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public FeatureNode [] parseRow(String [] row) {
		FeatureNode [] example = new FeatureNode[row.length-1];
		int maxindex = 0;
		for (int i=1; i<row.length; i++) {
			String [] iv = row[i].split(":");
			int index = Integer.parseInt(iv[0]);
			if (index <= maxindex) {
				throw new IllegalArgumentException("indices must be in increasing order!");
			}
			maxindex = index;
			double value = Double.parseDouble(iv[1]);
			example[i-1] = new FeatureNode(index, value);
		}
		if (n < maxindex)
			n = maxindex;
		return example;
	}
}
