import gates.pauli.XGate;
import gates.pauli.YGate;
import gates.pauli.ZGate;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;
import org.junit.Test;


public class Pauli_Gates {
    @Test
    public void XGate() {

        ComplexDoubleMatrix bra0ket
                = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket
                = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);

        // testing Qubit validity
        assert q0.isValid();
        assert q1.isValid();


        // checking and printing Qubit states prior to and after gate application
        XGate n = new XGate();
        q0.applyGate(n);
        q0.getState().print();
        q1.getState().print();

        // checking the XGate works for the Basis states of our Qubits
        assert q1.equals(q0);

        n.applyTo(q1.getState());
        n.applyTo(q0.getState());

        assert q1.equals(q0);


        q1.getState().print();
        q0.applyGate(n);
        q0.getState().print();
        // restored qubits to their original states

        // testing our scaled up version of the gate for 2 - qubit inputs
        // testing the scaled gate on the combined qubit of our basis states.
        XGate n2 = new XGate(2);
        Qubit q3 = q0.entangle(q1);
        assert q3.isValid();
        // making a copy of the original combined qubit to get reference results.
        // printing the states to make sure the gate has indeed been applied
        Qubit q4 = new Qubit(q3.getState());

        q3.getState().print();
        n2.getGate().print();

        Qubit qtest = new Qubit((n2.getGate()).mmul(q3.getState()));
        qtest.getState().print();

        q3.applyGate(n2);
        q3.getState().print();


        assert q3.isValid();
        assert !q3.equals(q4);

        q3.applyGate(n2);
        assert q3.equals(q4);


    }

    @Test
    public void YGate() {
        ComplexDoubleMatrix bra0ket
                = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket
                = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);

        YGate z = new YGate();
        // the ZGate maps |0>  to i|1> and maps |1> to -i|0>

        Qubit q0Prime = new Qubit(q1.getState().mul(new ComplexDouble(0, 1)));
        Qubit q0Second = new Qubit(q0.getState());

        q0Second.getState().print();
        q0Second.applyGate(z);
        q0Second.getState().print();
        assert q0Prime.equals(q0Second);

        Qubit q1Prime = new Qubit(q0.getState().mul(new ComplexDouble(0, -1)));
        Qubit q1Second = new Qubit(q1.getState());
        q1Second.getState().print();
        q1Second.applyGate(z);
        q1Second.getState().print();
        assert q1Prime.equals(q1Second);

    }

    @Test
    public void ZGate() {
        ComplexDoubleMatrix bra0ket
                = new ComplexDoubleMatrix(2, 1);

        bra0ket.put(0, 0, 1);
        bra0ket.put(1, 0, 0);
        ComplexDoubleMatrix bra1ket
                = new ComplexDoubleMatrix(2, 1);

        bra1ket.put(0, 0, 0);
        bra1ket.put(1, 0, 1);

        Qubit q0 = new Qubit(bra0ket);
        Qubit q1 = new Qubit(bra1ket);


        ZGate z = new ZGate();
        // the ZGate leaves |0> unchanged and maps |1> to -|1>

        Qubit q0Prime = new Qubit(q0.getState());
        q0.applyGate(z);
        assert q0Prime.equals(q0);

        Qubit q1Prime = new Qubit(q1.getState().mul(-1));
        q1.applyGate(z);
        assert q1Prime.equals(q1);


    }

}
