# Tugas 6: Aplikasi Cek Cuaca Sederhana

### Pembuat
- **Nama**: Muhammad Irwan Habibie
- **NPM**: 2210010461

---

## 1. Deskripsi Program
Aplikasi ini memungkinkan pengguna untuk:
- Mengambil informasi cuaca secara real-time untuk kota yang dipilih atau dimasukkan.
- Menampilkan data cuaca seperti deskripsi cuaca dan suhu untuk lokasi yang dipilih.
- Menyimpan data cuaca dalam format CSV untuk digunakan di masa mendatang.

## 2. Komponen GUI
- **JFrame**: Window utama aplikasi.
- **JPanel**: Panel untuk menampung komponen.
- **JLabel**: Label untuk menampilkan informasi cuaca.
- **JTextField**: Input untuk memasukkan nama kota.
- **JButton**: Tombol untuk memeriksa dan menyimpan cuaca.
- **JComboBox**: Dropdown untuk memilih kota dari daftar favorit.
- **JTable**: Tabel untuk menampilkan riwayat data cuaca.

## 3. Logika Program
- Menggunakan API eksternal **OpenWeatherMap** untuk mengambil data cuaca real-time.
- Menyimpan daftar kota favorit dalam file teks agar dapat dimuat kembali.
- Menyimpan data cuaca ke dalam file CSV, sehingga pengguna dapat melihat data cuaca yang tersimpan di masa lalu.

## 4. Events
Menggunakan **ActionListener** dan **ItemListener** untuk menangani interaksi pengguna:

### A. Tombol Cek Cuaca
Mengambil data cuaca berdasarkan nama kota yang dimasukkan dan menampilkan informasi cuaca pada aplikasi.

    private void tombolCekCuacaActionPerformed(java.awt.event.ActionEvent evt) {                                               
        String cityName = txtCityName.getText();
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
    }

### B. ItemListener pada JComboBox
Mengambil data cuaca untuk kota yang dipilih dari **JComboBox** dan menampilkannya di aplikasi.

    private void comboBoxCityItemStateChanged(java.awt.event.ItemEvent evt) {                                              
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedCity = (String) comboBoxCity.getSelectedItem();
            txtCityName.setText(selectedCity); // Mengisi txtCityName dengan kota yang dipilih
        }
    }  


## 5. Variasi
Aplikasi ini memiliki variasi tambahan berikut:

### A. Daftar Kota Favorit
Pengguna dapat menyimpan kota yang sering dicek sebagai daftar favorit yang dapat diakses dari **JComboBox**.

    private void tombolCekCuacaActionPerformed(java.awt.event.ActionEvent evt) {                                               
    ...
            if (!exists) {
                comboBoxCity.addItem(cityName);
                simpanKotaKeFile(); // Simpan kota ke file setelah penambahan
            }
        } else {
            JOptionPane.showMessageDialog(this, "Silakan masukkan nama kota!");
        }
    }
 
### B. Tombol untuk Menyimpan Data dari Tabel ke Dalam File CSV
Aplikasi menyimpan data cuaca dalam format CSV

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

### C. Tombol untuk Memuat data
Fitur untuk Memuat Data Cuaca yang Tersimpan dan Menampilkannya di JTable

    private void tombolMuatDataCuacaActionPerformed(java.awt.event.ActionEvent evt) {                                                    
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
    } 


## 6. Tampilan Pada Saat Aplikasi Di Jalankan
![TampilanCekCuaca](https://github.com/user-attachments/assets/169b04ca-2a5a-4d7c-acf4-aee4ab01b415)

## 6. Indikator Penilaian

| No  | Komponen          | Persentase |
| :-: | ------------------ | :--------: |
|  1  | Komponen GUI      |     10%    |
|  2  | Logika Program    |     20%    |
|  3  | Events            |     10%    |
|  4  | Kesesuaian UI     |     20%    |
|  5  | Memenuhi Variasi  |     40%    |
|     | **TOTAL**         |  **100%**  |

--- 
