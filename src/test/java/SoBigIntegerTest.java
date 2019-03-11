import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.*;

public class SoBigIntegerTest {

    @Test
    public void sum() {
        assertEquals((new SoBigInteger("1078")).toString(),
                (new SoBigInteger("878").sum(new SoBigInteger("200"))).toString());
        assertEquals((new SoBigInteger("1540907455523")).toString(),
                (new SoBigInteger("877778555524").sum(new SoBigInteger("663128899999"))).toString());
        assertEquals((new SoBigInteger("1541907422178")).toString(),
                (new SoBigInteger("1540907455523").sum(new SoBigInteger("999966655"))).toString());
        assertEquals((new SoBigInteger("1088744110766677788556")).toString(),
                (new SoBigInteger("88855555544444411112").sum(new SoBigInteger("999888555222233377444"))).toString());

    }

    @Test
    public void sub() {
        assertEquals((new SoBigInteger("3")).toString(),
                (new SoBigInteger("4").sub(new SoBigInteger("1"))).toString());
        assertEquals((new SoBigInteger("1088744107430011099679")).toString(),
                (new SoBigInteger("1088744110766677788556").sub(new SoBigInteger("3336666688877"))).toString());
        assertEquals((new SoBigInteger("1078744107430011099680")).toString(),
                (new SoBigInteger("1088744107430011099679").sub(new SoBigInteger("9999999999999999999"))).toString());
        assertEquals((new SoBigInteger("865236666999978")).toString(),
                (new SoBigInteger("8885555222244").sub(new SoBigInteger("874122222222222"))).toString());
    }

    @Test
    public void com(){
       assertEquals(0,
               new SoBigInteger("123").com(new SoBigInteger("123")));
        assertEquals(-1,
                new SoBigInteger("1272468493728927433").com(new SoBigInteger("5430384973097183679813767862846139")));
        assertEquals(1,
                new SoBigInteger("12399999999999444444444449999").com(new SoBigInteger("5413214321313413")));
        assertEquals(0,
                new SoBigInteger("100000000000000000").com(new SoBigInteger("100000000000000000")));
        assertEquals(-1,
                new SoBigInteger("1").com(new SoBigInteger("54398390183907298783")));
    }

    @Test
    public void mul() {
        assertEquals((new SoBigInteger("8623426233961964735856")).toString(),
                (new SoBigInteger("865236666999978").mul(new SoBigInteger("9966552"))).toString());
        assertEquals((new SoBigInteger("68873591653342803578790506122211115")).toString(),
                (new SoBigInteger("8855555555559999999").mul(new SoBigInteger("7777444477788885"))).toString());
        assertEquals((new SoBigInteger("5735235045483899403600735814000000")).toString(),
                (new SoBigInteger("764873469802800820").mul(new SoBigInteger("7498279482700000"))).toString());
    }

    @Test
    public void div() {
        assertEquals((new SoBigInteger("8750")).toString(),
                (new SoBigInteger("77777777777777777777777").div(new SoBigInteger("8888888888888888888"))).toString());
        assertEquals((new SoBigInteger("177")).toString(),
                (new SoBigInteger("9999999999999999").div(new SoBigInteger("56372867528908"))).toString());
        assertEquals((new SoBigInteger("100401514")).toString(),
                (new SoBigInteger("6754876248768723423").div(new SoBigInteger("67278628768"))).toString());


    }

    @Test
    public void mod() {
        assertEquals((new SoBigInteger("214234243060755758")).toString(),
                (new SoBigInteger("769789798616311313").mod(new SoBigInteger("555555555555555555"))).toString());
        assertEquals((new SoBigInteger("85555555555555555563")).toString(),
                (new SoBigInteger("885555555555555555555").mod(new SoBigInteger("88888888888888888888"))).toString());
        assertEquals((new SoBigInteger("88855552222448789273987932")).toString(),
                (new SoBigInteger("88855552222448789273987932").mod(new SoBigInteger("87412222222222276378637861873687637286823"))).toString());
    }
}