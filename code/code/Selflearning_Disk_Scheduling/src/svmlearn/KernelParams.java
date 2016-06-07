package svmlearn;
public class KernelParams {
	public int kernel = 1;
	protected double a;
	protected double b;
	protected double c;
	public KernelParams(int k, double a, double b, double c) {
		this.kernel = k;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public KernelParams() 
        {
		this(1,1,1,1);
	}
}
