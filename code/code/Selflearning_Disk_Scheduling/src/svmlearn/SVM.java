package svmlearn;

import java.util.*;

public class SVM {
	
	private Model model;
	private double C = 1;
	private double tol = 10e-3;
	private double tol2 = 10e-5;
	private double tol3 = 10e-3;
	private double tol4 = 10e-5;
	private int maxpass = 10;
	private double Ei, Ej;
	private double ai_old, aj_old, b_old;
	private double L, H;
	private double maxup, minlow;
	private double bup, blow;
	private int iup, ilow;
	private double eta;
	private List<Integer> I0, I1, I2, I3, I4;
	private double [] E;
	

	public SVM() {
	}
	public void svmTrain(Problem train) {
		KernelParams p = new KernelParams();
		svmTrain(train, p, 0);
	}

	public void svmTrain(Problem train, KernelParams p, int alg) {
		switch (alg) {
		case 0:
			SMO_simple(train, p);
			break;
		case 1:
			SMO_Platt(train, p);
			break;
		
		}
	}
	private int psmoTakeStep(int i, int j) {
		double ai, aj;
		if (i == j) return 0;
		b_old = model.b;
		ai_old = model.alpha[i];
		int yi = model.y[i];
		int yj = model.y[j];
		Ei = E[i];
		double s = yi*yj;
		L = computeL(yi, yj);
		H = computeH(yi, yj);
		if (L == H)
		return 0;
		double kii = kernel(i,i);
		double kjj = kernel(j,j);
		double kij = kernel(i,j); 
		eta = 2*kij-kii-kjj;
		if (eta < 0) {
			aj = aj_old - yj*(Ei-Ej)/eta;
			if (aj < L)
				aj = L;
			else if (aj > H)
				aj = H;
		} else {
			double vi, vj;
			vi = svmTestOne(model.x[i]) - yi*ai_old*kii - yj*aj_old*kij;
			vj = svmTestOne(model.x[j]) - yi*ai_old*kij - yj*aj_old*kjj;
			double Lobj = smoObj(L, yi, yj, kij, kii, kjj, vi, vj);
			double Hobj = smoObj(H, yi, yj, kij, kii, kjj, vi, vj);
			if (Lobj > Hobj+tol)
				aj = L;
			else if (Lobj < Hobj-tol)
				aj = H;
			else
				aj = aj_old;
		}
		if (aj < tol2)
			aj = 0;
		else if (aj > C-tol2)
			aj = C;
		if (Math.abs(aj-aj_old) < tol*(aj+aj_old+tol))
			return 0;
		ai = ai_old + s*(aj_old-aj);
		computeBias(ai, aj, yi, yj, kii, kjj, kij);
		model.alpha[i] = ai;
		model.alpha[j] = aj;
		for (int k=0; k<model.l; k++) {
			double kik = kernel(model.x[i], model.x[k]);  
			double kjk = kernel(model.x[j], model.x[k]);  
			E[k] += (ai-ai_old)*yi*kik + (aj-aj_old)*yj*kjk - b_old + model.b;
		}
		return 1;
	}
	private int psmoExamineExample(int j) {
		int i = 0;
		int randpos;
		int yj = model.y[j];
		aj_old = model.alpha[j];
		Ej = E[j];
		double rj = Ej*yj;
		if ((rj<-tol && aj_old<C) || (rj>tol && aj_old>0)) {
			boolean exists = false;
			for (int k=0; k<model.l; k++)
				if (model.alpha[k]>0 && model.alpha[k]<C) {
					exists = true;
					break;
				}
			if (exists) {
				int maxind = 0;
				double maxval = Math.abs(E[0]-Ej);
				for (int k=1; k<model.l; k++)
					if (Math.abs(E[k]-Ej) > maxval) {
						maxval = Math.abs(E[k]-Ej);
						maxind = k;
					}
				if (psmoTakeStep(maxind, j) == 1)
					return 1;
			}
			randpos = (int)Math.floor(Math.random()*model.l);
			for (int k=0; k<model.alpha.length; k++) {
				i = (randpos+k)%model.l;
				if (model.alpha[i]>0 && model.alpha[i]<C) {
					if (psmoTakeStep(i, j) == 1)
						return 1;
				}
			}
			randpos = (int)Math.floor(Math.random()*model.l);
			for (int k=0; k<model.alpha.length; k++) {
				i = (randpos+k)%model.l;
				if (psmoTakeStep(i, j) == 1)
					return 1;
			}
		}
		return 0;
	}
	private void SMO_Platt(Problem train, KernelParams p) {
		int numChanged = 0;
		int examineAll = 1;
		model = new Model();
		model.alpha = new double [train.l];
		model.b = 0;
		model.params = p;
		model.x = train.x;
		model.y = train.y;
		model.l = train.l;
		model.n = train.n;
		E = new double [model.l];
		for (int i=0; i<model.l; i++)
			E[i] = svmTestOne(model.x[i]) - model.y[i];
		while (numChanged > 0 || examineAll == 1) {
			numChanged = 0;
			if (examineAll == 1) {
				for (int i=0; i<model.l; i++) {
					numChanged += psmoExamineExample(i);
				}
			} else {
				for (int i=0; i<model.l; i++) {
					if (model.alpha[i] > 0 && model.alpha[i] < 0) {
						numChanged += psmoExamineExample(i);
					}
				}
			}
			if (examineAll == 1)
				examineAll = 0;
			else if (numChanged == 0)
				examineAll = 1;
			System.out.print(".");
		}
		System.out.println();
	}
	
