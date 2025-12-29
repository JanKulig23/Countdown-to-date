import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class Odliczanie extends JFrame {

    private JLabel labelCzas;
    // Usunąłem labelSzczegoly, bo wszystko wyświetlimy w jednym miejscu

    // Data docelowa: 30 Marca 2030, godzina 00:00
    private final LocalDateTime DATA_DOCELOWA = LocalDateTime.of(2030, Month.MARCH, 30, 0, 0);

    public Odliczanie() {
        super("Odliczanie do Emerytury");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Ustawienie głównej etykiety
        labelCzas = new JLabel("Ładowanie...", SwingConstants.CENTER);
        // Ustawiamy podstawową czcionkę (HTML i tak ją nadpisze w konkretnych miejscach)
        labelCzas.setFont(new Font("Arial", Font.BOLD, 20));
        labelCzas.setForeground(new Color(33, 33, 33));
        add(labelCzas, BorderLayout.CENTER);

        Timer timer = new Timer(1000, e -> aktualizujCzas());
        timer.start();
        aktualizujCzas();
    }

    private void aktualizujCzas() {
        LocalDateTime teraz = LocalDateTime.now();

        if (teraz.isAfter(DATA_DOCELOWA)) {
            labelCzas.setText("<html><center><h1>Data docelowa już minęła!</h1></center></html>");
            return;
        }

        // Obliczenia szczegółowe (Lata, Miesiące, Dni...)
        LocalDateTime temp = LocalDateTime.from(teraz);
        long lata = temp.until(DATA_DOCELOWA, ChronoUnit.YEARS);
        temp = temp.plusYears(lata);
        long miesiace = temp.until(DATA_DOCELOWA, ChronoUnit.MONTHS);
        temp = temp.plusMonths(miesiace);
        long dni = temp.until(DATA_DOCELOWA, ChronoUnit.DAYS);
        temp = temp.plusDays(dni);
        long godziny = temp.until(DATA_DOCELOWA, ChronoUnit.HOURS);
        temp = temp.plusHours(godziny);
        long minuty = temp.until(DATA_DOCELOWA, ChronoUnit.MINUTES);
        temp = temp.plusMinutes(minuty);
        long sekundy = temp.until(DATA_DOCELOWA, ChronoUnit.SECONDS);

        // Obliczenia łączne
        long calkowiteDni = ChronoUnit.DAYS.between(teraz, DATA_DOCELOWA);
        long calkowiteTygodnie = calkowiteDni / 7;

        // Budowanie jednego wspólnego tekstu w HTML
        // Używamy tego samego stylu (color:#0056b3) dla dolnej sekcji
        String tekstGlowny = String.format(
            "<html><center>" +
            "<span style='font-size:24px; color:#555'>Do Emerytury pozostało:</span><br><br>" +
            
            // Sekcja szczegółowa
            "<span style='color:#0056b3; font-size:36px'>%d lat, %d miesięcy, %d dni</span><br>" +
            "<span style='font-size:48px'>%02d : %02d : %02d</span><br><br>" +
            
            // Separator wizualny (opcjonalny, np. linia lub odstęp)
            "<br>" +
            
            // Sekcja zbiorcza - sformatowana tak samo jak lata/miesiące
            "<span style='font-size:18px; color:#555'>To łącznie:</span><br>" +
            "<span style='color:#0056b3; font-size:36px'>%d dni</span><br>" +
            "<span style='font-size:18px; color:#555'>(ok. %d tygodni)</span>" +
            
            "</center></html>",
            lata, miesiace, dni, godziny, minuty, sekundy, calkowiteDni, calkowiteTygodnie
        );

        labelCzas.setText(tekstGlowny);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Odliczanie().setVisible(true);
        });
    }
}