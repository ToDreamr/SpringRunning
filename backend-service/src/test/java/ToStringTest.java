import com.pray.entity.dto.CommonDTO;

/**
 * <p>
 * ToStringTest
 * <p>
 *
 * @author 花行 (Rain)
 * @since 2025/5/31 19:28
 */
public class ToStringTest {

    public static void main(String[] args) {
        CommonDTO dto = new CommonDTO();
        dto.setId("1");
        System.out.println(dto.toString());
    }
}