	private void SMO_simple(Problem train, KernelParams p) {
		int pass = 0;
		int alpha_change = 0;
		int i, j;
		double eta;
		//Initialize:
		model = new Model();
		model.alpha = new double [train.l];
		model.b = 0;
		model.params = p;
		model.x = train.x;
		model.y = train.y;
		model.l = train.l;
		model.n = train.n;
		while (pass < maxpass) {
			alpha_change = 0;
			for (i=0; i<train.l; i++) {
				Ei = svmTestOne(train.x[i]) - train.y[i];
				if ((train.y[i]*Ei<-tol && model.alpha[i]<C) || (train.y[i]*Ei>tol && model.alpha[i]>0)) {
					j = (int)Math.floor(Math.random()*(train.l-1));
					j = (j<i)?j:(j+1);
					Ej = svmTestOne(train.x[j]) - train.y[j];
					ai_old = model.alpha[i];
					aj_old = model.alpha[j];
					L = computeL(train.y[i], train.y[j]);
					H = computeH(train.y[i], train.y[j]);
					if (L == H) //next i
						continue;
					double kij = kernel(i,j); 
					double kii = kernel(i,i); 
					double kjj = kernel(j,j); 
					eta = 2*kij-kii-kjj;
					if (eta >= 0) 
						continue;
					model.alpha[j] = aj_old - (train.y[j]*(Ei-Ej))/eta;
					if (model.alpha[j] > H)
						model.alpha[j] = H;
					else if (model.alpha[j] < L)
						model.alpha[j] = L;
					if (Math.abs(model.alpha[j]-aj_old) < tol2) 
						continue;
					model.alpha[i] = ai_old + train.y[i]*train.y[j]*(aj_old-model.alpha[j]);
					computeBias(model.alpha[i], model.alpha[j], train.y[i], train.y[j], kii, kjj, kij);
					alpha_change++;
				}
			}
			if (alpha_change == 0)
				pass++;
			else
				pass = 0;
			if (alpha_change > 0)
				System.out.print(".");
			else
				System.out.print("*");
		}
		System.out.println();
	}
	

