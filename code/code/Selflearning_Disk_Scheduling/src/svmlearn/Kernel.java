package svmlearn;
public class Kernel 
{
	public static double euclidean_dist2(FeatureNode [] x, FeatureNode [] z) {
		double sum=0;
		int i,j;
		for (i=0,j=0;x!=null && z!=null && i<x.length && j<z.length;) {
			if (x[i].index<z[j].index) {
				sum+=x[i].value*x[i].value;
				i++;
			}
			else if (z[j].index<x[i].index) {
				sum+=z[j].value*z[j].value;
				j++;
			}
			else {
				sum+=(x[i].value-z[j].value)*(x[i].value-z[j].value);
				i++;
				j++;
			}
		}
		for (;x!=null && i<x.length;i++) {
			sum+=x[i].value*x[i].value;
		}
		for (;z!=null && j<z.length;j++) {
			sum+=z[j].value*z[j].value;
		}
		return sum;
	}
	public static double dot_product(FeatureNode [] x, FeatureNode [] z) {
		double sum=0;
		int i,j;
		for (i=0,j=0;x!=null && z!=null && i<x.length && j<z.length;) {
			if (x[i].index<z[j].index) {
				i++;
			}
			else if (z[j].index<x[i].index) {
				j++;
			}
			else {
				sum+=x[i].value*z[j].value;
				i++;
				j++;
			}
		}
		return sum;
	}
	public static double kLinear(FeatureNode [] x, FeatureNode [] z) {
		return dot_product(x, z);
	}

	public static double kPoly(FeatureNode [] x, FeatureNode [] z, double a, double b, double c) {
		if (c == 1.0)
			return a*dot_product(x, z)+b;
		return Math.pow(a*dot_product(x, z)+b, c);
	}

	public static double kGaussian(FeatureNode [] x, FeatureNode [] z, double sigma) {
		return (-0.5/sigma*sigma)*euclidean_dist2(x, z);
	}

	public static double kTanh(FeatureNode [] x, FeatureNode [] z, double a, double b) {
		return Math.tanh(a*dot_product(x, z)+b);
	}
}
