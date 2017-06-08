package p5.obliczajkas.model1.MathUtils;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseChangeTest {

    @Test
    public void transform() throws Exception {
        RealMatrix u = MatrixUtils.createRealMatrix(2, 2);
        u.setColumnVector(0, MatrixUtils.createRealVector(new double[]{1, 0}));
        u.setColumnVector(1, MatrixUtils.createRealVector(new double[]{2, 1}));
        BaseChange changer = new BaseChange(u);

        RealVector x0 = MatrixUtils.createRealVector(new double[]{0, 0});
        RealVector y0 = MatrixUtils.createRealVector(new double[]{0, 0});
        Assert.assertEquals(y0.getLInfDistance(changer.transform(x0)), 0, 1e-9);

        RealVector x1 = MatrixUtils.createRealVector(new double[]{3, 1});
        RealVector y1 = MatrixUtils.createRealVector(new double[]{1, 1});
        Assert.assertEquals(y1.getLInfDistance(changer.transform(x1)), 0, 1e-9);

        RealVector x2 = MatrixUtils.createRealVector(new double[]{2, -1});
        RealVector y2 = MatrixUtils.createRealVector(new double[]{4, -1});
        Assert.assertEquals(y2.getLInfDistance(changer.transform(x2)), 0, 1e-9);
    }

    @Test
    public void orthonormalGrammShmidtBase() throws Exception {
        RealMatrix v = MatrixUtils.createRealMatrix(2, 2);
        v.setColumnVector(0, MatrixUtils.createRealVector(new double[]{3, 1}));
        v.setColumnVector(1, MatrixUtils.createRealVector(new double[]{2, 2}));

        RealMatrix u = BaseChange.orthonormalGrammShmidtBase(v);

        RealMatrix uExpected = MatrixUtils.createRealMatrix(2, 2);
        uExpected.setColumnVector(0, MatrixUtils.createRealVector(new double[]{3, 1}).mapMultiply(1./Math.sqrt(10)));
        uExpected.setColumnVector(1, MatrixUtils.createRealVector(new double[]{-2./5, 6./5}).mapMultiply(1./Math.sqrt(40./25)));

        for (int i = 0; i < uExpected.getColumnDimension(); ++i) {
            double dst = uExpected.getColumnVector(i).getLInfDistance(u.getColumnVector(i));
            Assert.assertEquals(0, dst, 1e-9);
        }
    }

}