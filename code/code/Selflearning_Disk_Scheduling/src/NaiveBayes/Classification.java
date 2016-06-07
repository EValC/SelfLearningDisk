package NaiveBayes;

import java.util.Collection;


public class Classification<T, K> {
    private Collection<T> featureset;
    private K category;
    private float probability;
    public Classification(Collection<T> featureset, K category) {
        this(featureset, category, 1.0f);
    }
    public Classification(Collection<T> featureset, K category,
            float probability) {
        this.featureset = featureset;
        this.category = category;
        this.probability = probability;
    }
    public Collection<T> getFeatureset() {
        return featureset;
    }
    public float getProbability() {
        return this.probability;
    }

    public K getCategory() {
        return category;
    }
    
    public String toString() {
        return "[category=" + this.category
                + ", probabilitycalculated=" + this.probability
                + ", featureset=" + this.featureset
                + "]";
    }

}
