package com.example.zoo.service;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.Chuong;
import com.example.zoo.repository.DongVatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DongVatService {

    @Autowired
    public DongVatRepository dongVatRepository;

    @Autowired
    private ChuongService chuongService;

    public List<DongVat> layTatCa() {
        try {
            List<DongVat> result = dongVatRepository.findAll();
            System.out.println("Số động vật tìm thấy: " + result.size());
            return result;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean kiemTraSucChua(String maChuong, Long dongVatId) {
        try {
            Chuong chuong = chuongService.timTheoMa(maChuong);
            if (chuong == null) {
                return false;
            }

            long soDongVatHienTai = demDongVatTheoMaChuong(maChuong);

            if (dongVatId != null) {
                DongVat dongVatCu = timTheoId(dongVatId);
                if (dongVatCu != null && maChuong.equals(dongVatCu.getMaChuong())) {
                    soDongVatHienTai--;
                }
            }

            boolean coChoTrong = soDongVatHienTai < chuong.getSucChuaToiDa();
            System.out.println("Kiểm tra chuồng " + maChuong
                    + " - Hiện tại: " + soDongVatHienTai
                    + ", Tối đa: " + chuong.getSucChuaToiDa()
                    + ", Có chỗ: " + coChoTrong);

            return coChoTrong;
        } catch (Exception e) {
            System.err.println("Lỗi kiểm tra sức chứa: " + e.getMessage());
            return false;
        }
    }

    public void luuHoacCapNhat(DongVat dv) {
        try {
            String maChuongCu = null;
            if (dv.getId() != null) {
                DongVat dongVatCu = timTheoId(dv.getId());
                if (dongVatCu != null) {
                    maChuongCu = dongVatCu.getMaChuong();
                }
            }

            if (maChuongCu == null || !maChuongCu.equals(dv.getMaChuong())) {
                if (!kiemTraSucChua(dv.getMaChuong(), dv.getId())) {
                    Chuong chuong = chuongService.timTheoMa(dv.getMaChuong());
                    long soDongVatHienTai = demDongVatTheoMaChuong(dv.getMaChuong());
                    throw new RuntimeException(String.format(
                            "Chuồng %s đã đầy! Sức chứa tối đa: %d, hiện tại: %d động vật.",
                            dv.getMaChuong(),
                            chuong != null ? chuong.getSucChuaToiDa() : 0,
                            soDongVatHienTai));
                }
            }

            dongVatRepository.save(dv);

            if (maChuongCu != null && !maChuongCu.equals(dv.getMaChuong())) {
                capNhatSoLuongChuong(maChuongCu);
            }

            capNhatSoLuongChuong(dv.getMaChuong());

            System.out.println("Đã lưu động vật: " + dv.getTen() + " vào chuồng: " + dv.getMaChuong());
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu động vật: " + e.getMessage());
            throw e instanceof RuntimeException ? (RuntimeException) e
                    : new RuntimeException("Không thể lưu động vật.", e);
        }
    }

    public DongVat timTheoId(Long id) {
        try {
            Optional<DongVat> optional = dongVatRepository.findById(id);
            return optional.orElse(null);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm động vật: " + e.getMessage());
            return null;
        }
    }

    public void xoaTheoId(Long id) {
        try {
            DongVat dongVat = timTheoId(id);
            String maChuong = null;
            if (dongVat != null) {
                maChuong = dongVat.getMaChuong();
            }

            dongVatRepository.deleteById(id);

            if (maChuong != null) {
                capNhatSoLuongChuong(maChuong);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa động vật: " + e.getMessage());
            throw new RuntimeException("Xóa động vật thất bại.", e);
        }
    }

    public long demDongVatTheoMaChuong(String maChuong) {
        try {
            long count = dongVatRepository.countByMaChuong(maChuong);
            System.out.println("Số động vật trong chuồng " + maChuong + ": " + count);
            return count;
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm động vật: " + e.getMessage());
            return 0;
        }
    }

    public List<DongVat> layDongVatTheoMaChuong(String maChuong) {
        try {
            List<DongVat> result = dongVatRepository.findByMaChuong(maChuong);
            System.out.println("Tìm thấy " + result.size() + " động vật trong chuồng " + maChuong);
            for (DongVat dv : result) {
                System.out.println("  - " + dv.getTen() + " (ID: " + dv.getId() + ")");
            }
            return result;
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy động vật theo chuồng: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void capNhatSoLuongChuong(String maChuong) {
        if (maChuong != null) {
            int soLuongMoi = (int) demDongVatTheoMaChuong(maChuong);
            chuongService.capNhatSoLuongDongVat(maChuong, soLuongMoi);
        }
    }

    public List<String> layDanhSachChuongCoChoTrong() {
        try {
            return chuongService.layChuongCoChoTrong().stream()
                    .map(Chuong::getMaChuong)
                    .collect(java.util.stream.Collectors.toList());
        } catch (Exception e) {
            System.err.println("Lỗi lấy chuồng có chỗ trống: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<DongVat> timTheoLoai(String loai) {
        try {
            return dongVatRepository.findByLoai(loai);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm theo loài: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<DongVat> timTheoSucKhoe(String sucKhoe) {
        try {
            return dongVatRepository.findBySucKhoe(sucKhoe);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm theo sức khỏe: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<DongVat> timTheoKhoangTuoi(double minTuoi, double maxTuoi) {
        try {
            return dongVatRepository.findByTuoiBetween(minTuoi, maxTuoi);
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm theo khoảng tuổi: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public long demTongSoDongVat() {
        try {
            return dongVatRepository.count();
        } catch (Exception e) {
            System.err.println("Lỗi khi đếm tổng động vật: " + e.getMessage());
            return 0;
        }
    }

    public DongVat timTheoTen(String ten) {
        try {
            if (ten == null || ten.trim().isEmpty()) {
                return null;
            }
            return dongVatRepository.findByTen(ten.trim());
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm động vật theo tên: " + e.getMessage());
            return null;
        }
    }

    public List<DongVat> timTheoTenGanDung(String ten) {
        try {
            if (ten == null || ten.trim().isEmpty()) {
                return Collections.emptyList();
            }
            return dongVatRepository.findByTenContainingIgnoreCase(ten.trim());
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm động vật theo tên gần đúng: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
