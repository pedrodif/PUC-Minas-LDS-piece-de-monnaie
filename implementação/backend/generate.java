import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class generate-hash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Hash para 'senha123': " + encoder.encode("senha123"));
        System.out.println("Hash para 'pw': " + encoder.encode("pw"));
    }
}

