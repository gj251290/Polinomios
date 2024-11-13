import java.awt.Font;

import javax.swing.JLabel;

public class Polinomio {
    private Nodo cabeza;

    public Polinomio() {
        cabeza = null;
    }

    public Nodo getCabeza() {
        return cabeza;
    }

    public void agregar(Nodo n) {
        if (n != null) {
            if (cabeza == null) {
                cabeza = n;
            } else {
                Nodo apuntador = cabeza;
                Nodo predecesor = null;
                int encontrado = 0;
                while (apuntador != null && encontrado == 0) {
                    if (n.getExponente() == apuntador.getExponente()) {
                        encontrado = 1;
                    } else if (n.getExponente() > apuntador.getExponente()) {
                        encontrado = 2;
                    } else {
                        predecesor = apuntador;
                        apuntador = apuntador.siguiente;
                    }
                }
                if (encontrado == 1) {
                    double coeficiente = n.getCoeficiente() + apuntador.getCoeficiente();
                    if (coeficiente == 0) {
                        // quitar node
                        if (predecesor == null) {
                            cabeza = apuntador.siguiente;
                        } else {
                            predecesor.siguiente = apuntador.siguiente;
                        }
                    } else {
                        apuntador.setCoeficiente(coeficiente);
                    }
                } else {
                    insertar(n, predecesor);
                }
            }
        }
    }

    public void insertar(Nodo n, Nodo predecesor) {
        if (n != null) {
            if (predecesor == null) {
                n.siguiente = cabeza;
                cabeza = n;
            } else {
                n.siguiente = predecesor.siguiente;
                predecesor.siguiente = n;
            }
        }
    }

    public void limpiar() {
        cabeza = null;
    }

    public String[] getTextos() {
        String[] lineas = new String[2];
        String espacio = " ";
        Nodo apuntador = cabeza;
        lineas[0] = "";
        lineas[1] = "";
        while (apuntador != null) {
            String texto = String.valueOf(apuntador.getCoeficiente()) + " X";
            if (apuntador.getCoeficiente() >= 0) {
                texto = "+" + texto;
            }
            lineas[0] += String.format("%0" + texto.length() + "d", 0).replace("0", espacio);
            lineas[1] += texto;

            texto = String.valueOf(apuntador.getExponente());
            lineas[0] += texto;
            lineas[1] += String.format("%0" + texto.length() + "d", 0).replace("0", espacio);

            apuntador = apuntador.siguiente;
        }

        return lineas;
    }

    public void mostrar(JLabel lbl, String txt) {
        String[] lineas = getTextos();
        String espacio = "&nbsp;";
        lineas[0] = lineas[0].replace(" ", espacio);
        lineas[1] = lineas[1].replace(" ", espacio);
        lbl.setFont(new Font("Courier New", Font.PLAIN, 12));
        lbl.setText("<html>" + lineas[0] + "<br>" + lineas[1] + txt + "</html>");
    }

    public Polinomio derivar() {
        Polinomio pR = new Polinomio();
        Nodo apuntador = cabeza;
        while (apuntador != null) {
            if (apuntador.getExponente() != 0) {
                Nodo n = new Nodo(apuntador.getCoeficiente() * apuntador.getExponente(), apuntador.getExponente() - 1);
                pR.agregar(n);
            }
            apuntador = apuntador.siguiente;
        }

        return pR;
    }

    // ********** Metodos estaticos **********

