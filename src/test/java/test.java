
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Edwin Jaldin S.
 */
public class test {

    public static void main(String[] args) {

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(new Date());
        //System.out.println(date);
        //Date d = new Date();
        System.out.println("fecha: " + date);
    }

}
