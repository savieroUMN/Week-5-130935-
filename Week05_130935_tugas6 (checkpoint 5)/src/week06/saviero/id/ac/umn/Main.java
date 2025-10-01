package week06.saviero.id.ac.umn;
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
	static ArrayList<Barang> listBarang = new ArrayList<>();
    static ArrayList<Order> listOrder = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int idBarang = 1;
    static int idOrder = 1;

    public static void main(String[] args) {
        listBarang.add(new Handphone(idBarang++, "Samsung S9+ Hitam", 14499000, 10, "Hitam"));
        listBarang.add(new Handphone(idBarang++, "iPhone X Hitam", 17999000, 10, "Hitam"));
        listBarang.add(new Voucher(idBarang++, "Google Play", 20000, 100, 0.1));

        int pilih;
        do {
            System.out.println("------------Menu Toko Voucher & HP------------");
            System.out.println("1. Pesan Barang");
            System.out.println("2. Lihat Pesanan");
            System.out.println("3. Barang Baru");
            System.out.println("0. Keluar");
            System.out.print("Pilihan : ");
            pilih = sc.nextInt();
            sc.nextLine();

            switch (pilih) {
                case 1: pesanBarang(); break;
                case 2: lihatPesanan(); break;
                case 3: barangBaru(); break;
                case 0: System.out.println("Terima kasih!"); break;
                default: System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 0);
    }

    static void pesanBarang() {
        System.out.println("Daftar Barang Toko Voucher & HP");
        for (Barang b : listBarang) {
            System.out.println("ID : " + b.getId());
            System.out.println("Nama : " + b.getNama());
            System.out.println("Stock : " + b.getStok());
            System.out.printf("Harga : %,.0f\n", b.getHarga());
            if (b instanceof Handphone) {
                System.out.println("Warna : " + ((Handphone)b).getWarna());
            } else if (b instanceof Voucher) {
                System.out.println("Harga + Pajak : " + ((Voucher)b).getHargaJual());
            }
            System.out.println("-----------------------------------");
        }

        System.out.print("Pesan Barang (ID) : ");
        int id = sc.nextInt(); sc.nextLine();
        if (id == 0) return;

        Barang b = null;
        for (Barang brg : listBarang) {
            if (brg.getId() == id) {
                b = brg;
                break;
            }
        }

        if (b == null) {
            System.out.println("Barang tidak tersedia");
            return;
        }

        System.out.print("Masukkan Jumlah : ");
        int jumlah = sc.nextInt(); sc.nextLine();

        if (jumlah > b.getStok()) {
            System.out.println("Stok tidak mencukupi");
            return;
        }

        double totalHarga;
        if (b instanceof Voucher) {
            totalHarga = ((Voucher)b).getHargaJual() * jumlah;
        } else {
            totalHarga = b.getHarga() * jumlah;
        }

        System.out.printf("%d @ %s dengan total harga %,.0f\n", jumlah, b.getNama(), totalHarga);
        System.out.print("Masukkan jumlah uang : ");
        double uang = sc.nextDouble(); sc.nextLine();

        if (uang < totalHarga) {
            System.out.println("Jumlah uang tidak mencukupi");
            return;
        }

        b.minusStok(jumlah);

        if (b instanceof Handphone) {
            listOrder.add(new Order("OH" + idOrder++, (Handphone)b, jumlah));
        } else {
            listOrder.add(new Order("OV" + idOrder++, (Voucher)b, jumlah));
        }

        System.out.println("Berhasil dipesan");
	}
    
    static void lihatPesanan() {
        System.out.println("Daftar Pesanan Toko Multiguna");
        for (Order o : listOrder) {
            System.out.println("ID : " + o.getId());
            if (o.getHandphone() != null) {
                System.out.println("Nama : " + o.getHandphone().getNama());
                System.out.println("Jumlah : " + o.getJumlah());
                System.out.printf("Total : %,.0f\n", (o.getJumlah() * o.getHandphone().getHarga()));
            } else {
                System.out.println("Nama : " + o.getVoucher().getNama());
                System.out.println("Jumlah : " + o.getJumlah());
                System.out.printf("Total : %,.0f\n", (o.getJumlah() * o.getVoucher().getHargaJual()));
            }
            System.out.println("-----------------------------------");
        }
    }
    
    static void barangBaru() {
        System.out.print("Voucher / Handphone (V/H): ");
        String jenis = sc.nextLine();

        if (jenis.equalsIgnoreCase("h")) {
            System.out.print("Nama : ");
            String nama = sc.nextLine();
            System.out.print("Harga : ");
            double harga = sc.nextDouble();
            System.out.print("Stok : ");
            int stok = sc.nextInt(); sc.nextLine();
            System.out.print("Warna : ");
            String warna = sc.nextLine();

            listBarang.add(new Handphone(idBarang++, nama, harga, stok, warna));
            System.out.println("Handphone telah berhasil diinput");

        } else if (jenis.equalsIgnoreCase("v")) {
            System.out.print("Nama : ");
            String nama = sc.nextLine();
            System.out.print("Harga : ");
            double harga = sc.nextDouble();
            System.out.print("Stok : ");
            int stok = sc.nextInt();
            System.out.print("PPN : ");
            double pajak = sc.nextDouble(); sc.nextLine();

            listBarang.add(new Voucher(idBarang++, nama, harga, stok, pajak));
            System.out.println("Voucher telah berhasil diinput");
        }
    }
}
