package p5.obliczajkas.model1.MathUtils;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class BaseChange {

    private final RealMatrix transformationMatrix;

    // Cols represent subsequent base vectors
    public BaseChange(RealMatrix newBase) {
        this.transformationMatrix = MatrixUtils.inverse(newBase.transpose());
    }

    public RealVector transform(RealVector inp) {
        return this.transformationMatrix.preMultiply(inp);
    }

    public static RealMatrix orthonormalGrammShmidtBase(RealMatrix vs) {
        RealMatrix us = MatrixUtils.createRealMatrix(vs.getColumnDimension(), vs.getRowDimension());

        // https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process
        us.setColumnVector(0, vs.getColumnVector(0).unitVector());
        for (int i = 1; i < vs.getColumnDimension(); ++i) {
            RealVector v = vs.getColumnVector(i);
            RealVector u = v.copy();
            for (int j = 0; j < i; ++j) {
                RealVector proj = v.projection(us.getColumnVector(j));
                u = u.combineToSelf(1, -1, proj);
            }
            u.unitize();
            us.setColumnVector(i, u);
        }

        return us;
    }

    public static RealVector rotate90Deg(RealVector inp) {
        assert(inp.getDimension() == 2);
        return MatrixUtils.createRealVector(new double[]{inp.getEntry(1), -inp.getEntry(0)});
    }
}