	private double smoObj(double aj, int yi, int yj, double kij, double kii, double kjj, double vi, double vj) {
		double s = yi*yj;
		double gamma = ai_old + s*aj_old;
		return (gamma + (1-s)*aj - 0.5*kii*(gamma-s*aj)*(gamma-s*aj) - 0.5*kjj*aj*aj +
				- s*kij*(gamma-s*aj)*aj - yi*(gamma-s*aj)*vi - yj*aj*vj);
	}
	private int takeStep(int i, int j) {
		double kii, kjj, kij;
		double ai, aj;
		double gamma;
		double s;
		if (i == j)
			return 0;
		Ei = svmTestOne(model.x[i]) - model.y[i];
		Ej = svmTestOne(model.x[j]) - model.y[j];
		ai_old = model.alpha[i];
		aj_old = model.alpha[j];
		L = computeL(model.y[i], model.y[j]);
		H = computeH(model.y[i], model.y[j]);
		if (L == H)
			return 0;
		kij = kernel(i,j);
		kii = kernel(i,i);
		kjj = kernel(j,j);
		s = model.y[i]*model.y[j];
		gamma = ai_old + s*aj_old;
		eta = 2*kij-kii-kjj;
		if (eta < 0) {
			aj = aj_old - (model.y[j]*(Ei-Ej))/eta;
			if (aj > H)
				aj = H;
			else if (aj < L)
				aj = L;
		} else {
			double vi = 0, vj = 0;
			vi = svmTestOne(model.x[i]) - model.y[i]*ai_old*kii - model.y[j]*aj_old*kij;
			vj = svmTestOne(model.x[j]) - model.y[i]*ai_old*kij - model.y[j]*aj_old*kjj;
			double Lobj = (1-s)*L - kii*(-gamma*s*L + 0.5*L*L) - 0.5*kjj*L*L +
							+ kij*L*L + model.y[i]*s*L*vi - model.y[j]*L*vj;
			double Hobj = (1-s)*H - kii*(-gamma*s*H + 0.5*H*H) - 0.5*kjj*H*H +
							+ kij*H*H + model.y[i]*s*H*vi - model.y[j]*H*vj;
			if (Lobj > Hobj+tol4)
				aj = L;
			else if (Lobj < Hobj-tol4) 
				aj = H;
			else 
				aj = aj_old;
		}
		if (Math.abs(aj-aj_old) < tol4*(aj+aj_old+tol4))
			return 0;
		ai = ai_old + s*(aj_old-aj);		
		computeBias(ai, aj, model.y[i], model.y[j], kii, kjj, kij);
		model.alpha[i] = ai;
		model.alpha[j] = aj;
		updateISets(i, j);
		computeUpLow();
		return 1;
	}
	private void updateISets(int i, int j) {
		I0.remove((Integer)i);
		I0.remove((Integer)j);
		I1.remove((Integer)i);
		I1.remove((Integer)j);
		I2.remove((Integer)i);
		I2.remove((Integer)j);
		I3.remove((Integer)i);
		I3.remove((Integer)j);
		addtoSet(i, model.alpha[i], model.y[i]);
		addtoSet(j, model.alpha[j], model.y[j]);
	}
	private void addtoSet(int i, double a, int y) {
		if (a > 0 && a < C)
			I0.add(i);
		else if (y == 1 && a == 0)
			I1.add(i);
		else if (y == -1 && a == C)
			I2.add(i);
		else if (y == 1 && a == C)
			I3.add(i);
		else 
			I4.add(i);
	}
	private void computeUpLow() {
		boolean firstup = true; 
		boolean firstlow = true;
		double s = 0;
		for (int i=0; i<model.alpha.length; i++) {
			if ((model.alpha[i]>0 && model.alpha[i]<C) || (model.alpha[i]==0 && model.y[i]==1) || (model.alpha[i]==C && model.y[i]==-1)) {
				
				s = svmTestOne(model.x[i]) - model.y[i];
				if (firstup) {
					bup = s;
					iup = i;
					firstup = false;
				} else {
					if (s > bup) {
						bup = s;
						iup = i;
					}
				}
			} else if ((model.alpha[i]>0 && model.alpha[i]<C) || (model.alpha[i]==0 && model.y[i]==-1) || (model.alpha[i]==C && model.y[i]==1)) {
				
				s = svmTestOne(model.x[i]) - model.y[i];
				if (firstlow) {
					blow = s;
					ilow = i;
					firstlow = false;
				} else {
					if (s < blow) {
						blow = s;
						ilow = i;
					}
				}
			}
		}
	}
	
