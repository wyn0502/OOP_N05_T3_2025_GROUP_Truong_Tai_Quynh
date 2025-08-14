package com.example.zoo.service;

import com.example.zoo.interfaces.AbstractManager;
import com.example.zoo.model.Chuong;
import com.example.zoo.model.DongVat;
import com.example.zoo.repository.DongVatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DongVatService extends AbstractManager<DongVat, Long>{

    @Autowired
    public DongVatRepository dongVatRepository;

    @Autowired
    private ChuongService chuongService;

    @Override
    protected JpaRepository<DongVat, Long> getRepository() {
        return dongVatRepository;
    }

    public List<DongVat> layTatCa() {
        return hienThi();
    }

    @Override
    protected Long parseId(String idStr) {
        try {
            return Long.valueOf(idStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID không hợp lệ: " + idStr, e);
        }
    }


    @Override
    public void them(DongVat obj) {
        dongVatRepository.save(obj);
        capNhatSoLuongChuong(obj.getMaChuong());
    }

    @Override
    public void sua(String id, DongVat obj) {
        try {
            Long dvId = Long.parseLong(id);
            if (dongVatRepository.existsById(dvId)) {
                dongVatRepository.save(obj);
                capNhatSoLuongChuong(obj.getMaChuong());
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID không hợp lệ");
        }
    }

    public boolean kiemTraSucChua(String maChuong, Long dongVatId) {
        Chuong chuong = chuongService.timTheoMa(maChuong);
        if (chuong == null) {
            return false;
        }
        long soDongVatHienTai = demDongVatTheoMaChuong(maChuong);

        if (dongVatId != null) {
            DongVat dvCu = timTheoId(dongVatId);
            if (dvCu != null && maChuong.equals(dvCu.getMaChuong())) {
                soDongVatHienTai--;
            }
        }
        return soDongVatHienTai < chuong.getSucChuaToiDa();
    }

    public void luuHoacCapNhat(DongVat dv) {
        String maChuongCu = null;
        if (dv.getId() != null) {
            DongVat dvCu = timTheoId(dv.getId());
            if (dvCu != null) {
                maChuongCu = dvCu.getMaChuong();
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
    }

    public DongVat timTheoId(Long id) {
        Optional<DongVat> optional = dongVatRepository.findById(id);
        return optional.orElse(null);
    }

    public void xoaTheoId(Long id) {
        DongVat dv = timTheoId(id);
        String maChuong = (dv != null) ? dv.getMaChuong() : null;
        dongVatRepository.deleteById(id);
        if (maChuong != null) {
            capNhatSoLuongChuong(maChuong);
        }
    }

    public long demDongVatTheoMaChuong(String maChuong) {
        return dongVatRepository.countByMaChuong(maChuong);
    }

    public List<DongVat> layDongVatTheoMaChuong(String maChuong) {
        return dongVatRepository.findByMaChuong(maChuong);
    }

    public List<String> layDanhSachChuongCoChoTrong() {
        return chuongService.layChuongCoChoTrong().stream()
                .map(Chuong::getMaChuong)
                .toList();
    }

    private void capNhatSoLuongChuong(String maChuong) {
        int soLuongMoi = (int) demDongVatTheoMaChuong(maChuong);
        chuongService.capNhatSoLuongDongVat(maChuong, soLuongMoi);
    }
}
