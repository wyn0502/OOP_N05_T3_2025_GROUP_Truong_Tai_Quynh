package com.example.zoo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public abstract class AbstractManager<T, ID> implements IManager<T> {
    protected abstract JpaRepository<T, ID> getRepository();
    protected abstract ID parseId(String idStr);
    @Override
    public void them(T obj) {
        getRepository().save(obj);
    }
    @Override
    public List<T> hienThi() {
        return getRepository().findAll();
    }
    @Override
    public void sua(String id, T obj) {
        ID typedId = parseId(id);
        if (getRepository().existsById(typedId)) {
            getRepository().save(obj);
        } else {
            throw new IllegalArgumentException("Không tồn tại bản ghi với ID: " + id);
        }
    }
    @Override
    public void xoa(String id) {
        ID typedId = parseId(id);
        if (getRepository().existsById(typedId)) {
            getRepository().deleteById(typedId);
        } else {
            throw new IllegalArgumentException("Không tồn tại bản ghi với ID: " + id);
        }
    }
}
