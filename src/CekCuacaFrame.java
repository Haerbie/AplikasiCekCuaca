import java.awt.event.ItemEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONObject;

public class CekCuacaFrame extends javax.swing.JFrame {
    private static final String API_KEY = "8d185afaf5ea82e8a7ecec8ec832470c"; // Hanya kunci API saja
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String CITY_FILE_PATH = "data/cities.txt";
    private static final String WEATHER_FILE_PATH = "data/weather_data.csv";
    private DefaultTableModel tableModel;

    
    public String dapatkanDataCuaca(String cityName) {
        String response = "";
        try {
            // Buat URL lengkap dengan memasukkan cityName, API_KEY, dan bahasa (lang=id)
            String urlString = BASE_URL + "?q=" + cityName + "&appid=" + API_KEY + "&units=metric&lang=id";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public void tampilkanCuaca(String cityName) {
        // Dapatkan data cuaca dalam bentuk JSON
        String jsonData = dapatkanDataCuaca(cityName);
        JSONObject jsonObjek = new JSONObject(jsonData);

        // Ambil deskripsi cuaca dan suhu dari JSON
        String deskripsiCuaca = jsonObjek.getJSONArray("weather").getJSONObject(0).getString("description");
        String kodeIkon = jsonObjek.getJSONArray("weather").getJSONObject(0).getString("icon"); // Point 1: Dapatkan kode ikon cuaca
        double suhu = jsonObjek.getJSONObject("main").getDouble("temp");

        // Set teks deskripsi cuaca dan suhu ke JLabel
        labelDeskripsiCuaca.setText(deskripsiCuaca);
        labelSuhu.setText(suhu + "°C");

        // Buat URL untuk ikon cuaca berdasarkan kode ikon
        String urlIkon = "http://openweathermap.org/img/wn/" + kodeIkon + "@2x.png";

        try {
            // Load ikon dari URL dan set ke JLabel
            ImageIcon ikonCuaca = new ImageIcon(new URL(urlIkon));
            labelIkonCuaca.setIcon(ikonCuaca);
        } catch (Exception e) {
            e.printStackTrace();
            labelIkonCuaca.setText("Gagal memuat ikon cuaca");
        }
    }
    
    private void simpanKotaKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cities.txt"))) {
            for (int i = 0; i < comboBoxCity.getItemCount(); i++) {
                writer.write(comboBoxCity.getItemAt(i));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void muatKotaDariFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cities.txt"))) {
            String city;
            while ((city = reader.readLine()) != null) {
                comboBoxCity.addItem(city);
            }
        } catch (IOException e) {
            // Jika file tidak ditemukan, abaikan saja
            System.out.println("No saved cities found.");
        }
    }
    
    private void simpanDataCuacaKeFile(String cityName, String weatherDescription, double temperature) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("weather_data.csv", true))) {
            // Menuliskan data dalam format CSV
            writer.write(cityName + "," + weatherDescription + "," + temperature + "°C");
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Data cuaca berhasil disimpan!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data cuaca.");
            e.printStackTrace();
        }
    }

    /**
     * Creates new form CekCuacaFrame
     */
    public CekCuacaFrame() {
        initComponents();
        System.out.println("C:\\Users\\USER\\Documents\\NetBeansProjects\\AplikasiCekCuaca\\Simpan " + System.getProperty("user.dir"));
        muatKotaDariFile();
        
        // Inisialisasi model tabel
        tableModel = new DefaultTableModel(new String[]{"Nama Kota", "Cuaca", "Suhu"}, 0);
        tabelCuaca.setModel(tableModel);
    }

    public void itemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboBoxCity.getSelectedItem();
            tampilkanCuaca(selectedCity);  // Panggil metode untuk menampilkan cuaca kota yang dipilih
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tombolCekCuaca = new javax.swing.JButton();
        comboBoxCity = new javax.swing.JComboBox<>();
        labelDeskripsiCuaca = new javax.swing.JLabel();
        labelSuhu = new javax.swing.JLabel();
        namaKota = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelCuaca = new javax.swing.JTable();
        tombolMuatDataCuaca = new javax.swing.JButton();
        labelIkonCuaca = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tugas 6", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Aplikasi Cek Cuaca");
        jPanel1.add(jLabel1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(204, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Informasi Cuaca");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Cari Nama Kota");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(jLabel3, gridBagConstraints);

        tombolCekCuaca.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tombolCekCuaca.setText("Cek Cuaca");
        tombolCekCuaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolCekCuacaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(tombolCekCuaca, gridBagConstraints);

        comboBoxCity.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxCityItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(comboBoxCity, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(labelDeskripsiCuaca, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(labelSuhu, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(namaKota, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Cuaca            :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Suhu              :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Pilih Kota");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(jLabel6, gridBagConstraints);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Simpan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(jButton2, gridBagConstraints);

        tabelCuaca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelCuaca.setPreferredSize(new java.awt.Dimension(500, 300));
        jScrollPane1.setViewportView(tabelCuaca);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        tombolMuatDataCuaca.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tombolMuatDataCuaca.setText("Muat Data Cuaca");
        tombolMuatDataCuaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombolMuatDataCuacaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(tombolMuatDataCuaca, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
        jPanel2.add(labelIkonCuaca, gridBagConstraints);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tombolCekCuacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolCekCuacaActionPerformed
        String cityName = namaKota.getText();
        if (!cityName.isEmpty()) {
            tampilkanCuaca(cityName);

            boolean exists = false;
            for (int i = 0; i < comboBoxCity.getItemCount(); i++) {
                if (comboBoxCity.getItemAt(i).equalsIgnoreCase(cityName)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                comboBoxCity.addItem(cityName);
                simpanKotaKeFile(); // Simpan kota ke file setelah penambahan
            }
        } else {
            JOptionPane.showMessageDialog(this, "Silakan masukkan nama kota!");
        }
    }//GEN-LAST:event_tombolCekCuacaActionPerformed

    private void comboBoxCityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxCityItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboBoxCity.getSelectedItem();
            namaKota.setText(selectedCity); // Mengisi txtCityName dengan kota yang dipilih
        }
    }//GEN-LAST:event_comboBoxCityItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String cityName = namaKota.getText();
        String weatherDescription = labelDeskripsiCuaca.getText();
        String temperature = labelSuhu.getText();

        if (!cityName.isEmpty() && !weatherDescription.isEmpty() && !temperature.isEmpty()) {
            simpanDataCuacaKeFile(cityName, weatherDescription, Double.parseDouble(temperature.replace("°C", "")));
        } else {
            JOptionPane.showMessageDialog(this, "Data cuaca tidak lengkap. Pastikan semua data sudah terisi.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tombolMuatDataCuacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombolMuatDataCuacaActionPerformed
        tableModel.setRowCount(0); // Hapus data lama di tabel sebelum memuat data baru

        try (BufferedReader reader = new BufferedReader(new FileReader("weather_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    // Menambahkan data ke dalam tabel
                    tableModel.addRow(new Object[]{data[0], data[1], data[2]});
                }
            }
            JOptionPane.showMessageDialog(this, "Data cuaca berhasil dimuat ke tabel.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data cuaca.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_tombolMuatDataCuacaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CekCuacaFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CekCuacaFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBoxCity;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelDeskripsiCuaca;
    private javax.swing.JLabel labelIkonCuaca;
    private javax.swing.JLabel labelSuhu;
    private javax.swing.JTextField namaKota;
    private javax.swing.JTable tabelCuaca;
    private javax.swing.JButton tombolCekCuaca;
    private javax.swing.JButton tombolMuatDataCuaca;
    // End of variables declaration//GEN-END:variables
}
