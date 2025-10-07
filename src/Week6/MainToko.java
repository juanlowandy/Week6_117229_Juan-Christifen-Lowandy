package Week6;

import java.util.ArrayList;
import java.util.Scanner;

public class MainToko {
    static ArrayList<Barang> daftarBarang = new ArrayList<>();
    static ArrayList<Order> daftarPesanan = new ArrayList<>();
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        int menu;
        do {
            System.out.println("-----------Menu Toko Voucher & HP-----------");
            System.out.println("1. Pesan Barang");
            System.out.println("2. Lihat Pesanan");
            System.out.println("3. Barang Baru");
            System.out.println("0. Keluar");
            System.out.print("Pilihan : ");
            menu = in.nextInt();

            switch (menu) {
                case 1: pesanBarang(); break;
                case 2: lihatPesanan(); break;
                case 3: barangBaru(); break;
                case 0: System.out.println("Terima kasih!"); break;
                default: System.out.println("Pilihan salah!");
            }
        } while (menu != 0);
    }

    static void barangBaru() {
        System.out.print("Voucher / Handphone (V/H): ");
        char jenis = in.next().toLowerCase().charAt(0);

        System.out.print("Nama : ");
        in.nextLine();
        String nama = in.nextLine();

        System.out.print("Harga : ");
        double harga = in.nextDouble();

        System.out.print("Stok : ");
        int stok = in.nextInt();

        if (jenis == 'v') {
            System.out.print("PPN : ");
            double ppn = in.nextDouble();
            daftarBarang.add(new Voucher(daftarBarang.size() + 1, nama, harga, stok, ppn));
            System.out.println("Voucher telah berhasil diinput");
        } else if (jenis == 'h') {
            System.out.print("Warna : ");
            in.nextLine();
            String warna = in.nextLine();
            daftarBarang.add(new Handphone(daftarBarang.size() + 1, nama, harga, stok, warna));
            System.out.println("Handphone telah berhasil diinput");
        } else {
            System.out.println("Input tidak valid!");
        }
    }

    static void pesanBarang() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Belum ada barang tersedia!");
            return;
        }

        System.out.println("Daftar Barang Toko Voucher & HP");
        for (Barang b : daftarBarang) {
            System.out.println("ID : " + b.getId() + " | Nama : " + b.getNama() +
                    " | Stok : " + b.getStok() + " | Harga : " + b.getHarga());
        }

        System.out.print("Ketik 0 untuk batal\nPesan Barang (ID) : ");
        int pilih = in.nextInt();
        if (pilih == 0) return;

        Barang barang = null;
        for (Barang b : daftarBarang) {
            if (b.getId() == pilih) barang = b;
        }
        if (barang == null) {
            System.out.println("Barang tidak sesuai!");
            return;
        }

        System.out.print("Masukkan Jumlah : ");
        int jumlah = in.nextInt();
        if (jumlah > barang.getStok()) {
            System.out.println("Stok tidak mencukupi jumlah pesanan");
            return;
        }

        double total = 0;
        if (barang instanceof Voucher) {
            total = ((Voucher) barang).getHargaJual() * jumlah;
        } else {
            total = barang.getHarga() * jumlah;
        }

        System.out.println(jumlah + " @ " + barang.getNama() + " dengan total harga " + total);
        System.out.print("Masukkan jumlah uang : ");
        double uang = in.nextDouble();

        if (uang < total) {
            System.out.println("Jumlah uang tidak mencukupi");
            return;
        }

        barang.minusStok(jumlah);
        if (barang instanceof Voucher) {
            daftarPesanan.add(new Order("O" + (daftarPesanan.size() + 1), (Voucher) barang, jumlah));
        } else {
            daftarPesanan.add(new Order("O" + (daftarPesanan.size() + 1), (Handphone) barang, jumlah));
        }

        System.out.println("Berhasil dipesan");
    }

    static void lihatPesanan() {
        if (daftarPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan");
            return;
        }

        System.out.println("Daftar Pesanan Toko Multiguna");
        for (Order o : daftarPesanan) {
            if (o.getHandphone() != null) {
                System.out.println("ID : " + o.getId() + " | " + o.getHandphone().getNama() +
                        " Jumlah : " + o.getJumlah() + " Total : " + (o.getHandphone().getHarga() * o.getJumlah()));
            } else if (o.getVoucher() != null) {
                System.out.println("ID : " + o.getId() + " | " + o.getVoucher().getNama() +
                        " Jumlah : " + o.getJumlah() + " Total : " + (o.getVoucher().getHargaJual() * o.getJumlah()));
            }
        }
    }
}