    public static Polinomio sumar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null || apuntador2 != null) {
            Nodo n = null;

            if (apuntador1 != null && apuntador2 != null && apuntador1.getExponente() == apuntador2.getExponente()) {
                if (apuntador1.getCoeficiente() + apuntador2.getCoeficiente() != 0) {
                    n = new Nodo(apuntador1.getCoeficiente() + apuntador2.getCoeficiente(), apuntador1.getExponente());
                }
                apuntador1 = apuntador1.siguiente;
                apuntador2 = apuntador2.siguiente;
            } else if (apuntador2 == null
                    || (apuntador1 != null && apuntador1.getExponente() > apuntador2.getExponente())) {
                n = new Nodo(apuntador1.getCoeficiente(), apuntador1.getExponente());
                apuntador1 = apuntador1.siguiente;
            } else {
                n = new Nodo(apuntador2.getCoeficiente(), apuntador2.getExponente());
                apuntador2 = apuntador2.siguiente;
            }

            if (n != null) {
                pR.agregar(n);
            }
        }

        return pR;
    }

    public static Polinomio restar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        Nodo apuntador2 = p2.getCabeza();

        while (apuntador1 != null || apuntador2 != null) {
            Nodo n = null;

            if (apuntador1 != null && apuntador2 != null && apuntador1.getExponente() == apuntador2.getExponente()) {
                if (apuntador1.getCoeficiente() - apuntador2.getCoeficiente() != 0) {
                    n = new Nodo(apuntador1.getCoeficiente() - apuntador2.getCoeficiente(), apuntador1.getExponente());
                }
                apuntador1 = apuntador1.siguiente;
                apuntador2 = apuntador2.siguiente;
            } else if (apuntador2 == null
                    || (apuntador1 != null && apuntador1.getExponente() > apuntador2.getExponente())) {
                n = new Nodo(apuntador1.getCoeficiente(), apuntador1.getExponente());
                apuntador1 = apuntador1.siguiente;
            } else {
                n = new Nodo(-apuntador2.getCoeficiente(), apuntador2.getExponente());
                apuntador2 = apuntador2.siguiente;
            }

            if (n != null) {
                pR.agregar(n);
            }
        }

        return pR;
    }

    public static Polinomio multiplicar(Polinomio p1, Polinomio p2) {
        Polinomio pR = new Polinomio();

        Nodo apuntador1 = p1.getCabeza();
        while (apuntador1 != null) {

            Nodo apuntador2 = p2.getCabeza();
            while (apuntador2 != null) {
                Nodo n = new Nodo(apuntador1.getCoeficiente() * apuntador2.getCoeficiente(),
                        apuntador1.getExponente() + apuntador2.getExponente());
                pR.agregar(n);
                apuntador2 = apuntador2.siguiente;
            }
            apuntador1 = apuntador1.siguiente;
        }

        return pR;
    }

    private static boolean esPolinomioCero(Polinomio p) {
        Nodo actual = p.getCabeza();
        while (actual != null) {
            if (actual.getCoeficiente() != 0) {
                return false;
            }
            actual = actual.siguiente;
        }
        return true;
    }

    public static Polinomio[] dividir(Polinomio dividendo, Polinomio divisor) {

        if (esPolinomioCero(divisor)) {
            throw new ArithmeticException("División por cero: el polinomio divisor es un polinomio nulo.");
        }

        Polinomio cociente = new Polinomio();
        Polinomio resto = new Polinomio();

        Nodo nodoDividendo = dividendo.getCabeza();
        while (nodoDividendo != null) {
            resto.agregar(new Nodo(nodoDividendo.getCoeficiente(), nodoDividendo.getExponente()));
            nodoDividendo = nodoDividendo.siguiente;
        }

        Nodo nodoDivisor = divisor.getCabeza();

        while (resto.getCabeza() != null && resto.getCabeza().getExponente() >= nodoDivisor.getExponente()) {

            double coefCociente = resto.getCabeza().getCoeficiente() / nodoDivisor.getCoeficiente();
            int expCociente = resto.getCabeza().getExponente() - nodoDivisor.getExponente();
            Nodo terminoCociente = new Nodo(coefCociente, expCociente);
            cociente.agregar(terminoCociente);

            Polinomio producto = new Polinomio();
            Nodo nodoTemp = divisor.getCabeza();
            while (nodoTemp != null) {
                producto.agregar(
                        new Nodo(coefCociente * nodoTemp.getCoeficiente(), expCociente + nodoTemp.getExponente()));
                nodoTemp = nodoTemp.siguiente;
            }

            resto = Polinomio.restar(resto, producto);
        }

        return new Polinomio[] { cociente, resto };
    }

}