	private double computeL(int yi, int yj) {
		double L = 0;
		if (yi != yj) {
			L = Math.max(0, -ai_old+aj_old);
		} else {
			L = Math.max(0, ai_old+aj_old-C);
		}
		return L;
	}
	
	private double computeH(int yi, int yj) {
		double H = 0;
		if (yi != yj) {
			H = Math.min(C, -ai_old+aj_old+C);
		} else {
			H = Math.min(C, ai_old+aj_old);
		}
		return H;
	}
	
	private void computeBias(double ai, double aj, int yi, int yj, double kii, double kjj, double kij) {
		double b1 = model.b - Ei - yi*(ai-ai_old)*kii - yj*(aj-aj_old)*kij;
		double b2 = model.b - Ej - yi*(ai-ai_old)*kij - yj*(aj-aj_old)*kjj;
		if (0 < ai && ai<C)
			model.b = b1;
		else if (0 < aj && aj < C)
			model.b = b2;
		else
			model.b = (b1+b2)/2;		
	}
	
	public int [] svmTest(Problem test) {
		if (test == null) 
			return null;
		int [] pred = new int[test.l];
		for (int i=0; i<test.l; i++) {
			pred[i] = (svmTestOne(test.x[i])<0?-1:1);
		}
		return pred;
	}
	
	public double svmTestOne(FeatureNode [] x) {
		double f = 0;
		for (int i=0; i<model.l; i++) {
			f += model.alpha[i]*model.y[i]*kernel(x, model.x[i]);
		}
		return f+model.b;
	}
	
	private double kernel(FeatureNode [] x, FeatureNode [] z) {
		double ret = 0;
		if (model.params == null)
			model.params = new KernelParams(1,1,1,1);
		switch (model.params.kernel) {
		case 0: 
			break;
		case 1: 
			ret = Kernel.kLinear(x, z);
			break;
		case 2: 
			ret = Kernel.kPoly(x, z, model.params.a, model.params.b, model.params.c);
			break;
		case 3: 
			ret = Kernel.kGaussian(x, z, model.params.a);
			break;
		case 4: 
			ret = Kernel.kTanh(x, z, model.params.a, model.params.b);
			break;
		}
		return ret;
	}
	
	private double kernel(int i, int j) {
		double ret = 0;
		if (model.params == null)
			model.params = new KernelParams(1,1,1,1);
		switch (model.params.kernel) {
		case 0: 
			break;
		case 1: 
			ret = Kernel.kLinear(model.x[i], model.x[j]);
			break;
		case 2: 
			ret = Kernel.kPoly(model.x[i], model.x[j], model.params.a, model.params.b, model.params.c);
			break;
		case 3: 
			ret = Kernel.kGaussian(model.x[i], model.x[j], model.params.a);
			break;
		case 4: 
			ret = Kernel.kTanh(model.x[i], model.x[j], model.params.a, model.params.b);
			break;
		}
		return ret;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model m) {
		model = m;
	}
	public double getC() {
		return C;
	}
	public void setC(double C) {
		this.C = C;
	}
	public double getTolerance() {
		return tol;
	}
	public void setTolerance(double tol) {
		this.tol = tol; 
	}
	public double getTolerance2() {
		return tol2;
	}
	public void setTolerance2(double tol2) {
		this.tol2 = tol2; 
	}
	public int getMaxPass() {
		return maxpass;
	}
	public void setMaxPass(int p) { 
		maxpass = p;
	}
	public void setParameters(double C, double tol, double tol2, int maxpass) {
		this.C = C;
		this.tol = tol;
		this.tol2 = tol2;
		this.maxpass = maxpass;
	}
}
